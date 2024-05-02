package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;

public class BajaUsuario extends AppCompatActivity {
    ListView listaUsuarios;
    Button botonBaja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baja_usuario);

        listaUsuarios = findViewById(R.id.listaUsuariosBaja);

        botonBaja = findViewById(R.id.botonConfirmarBaja);

        botonBaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Aqui se realizara la eliminacion del Usuario que se ha seleccionado en la lista de usuarios.


                Toast.makeText(BajaUsuario.this, "Usuario con ID X, dado de Baja Permanente", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        listaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Aqui se realiza la seleccion del item (Usuario) de la lista

                //Info del Usuario seleccionado
                AlertDialog.Builder alerta = new AlertDialog.Builder(BajaUsuario.this);
                alerta.setTitle("INFORMACIÓN SELECCIÓN");
                alerta.setMessage("ID: " + "\n" +
                        "Nombre: " + "\n" +
                        "Primer Apellido: " + "\n" +
                        "Correo: ");
                alerta.create();
                alerta.show();
            }
        });

    }
}