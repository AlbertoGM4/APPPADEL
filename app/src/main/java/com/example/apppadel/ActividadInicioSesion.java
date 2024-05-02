package com.example.apppadel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apppadel.vista_propietario.MenuPricipalProp;

public class ActividadInicioSesion extends AppCompatActivity {
    Button botonLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        botonLogIn = findViewById(R.id.botonInicioSesion);

        botonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActividadInicioSesion.this, MenuPricipalProp.class);
                startActivity(i);
            }
        });



    }
}