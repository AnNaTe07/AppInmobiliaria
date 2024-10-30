package com.softannate.appinmobiliaria.modelos;

import java.io.Serializable;

public class Tipo implements Serializable {

    private int id;
    private String descripcion;

    public Tipo() {

    }

    public Tipo(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion; // Para mostrar la descripción
    }
}
