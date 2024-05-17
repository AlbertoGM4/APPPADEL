package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_torneos;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;
import com.example.apppadel.models.Torneo;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user.AltaUsuario;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NuevoTorneo extends AppCompatActivity {
    EditText nombreTorneo;
    ImageView calendarioInicio, calendarioFin;
    Button btnCrearTorneo;
    TextView seleccionFechaInicio, seleccionFechaFin;
    Calendar calendario;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_torneo);

        db = FirebaseFirestore.getInstance();

        nombreTorneo = findViewById(R.id.etNombreTorneo);

        calendarioInicio = findViewById(R.id.imagenCalendarioInicioTorneo);
        calendarioFin = findViewById(R.id.imagenCalendarioFinTorneo);

        btnCrearTorneo = findViewById(R.id.botonCreacionTorneo);

        seleccionFechaInicio = findViewById(R.id.tvSeleccionFechaInicio);
        seleccionFechaFin = findViewById(R.id.tvSeleccionFechaFin);

        calendario = Calendar.getInstance();

        calendarioInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear una nueva instancia de Calendar para evitar añadir un día cada vez
                Calendar calendarDialog = Calendar.getInstance();
                calendarDialog.add(Calendar.DAY_OF_MONTH, 1);  // Añadir un día para que no se pueda seleccionar el día actual

                int year = calendarDialog.get(Calendar.YEAR);
                int month = calendarDialog.get(Calendar.MONTH);
                int day = calendarDialog.get(Calendar.DAY_OF_MONTH);

                // Crear un DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(NuevoTorneo.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Configurar el calendario global con la fecha seleccionada
                        calendario.set(Calendar.YEAR, selectedYear);
                        calendario.set(Calendar.MONTH, selectedMonth);
                        calendario.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        String formattedDate = dateFormat.format(calendario.getTime());

                        seleccionFechaInicio.setText(formattedDate);
                        seleccionFechaFin.setText("  /  /  ");
                    }
                }, year, month, day);

                // Establecer la fecha mínima para el DatePicker
                datePickerDialog.getDatePicker().setMinDate(calendarDialog.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        calendarioFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar si la fecha de inicio ha sido establecida
                if (seleccionFechaInicio.getText().toString().isEmpty()) {
                    Toast.makeText(NuevoTorneo.this, "Por favor, seleccione primero la fecha de inicio.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Date fechaInicio = formatearFecha(seleccionFechaInicio.getText().toString());

                Calendar calendarInicio = Calendar.getInstance();
                calendarInicio.setTime(fechaInicio);

                // Crear el DatePickerDialog iniciando desde la fecha de inicio seleccionada
                DatePickerDialog datePickerDialog = new DatePickerDialog(NuevoTorneo.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(Calendar.YEAR, selectedYear);
                        selectedCalendar.set(Calendar.MONTH, selectedMonth);
                        selectedCalendar.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        String formattedDate = dateFormat.format(selectedCalendar.getTime());

                        seleccionFechaFin.setText(formattedDate);
                    }
                }, calendarInicio.get(Calendar.YEAR), calendarInicio.get(Calendar.MONTH), calendarInicio.get(Calendar.DAY_OF_MONTH));

                // Configurar la fecha mínima al DatePickerDialog
                datePickerDialog.getDatePicker().setMinDate(calendarInicio.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        btnCrearTorneo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cracion del nuevo Torneo
                if (nombreTorneo.getText().toString().isEmpty()){
                    nombreTorneo.setError("No se ha introducido el nombre del nuevo Torneo");

                } else {
                    if (seleccionFechaInicio.getText().toString().isEmpty() || seleccionFechaFin.getText().toString().isEmpty()){
                        Toast.makeText(NuevoTorneo.this, "Faltan campos de fecha por rellenar para poder crear el Nuevo Torneo", Toast.LENGTH_SHORT).show();

                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(NuevoTorneo.this);
                        alerta.setTitle("ALERTA");
                        alerta.setMessage("¿Seguro que quieres crear un Nuevo Torneo\n" +
                                "*Datos del Nuevo Torneo*\n" +
                                "- Nombre: " + nombreTorneo.getText().toString() +
                                "\n- Fecha Inicio: " + seleccionFechaInicio.getText().toString() +
                                "\n- Fecha Fin: " + seleccionFechaFin.getText().toString());
                        alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Comprobar fechas de Torneo.
                                comprobarFechasTorneo(seleccionFechaInicio.getText().toString(), seleccionFechaFin.getText().toString());
                            }
                        });
                        alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(NuevoTorneo.this, "Volviendo a la Creación de Torneos...", Toast.LENGTH_SHORT).show();
                            }
                        });
                        alerta.create();
                        alerta.show();
                    }
                }
            }
        });
    }

    private void comprobarFechasTorneo(String inicioTorneo, String finTorneo) {
        db.collection("torneos")
                .whereEqualTo("fecha_inicio", inicioTorneo)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) { // Ya hay torneos que empiezan ese mismo día.

                            AlertDialog.Builder alerta = new AlertDialog.Builder(NuevoTorneo.this);
                            alerta.setTitle("YA EXISTEN TORNEOS QUE EMPIEZAN ESE DIA");
                            alerta.setMessage("¿Seguro que quieres crear un Nuevo Torneo en las fechas seleccionadoas");
                            alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Si decide que si quiere crearlo se llama la metodo para crear el torneo.
                                    crearNuevoTorneo(nombreTorneo.getText().toString(),
                                            seleccionFechaInicio.getText().toString(),
                                            seleccionFechaFin.getText().toString());
                                }
                            });
                            alerta.setNegativeButton("No", null);
                            alerta.create();
                            alerta.show();

                        } else {
                            // No hay torneos en ese rango de fechas, proceder a crear uno nuevo
                            crearNuevoTorneo(nombreTorneo.getText().toString(),
                                    seleccionFechaInicio.getText().toString(),
                                    seleccionFechaFin.getText().toString());
                        }
                    } else {
                        Toast.makeText(NuevoTorneo.this, "Error al verificar las fechas del torneo en Firebase.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void crearNuevoTorneo(String nombre, String fechaInicio, String fechaFin) {
        Map<String, Object> nuevoTorneo = new HashMap<>();
        nuevoTorneo.put("nombre", nombre);
        nuevoTorneo.put("fecha_inicio", fechaInicio);
        nuevoTorneo.put("fecha_fin", fechaFin);
        nuevoTorneo.put("ganador_uno", "");
        nuevoTorneo.put("ganador_dos", "");

        db.collection("torneos").add(nuevoTorneo)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(this, "Nuevo Torneo creado con Éxito", Toast.LENGTH_SHORT).show();
                        // Devolver un Ok a la actividad anterior para que refresque que lista de torneos.
                        Intent i = new Intent();
                        i.putExtra("REFRESCAR", "OK");
                        setResult(10, i);
                        finish();

                    } else {
                        Toast.makeText(this, "Fallo a la hora de crear el nuevo torneo a la base de datos", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private Date formatearFecha(String fecha) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
        try {
            return formatoFecha.parse(fecha);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}