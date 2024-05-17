package com.example.apppadel.vista_propietario.opciones_menu_principal;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apppadel.R;
import com.example.apppadel.models.Torneo;
import com.example.apppadel.models.Usuario;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_pistas.ModificarReservaNuevaFecha;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_torneos.NuevoTorneo;
import com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_torneos.SeleccionGanadores;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class GestionarTorneos extends AppCompatActivity {
    Button btnCrearNuevoTorneo;
    ListView listaGanadoresTorneo;
    ArrayList<Torneo> lista;
    ArrayAdapter<Torneo> adapter;
    ActivityResultLauncher lanzador;
    Torneo torneoSeleccionado;
    FirebaseFirestore db;
    int posicionTorneo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_torneos);

        db = FirebaseFirestore.getInstance();

        btnCrearNuevoTorneo = findViewById(R.id.botonCrearNuevoTorneo);
        listaGanadoresTorneo = findViewById(R.id.listaGanadoresTorneo);

        //Listar los torneos que no tienen ganadores.
        listarTorneosSinGanador();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listaGanadoresTorneo.setAdapter(adapter);

        registerForContextMenu(listaGanadoresTorneo);

        btnCrearNuevoTorneo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Añadir confirmacion de la reserva
                //Antes de confirmar la reserva, mostrar la info del item que se ha seleccionado, y luego ya se puede confirmar
                Intent i = new Intent(GestionarTorneos.this, NuevoTorneo.class);
                lanzador.launch(i);
            }
        });

        listaGanadoresTorneo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                torneoSeleccionado = adapter.getItem(position);
                posicionTorneo = position;

                AlertDialog.Builder alerta = new AlertDialog.Builder(GestionarTorneos.this);
                alerta.setTitle("INFORMACIÓN DEL TORNEO SELECCIONADO");
                alerta.setMessage("*Datos del Torneo*\n" +
                        "- Nombre: " + torneoSeleccionado.getNombreTorneo() + "\n" +
                        "- Fecha Inicio: " + torneoSeleccionado.getFechaInicioTorneo() + "\n" +
                        "- Fecha Fin: " + torneoSeleccionado.getFechaFinTorneo() + "\n" +
                        "- Torneo SIN Ganadores. " );

                alerta.setPositiveButton("Volver", null);
                alerta.create();
                alerta.show();
            }
        });

        lanzador = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                // Viene de la actividad de Nuevo torneo.
                if (o.getResultCode() == 10){
                    String respuesta = o.getData().getStringExtra("REFRESCAR");
                    if (respuesta.equals("OK")){
                        Toast.makeText(GestionarTorneos.this, "Refrescando lista de Torneos...", Toast.LENGTH_SHORT).show();
                        listarTorneosSinGanador();

                        // Cargar de nuevo la lista de torneos
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1, lista);
                        listaGanadoresTorneo.setAdapter(adapter);

                    } else {
                        Toast.makeText(GestionarTorneos.this, "Algo pasa con los nuevos Torneos", Toast.LENGTH_SHORT).show();
                    }

                } else if (o.getResultCode() == 50) {
                    String actualizacion = o.getData().getStringExtra("ACTUALIZAR_LISTA");
                    String idTorneo = o.getData().getStringExtra("IDTORNEO");
                    String idGanadorUno = o.getData().getStringExtra("PRIMER_GANADOR_ID");
                    String idGanadorDos = o.getData().getStringExtra("SEGUNDO_GANADOR_ID");

                    if (actualizacion.equals("SI")){
                        actualizarGanadoresTorneo(idTorneo, idGanadorUno, idGanadorDos);

                    } else {
                        Toast.makeText(GestionarTorneos.this, "Fallo en la devolución del intent de actualizar desde SeleccionGanadores", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void actualizarGanadoresTorneo(String idTorneo, String idGanadorUno, String idGanadorDos) {
        DocumentReference doc = db.collection("torneos").document(idTorneo);

        db.runTransaction(transaction -> {
            DocumentSnapshot snapshot = transaction.get(doc);
            if (snapshot.exists()){
                transaction.update(doc, "ganador_uno", idGanadorUno);
                transaction.update(doc, "ganador_dos", idGanadorDos);
            }

            return null;
        }).addOnSuccessListener(aVoid -> {
            Toast.makeText(this, "Ganadores actualizados en el Torneo seleccionado", Toast.LENGTH_SHORT).show();
            listarTorneosSinGanador();

        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Fallo en la actualización de los ganadores del Torneo", Toast.LENGTH_SHORT).show();
        });

    }

    private void listarTorneosSinGanador() {
        lista = new ArrayList<>();

        db.collection("torneos")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        lista.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String id = document.getId();
                            String nombreTor = document.getString("nombre");
                            String inicio = document.getString("fecha_inicio");
                            String fin = document.getString("fecha_fin");
                            String ganadorUno = document.getString("ganador_uno");
                            String ganadorDos = document.getString("ganador_dos");

                            if (ganadorUno.isEmpty() && ganadorDos.isEmpty()){
                                lista.add(new Torneo(id, nombreTor, inicio, fin));
                            }
                        }

                        // Cargar de nuevo la lista de torneos
                        adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1, lista);
                        listaGanadoresTorneo.setAdapter(adapter);
                    } else {
                        Toast.makeText(this, "Error para listar los torneos que no poseen ganadores", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu_torneos, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        if (item.getItemId() == R.id.selectWinners){
            //Aqui se hará la accion de Seleccionar gnadores
            Intent intent = new Intent(GestionarTorneos.this, SeleccionGanadores.class);
            intent.putExtra("ID_TORNEO", adapter.getItem(position).getIdTorneo());
            intent.putExtra("NOMBRE_TORNEO", adapter.getItem(position).getNombreTorneo());
            lanzador.launch(intent);
        }

        return super.onContextItemSelected(item);
    }
}