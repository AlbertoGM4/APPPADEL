package com.example.apppadel.models;

public class Torneo {
    private String idTorneo;
    private String nombreTorneo;
    private String fechaInicioTorneo;
    private String fechaFinTorneo;
    private String ganadorUno;
    private String ganadorDos;

    public Torneo(String idTorneo, String nombreTorneo, String fechaInicioTorneo, String fechaFinTorneo) {
        this.idTorneo = idTorneo;
        this.nombreTorneo = nombreTorneo;
        this.fechaInicioTorneo = fechaInicioTorneo;
        this.fechaFinTorneo = fechaFinTorneo;
    }

    public Torneo(String idTorneo, String nombreTorneo, String fechaInicioTorneo, String fechaFinTorneo, String ganadorUno, String ganadorDos) {
        this.idTorneo = idTorneo;
        this.nombreTorneo = nombreTorneo;
        this.fechaInicioTorneo = fechaInicioTorneo;
        this.fechaFinTorneo = fechaFinTorneo;
        this.ganadorUno = ganadorUno;
        this.ganadorDos = ganadorDos;
    }

    public String getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(String idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public void setNombreTorneo(String nombreTorneo) {
        this.nombreTorneo = nombreTorneo;
    }

    public String getFechaInicioTorneo() {
        return fechaInicioTorneo;
    }

    public void setFechaInicioTorneo(String fechaInicioTorneo) {
        this.fechaInicioTorneo = fechaInicioTorneo;
    }

    public String getFechaFinTorneo() {
        return fechaFinTorneo;
    }

    public void setFechaFinTorneo(String fechaFinTorneo) {
        this.fechaFinTorneo = fechaFinTorneo;
    }

    public String getGanadorUno() {
        return ganadorUno;
    }

    public void setGanadorUno(String ganadorUno) {
        this.ganadorUno = ganadorUno;
    }

    public String getGanadorDos() {
        return ganadorDos;
    }

    public void setGanadorDos(String ganadorDos) {
        this.ganadorDos = ganadorDos;
    }

    @Override
    public String toString() {
        return nombreTorneo;
    }
}
