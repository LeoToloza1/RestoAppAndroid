package com.leotoloza.restoapp.Models;

import java.io.Serializable;
import java.util.List;

public class PedidoDTO implements Serializable {

    private int id;
    private String detalle;
    private ClienteDTO clienteDTO;
    private double total;
    private String fechaPedido;
    private List<ProductoDTO> productos;

    public PedidoDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public ClienteDTO getClienteDTO() {
        return clienteDTO;
    }

    public void setClienteDTO(ClienteDTO clienteDTO) {
        this.clienteDTO = clienteDTO;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public List<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoDTO> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
                "id=" + id +
                ", Detalle='" + detalle + '\'' +
                ", cliente=" + clienteDTO +
                ", total=" + total +
                ", fecha_pedido='" + fechaPedido + '\'' +
                ", productos=" + productos +
                '}';
    }
}
