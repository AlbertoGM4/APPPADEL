package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_tienda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;

public class ActualizarStock extends AppCompatActivity {
    TextView nombreProd, textoNumero;
    ImageView suma, resta;
    Button btnAnadir, btnRestar;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_stock);

        Intent i = getIntent();
        String nombreArt = i.getStringExtra("NOMBRE");

        nombreProd = findViewById(R.id.tvNombreProducto);
        nombreProd.setText(nombreArt);

        textoNumero = findViewById(R.id.tvNumeroStock);
        suma = findViewById(R.id.imagenBtnSumar);
        resta = findViewById(R.id.imagenBtnRestar);
        btnAnadir = findViewById(R.id.botonSumar);
        btnRestar = findViewById(R.id.botonRestar);

        suma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Suma un numero al textoNumero
                int numeroSuma;
                numeroSuma = Integer.parseInt(textoNumero.getText().toString());
                numeroSuma++;
                textoNumero.setText("" + numeroSuma);
            }
        });

        resta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Restar un numero al textoNumero
                int numResta;
                numResta = Integer.parseInt(textoNumero.getText().toString());

                if (numResta > 0){
                    numResta--;
                }
                textoNumero.setText("" + numResta);
            }
        });

        btnAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                intent.putExtra("NUMERO", textoNumero.getText().toString());
                setResult(1, intent);

                finish();
            }
        });

        btnRestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                intent.putExtra("NUMERO", textoNumero.getText().toString());
                setResult(2, intent);

                finish();

            }
        });
    }
}