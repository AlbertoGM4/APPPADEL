package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user;

import android.app.DatePickerDialog;
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

public class AltaUsuario extends AppCompatActivity {
    EditText nombreUser, ape1User, ape2User, telefonoUser, correoUser, contraUser;
    TextView seleccionFecha;
    Button botonCreacion;
    ImageView imagenCalendario;
    Switch switchSocio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_usuario);

        nombreUser = findViewById(R.id.etNombreUser);
        ape1User = findViewById(R.id.etPrimerApe);
        ape2User = findViewById(R.id.etSegundoApe);
        telefonoUser = findViewById(R.id.etTelefono);
        correoUser = findViewById(R.id.etCorreo);
        contraUser = findViewById(R.id.etContra);

        seleccionFecha = findViewById(R.id.tvSeleccionFecha);

        botonCreacion = findViewById(R.id.botonCrearUser);

        imagenCalendario = findViewById(R.id.imagenCalendario);

        switchSocio = findViewById(R.id.switchSocio);

        imagenCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calendario con la fecha actual
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Crear un DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(AltaUsuario.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Aquí puedes hacer lo que quieras con la fecha seleccionada
                        String selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        seleccionFecha.setText(selectedDate);
                    }
                }, year, month, day);

                // Mostrar el DatePickerDialog
                datePickerDialog.show();
            }
        });

        botonCreacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!camposCompletos(nombreUser, ape1User, ape2User, telefonoUser, correoUser, contraUser)) {
                    Toast.makeText(AltaUsuario.this, "Alguno de los campos no ha sido rellenado", Toast.LENGTH_SHORT).show();

                } else {
                    if (seleccionFecha.getText().toString().isEmpty()){
                        Toast.makeText(AltaUsuario.this, "El campo de la fecha no ha sido rellenado", Toast.LENGTH_SHORT).show();

                    } else {
                        //Validaciones de los Editext.
                        if (validarEmail(correoUser.getText().toString())) {
                            if (comprobarNumero(telefonoUser.getText().toString())){
                                if (contieneSoloLetras(nombreUser.getText().toString(), ape1User.getText().toString(), ape2User.getText().toString())) {
                                    Toast.makeText(AltaUsuario.this, "¡Todos los campos están completos y son correctos!", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(AltaUsuario.this, "Creación del nuevo Usuario con Exito", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(AltaUsuario.this, "Nombre o apellidos con errores", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AltaUsuario.this, "El teléfono es incorrecto", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AltaUsuario.this, "El correo no cumple el formato de Correo electrónico", Toast.LENGTH_SHORT).show();
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