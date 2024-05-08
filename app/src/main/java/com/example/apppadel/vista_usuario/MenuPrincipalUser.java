package com.example.apppadel.vista_usuario;

import android.annotation.SuppressLint;
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
import com.example.apppadel.vista_propietario.MenuPricipalProp;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_pistas.AnadirReserva;
import com.example.apppadel.vista_usuario.opciones_menu_user.ConsultarEventos;
import com.example.apppadel.vista_usuario.opciones_menu_user.ConsultarTienda;
import com.example.apppadel.vista_usuario.opciones_menu_user.ConsultarUsuarios;
import com.example.apppadel.vista_usuario.opciones_menu_user.ReservarPistas;

public class MenuPrincipalUser extends AppCompatActivity {
    Button reservarPista, consultarEventos, consultarUsuarios, consultarTienda;
    Intent i;
    ImageView cerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_user);

        reservarPista = findViewById(R.id.botonReservarPista);
        consultarEventos = findViewById(R.id.botonConsultarEventos);
        consultarUsuarios = findViewById(R.id.botonConsultarUsuarios);
        consultarTienda = findViewById(R.id.botonConsultarTienda);

        cerrarSesion = findViewById(R.id.imagenCerrarSesionUser);

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cerrar sesion volviendo a la ventana principal.
                Toast.makeText(MenuPrincipalUser.this, "Cerrando sesión...", Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);
            }
        });

        reservarPista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abre la ventana para reservar pistas desde la vista del Usuario
                i = new Intent(MenuPrincipalUser.this, AnadirReserva.class);
                startActivity(i);
            }
        });

        consultarEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MenuPrincipalUser.this, ConsultarEventos.class);
                startActivity(i);
            }
        });

        consultarUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MenuPrincipalUser.this, ConsultarUsuarios.class);
                startActivity(i);
            }
        });

        consultarTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MenuPrincipalUser.this, ConsultarTienda.class);
                startActivity(i);
            }
        });


    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Para cerrar sesión pulsa el botón de power", Toast.LENGTH_SHORT).show();
    }
}