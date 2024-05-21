package com.example.apppadel.vista_usuario.opciones_menu_user.reservas;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_pistas.EliminarReserva;
import com.example.apppadel.vista_usuario.opciones_menu_user.ConsultarUsuarios;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListaReservas extends AppCompatActivity {
    ImageView calendario;
    TextView fechaSeleccionada;
    ListView listView;
    List<Reserva> listaReservas;
    ArrayAdapter<Reserva> adapter;
    FirebaseFirestore db;
    Reserva reserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reservas);

        db = FirebaseFirestore.getInstance();

        listView = findViewById(R.id.listaReservasPorUsuario);
        calendario = findViewById(R.id.imagenCalendarioHorasReservadasUsuario);
        fechaSeleccionada = findViewById(R.id.tvSeleccionFechaPistasReservadasUsuario);

        Toast.makeText(this, "Seleccione una fecha para ver las reservas de esa fecha.", Toast.LENGTH_SHORT).show();

        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy", Locale.getDefault());

                try {
                    // Intenta parsear la fecha desde el TextView, si existe
                    String fechaExistente = fechaSeleccionada.getText().toString();
                    Date date = sdf.parse(fechaExistente);
                    calendar.setTime(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Crear un DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(ListaReservas.this, new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Aquí puedes hacer lo que quieras con la fecha seleccionada
                        String selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        fechaSeleccionada.setText(selectedDate);

                        listarReservasDeUsuario(fechaSeleccionada.getText().toString());

                    }
                }, year, month, day);

                // Establece la fecha mínima al día actual
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                reserva = adapter.getItem(position);

                String nombrePista = "";
                if (reserva.getIdPista().equals("oU77zxeWDqsWnfHv83c8")){
                    nombrePista = "Pista 1";

                } else if (reserva.getIdPista().equals("MuounKd3Wt6kqpIDwbpe")){
                    nombrePista = "Pista 2";
                } else {
                    nombrePista = "Pista 3";
                }

                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaReservas.this);
                alerta.setTitle("INFO DE RESERVAS DEL USUARIO");
                alerta.setMessage("- Fecha: " + reserva.getFechaReserva() + "\n" +
                        "- Hora de Inicio: " + reserva.getHoraInicioReserva() + "\n" +
                        "- Hora de Fin: " + reserva.getHoraFinReserva() + "\n" +
                        "- Pista: " + nombrePista);
                alerta.setPositiveButton("Volver", null);
                alerta.create();
                alerta.show();
            }
        });

    }

    private void listarReservasDeUsuario(String fechaSelect) {
        listaReservas = new ArrayList<>();

        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        String idUsuario = usuario.getUid();

        db.collection("reservas")
                .whereEqualTo("id_user", idUsuario)
                .whereEqualTo("fecha", fechaSelect)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        listaReservas.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String idReserva = document.getId();
                            String idPista = document.getString("id_pista");
                            String fecha = document.getString("fecha");
                            String horaInicio = document.getString("hora_inicio");
                            String horaFin = document.getString("hora_fin");

                            listaReservas.add(new Reserva(idReserva, horaInicio, horaFin, fecha, idPista));
                        }

                        if (listaReservas.isEmpty()){
                            Toast.makeText(this, "No hay horas reservadas para este día seleccionado", Toast.LENGTH_SHORT).show();
                        }

                        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaReservas);
                        listView.setAdapter(adapter);

                    } else {
                        Toast.makeText(this, "Fallo a la hora de encontrar registros en la base de datos", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}