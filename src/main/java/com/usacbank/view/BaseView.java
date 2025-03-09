package com.usacbank.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BaseView extends JFrame {
    protected Image backgroundImage;
    protected JPanel backgroundPanel;

    public BaseView(String title) {
        setTitle(title + " - USAC BANK");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Cargar imagen de fondo
        try {
            File imgFile = new File("C:/JAVIER_USAC/IPC1_Proyecto1_202303204/src/resources/background1.png");
            if (imgFile.exists()) {
                backgroundImage = ImageIO.read(imgFile);
            } else {
                System.err.println("No se encontró el archivo de imagen: " + imgFile.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Establecer icono de la aplicación
        ImageIcon icon = new ImageIcon("C:/JAVIER_USAC/IPC1_Proyecto1_202303204/src/resources/bank_icon.png");
        setIconImage(icon.getImage());

        // Panel principal con fondo
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

                    // Añadir una capa semitransparente para mejorar la legibilidad del texto
                    g2d.setColor(new Color(0, 0, 0, 0.3f));
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    GradientPaint gradient = new GradientPaint(
                            0, 0, new Color(0, 30, 70),
                            0, getHeight(), new Color(0, 70, 130));
                    ((Graphics2D) g).setPaint(gradient);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);
    }
}