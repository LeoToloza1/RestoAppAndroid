package com.leotoloza.restoapp.Models;

import java.io.Serializable;

public class ProductoDTO implements Serializable {
    private int ProductoId;
    private String Nombre_producto;
    private double Precio;
    private String Descripcion;
    private String ImagenUrl;
    private int Cantidad;
    private boolean seleccionado;

    public ProductoDTO() {
    }

    public int getProductoId() {
        return ProductoId;
    }

    public void setProductoId(int productoId) {
        ProductoId = productoId;
    }

    public String getNombre_producto() {
        return Nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        Nombre_producto = nombre_producto;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double precio) {
        Precio = precio;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getImagenUrl() {
        return ImagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        ImagenUrl = imagenUrl;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
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
                "ProductoId=" + ProductoId +
                ", Nombre_producto='" + Nombre_producto + '\'' +
                ", Precio=" + Precio +
                ", Descripcion='" + Descripcion + '\'' +
                ", ImagenUrl='" + ImagenUrl + '\'' +
                ", Cantidad=" + Cantidad +
                ", seleccionado=" + seleccionado +
                '}';
    }
}
