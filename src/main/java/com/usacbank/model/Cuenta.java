package com.usacbank.model;

public class Cuenta {
    private static int contadorCuentas = 1;
    private String id;
    private Cliente cliente;
    private double saldo;

    public Cuenta(Cliente cliente) {
        this.id = generarId();
        this.cliente = cliente;
        this.saldo = 0.0;
    }

    private String generarId() {
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