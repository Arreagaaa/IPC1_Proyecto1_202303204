package com.usacbank.controller;

import com.usacbank.model.Cliente;
import com.usacbank.model.Cuenta;
import com.usacbank.model.Bitacora;

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
            // Registrar en bitácora - Error por límite excedido
            System.out.println(new Bitacora(
                    "AdministradorIPC1B",
                    "Creación de cuenta",
                    "Error",
                    "El cliente " + cliente.getNombre() + " " + cliente.getApellido() +
                            " ya tiene el máximo de " + MAX_CUENTAS_POR_CLIENTE + " cuentas permitidas."));
            return 1; // Límite de cuentas por cliente alcanzado
        }

        Cuenta nuevaCuenta = new Cuenta(cliente);
        cliente.agregarCuenta(nuevaCuenta);
        cuentas.add(nuevaCuenta);

        // Registrar en bitácora - Éxito en la creación
        System.out.println(new Bitacora(
                "AdministradorIPC1B",
                "Creación de cuenta",
                "Éxito",
                "Cuenta creada con número '" + nuevaCuenta.getId() + "', saldo inicial: Q0.0"));

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

        // Registrar en bitácora - Resultado de búsqueda
        if (!cuentasCliente.isEmpty()) {
            System.out.println(new Bitacora(
                    "AdministradorIPC1B",
                    "Búsqueda de cuentas",
                    "Éxito",
                    "Se encontraron " + cuentasCliente.size() + " cuentas asociadas al cliente '" +
                            cliente.getNombre() + " " + cliente.getApellido() + "'."));
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
