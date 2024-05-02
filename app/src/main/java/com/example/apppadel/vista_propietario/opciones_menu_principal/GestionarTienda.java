package com.example.apppadel.vista_propietario.opciones_menu_principal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_tienda.ActualizarStock;

public class GestionarTienda extends AppCompatActivity {
    Button btnActualizarStock;
    ListView listaProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_tienda);

        btnActualizarStock = findViewById(R.id.botonActualizarStock);
        listaProductos = findViewById(R.id.listaProductos);

        btnActualizarStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GestionarTienda.this, ActualizarStock.class);
                startActivity(i);
            }
        });

        listaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Obtener el item seleccionado
            }
        });

    }
}