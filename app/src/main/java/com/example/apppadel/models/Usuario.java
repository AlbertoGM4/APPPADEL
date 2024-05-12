package com.example.apppadel.models;

public class Usuario {
    private String iDUser;
    private String nombreUser;
    private String primerApellido;
    private String segundoApellido;
    private int telefonoUser;
    private String fechaNacUser;
    private String correoElectronico;
    private String contrasenaUser;
    private String rol;

    public Usuario(String nombreUser, String primerApellido, String segundoApellido, int telefonoUser, String fechaNacUser, String correoElectronico, String contrasenaUser, String rol) {

        this.nombreUser = nombreUser;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.telefonoUser = telefonoUser;
        this.fechaNacUser = fechaNacUser;
        this.correoElectronico = correoElectronico;
        this.contrasenaUser = contrasenaUser;
        this.rol = rol;

    }

    public Usuario(String id, String nombre, String ape, String mail, String contra){
        this.iDUser = id;
        this.nombreUser = nombre;
        this.primerApellido = ape;
        this.correoElectronico = mail;
        this.contrasenaUser = contra;
    }

    public String getiDUser() {
        return iDUser;
    }

    public void setiDUser(String iDUser) {
        this.iDUser = iDUser;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public int getTelefonoUser() {
        return telefonoUser;
    }

    public void setTelefonoUser(int telefonoUser) {
        this.telefonoUser = telefonoUser;
    }

    public String getFechaNacUser() {
        return fechaNacUser;
    }

    public void setFechaNacUser(String fechaNacUser) {
        this.fechaNacUser = fechaNacUser;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasenaUser() {
        return contrasenaUser;
    }

    public void setContrasenaUser(String contrasenaUser) {
        this.contrasenaUser = contrasenaUser;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return nombreUser + " " + primerApellido + " - " + correoElectronico;
    }
}
