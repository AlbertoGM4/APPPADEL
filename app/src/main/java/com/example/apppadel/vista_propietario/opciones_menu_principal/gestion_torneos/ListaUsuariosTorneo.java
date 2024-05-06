package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_torneos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user.BajaUsuario;

import java.util.ArrayList;

public class ListaUsuariosTorneo extends AppCompatActivity {
    ListView listaUsuarios;
    ArrayList<String> lista;
    ArrayAdapter<String> adapter;
    EditText nombreGanador;
    String nombreSeleccionWinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios_torneo);

        Intent i = getIntent();
        String numIntegrante = i.getStringExtra("BOTON_PULSADO");

        nombreGanador = findViewById(R.id.etNombreUserSeleccionGanadores);
        listaUsuarios = findViewById(R.id.listaUsuariosSeleccionGanadores);

        lista = new ArrayList<>();

        lista.add("Alberto Guillen");
        lista.add("Ivan Fernandez");
        lista.add("Victor Barrio");
        lista.add("Usuario 4");
        lista.add("Usuario 5");
        lista.add("Usuario 6");
        lista.add("Usuario 7");
        lista.add("Usuario 8");
        lista.add("Usuario 9");
        lista.add("Usuario 10");
        lista.add("Usuario 11");
        lista.add("Usuario 12");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listaUsuarios.setAdapter(adapter);

        nombreGanador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nombreSeleccionWinner = adapter.getItem(position);

                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaUsuariosTorneo.this);
                alerta.setTitle("CONFIRMACIÓN");
                alerta.setMessage("¿Seguro que desea seleccionar a este Usuario?\n" +
                        "- Nombre: " + nombreSeleccionWinner);

                alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Si se confirma se pasa el nombre a la actividad anterior.
                        Intent intent = new Intent();
                        intent.putExtra("USUARIO", nombreSeleccionWinner);
                        intent.putExtra("integrante", numIntegrante);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                alerta.create();
                alerta.show();
            }
        });

    }
}