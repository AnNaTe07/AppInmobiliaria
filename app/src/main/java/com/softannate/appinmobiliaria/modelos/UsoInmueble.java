package com.softannate.appinmobiliaria.modelos;

public enum UsoInmueble {
    COMERCIAL(1),
    RESIDENCIAL(2);

    private final int value;

    UsoInmueble(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UsoInmueble fromValue(int value) {
        for (UsoInmueble uso : UsoInmueble.values()) {
            if (uso.getValue() == value) {
                return uso;
            }
        }
        throw new IllegalArgumentException("Valor desconocido: " + value);
    }
}

