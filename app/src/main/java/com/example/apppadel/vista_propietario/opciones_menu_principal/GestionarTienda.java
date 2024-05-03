package com.example.apppadel.vista_propietario.opciones_menu_principal;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_tienda.ActualizarStock;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_tienda.AdaptadorItemTienda;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user.BajaUsuario;

public class GestionarTienda extends AppCompatActivity {
    ListView listaProductos;
    String[] listaNombres = {"Producto 1", "Producto 2", "Producto 3", "Producto 4", "Producto 5"}; //Rellenar el Array con la info de la BD
    int[] cantidades = {3, 5, 1, 7, 10}; //Igual que arriba.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_tienda);


        listaProductos = findViewById(R.id.listaProductos);
        listaProductos.setAdapter(new AdaptadorItemTienda(this, listaNombres, cantidades));

        listaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(GestionarTienda.this);
                alerta.setTitle("INFORMACIÓN DEL PRODCUTO");
                alerta.setMessage("- Nombre artículo: " + listaNombres[position] + "\n" +
                        "- Descripción: " + "Descripcion del Producto :)" + "\n" +
                        "- Cantidad: " + cantidades[position] + "\netc");
                alerta.setPositiveButton("Actualizar Stock", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(GestionarTienda.this, ActualizarStock.class);
                        i.putExtra("NOMBRE", listaNombres[position]);
                        startActivity(i);

                    }
                });
                alerta.setNegativeButton("Volver", null);
                alerta.create();
                alerta.show();
            }
        });

    }
}