package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_pistas;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

public class EliminarReserva extends AppCompatActivity {
    ImageView imagenCalendario;
    ListView listaHorasReservadas;
    TextView textoFechaSeleccionada;
    ArrayAdapter<String> adapter;
    ArrayList<String> listaHoras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_reserva);

        imagenCalendario = findViewById(R.id.imagenCalendarioPistasReservadas);
        listaHorasReservadas = findViewById(R.id.listaHorasReservadasBaja);
        textoFechaSeleccionada = findViewById(R.id.tvSeleccionFechaEliminarPista);

        listaHoras = new ArrayList<>();

        listaHoras.add("08:00");
        listaHoras.add("09:30");
        listaHoras.add("11:00");
        listaHoras.add("12:30");
        listaHoras.add("14:00");
        listaHoras.add("17:00");
        listaHoras.add("18:30");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaHoras);

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
                //Aqui se obtendra el item (hora), que se desea reservar, para despues emplearlo
                AlertDialog.Builder alerta = new AlertDialog.Builder(EliminarReserva.this);
                alerta.setTitle("ALERTA");
                alerta.setMessage("¿Desea Eliminar la reserva de la Hora Seleccionada?\n" +
                        "- Hora Seleccionada: " + listaHoras.get(position));
                alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Dar de baja al Usuario
                        Toast.makeText(EliminarReserva.this, "Hora ELIMINADA de Reservas", Toast.LENGTH_SHORT).show();
                        listaHoras.remove(position);
                        //Para hacer la actualizacion de la lista de Usuarios.
                        adapter.notifyDataSetChanged();
                    }
                });
                alerta.create();
                alerta.show();

            }
        });


    }
}