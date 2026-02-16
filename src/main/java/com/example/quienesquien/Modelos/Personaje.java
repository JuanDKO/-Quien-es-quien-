package com.example.quienesquien.Modelos;

public class Personaje {
    private String nombre;
    private String imagen; // Para vincularlo con la imagen en el juego
    private boolean esHumano;
    private boolean puedeHablar;
    private boolean estaVivo;
    private boolean esPeleador;
    private boolean tienePoderes;
    private boolean esSerieAntes2010;
    private boolean esProtagonista;
    private boolean esMenorDeEdad;
    private boolean esAnimal;
    private boolean tienePelo;
    private boolean llevaRopa;
    private boolean puedeVolar;
    private boolean esDeNickelodeon;

    public Personaje(String nombre, String imagen, boolean esHumano, boolean puedeHablar, boolean estaVivo,
            boolean esPeleador, boolean tienePoderes, boolean esSerieAntes2010,
            boolean esProtagonista, boolean esMenorDeEdad, boolean esAnimal, boolean tienePelo, boolean llevaRopa,
            boolean puedeVolar, boolean esDeNickelodeon) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.esHumano = esHumano;
        this.puedeHablar = puedeHablar;
        this.estaVivo = estaVivo;
        this.esPeleador = esPeleador;
        this.tienePoderes = tienePoderes;
        this.esSerieAntes2010 = esSerieAntes2010;
        this.esProtagonista = esProtagonista;
        this.esMenorDeEdad = esMenorDeEdad;
        this.esAnimal = esAnimal;
        this.tienePelo = tienePelo;
        this.llevaRopa = llevaRopa;
        this.puedeVolar = puedeVolar;
        this.esDeNickelodeon = esDeNickelodeon;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public boolean isEsHumano() {
        return esHumano;
    }

    public boolean isPuedeHablar() {
        return puedeHablar;
    }

    public boolean isEstaVivo() {
        return estaVivo;
    }

    public boolean isEsPeleador() {
        return esPeleador;
    }

    public boolean isTienePoderes() {
        return tienePoderes;
    }

    public boolean isEsSerieAntes2010() {
        return esSerieAntes2010;
    }

    public boolean isEsProtagonista() {
        return esProtagonista;
    }

    public boolean isEsMenorDeEdad() {
        return esMenorDeEdad;
    }

    public boolean isEsAnimal() {
        return esAnimal;
    }

    public boolean isTienePelo() {
        return tienePelo;
    }

    public boolean isLlevaRopa() {
        return llevaRopa;
    }

    public boolean isPuedeVolar() {
        return puedeVolar;
    }

    public boolean isEsDeNickelodeon() {
        return esDeNickelodeon;
    }

    @Override
    public String toString() {
        return "Personaje{" +
                "nombre='" + nombre + '\'' +
                ", imagen='" + imagen + '\'' +
                ", esHumano=" + esHumano +
                ", puedeHablar=" + puedeHablar +
                ", estaVivo=" + estaVivo +
                ", esPeleador=" + esPeleador +
                ", tienePoderes=" + tienePoderes +
                ", esSerieAntes2010=" + esSerieAntes2010 +
                ", esProtagonista=" + esProtagonista +
                ", esMenorDeEdad=" + esMenorDeEdad +
                '}';
    }
}
