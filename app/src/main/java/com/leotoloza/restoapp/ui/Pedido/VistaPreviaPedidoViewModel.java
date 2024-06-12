package com.leotoloza.restoapp.ui.Pedido;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.leotoloza.restoapp.Models.Pedido;
import com.leotoloza.restoapp.Models.PedidoDTO;
import com.leotoloza.restoapp.Models.Producto;
import com.leotoloza.restoapp.Models.ProductoDTO;
import com.leotoloza.restoapp.R;
import com.leotoloza.restoapp.Servicios.Dialogo;
import com.leotoloza.restoapp.Servicios.ToastPesonalizado;
import com.leotoloza.restoapp.request.ApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VistaPreviaPedidoViewModel extends AndroidViewModel {

    private MutableLiveData<List<Producto>> productosMutableLiveData;
    private MutableLiveData<List<Integer>> cantidadesMutableLiveData;
    private MutableLiveData<String> mensajeMutableLiveData;
    private MutableLiveData<List<ProductoDTO>> mutableLiveDataDTO;
    private MutableLiveData<Pedido> mutablePedido;

    public VistaPreviaPedidoViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Pedido> getMutablePedido() {
        if (mutablePedido == null) mutablePedido = new MutableLiveData<>();
        return mutablePedido;
    }

    public MutableLiveData<List<ProductoDTO>> getMutableLiveDataDTO() {
        if (mutableLiveDataDTO == null) mutableLiveDataDTO = new MutableLiveData<>();
        return mutableLiveDataDTO;
    }

    public MutableLiveData<List<Producto>> getProductosMutableLiveData() {
        if (productosMutableLiveData == null) {
            productosMutableLiveData = new MutableLiveData<>();
        }
        return productosMutableLiveData;
    }

    public MutableLiveData<List<Integer>> getCantidadesMutableLiveData() {
        if (cantidadesMutableLiveData == null) {
            cantidadesMutableLiveData = new MutableLiveData<>();
        }
        return cantidadesMutableLiveData;
    }

    public MutableLiveData<String> getMensajeMutableLiveData() {
        if (mensajeMutableLiveData == null) {
            mensajeMutableLiveData = new MutableLiveData<>();
        }
        return mensajeMutableLiveData;
    }

    public void recuperarProductos(Bundle bundle) {
        if (bundle != null) {
            HashMap<Producto, Integer> productosSeleccionados = (HashMap<Producto, Integer>) bundle.getSerializable("productosSeleccionados");
            if (productosSeleccionados != null) {
                List<Producto> productosList = new ArrayList<>();
                List<ProductoDTO> productoDTOList = new ArrayList<>();
                List<Integer> cantidadesList = new ArrayList<>();
                for (Map.Entry<Producto, Integer> entry : productosSeleccionados.entrySet()) {
                    Producto producto = entry.getKey();
                    Integer cantidad = entry.getValue();
                    productosList.add(producto);
                    cantidadesList.add(cantidad);
                    ProductoDTO productoDTO = new ProductoDTO();
                    productoDTO.setProductoId(producto.getId());
                    productoDTO.setNombre_producto(producto.getNombre_producto());
                    productoDTO.setDescripcion(producto.getDescripcion());
                    productoDTO.setImagenUrl(producto.getImagenUrl());
                    productoDTO.setPrecio(producto.getPrecio());
                    productoDTO.setCantidad(cantidad);
                    productoDTO.setSeleccionado(true);
                    productoDTOList.add(productoDTO);
                }
                productosMutableLiveData.setValue(productosList);
                mutableLiveDataDTO.setValue(productoDTOList);
                cantidadesMutableLiveData.setValue(cantidadesList);
            } else {
                mensajeMutableLiveData.setValue("No se encontraron productos seleccionados");
            }
        }
    }


    public void confirmarPedido(PedidoDTO pedidoDTO) {
        Log.d("salida", "confirmarPedido: "+pedidoDTO.toString());
        SharedPreferences sp = getApplication().getSharedPreferences("tokenRestoApp", 0);
        String token = sp.getString("tokenAcceso", "");
        token = "Bearer " + token;
        ApiClient.RestoApp endpoint = ApiClient.getRestoApp();
        Call<Pedido> llamada = endpoint.confirmarPedido(token, pedidoDTO);
        llamada.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                if (response.isSuccessful()) {
                    mutablePedido.setValue(response.body());
                    ToastPesonalizado.mostrarMensaje(getApplication().getApplicationContext(), "Se confirmo el pedido");

                } else {
                    mensajeMutableLiveData.setValue("Error al confirmar el pedido: " + response.message());
                    Log.d("salida", "onResponse: "+response.message());
                }
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                Log.d("Error al confirmar pedido", t.getMessage());
                mensajeMutableLiveData.setValue(t.getMessage());

            }
        });
    }


}



