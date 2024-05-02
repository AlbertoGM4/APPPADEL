package com.example.apppadel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apppadel.vista_propietario.MenuPricipalProp;
import com.example.apppadel.vista_usuario.MenuPrincipalSocio;
import com.example.apppadel.vista_usuario.MenuPrincipalUser;

public class ActividadInicioSesion extends AppCompatActivity {
    Button botonLogIn, btnLogInUser, botonSocio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        botonLogIn = findViewById(R.id.botonInicioSesion);
        btnLogInUser = findViewById(R.id.botonUsuario);
        botonSocio = findViewById(R.id.botonSocio);

        botonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActividadInicioSesion.this, MenuPricipalProp.class);
                startActivity(i);
            }
        });

        btnLogInUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActividadInicioSesion.this, MenuPrincipalUser.class);
                startActivity(intent);
            }
        });

        botonSocio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActividadInicioSesion.this, MenuPrincipalSocio.class);
                startActivity(intent);
            }
        });



    }
}