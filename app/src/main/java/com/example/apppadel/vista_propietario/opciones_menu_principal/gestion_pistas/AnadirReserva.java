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
import com.example.apppadel.models.Pista;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user.AltaUsuario;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user.BajaUsuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnadirReserva extends AppCompatActivity {
    ImageView imagenCalendario;
    ListView listaHorasLibres;
    ArrayAdapter<String> adapterSelectPista;
    ArrayAdapter<String> adaptador;
    TextView textoFechaSeleccionada;
    Spinner spinnerPistas;
    FirebaseFirestore db;
    String selectedSpinner, idPistaSeleccionada;
    List<String> horasOcupadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_reserva);

        //Inicializar el Firestore.
        db = FirebaseFirestore.getInstance();

        imagenCalendario = findViewById(R.id.imagenCalendarioPistasLibres);
        listaHorasLibres = findViewById(R.id.listaHorasLibresPista);
        textoFechaSeleccionada = findViewById(R.id.tvSeleccionFechaReservaPista);

        spinnerPistas =  findViewById(R.id.spinnerPistas);
        spinnerPistas.setEnabled(false);
        List<String> pistas = new ArrayList<>();
        pistas.add("Pistas:");
        pistas.add("Pista 1");
        pistas.add("Pista 2");
        pistas.add("Pista 3");
        adapterSelectPista = new ArrayAdapter<>(this, R.layout.spinner_item, pistas);
        spinnerPistas.setAdapter(adapterSelectPista);

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
                            listarHorasDePista(idPistaSeleccionada, textoFechaSeleccionada.getText().toString());

                        } else if (selectedSpinner.equals("Pista 2")) { //ID PISTA 2: MuounKd3Wt6kqpIDwbpe
                            idPistaSeleccionada = "MuounKd3Wt6kqpIDwbpe";
                            listarHorasDePista(idPistaSeleccionada, textoFechaSeleccionada.getText().toString());

                        }else { //ID PISTA 3: vwAEeoRlJLkEdQFWQxjC
                            idPistaSeleccionada = "vwAEeoRlJLkEdQFWQxjC";
                            listarHorasDePista(idPistaSeleccionada, textoFechaSeleccionada.getText().toString());
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(AnadirReserva.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Aquí puedes hacer lo que quieras con la fecha seleccionada
                        String selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        textoFechaSeleccionada.setText(selectedDate);

                        if (!spinnerPistas.isEnabled()) {
                            //Pongo el Spinner seleccionable, una vez se selecciona la fecha.
                            spinnerPistas.setEnabled(true);
                            listaHorasLibres.setAdapter(adaptador);

                        } else {
                            // A modo de actualizacion de la lista.
                            listarHorasDePista(idPistaSeleccionada, textoFechaSeleccionada.getText().toString());
                        }
                    }
                }, year, month, day);

                // Mostrar el DatePickerDialog
                datePickerDialog.show();
            }
        });

        listaHorasLibres.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String horaSelecionada = adaptador.getItem(position);

                AlertDialog.Builder alerta = new AlertDialog.Builder(AnadirReserva.this);
                alerta.setTitle("NUEVA RESERVA");
                alerta.setMessage("¿Desea confirmar la reserva de la Hora Seleccionada?\n" +
                        "- Hora Seleccionada: " + horaSelecionada + "\n" +
                        "- Pista: " + selectedSpinner);
                alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Metodo para crear la reserva en Firestore.
                        crearNuevaReserva(horaSelecionada);
                    }
                });
                alerta.setNegativeButton("Cancelar", null);
                alerta.create();
                alerta.show();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void crearNuevaReserva(String horaSelecionada) {
        // Cálculo de la hora de fin dependiendo de la hora de inicio seleccionada.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(horaSelecionada, formatter);
        LocalTime newTime = time.plusHours(1).plusMinutes(30);
        String horaFinalReserva = newTime.format(formatter);

        //Se rellena un mapa con la informacion que se va a añadir a la coleccion de reservas, como una nueva reserva
        Map<String, Object> nuevaReserva = new HashMap<>();
        nuevaReserva.put("id_user", FirebaseAuth.getInstance().getCurrentUser().getUid()); // Debería ser el de admin
        nuevaReserva.put("id_pista", idPistaSeleccionada);
        nuevaReserva.put("fecha", textoFechaSeleccionada.getText().toString());
        nuevaReserva.put("hora_inicio", horaSelecionada);
        nuevaReserva.put("hora_fin", horaFinalReserva);

        db.collection("reservas").add(nuevaReserva)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(this, "Reserva realizada y registrada con éxito", Toast.LENGTH_SHORT).show();
                        adaptador.remove(horaSelecionada);
                        adaptador.notifyDataSetChanged();

                    } else {
                        Toast.makeText(this, "Fallo a la hora de añadir la nueva reserva a la base de datos", Toast.LENGTH_SHORT).show();
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
                        horasDisponibles(horasOcupadas);

                    } else {
                        Toast.makeText(this, "Fallo en la consulta de horas del dia y pista seleccionado", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void horasDisponibles(List<String> hOcupadas) {
        //Lista de todas las horas.
        List<String> listaHorasDisponibles = new ArrayList<>();
        listaHorasDisponibles.add("9:00");
        listaHorasDisponibles.add("10:30");
        listaHorasDisponibles.add("12:00");
        listaHorasDisponibles.add("13:30");
        listaHorasDisponibles.add("15:00");
        listaHorasDisponibles.add("16:30");
        listaHorasDisponibles.add("18:00");
        listaHorasDisponibles.add("19:30");
        listaHorasDisponibles.add("21:00");
        listaHorasDisponibles.add("22:30");

        Toast.makeText(this, "Horas ocupadas: " + hOcupadas.size(), Toast.LENGTH_SHORT).show();

        //Asi se quedan las horas de pista que no estan reservadas.
        listaHorasDisponibles.removeAll(hOcupadas);

        adaptador = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, listaHorasDisponibles);
        listaHorasLibres.setAdapter(adaptador); //Aqui ya deberia mostrar la lista de horas disponibles para la reserva.

    }
}