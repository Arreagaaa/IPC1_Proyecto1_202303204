package com.usacbank.model;

import java.util.Calendar;
import java.util.Random;

public class Cuenta {
    private static int contadorCuentas = 1; // Comienza en 1 para generar 20BB251, 20BB252...
    private String id;
    private Cliente cliente;
    private double saldo;

    public Cuenta(Cliente cliente) {
        this.id = generarId();
        this.cliente = cliente;
        this.saldo = 0.0;
    }

    private String generarId() {
        // Formato: 20BB25X donde X es un contador secuencial
        return "20BB25" + contadorCuentas++;
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