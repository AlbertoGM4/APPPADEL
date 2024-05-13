package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;
import com.example.apppadel.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class VistaFormularioEdicion extends AppCompatActivity {

    EditText nombreEdit, ape1Edit, ape2Edit, telefonoEdit, contraEdit;
    TextView seleccionFechaEdit;
    Button botonCreacionEdit;
    ImageView imagenCalendarioEdit;
    Switch switchSocioEdit;
    String correoAntiguo, contraAntigua, rol, idDelUsuario;
    Map<String, Object> nuevosDatos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_formulario_edicion);

        Usuario usuario = getIntent().getParcelableExtra("COMPLETE_USER");

        nombreEdit = findViewById(R.id.etNombreUserEditar);
        ape1Edit = findViewById(R.id.etPrimerApeEditar);
        ape2Edit = findViewById(R.id.etSegundoApeEditar);
        telefonoEdit = findViewById(R.id.etTelefonoEditar);
        contraEdit = findViewById(R.id.etContraEditar);
        seleccionFechaEdit = findViewById(R.id.tvSeleccionFechaEditar);
        imagenCalendarioEdit = findViewById(R.id.imagenCalendarioEditar);
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

        switchSocioEdit = findViewById(R.id.switchSocioEditar);
        botonCreacionEdit = findViewById(R.id.botonConfirmarEdicion);

        // Relleno de campos del Formulario, Datos del usuario seleccionado.
        nombreEdit.setText(usuario.getNombreUser());
        ape1Edit.setText(usuario.getPrimerApellido());
        ape2Edit.setText(usuario.getSegundoApellido());
        telefonoEdit.setText(usuario.getTelefonoUser());
        contraEdit.setText(usuario.getContrasenaUser());
        seleccionFechaEdit.setText(usuario.getFechaNacUser());

        rol = usuario.getRol();
        if (rol.equals("socio")){
            switchSocioEdit.setChecked(true);
        } else {
            switchSocioEdit.setChecked(false);
        }

        //Para iniciar sesion en los credenciales.
        correoAntiguo = usuario.getCorreoElectronico();
        contraAntigua = usuario.getContrasenaUser();
        idDelUsuario = usuario.getiDUser();

        botonCreacionEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!camposCompletos(nombreEdit, ape1Edit, ape2Edit, telefonoEdit, contraEdit)) {
                    Toast.makeText(VistaFormularioEdicion.this, "Alguno de los campos no ha sido rellenado", Toast.LENGTH_SHORT).show();

                } else {
                    if (seleccionFechaEdit.getText().toString().isEmpty()){
                        Toast.makeText(VistaFormularioEdicion.this, "El campo de la fecha no ha sido rellenado", Toast.LENGTH_SHORT).show();

                    } else {
                        //Validaciones de los Editext.
                        if (comprobarNumero(telefonoEdit.getText().toString())){
                            if (contieneSoloLetras(nombreEdit.getText().toString(), ape1Edit.getText().toString(), ape2Edit.getText().toString())) {

                                //Aqui ya estan comprobados todos los campos y tódo es correcto.
                                AlertDialog.Builder alerta = new AlertDialog.Builder(VistaFormularioEdicion.this);
                                alerta.setTitle("EDICIÓN DE USUARIO");
                                alerta.setMessage("¿Confirmar la Edición del Usuario?");
                                alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        cambiarContraUser(correoAntiguo, contraAntigua);
                                    }
                                });
                                alerta.setNegativeButton("Cancelar", null);
                                alerta.create();
                                alerta.show();

                            } else {
                                Toast.makeText(VistaFormularioEdicion.this, "Nombre o apellidos con errores", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            telefonoEdit.setError("Mal formato de teléfono (9 dígitos obligatorios)");
                            Toast.makeText(VistaFormularioEdicion.this, "El teléfono es incorrecto", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }

    private void cambiarContraUser(String correo, String contra) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(correo, contra)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        FirebaseUser usuario = auth.getCurrentUser();
                        usuario.updatePassword(contraEdit.getText().toString())
                                .addOnCompleteListener(command1 -> {
                                    if (command1.isSuccessful()){
                                        //Contraseña actualizada.
                                        actualizarFirestore(idDelUsuario, nuevosDatos);

                                    } else {
                                        Toast.makeText(this, "Fallo en la actualizacion de la contraseña", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(this, "Fallo en el inicio de sesion del Usuario a modificar", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void actualizarFirestore(String idUser, Map<String, Object> nuevosDatos) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference doc = db.collection("usuarios").document(idUser); //Obtengo la coleccion pertinente para hacer los cambios.

        String rol;
        if (switchSocioEdit.isChecked()){
            rol = "socio";
        } else {
            rol = "usuario";
        }

        nuevosDatos = new HashMap<>();
        nuevosDatos.put("nombre", nombreEdit.getText().toString());
        nuevosDatos.put("primer_apellido", ape1Edit.getText().toString());
        nuevosDatos.put("segundo_apellido", ape2Edit.getText().toString());
        nuevosDatos.put("telefono", telefonoEdit.getText().toString());
        nuevosDatos.put("contra", contraEdit.getText().toString());
        nuevosDatos.put("fecha_nacimiento", seleccionFechaEdit.getText().toString());
        nuevosDatos.put("rol", rol);

        doc.update(nuevosDatos)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(this, "Actualización de los datos del usuario llevada a cabo con éxito", Toast.LENGTH_SHORT).show();

                        //Pasar info para la actualización de la lista.
                        Intent i = new Intent();
                        i.putExtra("RESPUESTA", "OK");
                        setResult(RESULT_OK, i);

                        finish();

                    } else {
                        Toast.makeText(this, "Fallo en la actualización de los datos del usuario", Toast.LENGTH_SHORT).show();
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