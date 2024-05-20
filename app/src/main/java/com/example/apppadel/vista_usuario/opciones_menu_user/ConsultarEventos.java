package com.example.apppadel.vista_usuario.opciones_menu_user;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apppadel.R;
import com.example.apppadel.models.Torneo;
import com.example.apppadel.models.Usuario;
import com.example.apppadel.vista_propietario.opciones_menu_principal.GestionarTorneos;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConsultarEventos extends AppCompatActivity {
    ListView listaEventos;
    TextView numTorneos;
    ArrayList<Torneo> lista;
    ArrayAdapter<Torneo> adapter;
    EditText nombreEvento;
    ArrayList<Usuario> nombresGanadores;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_eventos);

        db = FirebaseFirestore.getInstance();

        listaEventos = findViewById(R.id.listaEventos);
        nombreEvento = findViewById(R.id.etNombreEvento);
        numTorneos = findViewById(R.id.tvTotalListaTorneos);

        nombresGanadores = new ArrayList<>();

        listarTorneos();

        nombreEvento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listaEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Torneo torneo = adapter.getItem(position);

                obtenerNombreUsuario(torneo.getGanadorUno(), torneo.getGanadorDos(), errorMessage -> {
                    String nombresGanadoresStr;
                    if (errorMessage != null) {
                        nombresGanadoresStr = errorMessage;
                    } else {
                        nombresGanadoresStr = nombresGanadores.stream()
                                .map(Usuario::getNombreCompleto) // Devuelve el nombre y el primer apellido del Usuario
                                .collect(Collectors.joining("\n"));
                    }

                    AlertDialog.Builder alerta = new AlertDialog.Builder(ConsultarEventos.this);
                    alerta.setTitle("INFORMACIÓN DEL TORNEO SELECCIONADO.");
                    alerta.setMessage("*Datos del Torneo*\n" +
                            "- Nombre: " + torneo.getNombreTorneo() +
                            "\n- Fecha Inicio: " + torneo.getFechaInicioTorneo() +
                            "\n- Fecha Fin: " + torneo.getFechaFinTorneo() +
                            "\n- Acabado: " + comprobarFechaTorneo(torneo.getFechaFinTorneo()) +
                            "\n- Ganadores:\n" + nombresGanadoresStr);
                    alerta.setPositiveButton("Volver", null);
                    alerta.create();
                    alerta.show();
                });
            }
        });
    }

    private void listarTorneos() {
        lista = new ArrayList<>();

        db.collection("torneos")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        lista.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String id = document.getId();
                            String nombreTor = document.getString("nombre");
                            String inicio = document.getString("fecha_inicio");
                            String fin = document.getString("fecha_fin");
                            String ganadorUno = document.getString("ganador_uno");
                            String ganadorDos = document.getString("ganador_dos");

                            lista.add(new Torneo(id, nombreTor, inicio, fin, ganadorUno, ganadorDos));
                        }

                        // Cargar de nuevo la lista de torneos
                        adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, lista);
                        listaEventos.setAdapter(adapter);
                        numTorneos.append(lista.size() + ""); // Conocer el número total de Torneos que se han creado en el club.

                    } else {
                        Toast.makeText(this, "Error para listar los torneos que no poseen ganadores", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String comprobarFechaTorneo(String fechaFinTorneo){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        try {
            LocalDate date = LocalDate.parse(fechaFinTorneo, formatter);
            LocalDate today = LocalDate.now();
            if (date.isBefore(today)){
                return "SI";
            } else {
                return "NO";
            }

        } catch (DateTimeParseException e) {
            System.err.println("Formato de fecha inválido: " + e.getMessage());
            return "FALLO EN LECTURA";
        }
    }

    public interface OnNombresCargadosListener {
        // Creacion de una interfaz para manejar los eventos asincronos de Firebase. (Problemas al mostrar ganadores del torneo)
        void onNombresCargados(String errorMessage);
    }

    private void obtenerNombreUsuario(String primerWinner, String segundoWinner, OnNombresCargadosListener listener) {
        nombresGanadores = new ArrayList<>();

        // Comprobar que los IDs no esten vacios
        if ((primerWinner == null || primerWinner.isEmpty()) && (segundoWinner == null || segundoWinner.isEmpty())) {

            if (listener != null) { // No hay ganadores seleccionados
                listener.onNombresCargados("No hay ganadores seleccionados");
            }
            return;
        }

        // Se rellenan los ids de los ganadores en una lista
        List<String> ganadoresIds = new ArrayList<>();
        if (primerWinner != null && !primerWinner.isEmpty()) {
            ganadoresIds.add(primerWinner);
        }
        if (segundoWinner != null && !segundoWinner.isEmpty()) {
            ganadoresIds.add(segundoWinner);
        }

        db.collection("usuarios")
                .whereIn(FieldPath.documentId(), ganadoresIds)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        nombresGanadores.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            String userName = document.getString("nombre");
                            String surName = document.getString("primer_apellido");
                            nombresGanadores.add(new Usuario(id, userName, surName));
                        }
                        if (listener != null) {
                            listener.onNombresCargados(null);
                        }
                    } else {
                        Log.w("TAG", "Error getting documents.", task.getException());
                        if (listener != null) {
                            listener.onNombresCargados("Error al recuperar los datos de los ganadores");
                        }
                    }
                });
    }


}