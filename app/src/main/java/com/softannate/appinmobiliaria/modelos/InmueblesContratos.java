package com.softannate.appinmobiliaria.modelos;

import java.io.Serializable;

public class InmueblesContratos implements Serializable {

    private Inmueble inmueble;
    private Contrato contrato;

    public InmueblesContratos(Inmueble inmueble, Contrato contrato) {
        this.inmueble = inmueble;
        this.contrato = contrato;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
}
