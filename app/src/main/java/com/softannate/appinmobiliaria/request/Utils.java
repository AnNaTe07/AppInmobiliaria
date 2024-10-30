package com.softannate.appinmobiliaria.request;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
        public static String formatearFecha(String fechaStr) {
            try {
                SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date fecha = formatoEntrada.parse(fechaStr);
                return formatoSalida.format(fecha);
            } catch (Exception e) {
                e.printStackTrace();
                return fechaStr; // Retorno la cadena original en caso de error
            }
        }


    }


