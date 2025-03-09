package com.usacbank.model;

public class Cuenta {
    private static int contadorCuentas = 1;
    private String id;
    private Cliente cliente;
    private double saldo;

    public Cuenta(Cliente cliente) {
        this.id = "ACC" + (contadorCuentas++);
        this.cliente = cliente;
        this.saldo = 0.0;
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