package com.usacbank.view;

import com.usacbank.controller.CuentaController;
import com.usacbank.controller.ClienteController;
import com.usacbank.controller.TransaccionController;
import com.usacbank.controller.ReporteController;
import com.usacbank.model.Usuario;
import com.usacbank.model.Cliente;
import com.usacbank.model.Bitacora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.File;

public class GeneracionReportesView extends BaseView {
    private ClienteController clienteController;
    private ReporteController reporteController;
    private JTextField cuiField;
    private JComboBox<Cliente> clienteComboBox;

    public GeneracionReportesView(ClienteController clienteController, CuentaController cuentaController,
            TransaccionController transaccionController, Usuario usuario) {
        super("Generación de Reportes");
        this.clienteController = clienteController;
        this.reporteController = new ReporteController(clienteController, cuentaController, transaccionController);

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

        JLabel titleLabel = new JLabel("Generación de Reportes");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Seleccione el tipo de reporte que desea generar");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(subtitleLabel);

        // Panel de búsqueda de CUI
        JPanel searchPanel = new JPanel();
        searchPanel.setOpaque(false);
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 50));

        // Panel para ingresar CUI
        JPanel cuiPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        cuiPanel.setOpaque(false);

        JLabel cuiLabel = new JLabel("Ingrese CUI del cliente:");
        cuiLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cuiLabel.setForeground(Color.WHITE);

        cuiField = new JTextField(15);
        cuiField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cuiField.setPreferredSize(new Dimension(200, 30));

        cuiPanel.add(cuiLabel);
        cuiPanel.add(cuiField);

        // Panel para el ComboBox
        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        comboPanel.setOpaque(false);

        JLabel clienteLabel = new JLabel("O seleccione un cliente:");
        clienteLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        clienteLabel.setForeground(Color.WHITE);

        clienteComboBox = new JComboBox<>();
        clienteComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        clienteComboBox.setPreferredSize(new Dimension(300, 30));
        clienteComboBox.setBackground(Color.WHITE);

        // Cargar los clientes en el ComboBox
        cargarClientes();

        comboPanel.add(clienteLabel);
        comboPanel.add(clienteComboBox);

        searchPanel.add(cuiPanel);
        searchPanel.add(comboPanel);

        // Panel de botones de reportes
        JPanel reportButtonsPanel = new JPanel();
        reportButtonsPanel.setOpaque(false);
        reportButtonsPanel.setLayout(new GridLayout(1, 3, 20, 0));
        reportButtonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        reportButtonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        JButton transaccionesButton = createReportButton("Reporte de Historial de Transacciones");
        JButton depositosButton = createReportButton("Reporte de Depósitos Realizados");
        JButton retirosButton = createReportButton("Reporte de Retiros Realizados");

        reportButtonsPanel.add(transaccionesButton);
        reportButtonsPanel.add(depositosButton);
        reportButtonsPanel.add(retirosButton);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

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

        // Sincronizar ComboBox con TextField
        clienteComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Cliente clienteSeleccionado = (Cliente) clienteComboBox.getSelectedItem();
                if (clienteSeleccionado != null) {
                    cuiField.setText(clienteSeleccionado.getCui());
                }
            }
        });

        // Sincronizar TextField con ComboBox cuando se presione Enter
        cuiField.addActionListener(_ -> {
            String cui = cuiField.getText().trim();
            if (!cui.isEmpty()) {
                Cliente cliente = clienteController.getClientePorCui(cui);
                if (cliente != null) {
                    clienteComboBox.setSelectedItem(cliente);
                }
            }
        });

        // Acción para el botón de generar reporte de transacciones
        transaccionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporte(cuiField.getText().trim(), "transacciones");
            }
        });

        // Acción para el botón de generar reporte de depósitos
        depositosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporte(cuiField.getText().trim(), "depositos");
            }
        });

        // Acción para el botón de generar reporte de retiros
        retirosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporte(cuiField.getText().trim(), "retiros");
            }
        });

        // Acción para el botón volver
        volverButton.addActionListener(_ -> {
            dispose();
            new MenuUsuarioView(usuario, clienteController, cuentaController, transaccionController).setVisible(true);
        });

        // Agregar todos los elementos al panel de contenido
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(titlePanel);
        contentPanel.add(searchPanel);
        contentPanel.add(reportButtonsPanel);
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalGlue());

        mainContainer.add(contentPanel, BorderLayout.CENTER);
        backgroundPanel.add(mainContainer, BorderLayout.CENTER);
    }

    private void cargarClientes() {
        DefaultComboBoxModel<Cliente> model = new DefaultComboBoxModel<>();
        // Añadir un elemento vacío al inicio
        model.addElement(null);
        // Añadir todos los clientes
        for (Cliente cliente : clienteController.getClientes()) {
            model.addElement(cliente);
        }
        clienteComboBox.setModel(model);
        clienteComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                if (value == null) {
                    return super.getListCellRendererComponent(list, " ", index, isSelected,
                            cellHasFocus);
                }
                return super.getListCellRendererComponent(list, ((Cliente) value).toString(), index, isSelected,
                        cellHasFocus);
            }
        });
    }

    private JButton createReportButton(String title) {
        JButton button = new JButton(title);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 120, 215));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void generarReporte(String cui, String tipoReporte) {
        if (cui.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Debe ingresar un CUI o seleccionar un cliente",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String filename;
            switch (tipoReporte) {
                case "transacciones":
                    filename = reporteController.generarReporteTransacciones(cui);
                    break;
                case "depositos":
                    filename = reporteController.generarReporteDepositos(cui);
                    break;
                case "retiros":
                    filename = reporteController.generarReporteRetiros(cui);
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de reporte desconocido: " + tipoReporte);
            }

            // Registrar en bitácora
            System.out.println(new Bitacora(
                    "AdministradorIPC1B",
                    "Generación de reportes",
                    "Éxito",
                    "Reporte de " + tipoReporte + " generado para el cliente con CUI: " + cui));

            int option = JOptionPane.showConfirmDialog(null,
                    "Reporte generado exitosamente: " + filename + "\n¿Desea abrir el archivo ahora?",
                    "Reporte Generado",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
                try {
                    File pdfFile = new File(filename);
                    if (pdfFile.exists()) {
                        if (Desktop.isDesktopSupported()) {
                            Desktop.getDesktop().open(pdfFile);
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "No se puede abrir automáticamente el archivo PDF en este sistema.",
                                    "Error al abrir",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Error al abrir el archivo: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            // Registrar error en bitácora
            System.out.println(new Bitacora(
                    "AdministradorIPC1B",
                    "Generación de reportes",
                    "Error",
                    "Error al generar reporte de " + tipoReporte + " para el cliente con CUI: " + cui + ". Detalles: "
                            + ex.getMessage()));

            JOptionPane.showMessageDialog(null,
                    "Error al generar reporte: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
