package com.example.apppadel.vista_propietario;

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
import com.example.apppadel.vista_propietario.opciones_menu_principal.GestionarPistas;
import com.example.apppadel.vista_propietario.opciones_menu_principal.GestionarTienda;
import com.example.apppadel.vista_propietario.opciones_menu_principal.GestionarTorneos;
import com.example.apppadel.vista_propietario.opciones_menu_principal.GestionarUsuarios;

public class MenuPricipalProp extends AppCompatActivity {

    Button btnGesUsuarios, btnGesPistas, btnGesTorneos, btnGesTienda;
    ImageView imagenLogout;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pricipal_prop);

        btnGesUsuarios = findViewById(R.id.botonGestionarUsuarios);
        btnGesPistas = findViewById(R.id.botonGestionReservas);
        btnGesTorneos = findViewById(R.id.botonGestionTorneos);
        btnGesTienda = findViewById(R.id.botonGestionarTienda);

        imagenLogout = findViewById(R.id.imagenCerrarSesion);

        btnGesUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MenuPricipalProp.this, GestionarUsuarios.class);
                startActivity(i);
            }
        });

        btnGesPistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MenuPricipalProp.this, GestionarPistas.class);
                startActivity(i);
            }
        });

        btnGesTorneos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MenuPricipalProp.this, GestionarTorneos.class);
                startActivity(i);
            }
        });

        btnGesTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MenuPricipalProp.this, GestionarTienda.class);
                startActivity(i);
            }
        });

        imagenLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cerrar sesion volviendo a la ventana principal.
                Toast.makeText(MenuPricipalProp.this, "Cerrando sesión...", Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);

            }
        });

    }
}