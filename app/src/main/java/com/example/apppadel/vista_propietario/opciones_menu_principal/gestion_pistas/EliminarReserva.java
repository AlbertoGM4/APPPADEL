package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_pistas;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;
import com.example.apppadel.models.Reserva;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EliminarReserva extends AppCompatActivity {
    ImageView imagenCalendario;
    ListView listaHorasReservadas;
    TextView textoFechaSeleccionada;
    ArrayAdapter<Reserva> adapter;
    List<Reserva> horasOcupadas;
    Spinner spinnerPistas;
    String selectedSpinner, idPistaSeleccionada;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_reserva);

        db = FirebaseFirestore.getInstance();

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

        spinnerPistas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpinner = parent.getItemAtPosition(position).toString();

                if (!spinnerPistas.isEnabled()){
                    Toast.makeText(getApplicationContext(), "Primero debes seleccionar una Fecha", Toast.LENGTH_SHORT).show();

                } else {
                    if (position == 0){
                        Toast.makeText(getApplicationContext(), "Seleccione una Pista", Toast.LENGTH_SHORT).show();
                    }else {
                        if (selectedSpinner.equals("Pista 1")){ //ID PISTA 1: oU77zxeWDqsWnfHv83c8
                            idPistaSeleccionada = "oU77zxeWDqsWnfHv83c8";
                            listarHorasReservadas(idPistaSeleccionada, textoFechaSeleccionada.getText().toString());

                        } else if (selectedSpinner.equals("Pista 2")) { //ID PISTA 2: MuounKd3Wt6kqpIDwbpe
                            idPistaSeleccionada = "MuounKd3Wt6kqpIDwbpe";
                            listarHorasReservadas(idPistaSeleccionada, textoFechaSeleccionada.getText().toString());

                        }else { //ID PISTA 3: vwAEeoRlJLkEdQFWQxjC
                            idPistaSeleccionada = "vwAEeoRlJLkEdQFWQxjC";
                            listarHorasReservadas(idPistaSeleccionada, textoFechaSeleccionada.getText().toString());
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
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
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Aquí puedes hacer lo que quieras con la fecha seleccionada
                        String selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        textoFechaSeleccionada.setText(selectedDate);

                        if (!spinnerPistas.isEnabled()) {
                            //Pongo el Spinner seleccionable, una vez se selecciona la fecha.
                            spinnerPistas.setEnabled(true);
                            listaHorasReservadas.setAdapter(adapter);

                        } else {
                            // A modo de actualizacion de la lista.
                            listarHorasReservadas(idPistaSeleccionada, textoFechaSeleccionada.getText().toString());
                        }

                    }
                }, year, month, day);

                // Establece la fecha mínima al día actual
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        listaHorasReservadas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reserva reservaSeleccionada = (Reserva) parent.getItemAtPosition(position);

                AlertDialog.Builder alerta = new AlertDialog.Builder(EliminarReserva.this);
                alerta.setTitle("ALERTA");
                alerta.setMessage("¿Desea confirmar la reserva de la Hora Seleccionada?\n" +
                        "- Hora Seleccionada: " + reservaSeleccionada.getHoraInicioReserva() + "\n" +
                        "- Pista: " + selectedSpinner);
                alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Eliminar hora reservada de Firestore
                        eliminarReservaSeleccionada(reservaSeleccionada);
                    }
                });
                alerta.create();
                alerta.show();
            }
        });
    }

    private void eliminarReservaSeleccionada(Reserva reserva) {
        db.collection("reservas").document(reserva.getIdReserva())
                .delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(this, "Reserva eliminada de la base de datos con éxito", Toast.LENGTH_SHORT).show();
                        adapter.remove(reserva);
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(this, "Fallo a la hora de eliminar la reserva seleccionada", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void listarHorasReservadas(String idPistaSeleccionada, String fechaSeleccionada) {
        // Obtener la fecha actual
        String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("d/M/yyyy"));
        // Obtener la hora actual para compararla con las horas de reserva, cuando la fecah sea la de hoy
        String horaActual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

        horasOcupadas = new ArrayList<>();

        db.collection("reservas")
                .whereEqualTo("id_pista", idPistaSeleccionada)
                .whereEqualTo("fecha", fechaSeleccionada)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String horasReservadas = document.getString("hora_inicio");
                            String identificadorReserva = document.getId();

                            // Solo añadir horas si no son del día actual o si son del día actual pero no han pasado
                            if (!fechaSeleccionada.equals(fechaHoy) || horasReservadas.compareTo(horaActual) > 0) {
                                horasOcupadas.add(new Reserva(identificadorReserva, horasReservadas));
                            }
                        }

                        //Rellenar el ListView de horas reservadas.
                        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, horasOcupadas);
                        listaHorasReservadas.setAdapter(adapter);

                    } else {
                        Toast.makeText(this, "Fallo en la consulta de horas del dia y pista seleccionado", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}