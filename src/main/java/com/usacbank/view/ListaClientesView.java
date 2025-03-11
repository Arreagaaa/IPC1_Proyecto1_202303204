package com.usacbank.view;

import com.usacbank.controller.ClienteController;
import com.usacbank.controller.CuentaController;
import com.usacbank.model.Cliente;
import com.usacbank.model.Usuario;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ListaClientesView extends BaseView {
    private ClienteController clienteController;
    private Usuario usuario;
    private CuentaController cuentaController;

    public ListaClientesView(ClienteController clienteController, Usuario usuario, CuentaController cuentaController) {
        super("Lista de Clientes");
        this.clienteController = clienteController;
        this.usuario = usuario;
        this.cuentaController = cuentaController;

        // Contenedor principal con margen
        JPanel mainContainer = new JPanel();
        mainContainer.setOpaque(false);
        mainContainer.setLayout(new BorderLayout());
        mainContainer.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Panel para el título
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Lista de Clientes");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(20));

        // Información sobre límite de clientes
        JLabel infoLabel = new JLabel("Clientes registrados: " + clienteController.getClientes().size() + " / 5");
        infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(infoLabel);
        titlePanel.add(Box.createVerticalStrut(10));

        // Crear la tabla de clientes con el nuevo diseño
        String[] columnNames = { "CUI", "Nombre", "Apellido" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };

        // Llenar la tabla con datos de clientes
        for (Cliente cliente : clienteController.getClientes()) {
            Object[] row = { cliente.getCui(), cliente.getNombre(), cliente.getApellido() };
            model.addRow(row);
        }

        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setBackground(Color.WHITE); // Fondo blanco
        table.setForeground(new Color(0, 30, 70)); // Texto azul oscuro
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(new Color(220, 220, 220)); // Líneas de cuadrícula gris claro

        // Centrar el contenido de todas las celdas de la tabla
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Encabezados de columna
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(0, 90, 170));
        table.getTableHeader().setForeground(Color.WHITE);

        // También centrar los encabezados de las columnas
        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 90, 170)));

        mainContainer.add(titlePanel, BorderLayout.NORTH);
        mainContainer.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Botón para crear nuevo cliente
        JButton crearButton = new JButton("Crear Nuevo Cliente");
        crearButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        crearButton.setForeground(Color.WHITE);
        crearButton.setBackground(new Color(0, 120, 215));
        crearButton.setFocusPainted(false);
        crearButton.setBorderPainted(false);
        crearButton.setContentAreaFilled(true);
        crearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        crearButton.setPreferredSize(new Dimension(200, 40));

        // Deshabilitar el botón de crear cliente si se alcanzó el límite
        if (clienteController.limiteAlcanzado()) {
            crearButton.setEnabled(false);
            crearButton.setBackground(new Color(150, 150, 150));
            crearButton.setText("Límite de Clientes Alcanzado");
        }

        crearButton.addActionListener(e -> {
            dispose();
            new RegistroClienteView(clienteController, usuario, cuentaController).setVisible(true);
        });

        // Botón para volver al menú
        JButton volverButton = new JButton("Volver al Menú");
        volverButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        volverButton.setForeground(Color.WHITE);
        volverButton.setBackground(new Color(0, 120, 215));
        volverButton.setFocusPainted(false);
        volverButton.setBorderPainted(false);
        volverButton.setContentAreaFilled(true);
        volverButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        volverButton.setPreferredSize(new Dimension(200, 40));

        volverButton.addActionListener(e -> {
            dispose();
            new MenuUsuarioView(usuario, clienteController, cuentaController).setVisible(true);
        });

        buttonPanel.add(crearButton);
        buttonPanel.add(volverButton);

        mainContainer.add(buttonPanel, BorderLayout.SOUTH);
        backgroundPanel.add(mainContainer, BorderLayout.CENTER);
    }
}