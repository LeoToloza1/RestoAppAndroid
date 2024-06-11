package com.leotoloza.restoapp.Models;

import java.io.Serializable;
import java.time.LocalDate;

public class Envio implements Serializable {
    private int id;
    private LocalDate fecha;
    private double costo;
    private int id_repartidor;
    private Repartidor repartidor;
    private Pedido pedido;
    private int id_pedido;
    private boolean estado_envio;

    public Envio() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public int getId_repartidor() {
        return id_repartidor;
    }

    public void setId_repartidor(int id_repartidor) {
        this.id_repartidor = id_repartidor;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public boolean isEstado_envio() {
        return estado_envio;
    }

    public void setEstado_envio(boolean estado_envio) {
        this.estado_envio = estado_envio;
    }
}
