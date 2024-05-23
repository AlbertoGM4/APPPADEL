package com.example.apppadel.vista_usuario.opciones_menu_socio;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;
import com.example.apppadel.models.Usuario;
import com.example.apppadel.vista_usuario.opciones_menu_user.ConsultarTienda;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class ConsultarRanking extends AppCompatActivity {
    TextView numeroSocios;
    ListView listaRanking;
    ArrayList<Usuario> listaSocios;
    ArrayAdapter<Usuario> adapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_ranking);

        db = FirebaseFirestore.getInstance();
        listaRanking = findViewById(R.id.listaRanking);
        numeroSocios = findViewById(R.id.tvTotalSocios);
        listarSocios();

        listaRanking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario socio = adapter.getItem(position);

                AlertDialog.Builder alerta = new AlertDialog.Builder(ConsultarRanking.this);
                alerta.setTitle("INFORMACIÓN DEL SOCIO");
                alerta.setMessage(
                        "- Puntos de Socio: " + socio.getPuntosSocio() + "\n" +
                        "- Nombre: " + socio.getNombreUser() + "\n" +
                        "- Apellidos: " + socio.getPrimerApellido() + " " + socio.getSegundoApellido() + "\n" +
                        "- Correo: " + socio.getCorreoElectronico() + "\n" +
                        "- Fecha de nacimiento: " + socio.getFechaNacUser() + "\n" +
                        "- Teléfono: " + socio.getTelefonoUser() + "\n");

                alerta.setPositiveButton("Volver", null);
                alerta.create();
                alerta.show();
            }
        });

    }

    private void listarSocios() {
        listaSocios = new ArrayList<>();

        db.collection("usuarios")
                .whereEqualTo("rol", "socio")
                .orderBy("puntos", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    listaSocios.clear();
                    if (task.isSuccessful()){
                        for (DocumentSnapshot document : task.getResult()){
                            String id = document.getId();
                            String userName = document.getString("nombre");
                            String surName = document.getString("primer_apellido");
                            String secondSurName = document.getString("segundo_apellido");
                            String mail = document.getString("correo");
                            long puntos = document.getLong("puntos");
                            String phone = document.getString("telefono");
                            String fechaNac = document.getString("fecha_nacimiento");

                            listaSocios.add(new Usuario(id, userName, surName, secondSurName, mail, puntos, phone, fechaNac));
                        }
                        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaSocios);
                        listaRanking.setAdapter(adapter);
                        numeroSocios.append(listaSocios.size() + ""); // Número de Socios del Club

                    } else {
                        Toast.makeText(this, "Fallo a la hora de hacer la consulta en la Base de Datos", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}