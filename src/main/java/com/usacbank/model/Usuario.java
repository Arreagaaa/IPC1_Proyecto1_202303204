package com.usacbank.model;

public class Usuario {
    private String username;
    private String password;
    private String nombre;

    public Usuario(String username, String password, String nombre) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
    }

    // Método estático para crear el usuario por defecto
    public static Usuario crearUsuarioPorDefecto() {
        Usuario admin = new Usuario("AdministradorIPC1B", "ipc1B1s2025", "Administrador");

        // Registrar en bitácora la creación del usuario
        System.out.println(new Bitacora(
                "Sistema",
                "Creación de usuario por defecto",
                "Éxito",
                "Usuario administrador creado con credenciales por defecto."));

        return admin;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre() {
        return nombre;
    }
}