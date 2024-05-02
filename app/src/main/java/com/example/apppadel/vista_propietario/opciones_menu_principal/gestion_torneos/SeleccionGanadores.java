package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_torneos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user.BajaUsuario;

public class SeleccionGanadores extends AppCompatActivity {
    TextView nombreIntegranteUno, nombreIntegranteDos;
    Button btnIntegranteUno, btnIntegranteDos, btnConfirmarGanadores;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_ganadores);

        nombreIntegranteUno = findViewById(R.id.tvNombreIntegrante1);
        nombreIntegranteDos = findViewById(R.id.tvNombreIntegrante2);
        btnIntegranteDos = findViewById(R.id.botonIntegrante1);
        btnIntegranteUno = findViewById(R.id.botonIntegrante2);
        btnConfirmarGanadores = findViewById(R.id.botonConfirmarGanadores);

        btnIntegranteUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abre una lista con los Usuarios y seleccionas uno.
                //Crear una clase lista para seleccionar un Usuario.
                /*i = new Intent(SeleccionGanadores.this, null);
                startActivity(i);*/
                Toast.makeText(SeleccionGanadores.this, "Lista con Usuarios", Toast.LENGTH_SHORT).show();
            }
        });

        btnIntegranteDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abre una lista con los Usuarios y seleccionas uno.
                Toast.makeText(SeleccionGanadores.this, "Lista con Usuarios", Toast.LENGTH_SHORT).show();
            }
        });

        btnConfirmarGanadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Confirmas la seleccion de ganadores, saldra un Alert avisando de los puntos que van a ganar
                // y si quieres confirmarlo, luego acabara la actividad

                AlertDialog.Builder alerta = new AlertDialog.Builder(SeleccionGanadores.this);
                alerta.setTitle("ALERTA");
                alerta.setMessage("¿Confirmar Ganadores?, Se les asignaran +250 puntos por esta victoria");
                alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Dar de baja al Usuario
                        Toast.makeText(SeleccionGanadores.this, "Confirmando y añadiendo los puntos a los Usuarios", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                alerta.create();
                alerta.show();
            }
        });

    }
}