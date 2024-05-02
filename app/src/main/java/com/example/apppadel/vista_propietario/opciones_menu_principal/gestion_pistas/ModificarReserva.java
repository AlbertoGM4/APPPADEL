package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_pistas;

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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;

import java.util.Calendar;

public class ModificarReserva extends AppCompatActivity {
    ImageView imagenCalendarioEdit;
    Button btnCambiarReserva;
    ListView listaHorasReservadasEdit;
    TextView textoFechaSeleccionadaEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_reserva);

        imagenCalendarioEdit = findViewById(R.id.imagenCalendarioEdicion);
        btnCambiarReserva = findViewById(R.id.botonCambiarReserva);
        listaHorasReservadasEdit = findViewById(R.id.listaHorasEdicion);
        textoFechaSeleccionadaEdit = findViewById(R.id.tvSeleccionFechaEdicion);

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
                    }
                }, year, month, day);

                // Mostrar el DatePickerDialog
                datePickerDialog.show();
            }
        });

        btnCambiarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Añadir confirmacion de la reserva
                //Antes de confirmar la reserva, mostrar la info del item que se ha seleccionado, y luego ya se puede confirmar
                Toast.makeText(ModificarReserva.this, "Reserva seleccionada para su cambio", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(ModificarReserva.this, ModificarReservaNuevaFecha.class);
                startActivity(i);

                finish();
            }
        });

        listaHorasReservadasEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Aqui se obtendra el item (hora), que se desea reservar, para despues emplearlo
            }
        });

    }
}