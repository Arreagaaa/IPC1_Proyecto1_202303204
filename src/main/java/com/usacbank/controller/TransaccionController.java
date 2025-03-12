package com.usacbank.controller;

import com.usacbank.model.Cuenta;
import com.usacbank.model.Transaccion;

import java.util.ArrayList;
import java.util.List;

public class TransaccionController {
    public static final int MAX_TRANSACCIONES_POR_CUENTA = 25;
    private List<Transaccion> transacciones;

    public TransaccionController() {
        this.transacciones = new ArrayList<>();
    }

    public boolean registrarDeposito(Cuenta cuenta, double monto) {
        if (monto <= 0) {
            return false;
        }

        // Verificar límite de transacciones
        List<Transaccion> transaccionesCuenta = getTransaccionesPorCuenta(cuenta);
        if (transaccionesCuenta.size() >= MAX_TRANSACCIONES_POR_CUENTA) {
            return false; // Límite de transacciones alcanzado
        }

        cuenta.depositar(monto);
        // Ahora creamos la transacción después de modificar el saldo
        Transaccion transaccion = new Transaccion(cuenta, monto, "DEPOSITO");
        transacciones.add(transaccion);
        return true;
    }

    public boolean registrarRetiro(Cuenta cuenta, double monto) {
        // Validar monto positivo
        if (monto <= 0) {
            return false;
        }

        // Validar saldo suficiente y no cero
        if (cuenta.getSaldo() < monto || cuenta.getSaldo() == 0) {
            return false;
        }

        // Verificar límite de transacciones
        List<Transaccion> transaccionesCuenta = getTransaccionesPorCuenta(cuenta);
        if (transaccionesCuenta.size() >= MAX_TRANSACCIONES_POR_CUENTA) {
            return false; // Límite de transacciones alcanzado
        }

        // Realizar el retiro
        boolean retiroExitoso = cuenta.retirar(monto);

        if (retiroExitoso) {
            // Registrar la transacción después de modificar el saldo
            Transaccion transaccion = new Transaccion(cuenta, monto, "RETIRO");
            transacciones.add(transaccion);
            return true;
        }

        return false;
    }

    private int contarTransaccionesPorCuenta(Cuenta cuenta) {
        int contador = 0;
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getCuenta().getId().equals(cuenta.getId())) {
                contador++;
            }
        }
        return contador;
    }

    public boolean limiteTransaccionesAlcanzado(Cuenta cuenta) {
        return contarTransaccionesPorCuenta(cuenta) >= MAX_TRANSACCIONES_POR_CUENTA;
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

        // Ordenar transacciones por fecha, de la más antigua a la más reciente
        transaccionesCuenta.sort((t1, t2) -> t1.getFecha().compareTo(t2.getFecha()));

        return transaccionesCuenta;
    }
}