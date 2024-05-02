package com.example.apppadel.vista_usuario.opciones_menu_user;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ConsultarUsuarios extends AppCompatActivity {
    TextView numeroUsuarios;
    ListView listaUsuarios;
    ArrayList<String> lista;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_usuarios);

        numeroUsuarios = findViewById(R.id.tvTotalListaUsuarios);
        listaUsuarios = findViewById(R.id.listaConsulUsuario);

        lista = new ArrayList<>();
        lista.add("Usuario 1");
        lista.add("Usuario 2");
        lista.add("Usuario 3");
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
    }
}