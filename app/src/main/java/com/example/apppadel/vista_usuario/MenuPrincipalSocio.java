package com.example.apppadel.vista_usuario;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_pistas.AnadirReserva;
import com.example.apppadel.vista_usuario.opciones_menu_socio.ConsultarRanking;
import com.example.apppadel.vista_usuario.opciones_menu_user.ConsultarEventos;
import com.example.apppadel.vista_usuario.opciones_menu_user.ConsultarTienda;
import com.example.apppadel.vista_usuario.opciones_menu_user.ConsultarUsuarios;

public class MenuPrincipalSocio extends AppCompatActivity {

    Button reservarPistaSocio, consultarEventosSocio, consultarUsuariosSocio, consultarTiendaSocio, consultarRankingSocio;
    Intent i;
    ImageView cerrarSesionSocio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_socio);

        reservarPistaSocio = findViewById(R.id.botonReservarPistaSocio);
        consultarEventosSocio = findViewById(R.id.botonConsultarEventosSocio);
        consultarUsuariosSocio = findViewById(R.id.botonConsultarUsuariosSocio);
        consultarTiendaSocio = findViewById(R.id.botonConsultarTiendaSocio);
        consultarRankingSocio = findViewById(R.id.botonConsultarRankingSocio);

        cerrarSesionSocio = findViewById(R.id.imagenCerrarSesionSocio);

        cerrarSesionSocio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cerrar sesion volviendo a la ventana principal.
                Toast.makeText(MenuPrincipalSocio.this, "Cerrando sesión...", Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);
            }
        });

        reservarPistaSocio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abre la ventana para reservar pistas desde la vista del Usuario
                i = new Intent(MenuPrincipalSocio.this, AnadirReserva.class);
                startActivity(i);
            }
        });

        consultarEventosSocio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MenuPrincipalSocio.this, ConsultarEventos.class);
                startActivity(i);
            }
        });

        consultarUsuariosSocio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MenuPrincipalSocio.this, ConsultarUsuarios.class);
                startActivity(i);
            }
        });

        consultarTiendaSocio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MenuPrincipalSocio.this, ConsultarTienda.class);
                startActivity(i);
            }
        });

        consultarRankingSocio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MenuPrincipalSocio.this, ConsultarRanking.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Para cerrar sesión pulsa el botón de power", Toast.LENGTH_SHORT).show();
    }

}