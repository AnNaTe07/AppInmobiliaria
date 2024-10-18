package com.softannate.appinmobiliaria.modelos;

import java.io.Serializable;

public class Inmueble implements Serializable {

    private int id;
    private UsoInmueble uso;
    private String direccion;
    private Tipo tipo;
    private int ambientes;
    private double latitud;
    private double longitud;
    private float superficie;
    private double precio;
    private Propietario propietario;
    private boolean estado;
    private String foto;

    public Inmueble(UsoInmueble uso, String direccion, Tipo tipo, int ambientes, double latitud, double longitud, float superficie, double precio, Propietario propietario, boolean estado, String foto) {
        this.uso = uso;
        this.direccion = direccion;
        this.tipo = tipo;
        this.ambientes = ambientes;
        this.latitud = latitud;
        this.longitud = longitud;
        this.superficie = superficie;
        this.precio = precio;
        this.propietario = propietario;
        this.estado = estado;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UsoInmueble getUso() {
        return uso;
    }

    public void setUso(UsoInmueble uso) {
        this.uso = uso;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public float getSuperficie() {
        return superficie;
    }

    public void setSuperficie(float superficie) {
        this.superficie = superficie;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
