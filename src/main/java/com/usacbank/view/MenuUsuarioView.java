package com.usacbank.view;

import com.usacbank.controller.ClienteController;
import com.usacbank.controller.CuentaController;
import com.usacbank.controller.TransaccionController;
import com.usacbank.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class MenuUsuarioView extends BaseView {
    private Usuario usuario;
    private ClienteController clienteController;
    private CuentaController cuentaController;
    private TransaccionController transaccionController;

    public MenuUsuarioView(Usuario usuario, ClienteController clienteController, CuentaController cuentaController,
            TransaccionController transaccionController) {
        super("Menú de Usuario");
        this.usuario = usuario;
        this.clienteController = clienteController;
        this.cuentaController = cuentaController;
        this.transaccionController = transaccionController;

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
                "Crear Cliente", "Ver Clientes", "Crear Cuenta", "Ver Cuentas",
                "Depósitos", "Retiros", "Historial de Transacciones",
                "Generación de Reportes", "Cerrar Sesión"
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

            // Configurar acciones para cada botón
            switch (label) {
                case "Crear Cliente":
                    button.addActionListener(e -> {
                        dispose();
                        new RegistroClienteView(clienteController, usuario, cuentaController,
                                transaccionController).setVisible(true);
                    });
                    break;

                case "Ver Clientes":
                    button.addActionListener(e -> {
                        if (!clienteController.existenClientes()) {
                            JOptionPane.showMessageDialog(null,
                                    "No hay clientes registrados en el sistema.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            dispose();
                            new ListaClientesView(clienteController, usuario, cuentaController,
                                    transaccionController).setVisible(true);
                        }
                    });
                    break;

                case "Crear Cuenta":
                    button.addActionListener(e -> {
                        if (!clienteController.existenClientes()) {
                            JOptionPane.showMessageDialog(null,
                                    "No hay clientes registrados. Debe crear clientes antes de crear cuentas.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            dispose();
                            new CrearCuentaView(cuentaController, clienteController,
                                    transaccionController, usuario).setVisible(true);
                        }
                    });
                    break;

                case "Ver Cuentas":
                    button.addActionListener(e -> {
                        if (!cuentaController.existenCuentas()) {
                            JOptionPane.showMessageDialog(null,
                                    "No hay cuentas registradas en el sistema.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else if (!clienteController.existenClientes()) {
                            JOptionPane.showMessageDialog(null,
                                    "No hay clientes registrados en el sistema.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            dispose();
                            new ListaCuentaView(cuentaController, clienteController,
                                    transaccionController, usuario).setVisible(true);
                        }
                    });
                    break;

                case "Depósitos":
                    button.addActionListener(e -> {
                        if (!cuentaController.existenCuentas()) {
                            JOptionPane.showMessageDialog(null,
                                    "No hay cuentas registradas. No se pueden realizar depósitos.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            dispose();
                            new DepositoView(cuentaController, clienteController, transaccionController, usuario)
                                    .setVisible(true);
                        }
                    });
                    break;

                case "Retiros":
                    button.addActionListener(e -> {
                        if (!cuentaController.existenCuentas()) {
                            JOptionPane.showMessageDialog(null,
                                    "No hay cuentas registradas. No se pueden realizar retiros.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            dispose();
                            new RetiroView(cuentaController, clienteController, transaccionController, usuario)
                                    .setVisible(true);
                        }
                    });
                    break;

                case "Historial de Transacciones":
                    button.addActionListener(e -> {
                        if (!cuentaController.existenCuentas()) {
                            JOptionPane.showMessageDialog(null,
                                    "No hay cuentas registradas. No hay historial de transacciones para mostrar.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            dispose();
                            new HistorialTransaccionesView(cuentaController, clienteController, transaccionController,
                                    usuario).setVisible(true);
                        }
                    });
                    break;

                case "Generación de Reportes":
                    button.addActionListener(e -> {
                        if (!cuentaController.existenCuentas() && !clienteController.existenClientes()) {
                            JOptionPane.showMessageDialog(null,
                                    "No hay datos suficientes para generar reportes.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            dispose();
                            new GeneracionReportesView(clienteController, cuentaController,
                                    transaccionController, usuario).setVisible(true);
                        }
                    });
                    break;

                case "Cerrar Sesión":
                    button.addActionListener(e -> {
                        dispose();
                        Usuario usuarioPorDefecto = Usuario.crearUsuarioPorDefecto();
                        new MainView(usuarioPorDefecto, clienteController, cuentaController, transaccionController)
                                .setVisible(true);
                    });
                    break;

                default:
                    // Para los botones sin funcionalidad aún
                    button.addActionListener(e -> {
                        JOptionPane.showMessageDialog(null,
                                "Funcionalidad no implementada aún: " + label,
                                "En desarrollo",
                                JOptionPane.INFORMATION_MESSAGE);
                    });
                    break;
            }

            buttonPanel.add(button);
        }

        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalGlue());

        backgroundPanel.add(mainPanel, BorderLayout.CENTER);

        // Acción para mostrar los datos del estudiante
        datosEstudianteItem.addActionListener(e -> mostrarDatosEstudiante());
    }

    private void mostrarDatosEstudiante() {
        // Crear un nuevo JFrame para mostrar los datos del estudiante
        JFrame datosEstudianteFrame = new JFrame("Datos del Estudiante");
        datosEstudianteFrame.setSize(400, 300);
        datosEstudianteFrame.setLocationRelativeTo(null);
        datosEstudianteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Crear el panel de contenido
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color(0, 30, 70)); // Fondo oscuro

        // Crear un borde alrededor del panel de contenido
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        // Título destacado
        JLabel titleLabel = new JLabel("Datos del Estudiante");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Agregar los datos del estudiante
        JLabel nombreLabel = new JLabel("Nombre: Christian Javier Rivas Arreaga");
        nombreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        nombreLabel.setForeground(Color.WHITE); // Texto blanco
        nombreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel carnetLabel = new JLabel("Carnet: 202303204");
        carnetLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        carnetLabel.setForeground(Color.WHITE); // Texto blanco
        carnetLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel seccionLabel = new JLabel("Sección: B Laboratorio IPC1");
        seccionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        seccionLabel.setForeground(Color.WHITE); // Texto blanco
        seccionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(nombreLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(carnetLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(seccionLabel);

        datosEstudianteFrame.add(contentPanel);
        datosEstudianteFrame.setVisible(true);
    }
}