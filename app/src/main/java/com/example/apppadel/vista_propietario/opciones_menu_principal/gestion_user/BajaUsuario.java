package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;
import com.example.apppadel.models.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BajaUsuario extends AppCompatActivity {
    ListView listaUsuarios;
    ArrayList<Usuario> lista;
    ArrayAdapter<Usuario> adapter;
    EditText nombreUser;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baja_usuario);

        nombreUser = findViewById(R.id.etNombreUserBaja);
        listaUsuarios = findViewById(R.id.listaUsuariosBaja);

        lista = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listaUsuarios.setAdapter(adapter);

        //Inicializo Firestore, y rellenar lista de usuarios.
        db = FirebaseFirestore.getInstance();

        // Metodo para listar usuarios.
        listarUsuarios();

        nombreUser.addTextChangedListener(new TextWatcher() {
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
                // Recupera al Usuario seleccionado
                Usuario selectedUser = adapter.getItem(position);

                new AlertDialog.Builder(BajaUsuario.this)
                        .setTitle("Confirmación")
                        .setMessage("¿Está seguro de que desea eliminar al Usuario seleccionado?,\n- Usuario seleccionado: " + selectedUser)
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Lo elimino de la Base de Datos
                                db.collection("usuarios").document(selectedUser.getiDUser()).delete()
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()){
                                                // Elimina de la lista original
                                                lista.remove(selectedUser);
                                                adapter.notifyDataSetChanged();

                                                Toast.makeText(BajaUsuario.this, "Usuario: " + selectedUser + ", dado de Baja de la Base de Datos", Toast.LENGTH_SHORT).show();
                                                finish();

                                            } else {
                                                Toast.makeText(BajaUsuario.this, "Error a la hora de dar de baja al usuario seleccionado", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                nombreUser.setText("");
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });
    }

    private void listarUsuarios() {
        db.collection("usuarios")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        lista.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            String userName = document.getString("nombre");

                            // Si encuentra al admin, este no lo añade a la lista de Usuarios, para no mostrarlo
                            if (!id.equals("NBWFk4ARbGNelIC2F6wCj18k2Pw1")) {
                                lista.add(new Usuario(id, userName));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.w("TAG", "Error getting documents.", task.getException());
                    }
                });
    }
}