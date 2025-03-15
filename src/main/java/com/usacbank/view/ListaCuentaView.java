package com.usacbank.view;

import com.usacbank.controller.CuentaController;
import com.usacbank.controller.ClienteController;
import com.usacbank.controller.TransaccionController;
import com.usacbank.model.Cliente;
import com.usacbank.model.Cuenta;
import com.usacbank.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListaCuentaView extends BaseView {

    public ListaCuentaView(CuentaController cuentaController, ClienteController clienteController,
            TransaccionController transaccionController, Usuario usuario) {
        super("Ver Cuentas");

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

        // Después del título y antes de la tabla de clientes, agrega un panel de
        // búsqueda
        JPanel searchPanel = new JPanel();
        searchPanel.setOpaque(false);
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        searchPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel searchLabel = new JLabel("Buscar por CUI:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchLabel.setForeground(Color.WHITE);

        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchField.setPreferredSize(new Dimension(200, 30));

        JButton searchButton = new JButton("Buscar");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(new Color(0, 120, 215));
        searchButton.setFocusPainted(false);
        searchButton.setBorderPainted(false);
        searchButton.setContentAreaFilled(true);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Panel para la tabla de clientes
        JPanel tablePanel = new JPanel();
        tablePanel.setOpaque(false);
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel clientesLabel = new JLabel("Clientes:");
        clientesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        clientesLabel.setForeground(Color.WHITE);
        clientesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Crear un modelo de tabla no editable
        String[] clientesColumnNames = { "CUI", "Nombre", "Apellido" };
        DefaultTableModel clientesTableModel = new DefaultTableModel(clientesColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer todas las celdas no editables
            }
        };

        JTable clientesTable = new JTable(clientesTableModel);
        clientesTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        clientesTable.setRowHeight(30);

        // Personalizar los headers de la tabla de clientes
        JTableHeader clientesHeader = clientesTable.getTableHeader();
        clientesHeader.setFont(new Font("Segoe UI", Font.BOLD, 16));
        clientesHeader.setForeground(Color.WHITE);
        clientesHeader.setBackground(new Color(0, 120, 215));
        clientesHeader.setReorderingAllowed(false);
        clientesHeader.setResizingAllowed(true);

        // Centrar el texto de los headers
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) clientesHeader.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

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

        // Crear un modelo de tabla no editable para cuentas
        String[] cuentasColumnNames = { "ID Cuenta", "Saldo" };
        DefaultTableModel cuentasTableModel = new DefaultTableModel(cuentasColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer todas las celdas no editables
            }
        };

        JTable cuentasTable = new JTable(cuentasTableModel);
        cuentasTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cuentasTable.setRowHeight(30);

        // Personalizar los headers de la tabla de cuentas
        JTableHeader cuentasHeader = cuentasTable.getTableHeader();
        cuentasHeader.setFont(new Font("Segoe UI", Font.BOLD, 16));
        cuentasHeader.setForeground(Color.WHITE);
        cuentasHeader.setBackground(new Color(0, 120, 215));
        cuentasHeader.setReorderingAllowed(false);
        cuentasHeader.setResizingAllowed(true);

        // Centrar el texto de los headers
        DefaultTableCellRenderer cuentasHeaderRenderer = (DefaultTableCellRenderer) cuentasHeader.getDefaultRenderer();
        cuentasHeaderRenderer.setHorizontalAlignment(JLabel.CENTER);

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

        // En el panel de botones, añadir un nuevo botón para crear cuenta
        JButton crearButton = new JButton("CREAR NUEVA CUENTA");
        crearButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        crearButton.setForeground(Color.WHITE);
        crearButton.setBackground(new Color(0, 120, 215));
        crearButton.setFocusPainted(false);
        crearButton.setBorderPainted(false);
        crearButton.setContentAreaFilled(true);
        crearButton.setPreferredSize(new Dimension(200, 50));
        crearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efectos al pasar el mouse por el botón crear
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

        // Acción para el botón crear
        crearButton.addActionListener(_ -> {
            dispose();
            new CrearCuentaView(cuentaController, clienteController,
                    transaccionController, usuario).setVisible(true);
        });

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

        // Implementar la acción de búsqueda por CUI
        searchButton.addActionListener(_ -> {
            String cuiBusqueda = searchField.getText().trim();
            if (cuiBusqueda.isEmpty()) {
                // Si el campo está vacío, mostrar todos los clientes
                clientesTableModel.setRowCount(0);
                for (Cliente cliente : clienteController.getClientes()) {
                    Object[] rowData = { cliente.getCui(), cliente.getNombre(), cliente.getApellido() };
                    clientesTableModel.addRow(rowData);
                }
            } else {
                // Buscar cliente con ese CUI
                Cliente clienteEncontrado = clienteController.getClientePorCui(cuiBusqueda);
                clientesTableModel.setRowCount(0);
                if (clienteEncontrado != null) {
                    Object[] rowData = { clienteEncontrado.getCui(), clienteEncontrado.getNombre(),
                            clienteEncontrado.getApellido() };
                    clientesTableModel.addRow(rowData);

                    // Opcional: mostrar sus cuentas automáticamente
                    List<Cuenta> cuentas = cuentaController.getCuentasPorCliente(clienteEncontrado);
                    cuentasTableModel.setRowCount(0);
                    for (Cuenta cuenta : cuentas) {
                        Object[] cuentaRow = { cuenta.getId(), cuenta.getSaldo() };
                        cuentasTableModel.addRow(cuentaRow);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No se encontró ningún cliente con el CUI: " + cuiBusqueda,
                            "Cliente no encontrado",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Acción para el botón volver
        volverButton.addActionListener(_ -> {
            dispose();
            new MenuUsuarioView(usuario, clienteController, cuentaController,
                    transaccionController).setVisible(true);
        });

        // Modificar el panel de botones para incluir los tres botones
        buttonPanel.add(buscarButton);
        buttonPanel.add(crearButton);
        buttonPanel.add(volverButton);

        // Agregar todos los elementos al panel de contenido
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(titlePanel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(searchPanel);
        contentPanel.add(Box.createVerticalStrut(10));
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