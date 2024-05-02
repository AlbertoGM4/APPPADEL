package com.example.apppadel.vista_usuario.opciones_menu_user;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apppadel.R;

import java.util.ArrayList;

public class ConsultarEventos extends AppCompatActivity {
    ListView listaEventos;
    ArrayList<String> lista;
    ArrayAdapter<String> adapter;
    EditText nombreEvento;
    Button btnBuscarEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_eventos);

        listaEventos = findViewById(R.id.listaEventos);
        nombreEvento = findViewById(R.id.etNombreEvento);
        btnBuscarEvento = findViewById(R.id.botonBuscarEvento);

        lista = new ArrayList<>();
        lista.add("Torneo 1");
        lista.add("Torneo 2");
        lista.add("Torneo 3");
        lista.add("Torneo 4");
        lista.add("Torneo 5");
        lista.add("Torneo 6");
        lista.add("Torneo 7");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, lista);
        listaEventos.setAdapter(adapter);

        listaEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Info del Torneo que se ha seleccionado.
            }
        });

        btnBuscarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Buscar por nombre del Evento.
                Toast.makeText(ConsultarEventos.this, "Buscador por evento", Toast.LENGTH_SHORT).show();
            }
        });

    }
}