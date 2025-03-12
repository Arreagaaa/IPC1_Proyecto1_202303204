package com.usacbank.view;

import com.usacbank.controller.ClienteController;
import com.usacbank.controller.CuentaController;
import com.usacbank.controller.TransaccionController;
import com.usacbank.model.Cliente;
import com.usacbank.model.Cuenta;
import com.usacbank.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class DepositoView extends BaseView {
    private CuentaController cuentaController;
    private ClienteController clienteController;
    private TransaccionController transaccionController;
    private Usuario usuario;

    public DepositoView(CuentaController cuentaController, ClienteController clienteController,
            TransaccionController transaccionController, Usuario usuario) {
        super("Depósito");
        this.cuentaController = cuentaController;
        this.clienteController = clienteController;
        this.transaccionController = transaccionController;
        this.usuario = usuario;

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

        JLabel titleLabel = new JLabel("Realizar Depósito");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(20));

        // Panel para el formulario
        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ComboBox para seleccionar cuenta
        JLabel cuentaLabel = new JLabel("Seleccione una cuenta:");
        cuentaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        cuentaLabel.setForeground(Color.WHITE);
        cuentaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JComboBox<String> cuentaComboBox = new JComboBox<>();
        cuentaComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cuentaComboBox.setMaximumSize(new Dimension(400, 30));

        // Llenar el ComboBox con las cuentas disponibles
        for (Cuenta cuenta : cuentaController.getCuentas()) {
            Cliente cliente = cuenta.getCliente();
            String nombreCompleto = cliente.getNombre() + " " + cliente.getApellido();
            cuentaComboBox.addItem(cuenta.getId() + " - " + nombreCompleto);
        }

        // Campo para el monto
        JLabel montoLabel = new JLabel("Monto a depositar (Q):");
        montoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        montoLabel.setForeground(Color.WHITE);
        montoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField montoField = new JTextField();
        montoField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        montoField.setMaximumSize(new Dimension(400, 30));

        // Agregar los componentes al panel del formulario
        formPanel.add(cuentaLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(cuentaComboBox);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(montoLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(montoField);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        // Botón para realizar depósito
        JButton depositarButton = new JButton("DEPOSITAR");
        depositarButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        depositarButton.setForeground(Color.WHITE);
        depositarButton.setBackground(new Color(0, 120, 215));
        depositarButton.setFocusPainted(false);
        depositarButton.setBorderPainted(false);
        depositarButton.setContentAreaFilled(true);
        depositarButton.setPreferredSize(new Dimension(150, 50));
        depositarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

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

        // Acción para el botón depositar
        depositarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cuentaComboBox.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String montoTexto = montoField.getText().trim();
                if (montoTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese un monto.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    double monto = Double.parseDouble(montoTexto);
                    if (monto <= 0) {
                        JOptionPane.showMessageDialog(null, "El monto debe ser mayor que cero.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Obtener el ID de cuenta seleccionada
                    String seleccion = (String) cuentaComboBox.getSelectedItem();
                    String idCuenta = seleccion.split(" - ")[0];

                    // Buscar la cuenta en el controlador
                    Cuenta cuentaSeleccionada = null;
                    for (Cuenta cuenta : cuentaController.getCuentas()) {
                        if (cuenta.getId().equals(idCuenta)) {
                            cuentaSeleccionada = cuenta;
                            break;
                        }
                    }

                    if (cuentaSeleccionada != null) {
                        // Realizar el depósito
                        boolean resultado = transaccionController.registrarDeposito(cuentaSeleccionada, monto);

                        if (resultado) {
                            DecimalFormat df = new DecimalFormat("#,##0.00");
                            JOptionPane.showMessageDialog(null,
                                    "Depósito realizado exitosamente.\n" +
                                            "Nuevo saldo: Q" + df.format(cuentaSeleccionada.getSaldo()),
                                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

                            // Redirigir a la lista de cuentas para ver el cambio inmediatamente
                            dispose();
                            new ListaCuentaView(cuentaController, clienteController,
                                    transaccionController, usuario).setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "No se pudo realizar el depósito.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Formato de monto inválido. Ingrese un valor numérico.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción para el botón volver
        volverButton.addActionListener(e -> {
            dispose();
            new MenuUsuarioView(usuario, clienteController, cuentaController, transaccionController).setVisible(true);
        });

        // Agregar botones al panel de botones
        buttonPanel.add(depositarButton);
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
    }
}