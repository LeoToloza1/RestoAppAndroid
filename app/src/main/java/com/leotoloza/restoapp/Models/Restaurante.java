package com.leotoloza.restoapp.Models;

import java.io.Serializable;

public class Restaurante implements Serializable {
    private int id;
    private String nombre_restaurante;
    private String direccion_restaurante;
    private String email_restaurante;
    private String telefono_restaurante;
    private int id_rubro;
    private String logo_url;
    private Rubro rubro;

    public Restaurante() {}

    public Restaurante(int id, String nombre_restaurante, String direccion_restaurante, String email_restaurante, String telefono_restaurante, int id_rubro, String logo_url, Rubro rubro) {
        this.id = id;
        this.nombre_restaurante = nombre_restaurante;
        this.direccion_restaurante = direccion_restaurante;
        this.email_restaurante = email_restaurante;
        this.telefono_restaurante = telefono_restaurante;
        this.id_rubro = id_rubro;
        this.logo_url = logo_url;
        this.rubro = rubro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_restaurante() {
        return nombre_restaurante;
    }

    public void setNombre_restaurante(String nombre_restaurante) {
        this.nombre_restaurante = nombre_restaurante;
    }

    public String getDireccion_restaurante() {
        return direccion_restaurante;
    }

    public void setDireccion_restaurante(String direccion_restaurante) {
        this.direccion_restaurante = direccion_restaurante;
    }

    public String getEmail_restaurante() {
        return email_restaurante;
    }

    public void setEmail_restaurante(String email_restaurante) {
        this.email_restaurante = email_restaurante;
    }

    public String getTelefono_restaurante() {
        return telefono_restaurante;
    }

    public void setTelefono_restaurante(String telefono_restaurante) {
        this.telefono_restaurante = telefono_restaurante;
    }

    public int getId_rubro() {
        return id_rubro;
    }

    public void setId_rubro(int id_rubro) {
        this.id_rubro = id_rubro;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }
}
