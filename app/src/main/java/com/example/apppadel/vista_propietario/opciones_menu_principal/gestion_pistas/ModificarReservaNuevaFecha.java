package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_pistas;

import android.app.DatePickerDialog;
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

public class ModificarReservaNuevaFecha extends AppCompatActivity {
    ImageView imagenCalendarioNuevaReserva;
    ListView listaHorasLibresNuevaReserva;
    TextView textoFechaSeleccionadaNuevaReserva;
    ArrayList<String> lista;
    ArrayAdapter<String> adapter;

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

        lista = new ArrayList<>();
        lista.add("08:00");
        lista.add("09:30");
        lista.add("11:00");
        lista.add("12:30");
        lista.add("14:00");
        lista.add("17:00");
        lista.add("18:30");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listaHorasLibresNuevaReserva.setAdapter(adapter);

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
                //Selecciona el item por el que quiere cambiar la reserva anterior.
                AlertDialog.Builder alerta = new AlertDialog.Builder(ModificarReservaNuevaFecha.this);
                alerta.setTitle("ALERTA");
                alerta.setMessage("¿Desea Cambiar la Hora de reserva por la Hora Seleccionada?\n" +
                        "- ANTIGUA Hora de Reserva: " + hora + " del dia " + fecha +
                        "\n- NUEVA Hora de Reserva: " + lista.get(position) + " del día " + textoFechaSeleccionadaNuevaReserva.getText().toString());
                alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Dar de baja al Usuario
                        Toast.makeText(ModificarReservaNuevaFecha.this, "Hora de Reservas MODIFICADA con Éxito", Toast.LENGTH_SHORT).show();

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