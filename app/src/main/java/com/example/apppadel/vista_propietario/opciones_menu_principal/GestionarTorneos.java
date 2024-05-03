package com.example.apppadel.vista_propietario.opciones_menu_principal;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
        lista.add("Torneo 1");
        lista.add("Torneo 2");
        lista.add("Torneo 3");
        lista.add("Torneo 4");
        lista.add("Torneo 5");
        lista.add("Torneo 6");
        lista.add("Torneo 7");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listaGanadoresTorneo.setAdapter(adapter);

        registerForContextMenu(listaGanadoresTorneo);

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
                //Mostrar info del torneo
                AlertDialog.Builder alerta = new AlertDialog.Builder(GestionarTorneos.this);
                alerta.setTitle("INFORMACIÓN DEL TORNEO");
                alerta.setMessage("*Datos del Torneo*\n" +
                        "- Nombre: " + lista.get(position) +
                        "\n- Fecha Inicio: SI\n- Fecha Fin: SI");
                alerta.setPositiveButton("Volver", null);
                alerta.create();
                alerta.show();
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu_torneos, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        if (item.getItemId() == R.id.selectWinners){
            //Aqui se hará la accion de Seleccionar gnadores
            Intent intent = new Intent(GestionarTorneos.this, SeleccionGanadores.class);
            intent.putExtra("NOMBRETORNEO", lista.get(position));
            startActivity(intent);
        }

        return super.onContextItemSelected(item);
    }
}