package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_tienda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AnadirProducto extends AppCompatActivity {
    EditText nombreProducto, cantidad, descripcion, tipo, precio;
    Button botonCrearProducto;
    FirebaseFirestore db;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_producto);

        db = FirebaseFirestore.getInstance();

        nombreProducto = findViewById(R.id.etNombreProductoNuevo);
        cantidad = findViewById(R.id.etCantidad);
        descripcion = findViewById(R.id.etDescripcion);
        tipo = findViewById(R.id.etTipo);
        precio = findViewById(R.id.etPrecio);

        botonCrearProducto = findViewById(R.id.botonCrearProducto);

        botonCrearProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear el nuevo producto
                if (!camposCompletos(nombreProducto, cantidad, descripcion, tipo, precio)){
                    Toast.makeText(AnadirProducto.this, "Alguno de los campos obligatorios esta sin rellenar", Toast.LENGTH_SHORT).show();
                } else {
                    // Comporbar que los campos contengan bien los datos
                    if (comprobarNumeroEntero(cantidad.getText().toString())) {
                        // Cantidad en un formato correcto
                        if (esNumerico(precio.getText().toString())){
                            // Formatos y campos rellenador correctamente
                            anadirNuevoProducto();
                        } else {
                            Toast.makeText(AnadirProducto.this, "Datos de precio con un formato incorrecto", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(AnadirProducto.this, "La cantidad tiene un formato no válido, se ha introducido mal o el valor el 0.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void anadirNuevoProducto() {
        Map<String, Object> nuevoProducto = new HashMap<>();
        nuevoProducto.put("nombre", nombreProducto.getText().toString());
        nuevoProducto.put("descripcion", descripcion.getText().toString());
        nuevoProducto.put("cantidad", Integer.parseInt(cantidad.getText().toString()));
        nuevoProducto.put("precio", Float.valueOf(precio.getText().toString()));
        nuevoProducto.put("tipo", tipo.getText().toString());

        db.collection("productos")
                .add(nuevoProducto)
                .addOnCompleteListener(task -> {
                   if (task.isSuccessful()){
                       Toast.makeText(this, "Producto añadido a la base de datos correctamente", Toast.LENGTH_SHORT).show();
                       i = new Intent();
                       setResult(3, i);
                       finish();
                   } else {
                       Toast.makeText(this, "Fallo a la hora de añadir el nuevo producto a la base de datos", Toast.LENGTH_SHORT).show();
                   }
                });
    }

    private boolean camposCompletos(EditText... campos) {
        for (EditText campo : campos) {
            if (campo.getText().toString().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean esNumerico(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        String patronDecimal = "^-?\\d+(\\.\\d+)?$";
        return input.matches(patronDecimal);
    }

    public boolean comprobarNumeroEntero (String text) {
        return text.matches("[0-9]{1,1000}");
    }

}