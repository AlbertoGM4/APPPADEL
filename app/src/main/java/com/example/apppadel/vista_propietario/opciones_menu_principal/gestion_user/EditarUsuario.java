package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        listaUsuarios = findViewById(R.id.listaUsuariosEditar);
        list = new ArrayList<>();
        list.add("Usuario 1");
        list.add("Usuario 3");
        list.add("Usuario 2");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listaUsuarios.setAdapter(adapter);

        listaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Seleccion de item (Usuario)
                //Apertura de una nueva ventana para editar la info del Usuario que se ha seleccionado.

                Intent intent = new Intent(EditarUsuario.this, VistaFormularioEdicion.class);
                //Aqui se le pasara toda la informacion del usuario seleccionado de la lista.
                intent.putExtra("NOMBRE", "Alberto");
                intent.putExtra("APE1", "Guillen");
                intent.putExtra("APE2", "Martin");
                startActivity(intent);
            }
        });

    }
}