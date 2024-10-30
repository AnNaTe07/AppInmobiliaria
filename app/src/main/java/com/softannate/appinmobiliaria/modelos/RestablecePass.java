package com.softannate.appinmobiliaria.modelos;

public class RestablecePass {

        public String Token;
        public String Email;
        public String NuevaContrasena;


    public RestablecePass(String token, String email, String nuevaContrasena) {
        Token = token;
        Email = email;
        NuevaContrasena = nuevaContrasena;
    }


    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNuevaContrasena() {
        return NuevaContrasena;
    }

    public void setNuevaContrasena(String nuevaContrasena) {
        NuevaContrasena = nuevaContrasena;
    }
}
