package com.leotoloza.restoapp.Models;

import java.io.Serializable;

public class ProductoSeleccionado implements Serializable {
    private Producto producto;
    private int cantidad;
    private boolean seleccionado;

    public ProductoSeleccionado() {
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
}
