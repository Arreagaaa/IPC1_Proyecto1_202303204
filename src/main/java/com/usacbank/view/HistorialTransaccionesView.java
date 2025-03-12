package com.usacbank.view;

import com.usacbank.controller.ClienteController;
import com.usacbank.controller.CuentaController;
import com.usacbank.controller.TransaccionController;
import com.usacbank.model.Cliente;
import com.usacbank.model.Cuenta;
import com.usacbank.model.Transaccion;
import com.usacbank.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class HistorialTransaccionesView extends BaseView {
    private CuentaController cuentaController;
    private ClienteController clienteController;
    private TransaccionController transaccionController;
    private Usuario usuario;

    public HistorialTransaccionesView(CuentaController cuentaController, ClienteController clienteController,
            TransaccionController transaccionController, Usuario usuario) {
        super("Historial de Transacciones");
        this.cuentaController = cuentaController;
        this.clienteController = clienteController;
        this.transaccionController = transaccionController;
        this.usuario = usuario;

        // Contenedor principal con margen
        JPanel mainContainer = new JPanel();
        mainContainer.setOpaque(false);
        mainContainer.setLayout(new BorderLayout());
        mainContainer.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // Panel central que contiene todo
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Panel para el título
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Historial de Transacciones");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(20));

        // Panel para la búsqueda de cuenta (COMBO BOX)
        JPanel searchComboPanel = new JPanel();
        searchComboPanel.setOpaque(false);
        searchComboPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        searchComboPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel cuentaLabel = new JLabel("Seleccione una cuenta:");
        cuentaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cuentaLabel.setForeground(Color.WHITE);

        JComboBox<String> cuentaComboBox = new JComboBox<>();
        cuentaComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cuentaComboBox.setPreferredSize(new Dimension(300, 30));

        // Llenar el ComboBox con las cuentas disponibles
        for (Cuenta cuenta : cuentaController.getCuentas()) {
            Cliente cliente = cuenta.getCliente();
            String nombreCompleto = cliente.getNombre() + " " + cliente.getApellido();
            cuentaComboBox.addItem(cuenta.getId() + " - " + nombreCompleto);
        }

        JButton buscarComboButton = new JButton("Mostrar");
        buscarComboButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        buscarComboButton.setForeground(Color.WHITE);
        buscarComboButton.setBackground(new Color(0, 120, 215));
        buscarComboButton.setFocusPainted(false);
        buscarComboButton.setBorderPainted(false);
        buscarComboButton.setContentAreaFilled(true);

        searchComboPanel.add(cuentaLabel);
        searchComboPanel.add(cuentaComboBox);
        searchComboPanel.add(buscarComboButton);

        // Panel para búsqueda por ID
        JPanel searchIdPanel = new JPanel();
        searchIdPanel.setOpaque(false);
        searchIdPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchIdPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel orLabel = new JLabel("O");
        orLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        orLabel.setForeground(Color.WHITE);

        JLabel idLabel = new JLabel("Busque por ID de cuenta:");
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        idLabel.setForeground(Color.WHITE);

        JTextField idTextField = new JTextField();
        idTextField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        idTextField.setPreferredSize(new Dimension(150, 30));

        JButton buscarIdButton = new JButton("Buscar");
        buscarIdButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        buscarIdButton.setForeground(Color.WHITE);
        buscarIdButton.setBackground(new Color(0, 120, 215));
        buscarIdButton.setFocusPainted(false);
        buscarIdButton.setBorderPainted(false);
        buscarIdButton.setContentAreaFilled(true);

        searchIdPanel.add(orLabel);
        searchIdPanel.add(idLabel);
        searchIdPanel.add(idTextField);
        searchIdPanel.add(buscarIdButton);

        // Panel para mostrar la información del cliente
        JPanel clienteInfoPanel = new JPanel();
        clienteInfoPanel.setOpaque(false);
        clienteInfoPanel.setLayout(new BoxLayout(clienteInfoPanel, BoxLayout.Y_AXIS));
        clienteInfoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        clienteInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel cuiLabel = new JLabel("CUI: ");
        cuiLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cuiLabel.setForeground(Color.WHITE);
        cuiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nombreLabel = new JLabel("Nombre: ");
        nombreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        nombreLabel.setForeground(Color.WHITE);
        nombreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel apellidoLabel = new JLabel("Apellido: ");
        apellidoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        apellidoLabel.setForeground(Color.WHITE);
        apellidoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel saldoLabel = new JLabel("Saldo Actual: ");
        saldoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        saldoLabel.setForeground(Color.WHITE);
        saldoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        clienteInfoPanel.add(cuiLabel);
        clienteInfoPanel.add(Box.createVerticalStrut(5));
        clienteInfoPanel.add(nombreLabel);
        clienteInfoPanel.add(Box.createVerticalStrut(5));
        clienteInfoPanel.add(apellidoLabel);
        clienteInfoPanel.add(Box.createVerticalStrut(5));
        clienteInfoPanel.add(saldoLabel);

        // Panel para la tabla de transacciones
        JPanel tablePanel = new JPanel();
        tablePanel.setOpaque(false);
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel transaccionesLabel = new JLabel("Transacciones:");
        transaccionesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        transaccionesLabel.setForeground(Color.WHITE);
        transaccionesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Crear modelo de tabla para transacciones
        String[] columnNames = { "ID", "Fecha y Hora", "Detalle", "Débito (Q)", "Crédito (Q)", "Saldo (Q)" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };

        JTable transaccionesTable = new JTable(tableModel);
        transaccionesTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        transaccionesTable.setRowHeight(30);

        // Personalizar los headers de la tabla
        JTableHeader header = transaccionesTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(0, 120, 215));
        header.setReorderingAllowed(false);

        // Centrar el texto de los headers
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Renderer para alinear los valores de la tabla
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < transaccionesTable.getColumnCount(); i++) {
            transaccionesTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(transaccionesTable);
        scrollPane.setPreferredSize(new Dimension(700, 300));

        tablePanel.add(transaccionesLabel);
        tablePanel.add(Box.createVerticalStrut(10));
        tablePanel.add(scrollPane);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton volverButton = new JButton("VOLVER");
        volverButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        volverButton.setForeground(Color.WHITE);
        volverButton.setBackground(new Color(100, 100, 100));
        volverButton.setFocusPainted(false);
        volverButton.setBorderPainted(false);
        volverButton.setContentAreaFilled(true);
        volverButton.setPreferredSize(new Dimension(150, 50));
        volverButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(volverButton);

        // Método para mostrar las transacciones de una cuenta
        ActionListener mostrarTransacciones = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cuenta cuentaSeleccionada = null;

                // Verificar si la acción vino del combo o del campo de texto
                if (e.getSource() == buscarComboButton && cuentaComboBox.getSelectedIndex() != -1) {
                    // Obtener cuenta del ComboBox
                    String seleccion = (String) cuentaComboBox.getSelectedItem();
                    String idCuenta = seleccion.split(" - ")[0];
                    cuentaSeleccionada = cuentaController.getCuentaPorId(idCuenta); // Usar el método del controlador
                } else if (e.getSource() == buscarIdButton) {
                    // Obtener cuenta del campo de texto
                    String idCuenta = idTextField.getText().trim();
                    if (idCuenta.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Ingrese un ID de cuenta.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    cuentaSeleccionada = cuentaController.getCuentaPorId(idCuenta); // Usar el método del controlador

                    // Si la encontramos, seleccionarla en el ComboBox también
                    if (cuentaSeleccionada != null) {
                        for (int i = 0; i < cuentaComboBox.getItemCount(); i++) {
                            String item = cuentaComboBox.getItemAt(i);
                            if (item.startsWith(idCuenta)) {
                                cuentaComboBox.setSelectedIndex(i);
                                break;
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "No existe ninguna cuenta con el ID: " + idCuenta,
                                "Cuenta no encontrada", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if (cuentaSeleccionada != null) {
                    Cliente cliente = cuentaSeleccionada.getCliente();

                    // Actualizar la información del cliente
                    cuiLabel.setText("CUI: " + cliente.getCui());
                    nombreLabel.setText("Nombre: " + cliente.getNombre());
                    apellidoLabel.setText("Apellido: " + cliente.getApellido());

                    DecimalFormat df = new DecimalFormat("#,##0.00");
                    saldoLabel.setText("Saldo Actual: Q" + df.format(cuentaSeleccionada.getSaldo()));

                    // Obtener y mostrar transacciones
                    List<Transaccion> transacciones = transaccionController
                            .getTransaccionesPorCuenta(cuentaSeleccionada);
                    tableModel.setRowCount(0); // Limpiar la tabla

                    if (transacciones.isEmpty()) {
                        JOptionPane.showMessageDialog(null,
                                "Esta cuenta no tiene transacciones registradas.",
                                "Sin transacciones",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        for (Transaccion transaccion : transacciones) {
                            // Usar el método getFechaFormateada() que ya está implementado
                            String fecha = transaccion.getFechaFormateada();
                            String detalle = transaccion.getDetalle();

                            // Usar directamente los valores de débito y crédito
                            String debito = transaccion.getMontoDebito() > 0 ? df.format(transaccion.getMontoDebito())
                                    : "";
                            String credito = transaccion.getMontoCredito() > 0
                                    ? df.format(transaccion.getMontoCredito())
                                    : "";

                            // Usar el saldo resultante almacenado
                            String saldo = df.format(transaccion.getSaldoResultante());

                            Object[] rowData = {
                                    transaccion.getId(),
                                    fecha,
                                    detalle,
                                    debito,
                                    credito,
                                    saldo
                            };

                            tableModel.addRow(rowData);
                        }
                    }
                }
            }
        };

        // Asignar la acción a ambos botones
        buscarComboButton.addActionListener(mostrarTransacciones);
        buscarIdButton.addActionListener(mostrarTransacciones);

        // Acción para el botón volver
        volverButton.addActionListener(e -> {
            dispose();
            new MenuUsuarioView(usuario, clienteController, cuentaController, transaccionController).setVisible(true);
        });

        // Agregar todos los elementos al panel de contenido
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(titlePanel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(searchComboPanel);
        contentPanel.add(searchIdPanel); // Nuevo panel de búsqueda por ID
        contentPanel.add(clienteInfoPanel);
        contentPanel.add(tablePanel);
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalGlue());

        mainContainer.add(contentPanel, BorderLayout.CENTER);
        backgroundPanel.add(mainContainer, BorderLayout.CENTER);
    }
}
