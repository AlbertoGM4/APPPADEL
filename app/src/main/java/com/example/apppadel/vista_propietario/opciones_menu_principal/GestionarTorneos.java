package com.example.apppadel.vista_propietario.opciones_menu_principal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

import java.util.Calendar;

public class GestionarTorneos extends AppCompatActivity {
    ImageView imagenCalendarioGestionTorneo;
    Button btnCrearNuevoTorneo;
    ListView listaGanadoresTorneo;
    TextView textoFechaSeleccionadaGestionTorneo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_torneos);

        imagenCalendarioGestionTorneo = findViewById(R.id.imagenCalendarioGestionTorneo);
        btnCrearNuevoTorneo = findViewById(R.id.botonCrearNuevoTorneo);
        listaGanadoresTorneo = findViewById(R.id.listaGanadoresTorneo);
        textoFechaSeleccionadaGestionTorneo = findViewById(R.id.tvSeleccionFechaGestionTorneo);

        imagenCalendarioGestionTorneo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calendario con la fecha actual
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Crear un DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(GestionarTorneos.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Aquí puedes hacer lo que quieras con la fecha seleccionada
                        String selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        textoFechaSeleccionadaGestionTorneo.setText(selectedDate);
                    }
                }, year, month, day);

                // Mostrar el DatePickerDialog
                datePickerDialog.show();
            }
        });

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
            }
        });

    }
}