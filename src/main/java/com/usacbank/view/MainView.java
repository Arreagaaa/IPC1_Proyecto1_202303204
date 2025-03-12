package com.usacbank.view;

import com.usacbank.controller.ClienteController;
import com.usacbank.controller.CuentaController;
import com.usacbank.controller.TransaccionController;
import com.usacbank.model.Usuario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainView extends BaseView {
    private JButton pressButton;
    private Timer animationTimer;
    private float alpha = 1.0f;
    private Usuario usuarioPorDefecto;
    private ClienteController clienteController;
    private CuentaController cuentaController;
    private TransaccionController transaccionController;

    public MainView(Usuario usuarioPorDefecto, ClienteController clienteController, CuentaController cuentaController,
            TransaccionController transaccionController) {
        super("USAC BANK");
        this.usuarioPorDefecto = usuarioPorDefecto;
        this.clienteController = clienteController;
        this.cuentaController = cuentaController;
        this.transaccionController = transaccionController;

        // Contenedor principal con margen
        JPanel mainContainer = new JPanel();
        mainContainer.setOpaque(false);
        mainContainer.setLayout(new BorderLayout());
        mainContainer.setBorder(BorderFactory.createEmptyBorder(60, 80, 60, 80)); // Márgenes más amplios

        // Panel central que contiene todo
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Logo y nombre del banco en diseño más elegante
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Logo del banco más profesional
        ImageIcon originalIcon = new ImageIcon("C:/JAVIER_USAC/IPC1_Proyecto1_202303204/src/resources/bank_icon.png");
        Image scaledIcon = originalIcon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        ImageIcon icono = new ImageIcon(scaledIcon);
        JLabel iconLabel = new JLabel(icono);

        // Panel para el título con estilo
        JPanel titleTextPanel = new JPanel();
        titleTextPanel.setOpaque(false);
        titleTextPanel.setLayout(new BoxLayout(titleTextPanel, BoxLayout.Y_AXIS));

        JLabel bankNameLabel = new JLabel("USAC BANK");
        bankNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        bankNameLabel.setForeground(Color.WHITE);
        bankNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sloganLabel = new JLabel("300 Años de Excelencia Financiera");
        sloganLabel.setFont(new Font("Segoe UI Light", Font.ITALIC, 18));
        sloganLabel.setForeground(new Color(220, 220, 220));
        sloganLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        titleTextPanel.add(bankNameLabel);
        titleTextPanel.add(Box.createVerticalStrut(5));
        titleTextPanel.add(sloganLabel);

        logoPanel.add(iconLabel);
        logoPanel.add(titleTextPanel);

        // Panel para mensaje de bienvenida
        JPanel welcomePanel = new JPanel();
        welcomePanel.setOpaque(false);
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomePanel.setBorder(new EmptyBorder(40, 0, 40, 0));

        JLabel welcomeLabel = new JLabel("¡Bienvenido a su Banca en Línea!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel infoLabel = new JLabel("Administre sus finanzas de manera rápida y segura");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoLabel.setForeground(new Color(220, 220, 220));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        welcomePanel.add(welcomeLabel);
        welcomePanel.add(Box.createVerticalStrut(15));
        welcomePanel.add(infoLabel);

        // Botón con efecto de brillo
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Creamos el botón con texto en lugar de imagen
        pressButton = new JButton("INGRESAR AL SISTEMA");
        pressButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pressButton.setForeground(Color.WHITE);
        pressButton.setBackground(new Color(0, 120, 215));
        pressButton.setFocusPainted(false);
        pressButton.setBorderPainted(false);
        pressButton.setContentAreaFilled(true);
        pressButton.setPreferredSize(new Dimension(250, 50));
        pressButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efectos al pasar el mouse sobre el botón
        pressButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                pressButton.setBackground(new Color(30, 144, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pressButton.setBackground(new Color(0, 120, 215));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                pressButton.setBackground(new Color(0, 90, 170));
            }
        });

        pressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Redireccionando al registro de usuario...");
                startFadeOutAnimation();
                SwingUtilities.invokeLater(
                        () -> new RegistroUsuarioView(usuarioPorDefecto, clienteController, cuentaController,
                                transaccionController)
                                .setVisible(true));
            }
        });

        buttonPanel.add(pressButton);

        // Pie de página
        JPanel footerPanel = new JPanel();
        footerPanel.setOpaque(false);
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBorder(new EmptyBorder(30, 0, 0, 0));

        JLabel copyrightLabel = new JLabel("© 2025 USAC International Bank - Todos los derechos reservados");
        copyrightLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        copyrightLabel.setForeground(new Color(200, 200, 200));

        footerPanel.add(copyrightLabel);

        // Agregamos todos los elementos al panel principal
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(logoPanel);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(welcomePanel);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createVerticalStrut(40));
        contentPanel.add(footerPanel);

        mainContainer.add(contentPanel, BorderLayout.CENTER);
        backgroundPanel.add(mainContainer, BorderLayout.CENTER);
    }

    // Método para animar la transición de salida
    private void startFadeOutAnimation() {
        animationTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha -= 0.05f;
                if (alpha <= 0) {
                    alpha = 0;
                    animationTimer.stop();
                    dispose(); // Cerramos esta ventana cuando la animación termina
                }
                repaint();
            }
        });
        animationTimer.start();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Creamos los objetos necesarios para el constructor de MainView
        Usuario usuarioPorDefecto = Usuario.crearUsuarioPorDefecto();
        ClienteController clienteController = new ClienteController();
        CuentaController cuentaController = new CuentaController();
        TransaccionController transaccionController = new TransaccionController();

        SwingUtilities.invokeLater(
                () -> new MainView(usuarioPorDefecto, clienteController, cuentaController, transaccionController)
                        .setVisible(true));
    }
}
