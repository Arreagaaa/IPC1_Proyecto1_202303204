package com.usacbank;

import com.usacbank.controller.CuentaController;
import com.usacbank.controller.ClienteController;
import com.usacbank.model.Usuario;
import com.usacbank.view.MainView;

public class Main {
    public static void main(String[] args) {
        // Crear el usuario por defecto (administrador)
        Usuario usuarioPorDefecto = Usuario.crearUsuarioPorDefecto();

        // Crear el controlador de clientes
        ClienteController clienteController = new ClienteController();

        // Crear el controlador de cuentas
        CuentaController cuentaController = new CuentaController();

        // Imprimir las credenciales del usuario por defecto (para verificar)
        System.out.println("Usuario por defecto:");
        System.out.println("Username: " + usuarioPorDefecto.getUsername());
        System.out.println("Password: " + usuarioPorDefecto.getPassword());

        // Iniciar la aplicación con la vista principal
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainView(usuarioPorDefecto, clienteController, cuentaController).setVisible(true);
        });
    }
}