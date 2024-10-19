package com.softannate.appinmobiliaria.modelos;

import java.util.Date;

public class Pago {

    private int id;
    private int nro;
    private Date fecha;
    private double monto;
    private Contrato contrato;

    public Pago(int id, int nro, Date fecha, double monto, Contrato contrato) {
        this.id = id;
        this.nro = nro;
        this.fecha = fecha;
        this.monto = monto;
        this.contrato = contrato;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
}
