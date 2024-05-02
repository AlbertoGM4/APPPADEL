package com.example.apppadel.vista_propietario.opciones_menu_principal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user.AltaUsuario;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user.BajaUsuario;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user.EditarUsuario;

public class GestionarUsuarios extends AppCompatActivity {

    Button altaUsuario, bajaUsuario, editUsuario;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_usuarios);

        altaUsuario = findViewById(R.id.botonAltaUser);
        bajaUsuario = findViewById(R.id.botonBajaUser);
        editUsuario = findViewById(R.id.botonEditarUser);

        altaUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(GestionarUsuarios.this, AltaUsuario.class);
                startActivity(i);
            }
        });

        bajaUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(GestionarUsuarios.this, BajaUsuario.class);
                startActivity(i);
            }
        });

        editUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(GestionarUsuarios.this, EditarUsuario.class);
                startActivity(i);
            }
        });

    }
}