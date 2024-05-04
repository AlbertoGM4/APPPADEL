package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_tienda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.apppadel.R;

public class AdaptadorItemTienda extends BaseAdapter {
    private static LayoutInflater inflater = null; //Esto nos sirve para poder inflar la vista con el Layout qeu he creado
    Context contexto;
    String[] nombres;
    int[] cantidades;

    public AdaptadorItemTienda(Context contexto, String[] nombres, int[] cantidades) {
        this.contexto = contexto;
        this.nombres = nombres;
        this.cantidades = cantidades;

        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View view = inflater.inflate(R.layout.items_tienda_layout, null);

        TextView nombreArticulo = view.findViewById(R.id.tvNombreArticulo);
        TextView cantidadArticulo = view.findViewById(R.id.tvCantArticulo);

        nombreArticulo.setText("" + nombres[position]);
        cantidadArticulo.setText("" + cantidades[position]);

        return view;
    }

    @Override
    public int getCount() {
        return nombres.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }




}
