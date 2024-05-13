package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;
import com.example.apppadel.models.Usuario;
import com.example.apppadel.vista_propietario.opciones_menu_principal.GestionarTorneos;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class EditarUsuario extends AppCompatActivity {
    ListView listaUsuarios;
    ArrayAdapter<Usuario> adapter;
    ArrayList<Usuario> list;
    EditText nomBuscar;
    FirebaseFirestore db;
    ActivityResultLauncher lanzador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        nomBuscar =  findViewById(R.id.etNomUserEdit);
        listaUsuarios = findViewById(R.id.listaUsuariosEditar);
        list = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listaUsuarios.setAdapter(adapter);

        //Inicializo Firestore, y rellenar lista de usuarios.
        db = FirebaseFirestore.getInstance();

        listarUsuariosEditar();

        listaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario selectedUser = adapter.getItem(position);

                new AlertDialog.Builder(EditarUsuario.this)
                        .setTitle("Confirmación")
                        .setMessage("¿Está seguro de que desea Editar al Usuario: " + selectedUser.getNombreUser() + "?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(EditarUsuario.this, VistaFormularioEdicion.class);
                                //Aqui se le pasara toda la informacion del usuario seleccionado de la lista.
                                intent.putExtra("COMPLETE_USER", selectedUser);
                                lanzador.launch(intent);
                                nomBuscar.setText("");

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }

        });

        lanzador = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {

                if (o.getResultCode() == RESULT_OK && o.getData() != null) {
                    Intent data = o.getData();
                    String respuesta = data.getStringExtra("RESPUESTA");

                    if (respuesta.equals("OK")){
                        //Vuelvo a cargar los datos de la lista con los datos actualizadops de Firestore.
                        list = new ArrayList<>();
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1, list);
                        listaUsuarios.setAdapter(adapter);

                        listarUsuariosEditar();

                    } else {
                        Toast.makeText(EditarUsuario.this, "Fallo al devolver los datos actualizados", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(EditarUsuario.this, "Operación cancelada o no completada", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nomBuscar.addTextChangedListener(new TextWatcher() {
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

    }

    private void listarUsuariosEditar() {
        db.collection("usuarios")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        list.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String id = document.getId();
                            String userName = document.getString("nombre") != null ? document.getString("nombre") : "";
                            String surName = document.getString("primer_apellido") != null ? document.getString("primer_apellido") : "";
                            String secondSurName = document.getString("segundo_apellido") != null ? document.getString("segundo_apellido") : "";
                            String phone = document.getString("telefono") != null ? document.getString("telefono") : "";
                            String dateBorn = document.getString("fecha_nacimiento") != null ? document.getString("fecha_nacimiento") : "";
                            String mail = document.getString("correo") != null ? document.getString("correo") : "";
                            String pass = document.getString("contra") != null ? document.getString("contra") : "";
                            String rol = document.getString("rol") != null ? document.getString("rol") : "";

                            // Si encuentra al admin, este no lo añade a la lista de Usuarios, para no mostrarlo
                            if (!id.equals("NBWFk4ARbGNelIC2F6wCj18k2Pw1")) {
                                list.add(new Usuario(id, userName, surName, secondSurName, phone, dateBorn, mail, pass, rol));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.w("TAG", "Error getting documents.", task.getException());
                    }
                });
    }
}