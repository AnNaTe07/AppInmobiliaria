package com.softannate.appinmobiliaria.modelos;

public class Pass {

    private String passActual;
    private String nuevoPass;

    public Pass(String passActual, String nuevoPass) {
        this.passActual = passActual;
        this.nuevoPass = nuevoPass;
    }

    public String getPassActual() {
        return passActual;
    }

    public void setPassActual(String passActual) {
        this.passActual = passActual;
    }

    public String getNuevoPass() {
        return nuevoPass;
    }

    public void setNuevoPass(String nuevoPass) {
        this.nuevoPass = nuevoPass;
    }
}
