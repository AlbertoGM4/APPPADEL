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
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_pistas.AnadirReserva;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_pistas.EliminarReserva;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_pistas.ModificarReserva;

public class GestionarPistas extends AppCompatActivity {
    Button btnAnadirReservas, btnEliminarReserva, btnModReserva;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_pistas);

        btnAnadirReservas = findViewById(R.id.botonAnadirReserva);
        btnEliminarReserva = findViewById(R.id.botonEliminarReserva);
        btnModReserva = findViewById(R.id.botonEditarReserva);

        btnAnadirReservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(GestionarPistas.this, AnadirReserva.class);
                startActivity(i);
            }
        });

        btnEliminarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(GestionarPistas.this, EliminarReserva.class);
                startActivity(i);
            }
        });

        btnModReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(GestionarPistas.this, ModificarReserva.class);
                startActivity(i);
            }
        });

    }
}