package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
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
        String nomAntiguo = i.getStringExtra("NOMBRE");

        nombreEdit = findViewById(R.id.etNombreUserEditar);
        ape1Edit = findViewById(R.id.etPrimerApeEditar);
        ape2Edit = findViewById(R.id.etSegundoApeEditar);
        telefonoEdit = findViewById(R.id.etTelefonoEditar);
        correoEdit = findViewById(R.id.etCorreoEditar);
        contraEdit = findViewById(R.id.etContraEditar);

        //Campos del intento de la acitividad anterior
        nombreEdit.setText(i.getStringExtra("NOMBRE"));

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
                if (!camposCompletos(nombreEdit, ape1Edit, ape2Edit, telefonoEdit, correoEdit, contraEdit)) {
                    Toast.makeText(VistaFormularioEdicion.this, "Alguno de los campos no ha sido rellenado", Toast.LENGTH_SHORT).show();

                } else {
                    if (seleccionFechaEdit.getText().toString().isEmpty()){
                        Toast.makeText(VistaFormularioEdicion.this, "El campo de la fecha no ha sido rellenado", Toast.LENGTH_SHORT).show();

                    } else {
                        //Validaciones de los Editext.
                        if (validarEmail(correoEdit.getText().toString())) {
                            if (comprobarNumero(telefonoEdit.getText().toString())){
                                if (contieneSoloLetras(nombreEdit.getText().toString(), ape1Edit.getText().toString(), ape2Edit.getText().toString())) {

                                    AlertDialog.Builder alerta = new AlertDialog.Builder(VistaFormularioEdicion.this);
                                    alerta.setTitle("EDICIÓN DE USUARIO");
                                    alerta.setMessage("¿Confirmar la Edición del Usuario Seleccionado?\n" +
                                            "(*Usuario Antiguo: " + nomAntiguo + "*)\n" +
                                            "(*Nuevo Usuario: " + nombreEdit.getText().toString() + "*)");
                                    alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(VistaFormularioEdicion.this, "Todos los campos están completos!", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(VistaFormularioEdicion.this, "Modificación del Usuario realizada con Éxito.", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                                    alerta.setNegativeButton("Volver", null);
                                    alerta.create();
                                    alerta.show();

                                } else {
                                    Toast.makeText(VistaFormularioEdicion.this, "Nombre o apellidos con errores", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(VistaFormularioEdicion.this, "El teléfono es incorrecto", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(VistaFormularioEdicion.this, "El correo no cumple el formato de Correo electrónico", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
    private boolean camposCompletos(EditText... campos) {
        for (EditText campo : campos) {
            if (campo.getText().toString().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    };

    public boolean validarEmail (String email) {
        return email.contains("@") && (email.endsWith(".com") || email.endsWith(".es"));
    }

    public static boolean comprobarNumero (String text) {
        return text.matches("[0-9]{9}");
    }

    public boolean contieneSoloLetras(String nombre, String ape1, String ape2) {
        //Comprueba que sean solo letras y espacios.
        boolean nombreValido = nombre.matches("[\\p{L} ]+");
        boolean ape1Valido = ape1.matches("[\\p{L} ]+");
        boolean ape2Valido = ape2.matches("[\\p{L} ]+");

        // Devuelve true si todos los campos contienen solo letras
        return nombreValido && ape1Valido && ape2Valido;
    }
}