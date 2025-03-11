package com.usacbank.controller;

import com.usacbank.model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ClienteController {
    private List<Cliente> clientes;
    private static final int LIMITE_CLIENTES = 5; // Máximo de clientes permitidos

    public ClienteController() {
        this.clientes = new ArrayList<>();
    }

    /**
     * Crea un nuevo cliente si el CUI no está duplicado y no se ha alcanzado el
     * límite
     * 
     * @param cliente El cliente a crear
     * @return 0 si se creó exitosamente, 1 si el CUI está duplicado, 2 si se
     *         alcanzó el límite
     */
    public int crearCliente(Cliente cliente) {
        // Verificar si ya existe un cliente con el mismo CUI
        if (existeCUI(cliente.getCui())) {
            return 1; // CUI duplicado
        }

        // Verificar si se ha alcanzado el límite de clientes
        if (clientes.size() >= LIMITE_CLIENTES) {
            return 2; // Límite alcanzado
        }

        // Si no hay problemas, agregar el cliente
        clientes.add(cliente);
        return 0; // Éxito
    }

    /**
     * Crea un nuevo cliente con los datos proporcionados
     * 
     * @return 0 si se creó exitosamente, 1 si el CUI está duplicado, 2 si se
     *         alcanzó el límite
     */
    public int crearCliente(String cui, String nombre, String apellido) {
        Cliente nuevoCliente = new Cliente(cui, nombre, apellido);
        return crearCliente(nuevoCliente);
    }

    /**
     * Verifica si ya existe un cliente con el CUI especificado
     */
    public boolean existeCUI(String cui) {
        for (Cliente cliente : clientes) {
            if (cliente.getCui().equals(cui)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si se ha alcanzado el límite de clientes
     */
    public boolean limiteAlcanzado() {
        return clientes.size() >= LIMITE_CLIENTES;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public Cliente getClientePorCui(String cui) {
        for (Cliente cliente : getClientes()) {
            if (cliente.getCui().equals(cui)) {
                return cliente;
            }
        }
        return null;
    }

    public boolean existenClientes() {
        return !clientes.isEmpty();
    }
}