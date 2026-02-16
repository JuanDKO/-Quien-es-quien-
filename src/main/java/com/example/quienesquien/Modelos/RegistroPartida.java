package com.example.quienesquien.Modelos;

public class RegistroPartida {
    private String nombre;
    private int puntuacion;

    public RegistroPartida(String nombre, int puntuacion) {
        this.nombre = nombre;
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntuacion() {
        return puntuacion;
    }
}
