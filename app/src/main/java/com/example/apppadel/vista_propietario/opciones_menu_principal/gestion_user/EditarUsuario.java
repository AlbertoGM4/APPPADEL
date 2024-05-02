package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;

public class EditarUsuario extends AppCompatActivity {
    ListView listaUsuarios;
    Button botonEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        listaUsuarios = findViewById(R.id.listaUsuariosEditar);

        botonEditar = findViewById(R.id.botonParaEditar);

        listaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Seleccion de item (Usuario)
            }
        });

        botonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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