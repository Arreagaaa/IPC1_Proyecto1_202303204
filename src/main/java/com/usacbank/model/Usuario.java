package com.usacbank.model;

public class Usuario {
    private String username;
    private String password;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Método estático para crear el usuario por defecto
    public static Usuario crearUsuarioPorDefecto() {
        return new Usuario("AdministradorIPC1B", "ipc1B1s2025");
    }
}