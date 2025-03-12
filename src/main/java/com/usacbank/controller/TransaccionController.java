package com.usacbank.controller;

import com.usacbank.model.Cuenta;
import com.usacbank.model.Transaccion;

import java.util.ArrayList;
import java.util.List;

public class TransaccionController {
    private List<Transaccion> transacciones;

    public TransaccionController() {
        this.transacciones = new ArrayList<>();
    }

    public boolean registrarDeposito(Cuenta cuenta, double monto) {
        if (monto <= 0) {
            return false;
        }

        cuenta.depositar(monto);
        Transaccion transaccion = new Transaccion(cuenta, monto, "DEPOSITO");
        transacciones.add(transaccion);
        return true;
    }

    public boolean registrarRetiro(Cuenta cuenta, double monto) {
        // Validar que el monto sea positivo
        if (monto <= 0) {
            return false;
        }

        // Validar que haya saldo suficiente
        if (cuenta.getSaldo() < monto) {
            return false;
        }

        // Validar que el saldo no sea cero
        if (cuenta.getSaldo() == 0) {
            return false;
        }

        // Realizar el retiro
        boolean retiroExitoso = cuenta.retirar(monto);

        if (retiroExitoso) {
            // Registrar la transacción
            Transaccion transaccion = new Transaccion(cuenta, monto, "RETIRO");
            transacciones.add(transaccion);
            return true;
        }

        return false;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public List<Transaccion> getTransaccionesPorCuenta(Cuenta cuenta) {
        List<Transaccion> transaccionesCuenta = new ArrayList<>();
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getCuenta().equals(cuenta)) {
                transaccionesCuenta.add(transaccion);
            }
        }
        return transaccionesCuenta;
    }
}