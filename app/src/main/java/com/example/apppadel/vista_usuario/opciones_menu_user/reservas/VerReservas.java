package com.example.apppadel.vista_usuario.opciones_menu_user.reservas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;
import com.example.apppadel.models.Usuario;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_pistas.AnadirReserva;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class VerReservas extends AppCompatActivity {
    Button botonConsultarReservas, botonReservasPistas;
    Intent i;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_reservas);

        db = FirebaseFirestore.getInstance();

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
                comprobarReservas();
            }
        });

    }

    private void comprobarReservas() {
        // Obtener el Id del Usuario logeado.
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        String idUsuario = usuario.getUid();

        db.collection("reservas")
                .whereEqualTo("id_user", idUsuario)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        if (task.getResult().isEmpty()){
                            Log.d("FirebaseReservas", "SIN RESERVAS");
                            Toast.makeText(this, "No tienes ninguna reserva realizada.", Toast.LENGTH_LONG).show();
                            Toast.makeText(this, "Crea una nueva para poder empezar a verlas.", Toast.LENGTH_LONG).show();
                        } else {
                            i = new Intent(VerReservas.this, ListaReservas.class);
                            startActivity(i);
                        }
                    } else {
                        Toast.makeText(this, "Fallo en la consulta de las reservas del Usuario", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}