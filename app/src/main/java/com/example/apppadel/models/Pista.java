package com.example.apppadel.models;

public class Pista {

    private String idPista;
    private String nombrePista;
    private String[] horasPista;

    public Pista(String idPista, String nombrePista, String[] horasPista) {
        this.idPista = idPista;
        this.nombrePista = nombrePista;
        this.horasPista = horasPista;
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

    public String[] getHorasPista() {
        return horasPista;
    }

    public void setHorasPista(String[] horasPista) {
        this.horasPista = horasPista;
    }

    @Override
    public String toString() {
        return nombrePista;
    }
}
