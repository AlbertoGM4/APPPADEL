package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_pistas;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;

import java.util.Calendar;

public class EliminarReserva extends AppCompatActivity {
    ImageView imagenCalendario;
    Button btnEliminarReserva;
    ListView listaHorasReservadas;
    TextView textoFechaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_reserva);

        imagenCalendario = findViewById(R.id.imagenCalendarioPistasReservadas);
        btnEliminarReserva = findViewById(R.id.botonEliminarReservaPista);
        listaHorasReservadas = findViewById(R.id.listaHorasReservadas);
        textoFechaSeleccionada = findViewById(R.id.tvSeleccionFechaEliminarPista);

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
                    }
                }, year, month, day);

                // Mostrar el DatePickerDialog
                datePickerDialog.show();
            }
        });

        btnEliminarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Añadir confirmacion de la reserva
                //Antes de confirmar la reserva, mostrar la info del item que se ha seleccionado, y luego ya se puede confirmar
                Toast.makeText(EliminarReserva.this, "Reserva para el día X, ELIMINADA con éxito", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        listaHorasReservadas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Aqui se obtendra el item (hora), que se desea reservar, para despues emplearlo
            }
        });


    }
}