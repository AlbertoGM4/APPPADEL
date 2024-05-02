package com.example.apppadel.vista_usuario.opciones_menu_user;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apppadel.R;

import java.util.ArrayList;

public class ConsultarTienda extends AppCompatActivity {
    EditText nombreProd;
    Button buscar;
    ListView listaProductos;
    ArrayList<String> lista;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_tienda);

        nombreProd = findViewById(R.id.etNombreProd);
        buscar = findViewById(R.id.botonBuscarProd);
        listaProductos = findViewById(R.id.listaProductosTienda);
        lista = new ArrayList<>();
        lista.add("Producto 1");
        lista.add("Producto 2");
        lista.add("Producto 3");
        lista.add("Producto 4");
        lista.add("Producto 5");
        lista.add("Producto 6");
        lista.add("Producto 7");
        lista.add("Producto 8");
        lista.add("Producto 9");
        lista.add("Producto 10");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listaProductos.setAdapter(adapter);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Busacar dependiendo del prodcuto que hay en el nombreProd.
                if (nombreProd.getText().toString().isEmpty()){
                    Toast.makeText(ConsultarTienda.this, "Introduzca un Producto a buscar de la lista", Toast.LENGTH_SHORT).show();

                } else {
                    //Buscar por el producto.
                    if (lista.contains(nombreProd.getText().toString())){
                        Toast.makeText(ConsultarTienda.this, "Hola", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ConsultarTienda.this, "El Producto que has añadido no existe o ha sido mal escrito", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}