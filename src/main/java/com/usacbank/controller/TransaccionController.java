package com.usacbank.controller;

import com.usacbank.model.Cuenta;
import com.usacbank.model.Transaccion;
import com.usacbank.model.Bitacora;

import java.util.ArrayList;
import java.util.List;

public class TransaccionController {
    public static final int MAX_TRANSACCIONES_POR_CUENTA = 25;
    private List<Transaccion> transacciones;

    public TransaccionController() {
        this.transacciones = new ArrayList<>();
    }

    public boolean registrarDeposito(Cuenta cuenta, double monto) {
        // Validar monto positivo
        if (monto <= 0) {
            // Registrar error en bitácora - Monto inválido
            System.out.println(new Bitacora(
                    "AdministradorIPC1B",
                    "Depósito",
                    "Error",
                    "Monto inválido: Q" + monto + ". El monto debe ser mayor que cero."));
            return false;
        }

        // Verificar límite de transacciones
        if (limiteTransaccionesAlcanzado(cuenta)) {
            // Registrar error en bitácora - Límite de transacciones
            System.out.println(new Bitacora(
                    "AdministradorIPC1B",
                    "Depósito",
                    "Error",
                    "Límite de transacciones alcanzado para la cuenta " + cuenta.getId() +
                            ". Máximo permitido: " + MAX_TRANSACCIONES_POR_CUENTA));
            return false;
        }

        // Registrar el depósito
        double saldoAnterior = cuenta.getSaldo();
        cuenta.depositar(monto);

        // Usar el constructor correcto de Transaccion
        Transaccion nuevaTransaccion = new Transaccion(cuenta, monto, "DEPOSITO");
        transacciones.add(nuevaTransaccion);

        // Registrar éxito en bitácora
        System.out.println(new Bitacora(
                "AdministradorIPC1B",
                "Depósito",
                "Éxito",
                "Depósito de Q" + monto + " realizado en la cuenta " + cuenta.getId() +
                        ". Saldo anterior: Q" + saldoAnterior + ", Saldo actual: Q" + cuenta.getSaldo()));

        return true;
    }

    public boolean registrarRetiro(Cuenta cuenta, double monto) {
        // Validar monto positivo
        if (monto <= 0) {
            // Registrar error en bitácora - Monto inválido
            System.out.println(new Bitacora(
                    "AdministradorIPC1B",
                    "Retiro",
                    "Error",
                    "Monto inválido: Q" + monto + ". El monto debe ser mayor que cero."));
            return false;
        }

        // Validar saldo cero
        if (cuenta.getSaldo() == 0) {
            // Registrar error en bitácora - Saldo cero
            System.out.println(new Bitacora(
                    "AdministradorIPC1B",
                    "Retiro",
                    "Error",
                    "La cuenta " + cuenta.getId() + " tiene saldo cero. No se puede realizar el retiro."));
            return false;
        }

        // Validar saldo suficiente
        if (cuenta.getSaldo() < monto) {
            // Registrar error en bitácora - Saldo insuficiente
            System.out.println(new Bitacora(
                    "AdministradorIPC1B",
                    "Retiro",
                    "Error",
                    "Saldo insuficiente. Monto solicitado: Q" + monto + ", saldo disponible: Q" + cuenta.getSaldo()));
            return false;
        }

        // Verificar límite de transacciones
        if (limiteTransaccionesAlcanzado(cuenta)) {
            // Registrar error en bitácora - Límite de transacciones
            System.out.println(new Bitacora(
                    "AdministradorIPC1B",
                    "Retiro",
                    "Error",
                    "Límite de transacciones alcanzado para la cuenta " + cuenta.getId() +
                            ". Máximo permitido: " + MAX_TRANSACCIONES_POR_CUENTA));
            return false;
        }

        // Registrar el retiro
        double saldoAnterior = cuenta.getSaldo();
        boolean retiroExitoso = cuenta.retirar(monto);

        if (retiroExitoso) {
            // Usar el constructor correcto de Transaccion
            Transaccion nuevaTransaccion = new Transaccion(cuenta, monto, "RETIRO");
            transacciones.add(nuevaTransaccion);

            // Registrar éxito en bitácora
            System.out.println(new Bitacora(
                    "AdministradorIPC1B",
                    "Retiro",
                    "Éxito",
                    "Retiro de Q" + monto + " realizado en la cuenta " + cuenta.getId() +
                            ". Saldo anterior: Q" + saldoAnterior + ", Saldo actual: Q" + cuenta.getSaldo()));

            return true;
        } else {
            // Error no especificado en el retiro
            System.out.println(new Bitacora(
                    "AdministradorIPC1B",
                    "Retiro",
                    "Error",
                    "Error desconocido al intentar retirar Q" + monto + " de la cuenta " + cuenta.getId()));

            return false;
        }
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

    public List<Transaccion> getDepositosPorCuenta(Cuenta cuenta) {
        List<Transaccion> depositos = new ArrayList<>();
        for (Transaccion transaccion : getTransaccionesPorCuenta(cuenta)) {
            // Verificar si es un depósito basándose en el monto de crédito
            if (transaccion.getMontoCredito() > 0) {
                depositos.add(transaccion);
            }
        }
        return depositos;
    }

    public List<Transaccion> getRetirosPorCuenta(Cuenta cuenta) {
        List<Transaccion> retiros = new ArrayList<>();
        for (Transaccion transaccion : getTransaccionesPorCuenta(cuenta)) {
            // Verificar si es un retiro basándose en el monto de débito
            if (transaccion.getMontoDebito() > 0) {
                retiros.add(transaccion);
            }
        }
        return retiros;
    }
}