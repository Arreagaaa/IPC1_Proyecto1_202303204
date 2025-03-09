package com.usacbank.model;

import java.time.LocalDateTime;

public class Bitacora {
    private LocalDateTime fecha;
    private String usuario;
    private String accion;
    private String resultado;
    private String detalles;

    public Bitacora(String usuario, String accion, String resultado, String detalles) {
        this.fecha = LocalDateTime.now();
        this.usuario = usuario;
        this.accion = accion;
        this.resultado = resultado;
        this.detalles = detalles;
    }

    public String toString() {
        return String.format("[%s] Usuario: %s - Acción: %s - Resultado: %s - Detalles: %s", fecha, usuario, accion,
                resultado, detalles);
    }
}
