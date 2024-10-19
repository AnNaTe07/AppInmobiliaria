package com.softannate.appinmobiliaria.modelos;

public class Inquilino {

    private int id;
    private String documento;
    private String nombreCompleto;
    private String apellido;
    private String email;
    private String telefono;

    public Inquilino(int id, String dni, String nombreCompleto, String apellido, String email, String telefono) {
        this.id = id;
        this.documento = dni;
        this.nombreCompleto = nombreCompleto;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDni() {
        return documento;
    }

    public void setDni(String dni) {
        this.documento = dni;
    }

    public String getNombre() {
        return nombreCompleto;
    }

    public void setNombre(String nombre) {
        this.nombreCompleto = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
