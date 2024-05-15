package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_pistas;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apppadel.R;
import com.example.apppadel.models.Reserva;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModificarReserva extends AppCompatActivity {
    ImageView imagenCalendarioEdit;
    ListView listaHorasReservadasEdit;
    ArrayAdapter<String> adapterSpinner;
    ArrayAdapter<Reserva> adapter;
    List<Reserva> horasOcupadas;
    TextView textoFechaSeleccionadaEdit;
    Spinner spinnerPistas;
    ActivityResultLauncher lanzador;
    String selectedSpinner, idPistaSeleccionada;
    Reserva reservaSeleccionada;
    FirebaseFirestore db;
    Map<String, Object> nuevosDatosReserva = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_reserva);

        db = FirebaseFirestore.getInstance();

        imagenCalendarioEdit = findViewById(R.id.imagenCalendarioEdicion);
        listaHorasReservadasEdit = findViewById(R.id.listaHorasEdicion);
        textoFechaSeleccionadaEdit = findViewById(R.id.tvSeleccionFechaEdicion);

        spinnerPistas =  findViewById(R.id.spinnerPistasEdit);
        spinnerPistas.setEnabled(false);
        List<String> pistas = new ArrayList<>();
        pistas.add("Pistas:");
        pistas.add("Pista 1");
        pistas.add("Pista 2");
        pistas.add("Pista 3");
        adapterSpinner = new ArrayAdapter<>(this, R.layout.spinner_item, pistas);
        spinnerPistas.setAdapter(adapterSpinner);

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
                            listarHorasReservadas(idPistaSeleccionada, textoFechaSeleccionadaEdit.getText().toString());

                        } else if (selectedSpinner.equals("Pista 2")) { //ID PISTA 2: MuounKd3Wt6kqpIDwbpe
                            idPistaSeleccionada = "MuounKd3Wt6kqpIDwbpe";
                            listarHorasReservadas(idPistaSeleccionada, textoFechaSeleccionadaEdit.getText().toString());

                        }else { //ID PISTA 3: vwAEeoRlJLkEdQFWQxjC
                            idPistaSeleccionada = "vwAEeoRlJLkEdQFWQxjC";
                            listarHorasReservadas(idPistaSeleccionada, textoFechaSeleccionadaEdit.getText().toString());
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

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
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Aquí puedes hacer lo que quieras con la fecha seleccionada
                        String selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        textoFechaSeleccionadaEdit.setText(selectedDate);

                        if (!spinnerPistas.isEnabled()) {
                            //Pongo el Spinner seleccionable, una vez se selecciona la fecha.
                            spinnerPistas.setEnabled(true);
                            listaHorasReservadasEdit.setAdapter(adapter);

                        } else {
                            // A modo de actualizacion de la lista.
                            listarHorasReservadas(idPistaSeleccionada, textoFechaSeleccionadaEdit.getText().toString());
                        }
                    }
                }, year, month, day);

                // Establece la fecha mínima al día actual
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        listaHorasReservadasEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                reservaSeleccionada = (Reserva) parent.getItemAtPosition(position);

                AlertDialog.Builder alerta = new AlertDialog.Builder(ModificarReserva.this);
                alerta.setTitle("EDICIÓN DE RESERVAS");
                alerta.setMessage("¿Desea Modificar la reserva de la Hora Seleccionada?\n" +
                        "- Hora Seleccionada: " + reservaSeleccionada.getHoraInicioReserva() + "\n" +
                        "- Fecha: " + textoFechaSeleccionadaEdit.getText().toString() + "\n" +
                        "- Pista: " + selectedSpinner); // selectedSpinner devuelve el numero de la pista que se ha seleccionado.
                alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Abre la nueva actividad
                        Intent i = new Intent(ModificarReserva.this, ModificarReservaNuevaFecha.class);
                        i.putExtra("FECHA", reservaSeleccionada.getFechaReserva());
                        i.putExtra("INICIO", reservaSeleccionada.getHoraInicioReserva());
                        i.putExtra("NUM_PISTA", selectedSpinner);
                        lanzador.launch(i);
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

        lanzador = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onActivityResult(ActivityResult o) {
                if (o.getResultCode() == RESULT_OK) {
                    Intent data = o.getData();
                    String nuevaFecha = data.getStringExtra("NUEVA_FECHA");
                    String nuevoIdPista = data.getStringExtra("NUEVO_IDPISTA");
                    String nuevaHoraInicio = data.getStringExtra("NUEVA_HORA_INICIO");

                    actualizarReserva(nuevoIdPista, nuevaFecha, nuevaHoraInicio);

                } else {
                    Toast.makeText(ModificarReserva.this, "Se ha cancelado el proceso de Edición de la Reserva", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void actualizarReserva(String nuevoIdPista, String nuevaFecha, String nuevaHoraInicio) {

        //Nuevo mapa de datos.
        nuevosDatosReserva = new HashMap<>();
        nuevosDatosReserva.put("fecha", nuevaFecha);
        nuevosDatosReserva.put("hora_fin", calcularHoraFin(nuevaHoraInicio));
        nuevosDatosReserva.put("hora_inicio", nuevaHoraInicio);
        nuevosDatosReserva.put("id_pista", nuevoIdPista);

        // Actualizacion de la base de datos.
        db.collection("reservas").document(reservaSeleccionada.getIdReserva())
                .update(nuevosDatosReserva)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(this, "Actualización de los datos realizada con Éxito", Toast.LENGTH_SHORT).show();
                        // Actualizacion de las listas. adapter.notifyDataSetChange, no hace nada.
                        listarHorasReservadas(idPistaSeleccionada, textoFechaSeleccionadaEdit.getText().toString());
                    } else {
                        Toast.makeText(this, "Fallo en la actualización de los datos de la Ediciónm de la Reserva", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String calcularHoraFin(String horaInicio){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(horaInicio, formatter);
        LocalTime newTime = time.plusHours(1).plusMinutes(30);
        String horaFinalReserva = newTime.format(formatter);

        return horaFinalReserva;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void listarHorasReservadas(String idPistaSeleccionada, String fechaSeleccionada) {
        // Lista de objetos Reserva
        horasOcupadas = new ArrayList<>();

        // Calculo de la hora actual, para no poner las horas que ya han pasado en la lista de horas reservadas.
        String hoy = LocalDate.now().format(DateTimeFormatter.ofPattern("d/M/yyyy"));
        String horaActual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

        db.collection("reservas")
                .whereEqualTo("id_pista", idPistaSeleccionada)
                .whereEqualTo("fecha", fechaSeleccionada)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String identificadorReserva = document.getId(); //ID del documenmto reservas (Firestore)
                            String fechaDocReserva = document.getString("fecha");
                            String horaInicioReserva = document.getString("hora_inicio");
                            String horaFinReserva = document.getString("hora_fin");
                            String idPistaReservada = document.getString("id_pista");

                            if (!fechaDocReserva.equals(hoy) || horaInicioReserva.compareTo(horaActual) > 0) {
                                horasOcupadas.add(new Reserva(identificadorReserva, horaInicioReserva, horaFinReserva, fechaDocReserva, idPistaReservada));
                            }
                        }
                        //Rellenar el ListView de horas reservadas.
                        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, horasOcupadas);
                        listaHorasReservadasEdit.setAdapter(adapter);

                    } else {
                        Toast.makeText(this, "Fallo en la consulta de horas del dia y pista seleccionado", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}