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
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ModificarReserva extends AppCompatActivity {
    ImageView imagenCalendarioEdit;
    ListView listaHorasReservadasEdit;
    ArrayAdapter<String> adapter, adapterPistas;
    ArrayList<String> listaHorasP1, listaHorasP2, listaHorasP3;
    TextView textoFechaSeleccionadaEdit;
    Spinner spinnerPistas;
    Context context = this;
    ActivityResultLauncher lanzador;
    String posSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_reserva);

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
        adapterPistas = new ArrayAdapter<>(this, R.layout.spinner_item, pistas);
        spinnerPistas.setAdapter(adapterPistas);


        listaHorasP1 = new ArrayList<>();
        listaHorasP1.add("08:00");
        listaHorasP1.add("09:30");
        listaHorasP1.add("11:00");
        listaHorasP1.add("12:30");
        listaHorasP1.add("14:00");
        listaHorasP1.add("17:00");
        listaHorasP1.add("18:30");

        listaHorasP2 = new ArrayList<>();
        listaHorasP2.add("11:00");
        listaHorasP2.add("12:30");
        listaHorasP2.add("14:00");
        listaHorasP2.add("18:30");
        listaHorasP2.add("20:30");


        listaHorasP3 = new ArrayList<>();
        listaHorasP3.add("08:00");
        listaHorasP3.add("09:30");
        listaHorasP3.add("17:00");
        listaHorasP3.add("18:30");

        spinnerPistas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSpinner = parent.getItemAtPosition(position).toString();

                if (!spinnerPistas.isEnabled()){
                    Toast.makeText(context, "Primero debes seleccionar una Fecha", Toast.LENGTH_SHORT).show();

                } else {
                    if (position == 0){
                        Toast.makeText(context, "Seleccione una Pista", Toast.LENGTH_SHORT).show();

                    }else {
                        if (selectedSpinner.equals("Pista 1")){
                            adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, listaHorasP1);

                        } else if (selectedSpinner.equals("Pista 2")) {
                            adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, listaHorasP2);

                        }else {
                            adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, listaHorasP3);
                        }

                        listaHorasReservadasEdit.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
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
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Aquí puedes hacer lo que quieras con la fecha seleccionada
                        String selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        textoFechaSeleccionadaEdit.setText(selectedDate);

                        //Pongo el Spinner seleccionable, una vez se selecciona la fecha.
                        spinnerPistas.setEnabled(true);

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
                posSelect = adapter.getItem(position);

                AlertDialog.Builder alerta = new AlertDialog.Builder(ModificarReserva.this);
                alerta.setTitle("ALERTA");
                alerta.setMessage("¿Desea Modificar la reserva de la Hora Seleccionada?\n" +
                        "- Hora Seleccionada: " + posSelect + "\n" +
                        "del día " + textoFechaSeleccionadaEdit.getText().toString());
                alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Abre la nueva actividad
                        Intent i = new Intent(ModificarReserva.this, ModificarReservaNuevaFecha.class);
                        i.putExtra("DIA", textoFechaSeleccionadaEdit.getText().toString());
                        i.putExtra("HORA", posSelect);
                        lanzador.launch(i);

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

        lanzador = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if (o.getResultCode() == RESULT_OK) {
                    //Se elimina la Hora que coincida con la hora que se pasa aqui.
                    Intent data = o.getData();
                    String horaOk = data.getStringExtra("HORA");
                    if (horaOk.equals("OK")){
                        adapter.remove(posSelect);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(context, "Hora " + posSelect + " reservada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}