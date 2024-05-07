package com.example.apppadel.vista_usuario.opciones_menu_user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apppadel.R;
import com.example.apppadel.vista_propietario.opciones_menu_principal.GestionarTienda;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_tienda.ActualizarStock;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_tienda.AdaptadorItemTienda;

import java.util.ArrayList;

public class ConsultarTienda extends AppCompatActivity {
    ListView listaProductos;
    String[] listaNombres = {"Producto 1", "Producto 2", "Producto 3", "Producto 4", "Producto 5"}; //Rellenar el Array con la info de la BD
    int[] cantidades = {3, 5, 1, 7, 10}; //Igual que arriba.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_tienda);

        listaProductos = findViewById(R.id.listaProductosTienda);
        listaProductos.setAdapter(new AdaptadorItemTienda(this, listaNombres, cantidades));

        listaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ConsultarTienda.this);
                alerta.setTitle("INFORMACIÓN DEL PRODCUTO");
                alerta.setMessage("- Nombre artículo: " + listaNombres[position] + "\n" +
                        "- Descripción: " + "Descripcion del Producto :)" + "\n" +
                        "- Cantidad: " + cantidades[position] + "\netc");
                alerta.setPositiveButton("Volver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alerta.create();
                alerta.show();
            }
        });

    }
}