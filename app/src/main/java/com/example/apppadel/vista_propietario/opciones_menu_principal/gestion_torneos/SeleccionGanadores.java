package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_torneos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SeleccionGanadores extends AppCompatActivity {
    TextView nombreIntegranteUno, nombreIntegranteDos;
    Button btnIntegranteUno, btnIntegranteDos, btnConfirmarGanadores;
    Intent i;
    TextView textoNombreTorneo;
    ActivityResultLauncher lanzador;
    String idPrimerGanador = "", idSegundoGanador = ""; // Para asignar los puntos a los ganadores
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_ganadores);

        db = FirebaseFirestore.getInstance();

        i = getIntent();
        String idTorneo = i.getStringExtra("ID_TORNEO");
        String nomTor = i.getStringExtra("NOMBRE_TORNEO");

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
                i.putExtra("GANADOR_UNO", idPrimerGanador); // Se pasa vacio para saber que viene del primero
                i.putExtra("GANADOR_DOS", idSegundoGanador);
                lanzador.launch(i);
            }
        });

        btnIntegranteDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombreIntegranteUno.getText().toString().isEmpty()){
                    Toast.makeText(SeleccionGanadores.this, "Seleccione primero al Primer Integrante", Toast.LENGTH_SHORT).show();

                } else {
                    //Abre una lista con los Usuarios y seleccionas uno.
                    i = new Intent(SeleccionGanadores.this, ListaUsuariosTorneo.class);
                    i.putExtra("BOTON_PULSADO", "INTEGRANTE_DOS");
                    i.putExtra("GANADOR_UNO", idPrimerGanador); // Para que en la lista del segundo ganador no lo vuelva a listar
                    i.putExtra("GANADOR_DOS", idSegundoGanador);
                    lanzador.launch(i);
                }
            }
        });

        btnConfirmarGanadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombreIntegranteUno.getText().toString().isEmpty() || nombreIntegranteDos.getText().toString().isEmpty()){
                    Toast.makeText(SeleccionGanadores.this, "Debes rellenar ambos campos de integrantes", Toast.LENGTH_SHORT).show();

                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(SeleccionGanadores.this);
                    alerta.setTitle("ALERTA");
                    alerta.setMessage("¿Confirmar Ganadores?, Se les asignaran +250 puntos por esta victoria");
                    alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Añadir los puntos a los usuarios por los id.
                            // Primer Ganador
                            anadirPuntosAUsuarios(idPrimerGanador, 250);
                            Toast.makeText(SeleccionGanadores.this, "Puntos añadidos al Primer ganador", Toast.LENGTH_SHORT).show();

                            // Segundo Ganador
                            anadirPuntosAUsuarios(idSegundoGanador, 250);
                            Toast.makeText(SeleccionGanadores.this, "Puntos añadidos al Segundo ganador", Toast.LENGTH_SHORT).show();

                            Toast.makeText(SeleccionGanadores.this, "Volviendo...", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent();
                            i.putExtra("ACTUALIZAR_LISTA", "SI");
                            i.putExtra("IDTORNEO", idTorneo);
                            i.putExtra("PRIMER_GANADOR_ID", idPrimerGanador);
                            i.putExtra("SEGUNDO_GANADOR_ID", idSegundoGanador);
                            setResult(50, i);
                            finish();
                        }
                    });
                    alerta.create();
                    alerta.show();
                }
            }
        });

        lanzador = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onActivityResult(ActivityResult o) {

                if (o.getResultCode() == 1){ // Es del primer ganador
                    idPrimerGanador = o.getData().getStringExtra("ID");
                    nombreIntegranteUno.setText(o.getData().getStringExtra("NOMBRE"));

                } else if (o.getResultCode() == 2) { //Segundo ganador
                    idSegundoGanador = o.getData().getStringExtra("ID");
                    nombreIntegranteDos.setText(o.getData().getStringExtra("NOMBRE"));
                }
            }
        });

    }

    private void anadirPuntosAUsuarios(String idGanador, long puntos) {
        DocumentReference doc = db.collection("usuarios").document(idGanador);

        db.runTransaction(transaction -> {
            DocumentSnapshot snapshot = transaction.get(doc);
            if (snapshot.exists()){
                if (snapshot.contains("puntos")) {
                    long puntosActuales = snapshot.getLong("puntos");
                    transaction.update(doc, "puntos", puntosActuales + puntos);
                } else {
                    transaction.update(doc, "puntos", puntos);
                }
            }
            return null;
        }).addOnSuccessListener(aVoid -> {
            Toast.makeText(this, "Puntos actualizados al usuario con éxito", Toast.LENGTH_SHORT).show();

        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Fallo en la actualización de los puntos", Toast.LENGTH_SHORT).show();
        });

    }
}