package com.softannate.appinmobiliaria.modelos;

import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Contrato {

    private int id;
    private String desde;
    private String hasta;
    private Inmueble inmu;
    private Inquilino inqui;
    private double monto;

    public Contrato(int id, String  desde, String  hasta, Inmueble inmu, Inquilino inqui, double monto) {
        this.id = id;
        this.desde = desde;
        this.hasta = hasta;
        this.inmu = inmu;
        this.inqui = inqui;
        this.monto = monto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String  getDesde() {
        return desde;
    }

    public void setDesde(String  desde) {
        this.desde = desde;
    }

    public String  getHasta() {
        return hasta;
    }

    public void setHasta(String  hasta) {
        this.hasta = hasta;
    }

    public Inmueble getInmu() {
        return inmu;
    }

    public void setInmu(Inmueble inmu) {
        this.inmu = inmu;
    }

    public Inquilino getInqui() {
        return inqui;
    }

    public void setInqui(Inquilino inqui) {
        this.inqui = inqui;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
