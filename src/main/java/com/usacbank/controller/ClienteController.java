package com.usacbank.controller;

import com.usacbank.model.Cliente;
import com.usacbank.model.Bitacora;
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
            // Registrar en bitácora - Error por CUI duplicado
            System.out.println(new Bitacora(
                    "AdministradorIPC1B",
                    "Registro de cliente",
                    "Error",
                    "CUI duplicado: " + cliente.getCui() + ". No se pudo registrar al cliente."));
            return 1; // CUI duplicado
        }

        // Verificar si se ha alcanzado el límite de clientes
        if (clientes.size() >= LIMITE_CLIENTES) {
            // Registrar en bitácora - Error por límite alcanzado
            System.out.println(new Bitacora(
                    "AdministradorIPC1B",
                    "Registro de cliente",
                    "Error",
                    "Límite de clientes alcanzado (" + LIMITE_CLIENTES + "). No se pudo registrar al cliente."));
            return 2; // Límite alcanzado
        }

        // Si no hay problemas, agregar el cliente
        clientes.add(cliente);

        // Registrar en bitácora - Éxito
        System.out.println(new Bitacora(
                "AdministradorIPC1B",
                "Registro de cliente",
                "Éxito",
                "Cliente " + cliente.getNombre() + " " + cliente.getApellido() + " con CUI " + cliente.getCui()
                        + " registrado."));
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
                // Registrar en bitácora - Cliente encontrado
                System.out.println(new Bitacora(
                        "AdministradorIPC1B",
                        "Búsqueda de cliente",
                        "Éxito",
                        "Cliente encontrado con CUI: " + cui));
                return cliente;
            }
        }
        // Registrar en bitácora - Cliente no encontrado
        System.out.println(new Bitacora(
                "AdministradorIPC1B",
                "Búsqueda de cliente",
                "Error",
                "No se encontró cliente con CUI: " + cui));
        return null;
    }

    public boolean existenClientes() {
        return !clientes.isEmpty();
    }
}