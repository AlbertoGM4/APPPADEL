package com.example.apppadel.vista_usuario.opciones_menu_user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.apppadel.models.Producto;
import com.example.apppadel.models.Reserva;
import com.example.apppadel.models.Usuario;
import com.example.apppadel.vista_propietario.opciones_menu_principal.GestionarTienda;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_tienda.ActualizarStock;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_tienda.AdaptadorItemTienda;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class ConsultarTienda extends AppCompatActivity {
    ListView listaProductos;
    AdaptadorItemTienda adaptadorItem;
    List<Producto> productos;
    FirebaseFirestore db;
    Producto producto;
    boolean esSocio = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_tienda);

        db = FirebaseFirestore.getInstance();
        listaProductos = findViewById(R.id.listaProductosTienda);
        listarProductos();

        comprobarUsuarioIniciado();

        listaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                producto = (Producto) adaptadorItem.getItem(position);

                float precioAMostrar;
                String textoImporte = "";
                if (esSocio){
                    precioAMostrar = (float) (producto.getPrecioProducto() - (producto.getPrecioProducto() * .2));
                    textoImporte = " € (20% descontado)";
                } else {
                    precioAMostrar = producto.getPrecioProducto();
                    textoImporte = " €";
                }

                AlertDialog.Builder alerta = new AlertDialog.Builder(ConsultarTienda.this);
                alerta.setTitle("INFORMACIÓN DEL PRODUCTO SELECCIONADO");
                alerta.setMessage("- Nombre artículo: " + producto.getNombreProducto() + "\n" +
                        "- Cantidad: " + producto.getCantidadProducto() + "\n" +
                        "- Descripción: " + producto.getDescripcionProducto() + "\n" +
                        "- Precio unitario: " + precioAMostrar + textoImporte + "\n" +
                        "- Categoría: " + producto.getTipoProducto());
                alerta.setNegativeButton("Volver", null);
                alerta.create();
                alerta.show();
            }
        });

    }

    private void comprobarUsuarioIniciado() { // Metodo para aplicar el descuento al precio del producto.
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        String idUsuario = usuario.getUid();

        Log.d("FirestoreDocument", "ID: " + idUsuario);

        db.collection("usuarios").document(idUsuario)
                .get()
                .addOnCompleteListener(task -> {
                   if (task.isSuccessful()){
                       DocumentSnapshot doc = task.getResult();
                       String rol = doc.getString("rol");

                       Log.d("FirestoreDocument", "ROL: " + rol);

                       if (rol.equals("socio")){
                           Log.d("FirestoreDocument", "ES SOCIO");
                           esSocio = true;
                       } else {
                           Log.d("FirestoreDocument", "NO ES SOCIO");
                           esSocio = false;
                       }
                   } else {
                       Toast.makeText(this, "Fallo en la consulta para conocer si el usuario es socio o no.", Toast.LENGTH_SHORT).show();
                   }
                });
    }

    private void listarProductos() {
        productos = new ArrayList<>();

        db.collection("productos")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String idProd = document.getId();
                            String nombreProd = document.getString("nombre");
                            String descripcionProd = document.getString("descripcion");
                            float precioProd = document.getDouble("precio").floatValue();
                            String tipoProd = document.getString("tipo");

                            int cantidadProd = 0; // Default a 0 si no se encuentra o hay error
                            if (document.contains("cantidad") && document.getLong("cantidad") != null) {
                                cantidadProd = document.getLong("cantidad").intValue();
                            }

                            productos.add(new Producto(idProd, nombreProd, cantidadProd, descripcionProd, precioProd, tipoProd));
                        }

                        adaptadorItem = new AdaptadorItemTienda(getApplicationContext(), productos);
                        listaProductos.setAdapter(adaptadorItem);

                    } else {
                        Toast.makeText(this, "Error al listar los productos de la Base de Datos", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}