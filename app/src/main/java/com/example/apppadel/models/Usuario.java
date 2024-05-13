package com.example.apppadel.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Usuario implements Parcelable {
    private String iDUser;
    private String nombreUser;
    private String primerApellido;
    private String segundoApellido;
    private String telefonoUser;
    private String fechaNacUser;
    private String correoElectronico;
    private String contrasenaUser;
    private String rol;
    private int puntosSocio;

    public Usuario(String id, String nombreUser, String primerApellido, String segundoApellido, String telefonoUser, String fechaNacUser, String correoElectronico, String contrasenaUser, String rol) {

        this.iDUser = id;
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

    // Constructor Parcelable
    protected Usuario(Parcel in) {
        iDUser = in.readString();
        nombreUser = in.readString();
        primerApellido = in.readString();
        segundoApellido = in.readString();
        telefonoUser = in.readString();
        fechaNacUser = in.readString();
        correoElectronico = in.readString();
        contrasenaUser = in.readString();
        rol = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(iDUser);
        dest.writeString(nombreUser);
        dest.writeString(primerApellido);
        dest.writeString(segundoApellido);
        dest.writeString(telefonoUser);
        dest.writeString(fechaNacUser);
        dest.writeString(correoElectronico);
        dest.writeString(contrasenaUser);
        dest.writeString(rol);
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

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

    public String getTelefonoUser() {
        return telefonoUser;
    }

    public void setTelefonoUser(String telefonoUser) {
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
