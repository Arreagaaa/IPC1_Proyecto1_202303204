package com.usacbank.controller;

import com.usacbank.model.Cliente;
import com.usacbank.model.Cuenta;

import java.util.ArrayList;
import java.util.List;

public class CuentaController {
    private static final int MAX_CUENTAS_POR_CLIENTE = 4;
    private List<Cuenta> cuentas;

    public CuentaController() {
        this.cuentas = new ArrayList<>();
    }

    public int crearCuenta(Cliente cliente) {
        // Verificar si el cliente ya tiene el máximo de cuentas permitidas
        int cuentasDelCliente = 0;
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getCliente().equals(cliente)) {
                cuentasDelCliente++;
            }
        }

        if (cuentasDelCliente >= MAX_CUENTAS_POR_CLIENTE) {
            return 1; // Límite de cuentas por cliente alcanzado
        }

        Cuenta nuevaCuenta = new Cuenta(cliente);
        cliente.agregarCuenta(nuevaCuenta);
        cuentas.add(nuevaCuenta);

        System.out.println("Cuenta creada: " + nuevaCuenta.getId());
        System.out.println("Cuentas actuales:");
        for (Cuenta cuenta : cuentas) {
            System.out.println("ID: " + cuenta.getId() + ", Cliente: " + cuenta.getCliente().getNombre() + ", Saldo: "
                    + cuenta.getSaldo());
        }

        return 0; // Éxito
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public List<Cuenta> getCuentasPorCliente(Cliente cliente) {
        List<Cuenta> cuentasCliente = new ArrayList<>();
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getCliente().equals(cliente)) {
                cuentasCliente.add(cuenta);
            }
        }
        return cuentasCliente;
    }

    // Nuevo método para buscar una cuenta por su ID
    public Cuenta getCuentaPorId(String id) {
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getId().equals(id)) {
                return cuenta;
            }
        }
        return null; // Retorna null si no se encuentra la cuenta
    }

    public boolean existenCuentas() {
        return !cuentas.isEmpty();
    }
}
