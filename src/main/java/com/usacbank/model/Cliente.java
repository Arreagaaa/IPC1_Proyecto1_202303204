package com.usacbank.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String cui;
    private String nombre;
    private String apellido;
    private List<Cuenta> cuentas;

    public Cliente(String cui, String nombre, String apellido) {
        this.cui = cui;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cuentas = new ArrayList<>();
    }

    public String getCui() {
        return cui;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void agregarCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
    }

    @Override
    public String toString() {
        return cui + " - " + nombre + " " + apellido;
    }
}
