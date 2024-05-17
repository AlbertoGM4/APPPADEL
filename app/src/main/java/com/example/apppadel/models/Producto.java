package com.example.apppadel.models;

public class Producto {
    private String idProducto;
    private String nombreProducto;
    private int cantidadProducto;
    private String descripcionProducto;
    private float precioProducto;
    private String tipoProducto;

    public Producto(String idProducto, String nombreProducto, int cantidadProducto, String descripcionProducto, float precioProducto, String tipoProducto) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidadProducto = cantidadProducto;
        this.descripcionProducto = descripcionProducto;
        this.precioProducto = precioProducto;
        this.tipoProducto = tipoProducto;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public float getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(float precioProducto) {
        this.precioProducto = precioProducto;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    @Override
    public String toString() {
        return nombreProducto;
    }
}
