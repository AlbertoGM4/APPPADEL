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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ModificarReservaNuevaFecha extends AppCompatActivity {
    ImageView imagenCalendarioNuevaReserva;
    ListView listaHorasLibresNuevaReserva;
    TextView textoFechaSeleccionadaNuevaReserva;
    ArrayAdapter<String> adapter, adapterPistas;
    Spinner spinnerPistas;
    FirebaseFirestore db;
    String selectedSpinner, idPistaSeleccionada, itemNuevaHora;
    List<String> horasOcupadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_reserva_nueva_fecha);

        db = FirebaseFirestore.getInstance();

        Intent i = getIntent();
        String fechaAntigua = i.getStringExtra("FECHA");
        String horaInicioAntiguo = i.getStringExtra("INICIO");
        String numeroPistaAntiguo = i.getStringExtra("NUM_PISTA");

        imagenCalendarioNuevaReserva = findViewById(R.id.imagenCalendarioNuevaReserva);
        listaHorasLibresNuevaReserva = findViewById(R.id.listaHorasNuevasReservas);
        textoFechaSeleccionadaNuevaReserva = findViewById(R.id.tvSeleccionFechaNuevaReserva);

        //Poner en el TextView la fecha con la que se ha seleccionado la reserva anterior
        textoFechaSeleccionadaNuevaReserva.setText(fechaAntigua);

        spinnerPistas =  findViewById(R.id.spinnerPistasEditNuevaRes);
        List<String> pistas = new ArrayList<>();
        pistas.add("Pistas:");
        pistas.add("Pista 1");
        pistas.add("Pista 2");
        pistas.add("Pista 3");
        adapterPistas = new ArrayAdapter<>(this, R.layout.spinner_item, pistas);
        spinnerPistas.setAdapter(adapterPistas);

        spinnerPistas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                            listarHorasDePista(idPistaSeleccionada, textoFechaSeleccionadaNuevaReserva.getText().toString());

                        } else if (selectedSpinner.equals("Pista 2")) { //ID PISTA 2: MuounKd3Wt6kqpIDwbpe
                            idPistaSeleccionada = "MuounKd3Wt6kqpIDwbpe";
                            listarHorasDePista(idPistaSeleccionada, textoFechaSeleccionadaNuevaReserva.getText().toString());

                        }else { //ID PISTA 3: vwAEeoRlJLkEdQFWQxjC
                            idPistaSeleccionada = "vwAEeoRlJLkEdQFWQxjC";
                            listarHorasDePista(idPistaSeleccionada, textoFechaSeleccionadaNuevaReserva.getText().toString());
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
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

                        if (!spinnerPistas.isEnabled()) {
                            //Pongo el Spinner seleccionable, una vez se selecciona la fecha.
                            spinnerPistas.setEnabled(true);
                            //listaHorasLibresNuevaReserva.setAdapter(adapter);

                        } else {
                            // A modo de actualizacion de la lista.
                            listarHorasDePista(idPistaSeleccionada, textoFechaSeleccionadaNuevaReserva.getText().toString());
                        }
                    }
                }, year, month, day);

                // Establece la fecha mínima al día actual
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });


        listaHorasLibresNuevaReserva.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemNuevaHora = adapter.getItem(position);

                AlertDialog.Builder alerta = new AlertDialog.Builder(ModificarReservaNuevaFecha.this);
                alerta.setTitle("MODIFICACION DE RESERVA");
                alerta.setMessage("¿Desea Cambiar la Hora de reserva por la Hora Seleccionada?\n" +
                        "- ANTIGUA Hora de Reserva: " + horaInicioAntiguo + " del dia " + fechaAntigua + ", en la " + numeroPistaAntiguo + "\n" +
                        "- NUEVA Hora de Reserva: " + itemNuevaHora + " del día " + textoFechaSeleccionadaNuevaReserva.getText().toString() + ", en la " + selectedSpinner);
                alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent();
                        intent.putExtra("NUEVO_IDPISTA", idPistaSeleccionada);
                        intent.putExtra("NUEVA_HORA_INICIO", itemNuevaHora);
                        intent.putExtra("NUEVA_FECHA", textoFechaSeleccionadaNuevaReserva.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
                alerta.create();
                alerta.show();
            }
        });

    }

    public void listarHorasDePista(String idPista, String fechaSeleccionada){
        horasOcupadas = new ArrayList<>();

        db.collection("reservas")
                .whereEqualTo("id_pista", idPista)
                .whereEqualTo("fecha", fechaSeleccionada)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String horasReservadas = document.getString("hora_inicio");
                            horasOcupadas.add(horasReservadas);
                        }
                        horasDisponibles(horasOcupadas, fechaSeleccionada);

                    } else {
                        Toast.makeText(this, "Fallo en la consulta de horas del dia y pista seleccionado", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void horasDisponibles(List<String> hOcupadas, String fechaSelec) {
        //Lista de todas las horas.
        List<String> listaHorasDisponibles = new ArrayList<>();
        listaHorasDisponibles.add("09:00");
        listaHorasDisponibles.add("10:30");
        listaHorasDisponibles.add("12:00");
        listaHorasDisponibles.add("13:30");
        listaHorasDisponibles.add("15:00");
        listaHorasDisponibles.add("16:30");
        listaHorasDisponibles.add("18:00");
        listaHorasDisponibles.add("19:30");
        listaHorasDisponibles.add("21:00");
        listaHorasDisponibles.add("22:30");

        // Verificar se la fecha es de hoy.
        String fechaHoy = new SimpleDateFormat("d/M/yyyy", Locale.getDefault()).format(new Date());
        if (fechaHoy.equals(fechaSelec)) {
            final String horaActual = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
            listaHorasDisponibles.removeIf(hora -> hora.compareTo(horaActual) <= 0); // Si las horas coinciden, o son menores que la actual, las saca de la lista
        }

        listaHorasDisponibles.removeAll(hOcupadas);
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, listaHorasDisponibles);
        listaHorasLibresNuevaReserva.setAdapter(adapter);

    }
}