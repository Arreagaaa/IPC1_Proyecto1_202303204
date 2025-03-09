package com.usacbank.model;

import java.time.LocalDateTime;

public class Transaccion {
    private static int contadorTransacciones = 1;
    private String id;
    private String tipo;
    private double monto;
    private LocalDateTime fecha;

    public Transaccion(String tipo, double monto) {
        this.id = "TRX" + (contadorTransacciones++);
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}