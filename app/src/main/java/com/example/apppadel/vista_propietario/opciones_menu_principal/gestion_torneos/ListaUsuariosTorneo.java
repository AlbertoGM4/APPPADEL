package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_torneos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;
import com.example.apppadel.models.Torneo;
import com.example.apppadel.models.Usuario;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user.BajaUsuario;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ListaUsuariosTorneo extends AppCompatActivity {
    ListView listaUsuarios;
    ArrayList<Usuario> lista;
    ArrayAdapter<Usuario> adapter;
    EditText nombreGanador;
    String idGanadorUno, idGanadorDos;
    FirebaseFirestore db;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios_torneo);

        db = FirebaseFirestore.getInstance();

        Intent i = getIntent();
        String numIntegrante = i.getStringExtra("BOTON_PULSADO"); // Si es el primer o segundo ganador.
        idGanadorUno = i.getStringExtra("GANADOR_UNO");
        idGanadorDos = i.getStringExtra("GANADOR_DOS");

        nombreGanador = findViewById(R.id.etNombreUserSeleccionGanadores);
        listaUsuarios = findViewById(R.id.listaUsuariosSeleccionGanadores);

        listarListaUsuarios();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listaUsuarios.setAdapter(adapter);

        nombreGanador.addTextChangedListener(new TextWatcher() {
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

        listaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                usuario = adapter.getItem(position);

                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaUsuariosTorneo.this);
                alerta.setTitle("CONFIRMACIÓN");
                alerta.setMessage("¿Seguro que desea seleccionar a este Usuario?\n" +
                        "- ID Usuario: " + usuario.getiDUser() +"\n" +
                        "- Nombre: " + usuario.getNombreUser() + " " + usuario.getPrimerApellido());
                alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i;
                        if (numIntegrante.equals("INTEGRANTE_UNO")){
                            i = new Intent();
                            i.putExtra("ID", usuario.getiDUser());
                            i.putExtra("NOMBRE", usuario.toString());

                            Log.i("xxx", "id: " + usuario.getiDUser() +
                                    ", nombre: " + usuario.getNombreUser() +
                                    ", apellido: " + usuario.getPrimerApellido());
                            setResult(1, i);

                        } else if (numIntegrante.equals("INTEGRANTE_DOS")) {
                            i = new Intent();
                            i.putExtra("ID", usuario.getiDUser());
                            i.putExtra("NOMBRE", usuario.toString());

                            Log.i("xxx", "id2: " + usuario.getiDUser() +
                                    ", nombre2: " + usuario.getNombreUser() +
                                    ", apellido2: " + usuario.getPrimerApellido());
                            setResult(2, i);
                        }
                        finish();
                    }
                });
                alerta.create();
                alerta.show();
            }
        });

    }

    private void listarListaUsuarios() {
        lista = new ArrayList<>();

        db.collection("usuarios")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        lista.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String id = document.getId();
                            String userName = document.getString("nombre");
                            String surName = document.getString("primer_apellido");
                            String correo = document.getString("correo");

                            // Si encuentra al admin, este no lo añade a la lista de Usuarios, para no mostrarlo
                            if (!id.equals("NBWFk4ARbGNelIC2F6wCj18k2Pw1") && !id.equals(idGanadorUno) && !id.equals(idGanadorDos)) {
                                lista.add(new Usuario(id, userName, surName, correo));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.w("TAG", "Error getting documents.", task.getException());
                    }
                });
    }
}