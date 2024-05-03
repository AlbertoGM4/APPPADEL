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

public class ModificarReserva extends AppCompatActivity {
    ImageView imagenCalendarioEdit;
    ListView listaHorasReservadasEdit;
    ArrayList<String> lista;
    ArrayAdapter<String> adapter;
    TextView textoFechaSeleccionadaEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_reserva);

        imagenCalendarioEdit = findViewById(R.id.imagenCalendarioEdicion);
        listaHorasReservadasEdit = findViewById(R.id.listaHorasEdicion);
        textoFechaSeleccionadaEdit = findViewById(R.id.tvSeleccionFechaEdicion);

        lista = new ArrayList<>();
        lista.add("08:00");
        lista.add("09:30");
        lista.add("11:00");
        lista.add("12:30");
        lista.add("14:00");
        lista.add("17:00");
        lista.add("18:30");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);

        imagenCalendarioEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calendario con la fecha actual
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Crear un DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(ModificarReserva.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Aquí puedes hacer lo que quieras con la fecha seleccionada
                        String selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        textoFechaSeleccionadaEdit.setText(selectedDate);

                        //Hasta que no se seleccione una fecha no mostrara las reservas.
                        listaHorasReservadasEdit.setAdapter(adapter);
                    }
                }, year, month, day);

                // Mostrar el DatePickerDialog
                datePickerDialog.show();
            }

        });

        listaHorasReservadasEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Aqui se obtendra el item (hora), que se desea reservar, para despues emplearlo
                AlertDialog.Builder alerta = new AlertDialog.Builder(ModificarReserva.this);
                alerta.setTitle("ALERTA");
                alerta.setMessage("¿Desea Modificar la reserva de la Hora Seleccionada?\n" +
                        "- Hora Seleccionada: " + lista.get(position) + "\n" +
                        "del día " + textoFechaSeleccionadaEdit.getText().toString());
                alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Abre la nueva actividad
                        Intent i = new Intent(ModificarReserva.this, ModificarReservaNuevaFecha.class);
                        i.putExtra("DIA", textoFechaSeleccionadaEdit.getText().toString());
                        i.putExtra("HORA", lista.get(position));
                        startActivity(i);
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