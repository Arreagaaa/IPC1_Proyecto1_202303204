package com.usacbank.model;

import java.util.Date;

public class Transaccion {
    private static int contadorTransacciones = 1;
    private int id;
    private Cuenta cuenta;
    private double monto;
    private String tipo; // "DEPOSITO" o "RETIRO"
    private Date fecha;

    public Transaccion(Cuenta cuenta, double monto, String tipo) {
        this.id = contadorTransacciones++;
        this.cuenta = cuenta;
        this.monto = monto;
        this.tipo = tipo;
        this.fecha = new Date();
    }

    public int getId() {
        return id;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public double getMonto() {
        return monto;
    }

    public String getTipo() {
        return tipo;
    }

    public Date getFecha() {
        return fecha;
    }
}