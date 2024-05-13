package com.example.apppadel.models;

public class Reserva {
    private String idReserva;
    private String idUser;
    private String idPista;
    private String fechaReserva;
    private String horaInicioReserva;
    private String horaFinReserva;

    public Reserva(String idReserva, String horaInicioReserva) {
        this.idReserva = idReserva;
        this.horaInicioReserva = horaInicioReserva;
    }

    public String getIdReserva() {
        return idReserva;
    }
    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }
    public String getIdUser() {
        return idUser;
    }
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    public String getIdPista() {
        return idPista;
    }
    public void setIdPista(String idPista) {
        this.idPista = idPista;
    }
    public String getFechaReserva() {
        return fechaReserva;
    }
    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }
    public String getHoraInicioReserva() {
        return horaInicioReserva;
    }
    public void setHoraInicioReserva(String horaInicioReserva) {
        this.horaInicioReserva = horaInicioReserva;
    }
    public String getHoraFinReserva() {
        return horaFinReserva;
    }
    public void setHoraFinReserva(String horaFinReserva) {
        this.horaFinReserva = horaFinReserva;
    }
    @Override
    public String toString() {
        return horaInicioReserva;
    }
}
