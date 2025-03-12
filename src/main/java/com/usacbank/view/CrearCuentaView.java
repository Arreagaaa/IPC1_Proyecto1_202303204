package com.usacbank.view;

import com.usacbank.controller.CuentaController;
import com.usacbank.controller.ClienteController;
import com.usacbank.controller.TransaccionController;
import com.usacbank.model.Cliente;
import com.usacbank.model.Cuenta;
import com.usacbank.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CrearCuentaView extends BaseView {
    private CuentaController cuentaController;
    private ClienteController clienteController;
    private TransaccionController transaccionController;
    private Usuario usuario;

    public CrearCuentaView(CuentaController cuentaController, ClienteController clienteController,
            TransaccionController transaccionController, Usuario usuario) {
        super("Crear Cuenta");
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

        JLabel titleLabel = new JLabel("Crear Cuenta");
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

        JLabel clienteLabel = new JLabel("Cliente:");
        clienteLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        clienteLabel.setForeground(Color.WHITE);
        clienteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ComboBox con renderizador personalizado para mostrar cuántas cuentas tiene
        // cada cliente
        JComboBox<Cliente> clienteComboBox = new JComboBox<>();

        // Personalizar el renderizador del ComboBox
        clienteComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Cliente) {
                    Cliente cliente = (Cliente) value;
                    List<Cuenta> cuentasCliente = cuentaController.getCuentasPorCliente(cliente);
                    int numCuentas = cuentasCliente.size();
                    setText(cliente.toString() + " - Cuentas: " + numCuentas + "/4");

                    // Colorear en rojo si el cliente ya tiene 4 cuentas
                    if (numCuentas >= 4) {
                        setForeground(Color.RED);
                    } else {
                        setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
                    }
                }
                return this;
            }
        });

        // Agregar los clientes al ComboBox
        for (Cliente cliente : clienteController.getClientes()) {
            clienteComboBox.addItem(cliente);
        }

        clienteComboBox.setMaximumSize(new Dimension(400, 30));
        clienteComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        formPanel.add(clienteLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(clienteComboBox);

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

        // Dentro del ActionListener del botón crear:
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cliente clienteSeleccionado = (Cliente) clienteComboBox.getSelectedItem();
                if (clienteSeleccionado != null) {
                    int resultado = cuentaController.crearCuenta(clienteSeleccionado);
                    switch (resultado) {
                        case 0: // Éxito
                            JOptionPane.showMessageDialog(null, "Cuenta creada exitosamente.", "Éxito",
                                    JOptionPane.INFORMATION_MESSAGE);
                            // Redirigir a la lista de cuentas
                            dispose();
                            new ListaCuentaView(cuentaController, clienteController,
                                    transaccionController, usuario).setVisible(true);
                            break;
                        case 1: // Límite excedido
                            JOptionPane.showMessageDialog(null,
                                    "El cliente ya tiene el máximo de 4 cuentas permitidas.",
                                    "Error - Límite de cuentas",
                                    JOptionPane.ERROR_MESSAGE);
                            break;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción para el botón volver
        volverButton.addActionListener(e -> {
            dispose();
            new MenuUsuarioView(usuario, clienteController, cuentaController,
                    transaccionController).setVisible(true);
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
    }
}
