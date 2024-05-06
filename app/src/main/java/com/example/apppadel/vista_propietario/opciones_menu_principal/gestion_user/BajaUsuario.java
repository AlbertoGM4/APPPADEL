package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.ArrayList;

public class BajaUsuario extends AppCompatActivity {
    ListView listaUsuarios;
    ArrayList<String> lista;
    ArrayAdapter<String> adapter;
    EditText nombreUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baja_usuario);

        nombreUser = findViewById(R.id.etNombreUserBaja);
        listaUsuarios = findViewById(R.id.listaUsuariosBaja);

        lista = new ArrayList<>();
        // Agregar usuarios a la lista
        lista.add("Alberto Guillen");
        lista.add("Ivan Fernandez");
        lista.add("Victor Barrio");
        lista.add("Usuario 4");
        lista.add("Usuario 5");
        lista.add("Usuario 6");
        lista.add("Usuario 7");
        lista.add("Usuario 8");
        lista.add("Usuario 9");
        lista.add("Usuario 10");
        lista.add("Usuario 11");
        lista.add("Usuario 12");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listaUsuarios.setAdapter(adapter);

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
                final String selectedUser = adapter.getItem(position); // Consigue el usuario seleccionado del adaptador

                new AlertDialog.Builder(BajaUsuario.this)
                        .setTitle("Confirmación")
                        .setMessage("¿Está seguro de que desea eliminar al Usuario: " + selectedUser + "?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Elimina de la lista original
                                lista.remove(selectedUser);
                                // Notificar al adaptador para que refresque la lista filtrada
                                adapter.remove(selectedUser);
                                adapter.notifyDataSetChanged(); // Notifica al adaptador del cambio

                                Toast.makeText(BajaUsuario.this, "Usuario " + selectedUser + " eliminado", Toast.LENGTH_SHORT).show();
                                nombreUser.setText("");
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }
}