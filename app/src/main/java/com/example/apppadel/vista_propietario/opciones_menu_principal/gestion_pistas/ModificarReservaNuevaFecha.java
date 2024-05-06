package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_pistas;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class ModificarReservaNuevaFecha extends AppCompatActivity {
    ImageView imagenCalendarioNuevaReserva;
    ListView listaHorasLibresNuevaReserva;
    TextView textoFechaSeleccionadaNuevaReserva;
    ArrayAdapter<String> adapter, adapterPistas;
    ArrayList<String> listaHorasP1, listaHorasP2, listaHorasP3;
    Spinner spinnerPistas;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_reserva_nueva_fecha);

        Intent i = getIntent();
        String fecha = i.getStringExtra("DIA");
        String hora = i.getStringExtra("HORA");

        imagenCalendarioNuevaReserva = findViewById(R.id.imagenCalendarioNuevaReserva);
        listaHorasLibresNuevaReserva = findViewById(R.id.listaHorasNuevasReservas);
        textoFechaSeleccionadaNuevaReserva = findViewById(R.id.tvSeleccionFechaNuevaReserva);

        //Poner en el TextView la fecha con la que se ha seleccionado la reserva anterior
        textoFechaSeleccionadaNuevaReserva.setText(fecha);

        spinnerPistas =  findViewById(R.id.spinnerPistasEditNuevaRes);
        List<String> pistas = new ArrayList<>();
        pistas.add("Pistas:");
        pistas.add("Pista 1");
        pistas.add("Pista 2");
        pistas.add("Pista 3");
        adapterPistas = new ArrayAdapter<>(this, R.layout.spinner_item, pistas);
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

                    listaHorasLibresNuevaReserva.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imagenCalendarioNuevaReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calendario con la fecha actual
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Crear un DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(ModificarReservaNuevaFecha.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Aquí puedes hacer lo que quieras con la fecha seleccionada
                        String selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        textoFechaSeleccionadaNuevaReserva.setText(selectedDate);

                    }
                }, year, month, day);

                // Mostrar el DatePickerDialog
                datePickerDialog.show();
            }
        });


        listaHorasLibresNuevaReserva.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String posSelect = adapter.getItem(position);

                AlertDialog.Builder alerta = new AlertDialog.Builder(ModificarReservaNuevaFecha.this);
                alerta.setTitle("ALERTA");
                alerta.setMessage("¿Desea Cambiar la Hora de reserva por la Hora Seleccionada?\n" +
                        "- ANTIGUA Hora de Reserva: " + hora + " del dia " + fecha +
                        "\n- NUEVA Hora de Reserva: " + posSelect + " del día " + textoFechaSeleccionadaNuevaReserva.getText().toString());
                alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Dar de baja al Usuario
                        Toast.makeText(ModificarReservaNuevaFecha.this, "Hora de Reservas MODIFICADA con Éxito", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("HORA", "OK");
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alerta.create();
                alerta.show();
            }
        });

    }
}