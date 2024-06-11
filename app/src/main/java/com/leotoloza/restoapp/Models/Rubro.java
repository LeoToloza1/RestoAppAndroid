package com.leotoloza.restoapp.Models;

import java.io.Serializable;

public class Rubro implements Serializable {
private int id;
private String nombre_rubro;

    public Rubro(int id, String nombre_rubro) {
        this.id = id;
        this.nombre_rubro = nombre_rubro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_rubro() {
        return nombre_rubro;
    }

    public void setNombre_rubro(String nombre_rubro) {
        this.nombre_rubro = nombre_rubro;
    }

    @Override
    public String toString() {
        return "Rubro{" +
                "id=" + id +
                ", nombre_rubro='" + nombre_rubro + '\'' +
                '}';
    }
}
