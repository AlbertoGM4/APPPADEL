package com.example.apppadel.vista_propietario.opciones_menu_principal.gestion_tienda;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.apppadel.R;
import com.example.apppadel.models.Producto;

import java.util.List;

public class AdaptadorItemTienda extends BaseAdapter {
    LayoutInflater inflater;
    Context contexto;
    List<Producto> productos;

    public AdaptadorItemTienda(Context contexto, List<Producto> productos) {
        this.contexto = contexto;
        this.productos = productos;
        inflater = LayoutInflater.from(contexto);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.items_tienda_layout, parent, false);
            holder = new ViewHolder();
            holder.nombreArticulo = convertView.findViewById(R.id.tvNombreArticulo);
            holder.cantidadArticulo = convertView.findViewById(R.id.tvCantArticulo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Producto producto = productos.get(position);
        holder.nombreArticulo.setText(producto.getNombreProducto());
        holder.cantidadArticulo.setText(String.valueOf(producto.getCantidadProducto()));

        return convertView;
    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int position) {
        return productos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView nombreArticulo;
        TextView cantidadArticulo;
    }
}
