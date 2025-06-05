package com.oprosita.backend.util;

public class Util {
    public static String capitalizar(String texto) {
        return texto.isEmpty() ? texto :
                texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }
}
