package com.usacbank.controller;

import com.usacbank.model.Cliente;
import com.usacbank.model.Cuenta;

import java.util.ArrayList;
import java.util.List;

public class CuentaController {
    private List<Cuenta> cuentas;

    public CuentaController() {
        this.cuentas = new ArrayList<>();
    }

    public boolean crearCuenta(Cliente cliente) {
        Cuenta nuevaCuenta = new Cuenta(cliente);
        cliente.agregarCuenta(nuevaCuenta);
        boolean added = cuentas.add(nuevaCuenta);
        if (added) {
            System.out.println("Cuenta creada: " + nuevaCuenta.getId());
            System.out.println("Cuentas actuales:");
            for (Cuenta cuenta : cuentas) {
                System.out.println("ID: " + cuenta.getId() + ", Cliente: " + cuenta.getCliente().getNombre() + ", Saldo: " + cuenta.getSaldo());
            }
        }
        return added;
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
}
