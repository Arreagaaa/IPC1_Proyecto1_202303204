package com.usacbank.view;

import com.usacbank.controller.ClienteController;
import com.usacbank.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroUsuarioView extends BaseView {
    private Usuario usuarioPorDefecto;
    private ClienteController clienteController;

    public RegistroUsuarioView(Usuario usuarioPorDefecto, ClienteController clienteController) {
        super("Registro de Usuario");
        this.usuarioPorDefecto = usuarioPorDefecto;
        this.clienteController = clienteController;

        // Contenedor principal con margen
        JPanel mainContainer = new JPanel();
        mainContainer.setOpaque(false);
        mainContainer.setLayout(new BorderLayout());
        mainContainer.setBorder(BorderFactory.createEmptyBorder(60, 80, 60, 80));

        // Panel central que contiene todo
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Panel para el título
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Registro de Usuario");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(20));

        // Panel para el formulario de registro
        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel userLabel = new JLabel("Nombre de Usuario:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        userLabel.setForeground(Color.WHITE);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField userField = new JTextField();
        userField.setMaximumSize(new Dimension(300, 30));
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(300, 30));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        formPanel.add(userLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(userField);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(passwordLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(passwordField);

        // Botón de registro
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton registerButton = new JButton("REGISTRARSE");
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(0, 120, 215));
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(true);
        registerButton.setPreferredSize(new Dimension(250, 50));
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efectos al pasar el mouse sobre el botón
        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(new Color(30, 144, 255));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(new Color(0, 120, 215));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(new Color(0, 90, 170));
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals(usuarioPorDefecto.getUsername())
                        && password.equals(usuarioPorDefecto.getPassword())) {
                    System.out.println("Redireccionando al menú de usuario...");
                    SwingUtilities.invokeLater(
                            () -> new MenuUsuarioView(usuarioPorDefecto, clienteController).setVisible(true));
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Credenciales incorrectas. Intente de nuevo.",
                            "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonPanel.add(registerButton);

        // Agregar todos los elementos al panel de contenido
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(titlePanel);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(formPanel);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalGlue());

        mainContainer.add(contentPanel, BorderLayout.CENTER);
        backgroundPanel.add(mainContainer, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        Usuario usuarioPorDefecto = Usuario.crearUsuarioPorDefecto();
        ClienteController clienteController = new ClienteController(); // Corrección aquí
        SwingUtilities
                .invokeLater(() -> new RegistroUsuarioView(usuarioPorDefecto, clienteController).setVisible(true));
    }
}