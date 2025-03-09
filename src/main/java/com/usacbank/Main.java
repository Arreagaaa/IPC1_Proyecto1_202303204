package com.usacbank;

import com.usacbank.model.Usuario;
import com.usacbank.view.MainView;

public class Main {
    public static void main(String[] args) {
        // Crear el usuario por defecto
        Usuario usuarioPorDefecto = Usuario.crearUsuarioPorDefecto();
        
        // Imprimir las credenciales del usuario por defecto (para verificar)
        System.out.println("Usuario por defecto:");
        System.out.println("Username: " + usuarioPorDefecto.getUsername());
        System.out.println("Password: " + usuarioPorDefecto.getPassword());

        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainView().setVisible(true);
        });
    }
}