package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;

import java.util.Calendar;

public class VistaFormularioEdicion extends AppCompatActivity {

    EditText nombreEdit, ape1Edit, ape2Edit, telefonoEdit, correoEdit, contraEdit;
    TextView seleccionFechaEdit;
    Button botonCreacionEdit;
    ImageView imagenCalendarioEdit;
    Switch switchSocioEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_formulario_edicion);

        //Informacion del intent.
        Intent i = getIntent();

        nombreEdit = findViewById(R.id.etNombreUserEditar);
        ape1Edit = findViewById(R.id.etPrimerApeEditar);
        ape2Edit = findViewById(R.id.etSegundoApeEditar);
        telefonoEdit = findViewById(R.id.etTelefonoEditar);
        correoEdit = findViewById(R.id.etCorreoEditar);
        contraEdit = findViewById(R.id.etContraEditar);

        //Campos del intento de la acitividad anterior
        nombreEdit.setText(i.getStringExtra("NOMBRE"));
        ape1Edit.setText(i.getStringExtra("APE1"));
        ape2Edit.setText(i.getStringExtra("APE2"));

        seleccionFechaEdit = findViewById(R.id.tvSeleccionFechaEditar);

        imagenCalendarioEdit = findViewById(R.id.imagenCalendarioEditar);

        switchSocioEdit = findViewById(R.id.switchSocioEditar);

        botonCreacionEdit = findViewById(R.id.botonConfirmarEdicion);

        imagenCalendarioEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calendario con la fecha actual
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Crear un DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(VistaFormularioEdicion.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Aquí puedes hacer lo que quieras con la fecha seleccionada
                        String selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        seleccionFechaEdit.setText(selectedDate);
                    }
                }, year, month, day);

                // Mostrar el DatePickerDialog
                datePickerDialog.show();
            }
        });

        botonCreacionEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VistaFormularioEdicion.this, "Edicioón del Usuario X, realizada con éxito", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}