package com.example.apppadel.vista_usuario.opciones_menu_user;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user.VistaFormularioEdicion;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ConsultarUsuarios extends AppCompatActivity {
    TextView numeroUsuarios;
    ListView listaUsuarios;
    ArrayList<String> lista;
    ArrayAdapter<String> adapter;
    EditText nombreABuscar;
    String nomItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_usuarios);

        nombreABuscar = findViewById(R.id.etNombreUsuarioConsultaUsers);
        numeroUsuarios = findViewById(R.id.tvTotalListaUsuarios);
        listaUsuarios = findViewById(R.id.listaConsulUsuario);

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

        numeroUsuarios.append(lista.size() + "");

        listaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nomItemAdapter = adapter.getItem(position);

                //Mostrara info del usuario seleccionado.
                AlertDialog.Builder alerta = new AlertDialog.Builder(ConsultarUsuarios.this);
                alerta.setTitle("INFO DE USUARIO");
                alerta.setMessage("Nombre: " + nomItemAdapter);
                alerta.setPositiveButton("Vovler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nombreABuscar.setText("");

                        //Para que el teclado no se quede por medio una vez que sale del alert, para poder visualizar mejor la lista entera
                        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                });
                alerta.create();
                alerta.show();
            }
        });

        nombreABuscar.addTextChangedListener(new TextWatcher() {
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

    }
}