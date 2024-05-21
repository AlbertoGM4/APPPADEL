package com.example.apppadel.vista_usuario.opciones_menu_user.reservas;

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

public class VerReservas extends AppCompatActivity {
    Button botonConsultarReservas, botonReservasPistas;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_reservas);

        botonConsultarReservas = findViewById(R.id.botonConsultarReservasUsuario);
        botonReservasPistas = findViewById(R.id.botonReservarPistaVentanaVerReservas);

        botonReservasPistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(VerReservas.this, AnadirReserva.class);
                startActivity(i);
            }
        });

        botonConsultarReservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(VerReservas.this, ListaReservas.class);
                startActivity(i);
            }
        });

    }
}