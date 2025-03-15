package com.usacbank.model;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Transaccion {
    private static int contadorTransacciones = 1;
    private int id;
    private Cuenta cuenta;
    private Date fecha;
    private String detalle;
    // Para retiros
    private double montoDebito;
    // Para depósitos
    private double montoCredito;
    private double saldoResultante;

    public Transaccion(Cuenta cuenta, double monto, String tipo) {
        this.id = contadorTransacciones++;
        this.cuenta = cuenta;
        this.fecha = new Date();

        if (tipo.equals("DEPOSITO")) {
            this.detalle = "Depósito";
            this.montoCredito = monto;
            this.montoDebito = 0;
        } else if (tipo.equals("RETIRO")) {
            this.detalle = "Retiro";
            this.montoDebito = monto;
            this.montoCredito = 0;
        }

        this.saldoResultante = cuenta.getSaldo();
    }

    public int getId() {
        return id;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getDetalle() {
        return detalle;
    }

    public double getMontoDebito() {
        return montoDebito;
    }

    public double getMontoCredito() {
        return montoCredito;
    }

    public double getSaldoResultante() {
        return saldoResultante;
    }

    public String getFechaFormateada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(fecha);
    }
}