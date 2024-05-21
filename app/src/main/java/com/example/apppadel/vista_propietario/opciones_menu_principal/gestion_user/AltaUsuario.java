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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AltaUsuario extends AppCompatActivity {
    EditText nombreUser, ape1User, ape2User, telefonoUser, correoUser;
    TextView seleccionFecha;
    Button botonCreacion;
    ImageView imagenCalendario;
    Switch switchSocio;
    FirebaseAuth auth;
    FirebaseFirestore db;
    String rolDelUsuario = "usuario", contraPorDefecto = "123456";
    Map<String, Object> nuevoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_usuario);

        nombreUser = findViewById(R.id.etNombreUser);
        ape1User = findViewById(R.id.etPrimerApe);
        ape2User = findViewById(R.id.etSegundoApe);
        telefonoUser = findViewById(R.id.etTelefono);
        correoUser = findViewById(R.id.etCorreo);

        seleccionFecha = findViewById(R.id.tvSeleccionFecha);

        botonCreacion = findViewById(R.id.botonCrearUser);

        imagenCalendario = findViewById(R.id.imagenCalendario);

        switchSocio = findViewById(R.id.switchSocio);

        // Inicialización de Firebase y autentificación
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

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

        nuevoUsuario = new HashMap<>();

        botonCreacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Validaciones de los campos a añadir
                if (!camposCompletos(nombreUser, ape1User, ape2User, telefonoUser, correoUser)) {
                    Toast.makeText(AltaUsuario.this, "Alguno de los campos no ha sido rellenado", Toast.LENGTH_SHORT).show();

                } else {
                    if (seleccionFecha.getText().toString().isEmpty()){
                        Toast.makeText(AltaUsuario.this, "El campo de la fecha no ha sido rellenado", Toast.LENGTH_SHORT).show();

                    } else {
                        //Validaciones de los Editext.
                        if (validarEmail(correoUser.getText().toString())) {
                            if (comprobarNumero(telefonoUser.getText().toString())){
                                if (contieneSoloLetras(nombreUser.getText().toString(), ape1User.getText().toString(), ape2User.getText().toString())) {
                                    // COMPRUEBA EL ROL QUE SE LE QUIERE DAR AL USUARIO
                                    if (switchSocio.isChecked()){
                                        rolDelUsuario = "socio";
                                    }

                                    // Se rellena el mapa con los datos del nuevo usuario
                                    nuevoUsuario.put("nombre", nombreUser.getText().toString());
                                    nuevoUsuario.put("primer_apellido", ape1User.getText().toString());
                                    nuevoUsuario.put("segundo_apellido", ape2User.getText().toString());
                                    nuevoUsuario.put("telefono", telefonoUser.getText().toString());
                                    nuevoUsuario.put("correo", correoUser.getText().toString());
                                    nuevoUsuario.put("contra", contraPorDefecto);
                                    nuevoUsuario.put("fecha_nacimiento", seleccionFecha.getText().toString());
                                    nuevoUsuario.put("rol", rolDelUsuario);
                                    nuevoUsuario.put("puntos", 0);

                                    // Se añade el nuevo usuario a la base de datos
                                    anadirUsuario(correoUser.getText().toString(), contraPorDefecto);

                                } else {
                                    Toast.makeText(AltaUsuario.this, "Nombre o apellidos con errores", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                telefonoUser.setError("Formato de teléfono erroneo (9 dígitos obligatorios)");
                                Toast.makeText(AltaUsuario.this, "El teléfono es incorrecto", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            correoUser.setError("Error de formato (Ej: nombre@gmail.com/es)");
                            Toast.makeText(AltaUsuario.this, "El correo no cumple el formato de Correo electrónico", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    private void anadirUsuario(String mail, String contraPorDefecto) {
        auth.createUserWithEmailAndPassword(mail, contraPorDefecto)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()){
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null){
                            db.collection("usuarios").document(user.getUid())
                                    .set(nuevoUsuario)
                                    .addOnSuccessListener(command -> {
                                        Toast.makeText(this, "Usuario añadido con éxito a la base de datos", Toast.LENGTH_SHORT).show();
                                        finish();

                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(this, "Fallo a la hora de añadir al Usuario a la base de datos", Toast.LENGTH_SHORT).show();
                                        finish();

                                    });
                        }
                    }
        })
                .addOnFailureListener(command -> {
                    // PARA AVISAR AL ADMINISTRADOR QUE NO SE PUEDE CREAR EL USUARIO CON ESE MISMO CORREO ELECTRONICO.
                    Toast.makeText(this, "No se puede crear ese usuario, correo existente", Toast.LENGTH_SHORT).show();
                    correoUser.setError("El correo ya existe en la base de datos");

                });
    }

    private boolean camposCompletos(EditText... campos) {
        for (EditText campo : campos) {
            if (campo.getText().toString().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean validarEmail (String email) {
        return email.contains("@") && (email.endsWith(".com") || email.endsWith(".es"));
    }

    public boolean comprobarNumero (String text) {
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