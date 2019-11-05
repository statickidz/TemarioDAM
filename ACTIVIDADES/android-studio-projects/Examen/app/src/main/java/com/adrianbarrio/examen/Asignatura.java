package com.adrianbarrio.examen;

import java.io.Serializable;

/**
 * Created by DAM2 on 10/12/2015.
 */
public class Asignatura implements Serializable {

    private String nombre;
    private int nota;

    public Asignatura(String nombre, int nota) {
        this.setNombre(nombre);
        this.setNota(nota);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}
