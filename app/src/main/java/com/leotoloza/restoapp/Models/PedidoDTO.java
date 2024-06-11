package com.leotoloza.restoapp.Models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class PedidoDTO implements Serializable {

    private int id;
    private String Detalle;
    private List<ProductoDTO> Productos;

    public PedidoDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetalle() {
        return Detalle;
    }

    public void setDetalle(String detalle) {
        Detalle = detalle;
    }

    public List<ProductoDTO> getProductos() {
        return Productos;
    }

    public void setProductos(List<ProductoDTO> productos) {
        Productos = productos;
    }
}
