package com.softannate.appinmobiliaria.modelos;

public class Contrato {

    private int id;
    private String desde;
    private String hasta;
    private Inmueble inmu;
    private Inquilino inqui;
    private String inquilino;
    private String inmueble;
    private double monto;

    public Contrato(int id, String desde, String hasta, Inmueble inmu, Inquilino inqui, String inquilino, String inmueble, double monto) {
        this.id = id;
        this.desde = desde;
        this.hasta = hasta;
        this.inmu = inmu;
        this.inqui = inqui;
        this.inquilino = inquilino;
        this.inmueble = inmueble;
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

    public String getInquilino() {
        return inquilino;
    }

    public void setInquilino(String inquilino) {
        this.inquilino = inquilino;
    }

    public String getInmueble() {
        return inmueble;
    }

    public void setInmueble(String inmueble) {
        this.inmueble = inmueble;
    }
}
