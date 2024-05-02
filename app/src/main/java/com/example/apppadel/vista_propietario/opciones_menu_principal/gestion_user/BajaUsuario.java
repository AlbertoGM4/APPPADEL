package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user;

import android.content.DialogInterface;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baja_usuario);

        listaUsuarios = findViewById(R.id.listaUsuariosBaja);

        listaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Aqui se realiza la seleccion del item (Usuario) de la lista
                //Info del Usuario seleccionado
                AlertDialog.Builder alerta = new AlertDialog.Builder(BajaUsuario.this);
                alerta.setTitle("ALERTA");
                alerta.setMessage("¿Dar de Baja al Usuario Seleccionado?");
                alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Dar de baja al Usuario
                        Toast.makeText(BajaUsuario.this, "Dando de Baja al Usuario Seleccionado...", Toast.LENGTH_SHORT).show();
                        //Una vez confirmada la Baja del Usuario, refrescar la lista de Usuarios.
                    }
                });
                alerta.create();
                alerta.show();
            }
        });

    }
}