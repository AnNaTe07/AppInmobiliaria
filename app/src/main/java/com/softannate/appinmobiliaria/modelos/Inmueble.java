package com.softannate.appinmobiliaria.modelos;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.math.BigDecimal;

public class Inmueble implements Serializable {

    private int id;
    private int UsoInmuebleId;
    private UsoInmueble uso;
    private String direccion;
    private int tipoId;
    private Tipo tipo;
    private int ambientes;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private BigDecimal superficie;
    private BigDecimal precio;
    private int propietarioId;
    private Propietario propietario;
    private boolean estado;
    private String foto;


    public Inmueble() {

    }





    public Inmueble(int id, int UsoInmuebleId, UsoInmueble uso, String direccion, int tipoId, Tipo tipo, int ambientes, BigDecimal latitud, BigDecimal longitud, BigDecimal superficie, BigDecimal precio, int propietarioId, Propietario propietario, boolean estado, String foto) {
        this.id = id;
        this.UsoInmuebleId = UsoInmuebleId;
        this.uso = uso;
        this.direccion = direccion;
        this.tipoId = tipoId;
        this.tipo = tipo;
        this.ambientes = ambientes;
        this.latitud = latitud;
        this.longitud = longitud;
        this.superficie = superficie;
        this.precio = precio;
        this.propietarioId = propietarioId;
        this.propietario = propietario;
        this.estado = estado;
        this.foto = foto;
    }

    public int getTipoId() {
        return tipoId;
    }

    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsoInmuebleId(int usoInmuebleId) {
        UsoInmuebleId = usoInmuebleId;
    }

    public UsoInmueble getUso() {
        return uso;
    }

    public void setUso(UsoInmueble uso) {
        this.uso = uso;
    }

    public int getUsoId() {
        return UsoInmuebleId;
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

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public BigDecimal getSuperficie() {
        return superficie;
    }

    public void setSuperficie(BigDecimal superficie) {
        this.superficie = superficie;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
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
