package com.usacbank.model;

import java.util.Calendar;
import java.util.Random;

public class Cuenta {
    private static int contadorCuentas = 0; // Comienza en 0 para generar 00, 01, 02...
    private String id;
    private Cliente cliente;
    private double saldo;

    public Cuenta(Cliente cliente) {
        this.id = generarId();
        this.cliente = cliente;
        this.saldo = 0.0;
    }

    private String generarId() {
        // Formato: 20BBXX donde XX es un contador secuencial desde 00
        return String.format("20BB%02d", contadorCuentas++);
    }

    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double monto) {
        saldo += monto;
    }

    public boolean retirar(double monto) {
        if (monto > saldo)
            return false;
        saldo -= monto;
        return true;
    }
}