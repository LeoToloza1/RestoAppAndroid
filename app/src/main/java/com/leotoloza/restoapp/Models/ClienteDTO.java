package com.leotoloza.restoapp.Models;

import java.io.Serializable;

public class ClienteDTO implements Serializable {
    private int id;
    private String nombre_cliente;
    private String apellido_cliente;
    private String direccion_cliente;
    private String telefono_cliente;

    public ClienteDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        nombre_cliente = nombre_cliente;
    }

    public String getApellido_cliente() {
        return apellido_cliente;
    }

    public void setApellido_cliente(String apellido_cliente) {
        apellido_cliente = apellido_cliente;
    }

    public String getDireccion_cliente() {
        return direccion_cliente;
    }

    public void setDireccion_cliente(String direccion_cliente) {
        direccion_cliente = direccion_cliente;
    }

    public String getTelefono_cliente() {
        return telefono_cliente;
    }

    public void setTelefono_cliente(String telefono_cliente) {
        telefono_cliente = telefono_cliente;
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
                "Id=" + id +
                ", Nombre_cliente='" + nombre_cliente + '\'' +
                ", Apellido_cliente='" + apellido_cliente + '\'' +
                ", Direccion_cliente='" + direccion_cliente + '\'' +
                ", Telefono_cliente='" + telefono_cliente + '\'' +
                '}';
    }
}
