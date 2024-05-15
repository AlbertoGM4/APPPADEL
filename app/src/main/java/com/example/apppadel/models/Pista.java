package com.example.apppadel.models;

public class Pista {

    private String idPista;
    private String nombrePista;

    public Pista(String idPista, String nombrePista) {
        this.idPista = idPista;
        this.nombrePista = nombrePista;
    }

    public String getIdPista() {
        return idPista;
    }

    public void setIdPista(String idPista) {
        this.idPista = idPista;
    }

    public String getNombrePista() {
        return nombrePista;
    }

    public void setNombrePista(String nombrePista) {
        this.nombrePista = nombrePista;
    }

    @Override
    public String toString() {
        return nombrePista;
    }
}
