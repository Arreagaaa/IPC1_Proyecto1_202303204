package com.usacbank.controller;

import com.usacbank.model.Cliente;
import com.usacbank.model.Cuenta;
import com.usacbank.model.Transaccion;
import com.usacbank.model.Bitacora;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReporteController {
    private ClienteController clienteController;
    private CuentaController cuentaController;
    private TransaccionController transaccionController;

    public ReporteController(ClienteController clienteController,
            CuentaController cuentaController,
            TransaccionController transaccionController) {
        this.clienteController = clienteController;
        this.cuentaController = cuentaController;
        this.transaccionController = transaccionController;
    }

    public String generarReporteTransacciones(String cui) throws Exception {
        try {
            // Obtener el cliente por CUI
            Cliente cliente = clienteController.getClientePorCui(cui);

            if (cliente == null) {
                // Registrar error en bitácora
                System.out.println(new Bitacora(
                        "AdministradorIPC1B",
                        "Generación de reportes",
                        "Error",
                        "Cliente no encontrado con CUI: " + cui));
                throw new Exception("Cliente no encontrado con CUI: " + cui);
            }

            // Obtener las cuentas del cliente
            List<Cuenta> cuentasCliente = cuentaController.getCuentasPorCliente(cliente);

            if (cuentasCliente.isEmpty()) {
                throw new Exception("El cliente no tiene cuentas registradas");
            }

            // Crear la estructura de carpetas
            String baseDir = "Reportes/HistorialTransacciones";
            File dir = new File(baseDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Generar nombre de archivo con timestamp actual
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
            String timestamp = sdf.format(new Date());
            String filename = baseDir + "/reporteTransacciones_" + timestamp + ".pdf";

            // Crear documento PDF
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            // Añadir título
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Reporte de Transacciones - USAC BANK", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // Añadir información del cliente
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

            document.add(new Paragraph("Información del Cliente", boldFont));
            document.add(new Paragraph("CUI: " + cliente.getCui(), normalFont));
            document.add(new Paragraph("Nombre: " + cliente.getNombre() + " " + cliente.getApellido(), normalFont));
            document.add(new Paragraph(
                    "Fecha de generación: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()),
                    normalFont));
            document.add(new Paragraph("\n"));

            // Para cada cuenta, listar sus transacciones
            DecimalFormat df = new DecimalFormat("#,##0.00");
            boolean hayTransacciones = false;

            for (Cuenta cuenta : cuentasCliente) {
                List<Transaccion> transacciones = transaccionController.getTransaccionesPorCuenta(cuenta);

                if (!transacciones.isEmpty()) {
                    hayTransacciones = true;

                    document.add(new Paragraph("Cuenta: " + cuenta.getId(), boldFont));
                    document.add(new Paragraph("Saldo actual: Q" + df.format(cuenta.getSaldo()), normalFont));
                    document.add(new Paragraph("\n"));

                    // Crear tabla de transacciones
                    PdfPTable table = new PdfPTable(6);
                    table.setWidthPercentage(100);

                    // Configurar encabezados
                    PdfPCell cell = new PdfPCell(new Phrase("ID", boldFont));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Fecha y Hora", boldFont));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Detalle", boldFont));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Débito (Q)", boldFont));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Crédito (Q)", boldFont));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Saldo (Q)", boldFont));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(cell);

                    // Añadir datos de transacciones
                    for (Transaccion transaccion : transacciones) {
                        table.addCell(String.valueOf(transaccion.getId()));
                        table.addCell(transaccion.getFechaFormateada());
                        table.addCell(transaccion.getDetalle());

                        // Débito
                        cell = new PdfPCell(
                                new Phrase(
                                        transaccion.getMontoDebito() > 0 ? df.format(transaccion.getMontoDebito()) : "",
                                        normalFont));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cell);

                        // Crédito
                        cell = new PdfPCell(new Phrase(
                                transaccion.getMontoCredito() > 0 ? df.format(transaccion.getMontoCredito()) : "",
                                normalFont));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cell);

                        // Saldo resultante
                        cell = new PdfPCell(new Phrase(df.format(transaccion.getSaldoResultante()), normalFont));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table.addCell(cell);
                    }

                    document.add(table);
                    document.add(new Paragraph("\n"));
                }
            }

            if (!hayTransacciones) {
                document.add(new Paragraph("El cliente no tiene transacciones registradas en ninguna de sus cuentas.",
                        normalFont));
            }

            // Cerrar documento
            document.close();

            // Al finalizar exitosamente, registrar en bitácora
            System.out.println(new Bitacora(
                    "AdministradorIPC1B",
                    "Generación de reportes",
                    "Éxito",
                    "Reporte de transacciones generado para el cliente " + cliente.getNombre() +
                            " " + cliente.getApellido() + " (CUI: " + cui + ")"));

            return filename;
        } catch (Exception e) {
            // Registrar error en bitácora
            System.out.println(new Bitacora(
                    "AdministradorIPC1B",
                    "Generación de reportes",
                    "Error",
                    "Error al generar reporte de transacciones para CUI " + cui + ": " + e.getMessage()));
            throw e;
        }
    }

    public String generarReporteDepositos(String cui) throws Exception {
        try {
            // Obtener el cliente por CUI
            Cliente cliente = clienteController.getClientePorCui(cui);

            if (cliente == null) {
                // Registrar error en bitácora
                System.out.println(new Bitacora(
                        "AdministradorIPC1B",
                        "Generación de reportes",
                        "Error",
                        "Cliente no encontrado con CUI: " + cui));
                throw new Exception("Cliente no encontrado con CUI: " + cui);
            }

            // Obtener las cuentas del cliente
            List<Cuenta> cuentasCliente = cuentaController.getCuentasPorCliente(cliente);

            if (cuentasCliente.isEmpty()) {
                throw new Exception("El cliente no tiene cuentas registradas");
            }

            // Crear la estructura de carpetas
            String baseDir = "Reportes/Depositos";
            File dir = new File(baseDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Generar nombre de archivo con timestamp actual
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
            String timestamp = sdf.format(new Date());
            String filename = baseDir + "/reporteDepositos_" + timestamp + ".pdf";

            // Crear documento PDF
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            // Añadir título
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Reporte de Depósitos - USAC BANK", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // Añadir información del cliente
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

            document.add(new Paragraph("Información del Cliente", boldFont));
            document.add(new Paragraph("CUI: " + cliente.getCui(), normalFont));
            document.add(new Paragraph("Nombre: " + cliente.getNombre() + " " + cliente.getApellido(), normalFont));
            document.add(new Paragraph(
                    "Fecha de generación: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()),
                    normalFont));
            document.add(new Paragraph("\n"));

            // Para cada cuenta, listar sus depósitos
            DecimalFormat df = new DecimalFormat("#,##0.00");
            boolean hayDepositos = false;

            for (Cuenta cuenta : cuentasCliente) {
                List<Transaccion> transacciones = transaccionController.getTransaccionesPorCuenta(cuenta);

                if (!transacciones.isEmpty()) {
                    boolean cuentaTieneDepositos = false;

                    for (Transaccion transaccion : transacciones) {
                        if (transaccion.getMontoCredito() > 0) {
                            if (!cuentaTieneDepositos) {
                                hayDepositos = true;
                                cuentaTieneDepositos = true;

                                document.add(new Paragraph("Cuenta: " + cuenta.getId(), boldFont));
                                document.add(
                                        new Paragraph("Saldo actual: Q" + df.format(cuenta.getSaldo()), normalFont));
                                document.add(new Paragraph("\n"));

                                // Crear tabla de depósitos
                                PdfPTable table = new PdfPTable(4);
                                table.setWidthPercentage(100);

                                // Configurar encabezados
                                PdfPCell cell = new PdfPCell(new Phrase("ID", boldFont));
                                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                table.addCell(cell);

                                cell = new PdfPCell(new Phrase("Fecha y Hora", boldFont));
                                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                table.addCell(cell);

                                cell = new PdfPCell(new Phrase("Detalle", boldFont));
                                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                table.addCell(cell);

                                cell = new PdfPCell(new Phrase("Monto (Q)", boldFont));
                                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                table.addCell(cell);

                                document.add(table);
                            }

                            // Añadir datos de depósitos
                            PdfPTable table = new PdfPTable(4);
                            table.setWidthPercentage(100);

                            table.addCell(String.valueOf(transaccion.getId()));
                            table.addCell(transaccion.getFechaFormateada());
                            table.addCell(transaccion.getDetalle());

                            PdfPCell cell = new PdfPCell(
                                    new Phrase(df.format(transaccion.getMontoCredito()), normalFont));
                            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cell);

                            document.add(table);
                        }
                    }

                    document.add(new Paragraph("\n"));
                }
            }

            if (!hayDepositos) {
                document.add(new Paragraph("El cliente no tiene depósitos registrados en ninguna de sus cuentas.",
                        normalFont));
            }

            // Cerrar documento
            document.close();

            // Al finalizar exitosamente, registrar en bitácora
            System.out.println(new Bitacora(
                    "AdministradorIPC1B",
                    "Generación de reportes",
                    "Éxito",
                    "Reporte de depósitos generado para el cliente " + cliente.getNombre() +
                            " " + cliente.getApellido() + " (CUI: " + cui + ")"));

            return filename;
        } catch (Exception e) {
            // Registrar error en bitácora
            System.out.println(new Bitacora(
                    "AdministradorIPC1B",
                    "Generación de reportes",
                    "Error",
                    "Error al generar reporte de depósitos para CUI " + cui + ": " + e.getMessage()));
            throw e;
        }
    }

    public String generarReporteRetiros(String cui) throws Exception {
        try {
            // Obtener el cliente por CUI
            Cliente cliente = clienteController.getClientePorCui(cui);

            if (cliente == null) {
                // Registrar error en bitácora
                System.out.println(new Bitacora(
                        "AdministradorIPC1B",
                        "Generación de reportes",
                        "Error",
                        "Cliente no encontrado con CUI: " + cui));
                throw new Exception("Cliente no encontrado con CUI: " + cui);
            }

            // Obtener las cuentas del cliente
            List<Cuenta> cuentasCliente = cuentaController.getCuentasPorCliente(cliente);

            if (cuentasCliente.isEmpty()) {
                throw new Exception("El cliente no tiene cuentas registradas");
            }

            // Crear la estructura de carpetas
            String baseDir = "Reportes/Retiros";
            File dir = new File(baseDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Generar nombre de archivo con timestamp actual
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
            String timestamp = sdf.format(new Date());
            String filename = baseDir + "/reporteRetiros_" + timestamp + ".pdf";

            // Crear documento PDF
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            // Añadir título
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Reporte de Retiros - USAC BANK", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // Añadir información del cliente
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

            document.add(new Paragraph("Información del Cliente", boldFont));
            document.add(new Paragraph("CUI: " + cliente.getCui(), normalFont));
            document.add(new Paragraph("Nombre: " + cliente.getNombre() + " " + cliente.getApellido(), normalFont));
            document.add(new Paragraph(
                    "Fecha de generación: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()),
                    normalFont));
            document.add(new Paragraph("\n"));

            // Para cada cuenta, listar sus retiros
            DecimalFormat df = new DecimalFormat("#,##0.00");
            boolean hayRetiros = false;

            for (Cuenta cuenta : cuentasCliente) {
                List<Transaccion> transacciones = transaccionController.getTransaccionesPorCuenta(cuenta);

                if (!transacciones.isEmpty()) {
                    boolean cuentaTieneRetiros = false;

                    for (Transaccion transaccion : transacciones) {
                        if (transaccion.getMontoDebito() > 0) {
                            if (!cuentaTieneRetiros) {
                                hayRetiros = true;
                                cuentaTieneRetiros = true;

                                document.add(new Paragraph("Cuenta: " + cuenta.getId(), boldFont));
                                document.add(
                                        new Paragraph("Saldo actual: Q" + df.format(cuenta.getSaldo()), normalFont));
                                document.add(new Paragraph("\n"));

                                // Crear tabla de retiros
                                PdfPTable table = new PdfPTable(4);
                                table.setWidthPercentage(100);

                                // Configurar encabezados
                                PdfPCell cell = new PdfPCell(new Phrase("ID", boldFont));
                                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                table.addCell(cell);

                                cell = new PdfPCell(new Phrase("Fecha y Hora", boldFont));
                                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                table.addCell(cell);

                                cell = new PdfPCell(new Phrase("Detalle", boldFont));
                                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                table.addCell(cell);

                                cell = new PdfPCell(new Phrase("Monto (Q)", boldFont));
                                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                table.addCell(cell);

                                document.add(table);
                            }

                            // Añadir datos de retiros
                            PdfPTable table = new PdfPTable(4);
                            table.setWidthPercentage(100);

                            table.addCell(String.valueOf(transaccion.getId()));
                            table.addCell(transaccion.getFechaFormateada());
                            table.addCell(transaccion.getDetalle());

                            PdfPCell cell = new PdfPCell(
                                    new Phrase(df.format(transaccion.getMontoDebito()), normalFont));
                            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                            table.addCell(cell);

                            document.add(table);
                        }
                    }

                    document.add(new Paragraph("\n"));
                }
            }

            if (!hayRetiros) {
                document.add(new Paragraph("El cliente no tiene retiros registrados en ninguna de sus cuentas.",
                        normalFont));
            }

            // Cerrar documento
            document.close();

            // Al finalizar exitosamente, registrar en bitácora
            System.out.println(new Bitacora(
                    "AdministradorIPC1B",
                    "Generación de reportes",
                    "Éxito",
                    "Reporte de retiros generado para el cliente " + cliente.getNombre() +
                            " " + cliente.getApellido() + " (CUI: " + cui + ")"));

            return filename;
        } catch (Exception e) {
            // Registrar error en bitácora
            System.out.println(new Bitacora(
                    "AdministradorIPC1B",
                    "Generación de reportes",
                    "Error",
                    "Error al generar reporte de retiros para CUI " + cui + ": " + e.getMessage()));
            throw e;
        }
    }
}
