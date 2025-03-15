package com.usacbank.view;

import com.usacbank.controller.ClienteController;
import com.usacbank.controller.CuentaController;
import com.usacbank.controller.TransaccionController;
import com.usacbank.model.Cliente;
import com.usacbank.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroClienteView extends BaseView {

    public RegistroClienteView(ClienteController clienteController, Usuario usuario,
            CuentaController cuentaController, TransaccionController transaccionController) {
        super("Registro de Cliente");

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

        JLabel titleLabel = new JLabel("Crear Cliente");
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

        JLabel cuiLabel = new JLabel("CUI:");
        cuiLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        cuiLabel.setForeground(Color.WHITE);
        cuiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField cuiField = new JTextField();
        cuiField.setMaximumSize(new Dimension(300, 30));
        cuiField.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        nombreLabel.setForeground(Color.WHITE);
        nombreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField nombreField = new JTextField();
        nombreField.setMaximumSize(new Dimension(300, 30));
        nombreField.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        JLabel apellidoLabel = new JLabel("Apellido:");
        apellidoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        apellidoLabel.setForeground(Color.WHITE);
        apellidoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField apellidoField = new JTextField();
        apellidoField.setMaximumSize(new Dimension(300, 30));
        apellidoField.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        formPanel.add(cuiLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(cuiField);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(nombreLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(nombreField);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(apellidoLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(apellidoField);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        // Botón de creación
        JButton crearButton = new JButton("CREAR");
        crearButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        crearButton.setForeground(Color.WHITE);
        crearButton.setBackground(new Color(0, 120, 215));
        crearButton.setFocusPainted(false);
        crearButton.setBorderPainted(false);
        crearButton.setContentAreaFilled(true);
        crearButton.setPreferredSize(new Dimension(150, 50));
        crearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Botón para volver
        JButton volverButton = new JButton("VOLVER");
        volverButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        volverButton.setForeground(Color.WHITE);
        volverButton.setBackground(new Color(100, 100, 100));
        volverButton.setFocusPainted(false);
        volverButton.setBorderPainted(false);
        volverButton.setContentAreaFilled(true);
        volverButton.setPreferredSize(new Dimension(150, 50));
        volverButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efectos al pasar el mouse sobre el botón crear
        crearButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                crearButton.setBackground(new Color(30, 144, 255));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                crearButton.setBackground(new Color(0, 120, 215));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                crearButton.setBackground(new Color(0, 90, 170));
            }
        });

        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cui = cuiField.getText();
                String nombre = nombreField.getText();
                String apellido = apellidoField.getText();

                if (cui.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Intentar crear el cliente y manejar el resultado
                Cliente nuevoCliente = new Cliente(cui, nombre, apellido);
                int resultado = clienteController.crearCliente(nuevoCliente);

                switch (resultado) {
                    case 0: // Éxito
                        JOptionPane.showMessageDialog(null, "Cliente creado exitosamente.", "Éxito",
                                JOptionPane.INFORMATION_MESSAGE);

                        // Cambiar a la vista de lista de clientes
                        dispose();
                        new ListaClientesView(clienteController, usuario, cuentaController, transaccionController)
                                .setVisible(true);
                        break;

                    case 1: // CUI duplicado
                        JOptionPane.showMessageDialog(null,
                                "Ya existe un cliente con el CUI: " + cui,
                                "Error - CUI Duplicado",
                                JOptionPane.ERROR_MESSAGE);
                        break;

                    case 2: // Límite alcanzado
                        JOptionPane.showMessageDialog(null,
                                "Se ha alcanzado el límite de 5 clientes permitidos.",
                                "Error - Límite Alcanzado",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                }
            }
        });

        // Acción para el botón volver
        volverButton.addActionListener(_ -> {
            dispose();
            new MenuUsuarioView(usuario, clienteController, cuentaController, transaccionController).setVisible(true);
        });

        buttonPanel.add(crearButton);
        buttonPanel.add(volverButton);

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

        // Verificar inmediatamente si se alcanzó el límite de clientes
        if (clienteController.limiteAlcanzado()) {
            JOptionPane.showMessageDialog(null,
                    "Se ha alcanzado el límite de 5 clientes permitidos.",
                    "Límite Alcanzado",
                    JOptionPane.WARNING_MESSAGE);

            // Deshabilitar el botón de crear
            crearButton.setEnabled(false);
            crearButton.setBackground(new Color(150, 150, 150));
        }
    }
}