package com.usacbank.view;

import com.usacbank.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class MenuUsuarioView extends BaseView {
    public MenuUsuarioView(Usuario usuario) {
        super("Menú de Usuario");
        // Crear la barra de menú
        JMenuBar menuBar = new JMenuBar();
        JMenu ayudaMenu = new JMenu("Ayuda");
        JMenuItem datosEstudianteItem = new JMenuItem("Datos del estudiante");
        JMenuItem generacionBitacoraItem = new JMenuItem("Generación de Bitácora");

        ayudaMenu.add(datosEstudianteItem);
        ayudaMenu.add(generacionBitacoraItem);
        menuBar.add(ayudaMenu);
        setJMenuBar(menuBar);

        // Crear el panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Crear el label de bienvenida
        JLabel menuLabel = new JLabel("Bienvenido " + usuario.getUsername());
        menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        menuLabel.setForeground(Color.WHITE);
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(menuLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Crear el panel de botones con GridLayout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(3, 3, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Agregar margen

        // Crear los botones
        String[] buttonLabels = {
                "Registro de Usuario", "Crear Cuenta", "Crear Cuenta",
                "Depósitos", "Retiros", "Historial de Transacciones",
                "Generación de Reportes"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Segoe UI", Font.BOLD, 16));
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(0, 120, 215));
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setContentAreaFilled(true);
            button.setPreferredSize(new Dimension(200, 50));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Efectos al pasar el mouse sobre el botón
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(30, 144, 255));
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(0, 120, 215));
                }

                @Override
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(0, 90, 170));
                }
            });

            buttonPanel.add(button);
        }

        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalGlue());

        backgroundPanel.add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        Usuario usuarioPorDefecto = Usuario.crearUsuarioPorDefecto();
        SwingUtilities.invokeLater(() -> new MenuUsuarioView(usuarioPorDefecto).setVisible(true));
    }
}