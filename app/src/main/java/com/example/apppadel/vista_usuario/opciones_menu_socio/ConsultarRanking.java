package com.example.apppadel.vista_usuario.opciones_menu_socio;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;

import java.util.ArrayList;

public class ConsultarRanking extends AppCompatActivity {
    ListView listaRanking;
    ArrayList<String> lista;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_ranking);

        listaRanking = findViewById(R.id.listaRanking);

        lista = new ArrayList<>();
        lista.add("Socio 1");
        lista.add("Socio 2");
        lista.add("Socio 3");
        lista.add("Socio 4");
        lista.add("Socio 5");
        lista.add("Socio 6");
        lista.add("Socio 7");
        lista.add("Socio 8");
        lista.add("Socio 9");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, lista);
        listaRanking.setAdapter(adapter);

        listaRanking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Aqui saldra la informacion del Socio que se haya seleccionado.
            }
        });

    }
}