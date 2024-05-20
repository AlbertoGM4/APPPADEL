package com.example.apppadel.vista_usuario.opciones_menu_user;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.apppadel.R;
import com.example.apppadel.models.Usuario;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_user.VistaFormularioEdicion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ConsultarUsuarios extends AppCompatActivity {
    TextView numeroUsuarios;
    ListView listaUsuarios;
    ArrayList<Usuario> lista;
    ArrayAdapter<Usuario> adapter;
    EditText nombreABuscar;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_usuarios);

        db = FirebaseFirestore.getInstance();

        nombreABuscar = findViewById(R.id.etNombreUsuarioConsultaUsers);
        numeroUsuarios = findViewById(R.id.tvTotalListaUsuarios);
        listaUsuarios = findViewById(R.id.listaConsulUsuario);

        listarUsuarios();

        listaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario usuario = adapter.getItem(position);

                //Mostrara info del usuario seleccionado.
                AlertDialog.Builder alerta = new AlertDialog.Builder(ConsultarUsuarios.this);
                alerta.setTitle("INFO DE USUARIO");
                alerta.setMessage("- Nombre: " + usuario.getNombreUser() + "\n" +
                        "- Apellidos: " + usuario.getPrimerApellido() + " " + usuario.getSegundoApellido() + "\n" +
                        "- Correo: " + usuario.getCorreoElectronico() + "\n" +
                        "- Fecha de nacimiento: " + usuario.getFechaNacUser() + "\n" +
                        "- Teléfono: " + usuario.getTelefonoUser() + "\n" +
                        "- Rol en el Club: " + usuario.getRol());
                alerta.setPositiveButton("Volver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nombreABuscar.setText("");

                        //Para que el teclado no se quede por medio una vez que sale del alert, para poder visualizar mejor la lista entera
                        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                });
                alerta.create();
                alerta.show();
            }
        });

        nombreABuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void listarUsuarios() {
        // Obtengo el usuario con el que he iniciado la sesion
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        // Guardo el Id de este en un String para luego pasarlo a la lista y asi no añadir el usuario a la lista de Usuarios.
        String idUsuarioActual = currentUser != null ? currentUser.getUid() : null;

        lista = new ArrayList<>();

        db.collection("usuarios")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        lista.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String id = document.getId();
                            String userName = document.getString("nombre");
                            String surName = document.getString("primer_apellido");
                            String secondSurName = document.getString("segundo_apellido");
                            String phone = document.getString("telefono");
                            String dateBorn = document.getString("fecha_nacimiento");
                            String mail = document.getString("correo");
                            String rol = document.getString("rol");

                            // Si encuentra al admin, este no lo añade a la lista de Usuarios, para no mostrarlo
                            if (!id.equals("NBWFk4ARbGNelIC2F6wCj18k2Pw1") && !id.equals(idUsuarioActual)) {
                                lista.add(new Usuario(id, userName, surName, secondSurName, phone, dateBorn, mail, rol));
                            }
                        }
                        // Actualizar la lista cada vez que se llame al método
                        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
                        listaUsuarios.setAdapter(adapter);
                        numeroUsuarios.append(lista.size() + 1 + ""); // Contador de Usuarios

                    } else {
                        Log.w("TAG", "Error getting documents.", task.getException());
                    }
                });
    }
}