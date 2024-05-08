package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user;

import android.content.DialogInterface;
import android.content.Intent;
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

public class EditarUsuario extends AppCompatActivity {
    ListView listaUsuarios;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    EditText nomBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        nomBuscar =  findViewById(R.id.etNomUserEdit);
        listaUsuarios = findViewById(R.id.listaUsuariosEditar);
        list = new ArrayList<>();

        list.add("Alberto Guillen");
        list.add("Ivan Fernandez");
        list.add("Victor Barrio");
        list.add("alberto");
        list.add("Usuario 5");
        list.add("Usuario 6");
        list.add("Usuario 7");
        list.add("Usuario 8");
        list.add("Usuario 9");
        list.add("Usuario 10");
        list.add("Usuario 11");
        list.add("Usuario 12");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listaUsuarios.setAdapter(adapter);

        listaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nombre = adapter.getItem(position);

                new AlertDialog.Builder(EditarUsuario.this)
                        .setTitle("Confirmación")
                        .setMessage("¿Está seguro de que desea Editar al Usuario: " + nombre + "?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(EditarUsuario.this, VistaFormularioEdicion.class);
                                //Aqui se le pasara toda la informacion del usuario seleccionado de la lista.
                                intent.putExtra("NOMBRE", nombre);
                                startActivity(intent);
                                nomBuscar.setText("");
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
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
}