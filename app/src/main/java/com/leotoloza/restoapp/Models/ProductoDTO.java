package com.leotoloza.restoapp.Models;

import java.io.Serializable;

public class ProductoDTO implements Serializable {
    private int productoId;
    private String nombre_producto;
    private double precio;
    private String descripcion;
    private String imagenUrl;
    private int cantidad;
    private boolean seleccionado;

    public ProductoDTO() {
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
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

    @Override
    public String toString() {
        return "ProductoDTO{" +
                "productoId=" + productoId +
                ", nombre_producto='" + nombre_producto + '\'' +
                ", precio=" + precio +
                ", descripcion='" + descripcion + '\'' +
                ", imagenUrl='" + imagenUrl + '\'' +
                ", cantidad=" + cantidad +
                ", seleccionado=" + seleccionado +
                '}';
    }
}
