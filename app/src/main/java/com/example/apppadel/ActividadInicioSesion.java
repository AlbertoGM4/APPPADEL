package com.example.apppadel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apppadel.vista_propietario.MenuPricipalProp;
import com.example.apppadel.vista_usuario.MenuPrincipalSocio;
import com.example.apppadel.vista_usuario.MenuPrincipalUser;

public class ActividadInicioSesion extends AppCompatActivity {
    Button botonLogIn;
    EditText correo, contra;
    ImageView imagenOcultar;
    boolean esVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        botonLogIn = findViewById(R.id.botonInicioSesion);

        correo = findViewById(R.id.etCorreoLogIn);
        contra = findViewById(R.id.etContraLogIn);

        imagenOcultar = findViewById(R.id.imagenOcultarContra);

        botonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correo.getText().toString().equals("admin") && contra.getText().toString().equals("1234")){
                    Intent i = new Intent(ActividadInicioSesion.this, MenuPricipalProp.class);
                    startActivity(i);
                    correo.setText("");

                } else if (correo.getText().toString().equals("socio") && contra.getText().toString().equals("1234")) {
                    Intent intent = new Intent(ActividadInicioSesion.this, MenuPrincipalSocio.class);
                    startActivity(intent);
                    correo.setText("");

                } else if (correo.getText().toString().equals("user") && contra.getText().toString().equals("1234")) {
                    Intent intent = new Intent(ActividadInicioSesion.this, MenuPrincipalUser.class);
                    startActivity(intent);
                    correo.setText("");

                } else {
                    Toast.makeText(ActividadInicioSesion.this, "No hay ningún inicio de sesión con esas credenciales", Toast.LENGTH_SHORT).show();
                    correo.setText("");
                    contra.setText("");
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
}