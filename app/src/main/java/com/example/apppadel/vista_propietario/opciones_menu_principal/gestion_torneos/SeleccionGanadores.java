package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_torneos;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
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
    TextView textoNombreTorneo;
    ActivityResultLauncher lanzador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_ganadores);

        i = getIntent();
        String nomTor = i.getStringExtra("NOMBRETORNEO");
        int posTorneoABorrar = i.getIntExtra("POSICION", 0);

        nombreIntegranteUno = findViewById(R.id.tvNombreIntegrante1);
        nombreIntegranteDos = findViewById(R.id.tvNombreIntegrante2);

        btnIntegranteDos = findViewById(R.id.botonIntegrante2);
        btnIntegranteUno = findViewById(R.id.botonIntegrante1);
        btnConfirmarGanadores = findViewById(R.id.botonConfirmarGanadores);

        textoNombreTorneo = findViewById(R.id.tvNombreTorneoSeleccionGanadores);
        textoNombreTorneo.setText(nomTor);

        btnIntegranteUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abre una lista con los Usuarios y seleccionas uno.
                i = new Intent(SeleccionGanadores.this, ListaUsuariosTorneo.class);
                i.putExtra("BOTON_PULSADO", "INTEGRANTE_UNO");
                lanzador.launch(i);
            }
        });

        btnIntegranteDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abre una lista con los Usuarios y seleccionas uno.
                i = new Intent(SeleccionGanadores.this, ListaUsuariosTorneo.class);
                i.putExtra("BOTON_PULSADO", "INTEGRANTE_DOS");
                lanzador.launch(i);
            }
        });

        btnConfirmarGanadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(SeleccionGanadores.this);
                alerta.setTitle("ALERTA");
                alerta.setMessage("¿Confirmar Ganadores?, Se les asignaran +250 puntos por esta victoria");
                alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SeleccionGanadores.this, "Confirmando y añadiendo los puntos a los Usuarios", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent();
                        i.putExtra("RESULTADO", textoNombreTorneo.getText().toString());
                        i.putExtra("POSICION_TOR", posTorneoABorrar);
                        setResult(RESULT_OK, i);
                        finish();
                    }
                });
                alerta.create();
                alerta.show();
            }
        });

        lanzador = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {

                if (o.getResultCode() == RESULT_OK) {
                    Intent data = o.getData();
                    String usuarioSeleccionado = data.getStringExtra("USUARIO");
                    String botonPulsado = data.getStringExtra("integrante");

                    if (botonPulsado != null) {
                        if (botonPulsado.equals("INTEGRANTE_UNO")) {
                            nombreIntegranteUno.setText(usuarioSeleccionado);

                        } else if (botonPulsado.equals("INTEGRANTE_DOS")) {
                            nombreIntegranteDos.setText(usuarioSeleccionado);
                        }
                    }
                }
            }
        });

    }
}