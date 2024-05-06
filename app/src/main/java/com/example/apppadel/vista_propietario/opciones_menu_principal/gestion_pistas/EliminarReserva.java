package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_pistas;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EliminarReserva extends AppCompatActivity {
    ImageView imagenCalendario;
    ListView listaHorasReservadas;
    TextView textoFechaSeleccionada;
    ArrayAdapter<String> adapter;
    ArrayList<String> listaHorasP1, listaHorasP2, listaHorasP3;
    Spinner spinnerPistas;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_reserva);

        imagenCalendario = findViewById(R.id.imagenCalendarioPistasReservadas);
        listaHorasReservadas = findViewById(R.id.listaHorasReservadasBaja);
        textoFechaSeleccionada = findViewById(R.id.tvSeleccionFechaEliminarPista);

        spinnerPistas =  findViewById(R.id.spinnerPistasEliminar);
        spinnerPistas.setEnabled(false);
        List<String> pistas = new ArrayList<>();
        pistas.add("Pistas:");
        pistas.add("Pista 1");
        pistas.add("Pista 2");
        pistas.add("Pista 3");
        ArrayAdapter<String> adapterPistas = new ArrayAdapter<>(this, R.layout.spinner_item, pistas);
        spinnerPistas.setAdapter(adapterPistas);


        listaHorasP1 = new ArrayList<>();
        listaHorasP1.add("08:00");
        listaHorasP1.add("09:30");
        listaHorasP1.add("11:00");
        listaHorasP1.add("12:30");
        listaHorasP1.add("14:00");
        listaHorasP1.add("17:00");
        listaHorasP1.add("18:30");

        listaHorasP2 = new ArrayList<>();
        listaHorasP2.add("11:00");
        listaHorasP2.add("12:30");
        listaHorasP2.add("14:00");
        listaHorasP2.add("18:30");
        listaHorasP2.add("20:30");


        listaHorasP3 = new ArrayList<>();
        listaHorasP3.add("08:00");
        listaHorasP3.add("09:30");
        listaHorasP3.add("17:00");
        listaHorasP3.add("18:30");

        spinnerPistas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSpinner = parent.getItemAtPosition(position).toString();

                if (!spinnerPistas.isEnabled()){
                    Toast.makeText(context, "Primero debes seleccionar una Fecha", Toast.LENGTH_SHORT).show();

                } else {
                    if (position == 0){
                        Toast.makeText(context, "Seleccione una Pista", Toast.LENGTH_SHORT).show();

                    }else {
                        if (selectedSpinner.equals("Pista 1")){
                            adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, listaHorasP1);

                        } else if (selectedSpinner.equals("Pista 2")) {
                            adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, listaHorasP2);

                        }else {
                            adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, listaHorasP3);
                        }

                        listaHorasReservadas.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imagenCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calendario con la fecha actual
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Crear un DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(EliminarReserva.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Aquí puedes hacer lo que quieras con la fecha seleccionada
                        String selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        textoFechaSeleccionada.setText(selectedDate);

                        //Pongo el Spinner seleccionable, una vez se selecciona la fecha.
                        spinnerPistas.setEnabled(true);

                        listaHorasReservadas.setAdapter(adapter);
                    }
                }, year, month, day);

                // Mostrar el DatePickerDialog
                datePickerDialog.show();
            }
        });

        listaHorasReservadas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String posSelect = adapter.getItem(position);

                AlertDialog.Builder alerta = new AlertDialog.Builder(EliminarReserva.this);
                alerta.setTitle("ALERTA");
                alerta.setMessage("¿Desea Eliminar la reserva de la Hora Seleccionada?\n" +
                        "- Hora Seleccionada: " + posSelect);
                alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Dar de baja al Usuario
                        Toast.makeText(EliminarReserva.this, "Hora ELIMINADA de Reservas", Toast.LENGTH_SHORT).show();
                        adapter.remove(posSelect);
                        adapter.notifyDataSetChanged();
                    }
                });
                alerta.create();
                alerta.show();

            }
        });


    }
}