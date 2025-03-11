package com.usacbank.view;

import com.usacbank.controller.CuentaController;
import com.usacbank.controller.ClienteController;
import com.usacbank.model.Cliente;
import com.usacbank.model.Cuenta;
import com.usacbank.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListaCuentaView extends BaseView {
    private CuentaController cuentaController;
    private ClienteController clienteController;
    private Usuario usuario;

    public ListaCuentaView(CuentaController cuentaController, ClienteController clienteController, Usuario usuario) {
        super("Ver Cuentas");
        this.cuentaController = cuentaController;
        this.clienteController = clienteController;
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

        JLabel titleLabel = new JLabel("Ver Cuentas");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(20));

        // Panel para la tabla de clientes
        JPanel tablePanel = new JPanel();
        tablePanel.setOpaque(false);
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel clientesLabel = new JLabel("Clientes:");
        clientesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        clientesLabel.setForeground(Color.WHITE);
        clientesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] clientesColumnNames = { "CUI", "Nombre", "Apellido" };
        DefaultTableModel clientesTableModel = new DefaultTableModel(clientesColumnNames, 0);
        JTable clientesTable = new JTable(clientesTableModel);
        clientesTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        clientesTable.setRowHeight(30);

        for (Cliente cliente : clienteController.getClientes()) {
            Object[] rowData = { cliente.getCui(), cliente.getNombre(), cliente.getApellido() };
            clientesTableModel.addRow(rowData);
        }

        JScrollPane clientesScrollPane = new JScrollPane(clientesTable);
        clientesScrollPane.setPreferredSize(new Dimension(600, 200));

        // Panel para la tabla de cuentas
        JPanel cuentasPanel = new JPanel();
        cuentasPanel.setOpaque(false);
        cuentasPanel.setLayout(new BoxLayout(cuentasPanel, BoxLayout.Y_AXIS));
        cuentasPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel cuentasLabel = new JLabel("Cuentas:");
        cuentasLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        cuentasLabel.setForeground(Color.WHITE);
        cuentasLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] cuentasColumnNames = { "ID Cuenta", "Saldo" };
        DefaultTableModel cuentasTableModel = new DefaultTableModel(cuentasColumnNames, 0);
        JTable cuentasTable = new JTable(cuentasTableModel);
        cuentasTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cuentasTable.setRowHeight(30);

        JScrollPane cuentasScrollPane = new JScrollPane(cuentasTable);
        cuentasScrollPane.setPreferredSize(new Dimension(600, 200));

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        // Botón para buscar cuentas
        JButton buscarButton = new JButton("BUSCAR");
        buscarButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        buscarButton.setForeground(Color.WHITE);
        buscarButton.setBackground(new Color(0, 120, 215));
        buscarButton.setFocusPainted(false);
        buscarButton.setBorderPainted(false);
        buscarButton.setContentAreaFilled(true);
        buscarButton.setPreferredSize(new Dimension(150, 50));
        buscarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

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

        // Acción para el botón buscar
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = clientesTable.getSelectedRow();
                if (selectedRow != -1) {
                    String cui = (String) clientesTableModel.getValueAt(selectedRow, 0);
                    Cliente cliente = clienteController.getClientePorCui(cui);
                    if (cliente != null) {
                        List<Cuenta> cuentas = cuentaController.getCuentasPorCliente(cliente);

                        cuentasTableModel.setRowCount(0); // Limpiar la tabla de cuentas
                        for (Cuenta cuenta : cuentas) {
                            Object[] rowData = { cuenta.getId(), cuenta.getSaldo() };
                            cuentasTableModel.addRow(rowData);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Cliente no encontrado.", "Error",
                                JOptionPane.ERROR_MESSAGE);
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
            new MenuUsuarioView(usuario, clienteController, cuentaController).setVisible(true);
        });

        buttonPanel.add(buscarButton);
        buttonPanel.add(volverButton);

        // Agregar todos los elementos al panel de contenido
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(titlePanel);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(clientesLabel);
        contentPanel.add(clientesScrollPane);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(cuentasLabel);
        contentPanel.add(cuentasScrollPane);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalGlue());

        mainContainer.add(contentPanel, BorderLayout.CENTER);
        backgroundPanel.add(mainContainer, BorderLayout.CENTER);
    }
}