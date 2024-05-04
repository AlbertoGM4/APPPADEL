package com.example.apppadel.vista_usuario.opciones_menu_user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apppadel.R;
import com.example.apppadel.vista_propietario.opciones_menu_principal.GestionarTorneos;

import java.util.ArrayList;

public class ConsultarEventos extends AppCompatActivity {
    ListView listaEventos;
    ArrayList<String> lista;
    ArrayAdapter<String> adapter;
    EditText nombreEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_eventos);

        listaEventos = findViewById(R.id.listaEventos);
        nombreEvento = findViewById(R.id.etNombreEvento);

        lista = new ArrayList<>();
        lista.add("Torneo 1");
        lista.add("Torneo 2");
        lista.add("Torneo 3");
        lista.add("Torneo 4");
        lista.add("Torneo 5");
        lista.add("Torneo 6");
        lista.add("Torneo 7");
        lista.add("Torneo 8");
        lista.add("Torneo 9");
        lista.add("Torneo 10");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, lista);
        listaEventos.setAdapter(adapter);

        nombreEvento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listaEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ConsultarEventos.this);
                alerta.setTitle("INFORMACIÓN DEL TORNEO");
                alerta.setMessage("*Datos del Torneo*\n" +
                        "- Nombre: " + lista.get(position) +
                        "\n- Fecha Inicio: SI\n- Fecha Fin: SI" +
                        "\n- Acabado: SI/NO" +
                        "\n- Ganadores: (solo aparecerán si el torneo esta finalizado)");

                alerta.setPositiveButton("Volver", null);
                alerta.create();
                alerta.show();
            }
        });


    }
}