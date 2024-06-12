package com.leotoloza.restoapp.Models;

import java.io.Serializable;

public class Repartidor implements Serializable {
    private int id;
    private String nombre_repartidor;
    private String apellido_repartidor;
    private String email_repartidor;
    private String direccion_repartidor;
    private String telefono_repartidor;

    public Repartidor() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_repartidor() {
        return nombre_repartidor;
    }

    public void setNombre_repartidor(String nombre_repartidor) {
        this.nombre_repartidor = nombre_repartidor;
    }

    public String getApellido_repartidor() {
        return apellido_repartidor;
    }

    public void setApellido_repartidor(String apellido_repartidor) {
        this.apellido_repartidor = apellido_repartidor;
    }

    public String getEmail_repartidor() {
        return email_repartidor;
    }

    public void setEmail_repartidor(String email_repartidor) {
        this.email_repartidor = email_repartidor;
    }

    public String getDireccion_repartidor() {
        return direccion_repartidor;
    }

    public void setDireccion_repartidor(String direccion_repartidor) {
        this.direccion_repartidor = direccion_repartidor;
    }

    public String getTelefono_repartidor() {
        return telefono_repartidor;
    }

    public void setTelefono_repartidor(String telefono_repartidor) {
        this.telefono_repartidor = telefono_repartidor;
    }

    @Override
    public String toString() {
        return "Repartidor{" +
                "id=" + id +
                ", nombre_repartidor='" + nombre_repartidor + '\'' +
                ", apellido_repartidor='" + apellido_repartidor + '\'' +
                ", email_repartidor='" + email_repartidor + '\'' +
                ", direccion_repartidor='" + direccion_repartidor + '\'' +
                ", telefono_repartidor='" + telefono_repartidor + '\'' +
                '}';
    }
}
