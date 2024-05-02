package com.example.apppadel.vista_usuario.opciones_menu_user;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_eventos);

        listaEventos = findViewById(R.id.listaEventos);

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

        //Cuando toque un item de la lista, este debe enseñarme la informacion del Torneo, nombre, fecha inicio y fin
        // y ganadores si tiene. (Mirar para poner un fondo de color a los que ya han sido jugados y otro para los que no)

    }
}