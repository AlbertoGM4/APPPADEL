package com.example.apppadel.vista_propietario.opciones_menu_principal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;
import com.example.apppadel.models.Producto;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_tienda.ActualizarStock;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_tienda.AdaptadorItemTienda;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user.BajaUsuario;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class GestionarTienda extends AppCompatActivity {
    ListView listView;
    AdaptadorItemTienda adaptadorItem;
    List<Producto> productos;
    FirebaseFirestore db;
    Producto producto;
    ActivityResultLauncher lanzador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_tienda);

        db = FirebaseFirestore.getInstance();
        listView = findViewById(R.id.listaProductos);
        listarProductos();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                producto = (Producto) adaptadorItem.getItem(position);

                AlertDialog.Builder alerta = new AlertDialog.Builder(GestionarTienda.this);
                alerta.setTitle("INFORMACIÓN DEL PRODUCTO SELECCIONADO");
                alerta.setMessage("- ID: " + producto.getIdProducto() + "\n" +
                        "- Nombre artículo: " + producto.getNombreProducto() + "\n" +
                        "- Cantidad: " + producto.getCantidadProducto() + "\n" +
                        "- Descripción: " + producto.getDescripcionProducto() + "\n" +
                        "- Precio unitario: " + producto.getPrecioProducto() + " €\n" +
                        "- Tipo: " + producto.getTipoProducto());
                alerta.setPositiveButton("Actualizar Stock", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(GestionarTienda.this, ActualizarStock.class);
                        i.putExtra("NOMBRE", producto.getNombreProducto());
                        lanzador.launch(i);
                    }
                });
                alerta.setNegativeButton("Volver", null);
                alerta.create();
                alerta.show();
            }
        });

        lanzador = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                int cantidad = Integer.parseInt(o.getData().getStringExtra("NUMERO"));
                int cantidadStock = producto.getCantidadProducto();

                // Viene para ser Sumarselo al stock
                if (o.getResultCode() == 1){
                    sumarCantidad(cantidad);
                    // Para restar al stock
                } else if (o.getResultCode() == 2) {
                    if (cantidad > cantidadStock){
                        Toast.makeText(GestionarTienda.this, "No se puede restar la cantidad introducida, es mayor que la de stock.", Toast.LENGTH_SHORT).show();
                    } else {
                        restarCantidad(cantidad);
                    }
                }
            }
        });
    }

    private void restarCantidad(int cantidad) {
        DocumentReference doc = db.collection("productos").document(producto.getIdProducto());

        db.runTransaction(transaction -> {
            DocumentSnapshot snapshot = transaction.get(doc);
            if (snapshot.exists()){
                long cantidadActual = snapshot.getLong("cantidad");
                transaction.update(doc, "cantidad", cantidadActual - cantidad);
            }

            return null;
        }).addOnSuccessListener(aVoid -> {
            Toast.makeText(this, "Stock actualizado con éxito (Resta)", Toast.LENGTH_SHORT).show();
            listarProductos();

        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Fallo en la actualización del Stock (Resta)", Toast.LENGTH_SHORT).show();
        });
    }

    private void sumarCantidad(int cantidad) {
        DocumentReference doc = db.collection("productos").document(producto.getIdProducto());

        db.runTransaction(transaction -> {
            DocumentSnapshot snapshot = transaction.get(doc);
            if (snapshot.exists()){
                long cantidadActual = snapshot.getLong("cantidad");
                transaction.update(doc, "cantidad", cantidadActual + cantidad);
            }
            return null;
        }).addOnSuccessListener(aVoid -> {
            Toast.makeText(this, "Stock actualizado con éxito (Suma)", Toast.LENGTH_SHORT).show();
            listarProductos();

        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Fallo en la actualización del Stock (Suma)", Toast.LENGTH_SHORT).show();
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
                       listView.setAdapter(adaptadorItem);

                   } else {
                       Toast.makeText(this, "Error al listar los productos de la Base de Datos", Toast.LENGTH_SHORT).show();
                   }
                });
    }
}