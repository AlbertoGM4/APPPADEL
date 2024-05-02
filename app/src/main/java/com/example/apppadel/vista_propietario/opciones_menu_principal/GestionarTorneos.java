package com.example.apppadel.vista_propietario.opciones_menu_principal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apppadel.R;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_pistas.ModificarReservaNuevaFecha;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_torneos.NuevoTorneo;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_torneos.SeleccionGanadores;

import java.util.ArrayList;
import java.util.Calendar;

public class GestionarTorneos extends AppCompatActivity {
    Button btnCrearNuevoTorneo;
    ListView listaGanadoresTorneo;
    ArrayList<String> lista;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_torneos);

        btnCrearNuevoTorneo = findViewById(R.id.botonCrearNuevoTorneo);
        listaGanadoresTorneo = findViewById(R.id.listaGanadoresTorneo);

        lista = new ArrayList<>();
        lista.add("Usuario 1");
        lista.add("Usuario 2");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listaGanadoresTorneo.setAdapter(adapter);

        btnCrearNuevoTorneo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Añadir confirmacion de la reserva
                //Antes de confirmar la reserva, mostrar la info del item que se ha seleccionado, y luego ya se puede confirmar
                Intent i = new Intent(GestionarTorneos.this, NuevoTorneo.class);
                startActivity(i);
            }
        });

        listaGanadoresTorneo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Aqui se obtendra el item (hora), que se desea reservar, para despues emplearlo
                Intent intent = new Intent(GestionarTorneos.this, SeleccionGanadores.class);
                startActivity(intent);
            }
        });

    }
}