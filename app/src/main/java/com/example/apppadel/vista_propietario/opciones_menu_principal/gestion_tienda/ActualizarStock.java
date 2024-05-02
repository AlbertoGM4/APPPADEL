package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_tienda;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_stock);

        nombreProd = findViewById(R.id.tvNombreProducto);
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
                //Confrima el Stock y añade el nummero que hay en en el campo del nuemro
                Toast.makeText(ActualizarStock.this, "Stock actualizado(Sumado)", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnRestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Confrima el Stock y resta el nummero que hay en en el campo del nuemro
                Toast.makeText(ActualizarStock.this, "Stock actualizado(Restado)", Toast.LENGTH_SHORT).show();
                finish();

            }
        });


    }
}