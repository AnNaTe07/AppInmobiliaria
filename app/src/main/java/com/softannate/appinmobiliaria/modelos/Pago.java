package com.softannate.appinmobiliaria.modelos;

import java.util.Date;

public class Pago {

    private int id;
    private int nro;
    private String fecha;
    private double monto;
    private String direccion ;

    public Pago(int id, int nro, String fecha, double monto, String direccion) {
        this.id = id;
        this.nro = nro;
        this.fecha = fecha;
        this.monto = monto;
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
