package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_torneos;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user.AltaUsuario;

import java.util.Calendar;

public class NuevoTorneo extends AppCompatActivity {
    EditText nombreTorneo;
    ImageView calendatioInicio, calendarioFin;
    Button btnCrearTorneo;
    TextView seleccionFechaInicio, seleccionFechaFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_torneo);

        nombreTorneo = findViewById(R.id.etNombreTorneo);

        calendatioInicio = findViewById(R.id.imagenCalendarioInicioTorneo);
        calendarioFin = findViewById(R.id.imagenCalendarioFinTorneo);

        btnCrearTorneo = findViewById(R.id.botonCreacionTorneo);

        seleccionFechaInicio = findViewById(R.id.tvSeleccionFechaInicio);
        seleccionFechaFin = findViewById(R.id.tvSeleccionFechaFin);

        calendatioInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calendario con la fecha actual
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Crear un DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(NuevoTorneo.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Aquí puedes hacer lo que quieras con la fecha seleccionada
                        String selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        seleccionFechaInicio.setText(selectedDate);
                    }
                }, year, month, day);

                // Mostrar el DatePickerDialog
                datePickerDialog.show();
            }
        });

        calendarioFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calendario con la fecha actual
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Crear un DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(NuevoTorneo.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Aquí puedes hacer lo que quieras con la fecha seleccionada
                        String selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        seleccionFechaFin.setText(selectedDate);
                    }
                }, year, month, day);

                // Mostrar el DatePickerDialog
                datePickerDialog.show();
            }
        });

        btnCrearTorneo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cracion del nuevo Torneo
                if (nombreTorneo.getText().toString().isEmpty() || seleccionFechaInicio.getText().toString().isEmpty() || seleccionFechaFin.getText().toString().isEmpty()){
                    Toast.makeText(NuevoTorneo.this, "Faltan campos por rellenar para poder crear el Nuevo Torneo", Toast.LENGTH_SHORT).show();

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
                            AlertDialog.Builder alertaConfirmacion = new AlertDialog.Builder(NuevoTorneo.this);
                            alertaConfirmacion.setTitle("Creación del Nuevo Torneo");
                            alertaConfirmacion.setMessage("Torneo con nombre: " + nombreTorneo.getText().toString() +
                                    ", creado con éxito\nSe reservará la Pista 1 desde: " +
                                    seleccionFechaInicio.getText().toString() + ", hasta: " +
                                    seleccionFechaFin.getText().toString() + " al completo para la realización del Torneo");
                            alertaConfirmacion.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            alertaConfirmacion.create();
                            alertaConfirmacion.show();
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
        });

    }
}