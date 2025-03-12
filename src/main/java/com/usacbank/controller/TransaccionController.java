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

        // Realizar el depósito en la cuenta
        cuenta.depositar(monto);

        // Registrar la transacción
        Transaccion transaccion = new Transaccion(cuenta, monto, "DEPOSITO");
        transacciones.add(transaccion);

        return true;
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