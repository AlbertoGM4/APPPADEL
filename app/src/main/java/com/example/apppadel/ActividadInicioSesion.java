package com.example.apppadel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apppadel.vista_propietario.MenuPricipalProp;
import com.example.apppadel.vista_usuario.MenuPrincipalSocio;
import com.example.apppadel.vista_usuario.MenuPrincipalUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ActividadInicioSesion extends AppCompatActivity {
    Button botonLogIn;
    EditText correo, contra;
    ImageView imagenOcultar;
    boolean esVisible = false;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        botonLogIn = findViewById(R.id.botonInicioSesion);

        correo = findViewById(R.id.etCorreoLogIn);
        contra = findViewById(R.id.etContraLogIn);

        imagenOcultar = findViewById(R.id.imagenOcultarContra);

        auth = FirebaseAuth.getInstance();

        botonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (correo.getText().toString().isEmpty()){
                    Toast.makeText(ActividadInicioSesion.this, "Es necesario añadir un correo electrónico", Toast.LENGTH_SHORT).show();
                    if (contra.getText().toString().isEmpty()) {
                        Toast.makeText(ActividadInicioSesion.this, "Y también una contraseña", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    if (contra.getText().toString().isEmpty()) {
                        Toast.makeText(ActividadInicioSesion.this, "Es necesario añadir una contraseña", Toast.LENGTH_SHORT).show();

                    } else {
                        // Campos de correo y contraseña rellenados.
                        //Comporbar el inicio de sesion
                        loginUser(correo.getText().toString(), contra.getText().toString());
                    }
                }
            }
        });

        imagenOcultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!esVisible) {
                    // Mostrar la contraseña
                    contra.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
                    esVisible = true;
                    imagenOcultar.setImageResource(R.drawable.ver);

                } else {
                    // Ocultar la contraseña
                    contra.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    esVisible = false;
                    imagenOcultar.setImageResource(R.drawable.esconder);
                }
            }
        });
    }

    private void loginUser (String correo, String contrasena) {
        auth.signInWithEmailAndPassword(correo, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser usuario = auth.getCurrentUser();

                    if (usuario != null){
                        tipoUser(usuario);
                    }

                } else {
                    Toast.makeText(ActividadInicioSesion.this, "Datos incorrectos al iniciar sesión", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void tipoUser(FirebaseUser usuario) {

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.collection("usuarios").document(usuario.getUid()).get()
                .addOnSuccessListener(documentSnapshot -> {

                    if (documentSnapshot.exists()){
                        // Comprobacion de la contraseña por defecto
                        if (documentSnapshot.getString("contra").equals("123456")){
                            // Cambio de contra por la personal del usuario.
                            dialogoCambioContra(usuario);

                        } else {
                            //Apertura de la ventan correspondiente
                            String nom = documentSnapshot.getString("nombre");
                            String rol = documentSnapshot.getString("rol");
                            seleccionarVentanaMenu(nom, rol);
                        }

                    } else {
                        Toast.makeText(this, "Datos del Usuario no encontrados en la Base de Datos", Toast.LENGTH_SHORT).show();

                    }
        })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Fallo en la identidicación", Toast.LENGTH_SHORT).show();
                });
    }

    private void dialogoCambioContra(FirebaseUser usuario) {
        LayoutInflater inflater = getLayoutInflater();
        View vista = inflater.inflate(R.layout.layout_cambio_contra, null);

        //Declarar los cambios del nuevo Layout.
        EditText nuevaContra = vista.findViewById(R.id.newPassword);
        EditText repetirNuevaContra = vista.findViewById(R.id.repetNewPassword);

        //Se crea el AlertDialog.
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Cambiar Contraseña")
                .setMessage("Para continuar debe añádir una nueva contraseña\n- La nueva contraseña tiene que tener 6 carácteres o más, y no puede ser '123456'")
                .setView(vista)
                .setPositiveButton("Cambiar", (dialogInterface, i) -> {
                    if (nuevaContra.getText().toString().equals(repetirNuevaContra.getText().toString())){
                        //Las contraseñas coinciden y se procede al cambio de contraseña
                        cambiarContrasena(usuario, nuevaContra.getText().toString().trim());

                    } else {
                        Toast.makeText(this, "Las contraseñas no coinciden, revise lo escrito", Toast.LENGTH_SHORT).show();
                        repetirNuevaContra.setError("Esta contraseña no coincide con la de encima");

                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();

        dialog.show();
    }

    private void cambiarContrasena(FirebaseUser usuario, String nuevaContra) {
        //Actualizacion de la parte de la Autentificacion del usuario.
        AuthCredential credential = EmailAuthProvider.getCredential(usuario.getEmail(), "123456");
        usuario.reauthenticate(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                usuario.updatePassword(nuevaContra).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Toast.makeText(this, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show();
                        //Cambio de contraseña en el Firestore.
                        actualizarContraBD(usuario, nuevaContra);

                    } else {
                        Toast.makeText(this, "Error al actualizar contraseña", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Error en la re-identificación del usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarContraBD(FirebaseUser usuario, String nuevaContra) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference doc = firestore.collection("usuarios").document(usuario.getUid());

        doc.update("contra", nuevaContra)
                .addOnSuccessListener(command -> {
                    Toast.makeText(this, "Contraseña del Usuario cambiada correctamente, iniciando sesión...", Toast.LENGTH_SHORT).show();
                    firestore.collection("usuarios").document(usuario.getUid()).get().addOnSuccessListener(documentSnapshot -> {
                        //Se inicia sesion una vez cambiada la contraseña.
                        seleccionarVentanaMenu(documentSnapshot.getString("nombre"), documentSnapshot.getString("rol"));
                    });

                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Fallo en la actualización de la contraseña del usuario en la base de datos", Toast.LENGTH_SHORT).show();
                });

    }

    private void seleccionarVentanaMenu(String nomUser, String rolUser) {

        if (rolUser.equals("admin")){
            Intent i = new Intent(ActividadInicioSesion.this, MenuPricipalProp.class);
            startActivity(i);
            contra.setText("");

        } else if (rolUser.equals("socio")) {
            Intent intent = new Intent(ActividadInicioSesion.this, MenuPrincipalSocio.class);
            startActivity(intent);
            contra.setText("");

        } else {
            Intent intent = new Intent(ActividadInicioSesion.this, MenuPrincipalUser.class);
            startActivity(intent);
            contra.setText("");

        }
        Toast.makeText(this, "¡Hola " + nomUser + "!, que gusto verte.", Toast.LENGTH_SHORT).show();
    }
}