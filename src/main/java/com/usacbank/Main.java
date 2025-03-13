package com.usacbank;

import com.usacbank.model.Usuario;
import com.usacbank.model.Bitacora;
import com.usacbank.controller.ClienteController;
import com.usacbank.controller.CuentaController;
import com.usacbank.controller.TransaccionController;
import com.usacbank.view.MainView;

public class Main {
    public static void main(String[] args) {
        // Registrar inicio del sistema en la bitácora
        System.out.println(new Bitacora(
                "Sistema",
                "Inicio de aplicación",
                "Éxito",
                "USAC BANK iniciado correctamente."));

        // Crear el usuario por defecto (administrador)
        Usuario usuarioPorDefecto = Usuario.crearUsuarioPorDefecto();

        // Crear el controlador de clientes
        ClienteController clienteController = new ClienteController();

        // Crear el controlador de cuentas
        CuentaController cuentaController = new CuentaController();

        // Crear el controlador de transacciones
        TransaccionController transaccionController = new TransaccionController();

        // Imprimir las credenciales del usuario por defecto
        System.out.println("Usuario por defecto:");
        System.out.println("Username: " + usuarioPorDefecto.getUsername());
        System.out.println("Password: " + usuarioPorDefecto.getPassword());

        // Iniciar la aplicación con la vista principal
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainView(usuarioPorDefecto, clienteController, cuentaController, transaccionController)
                    .setVisible(true);
        });
    }
}