package com.leotoloza.restoapp.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.leotoloza.restoapp.Models.Producto;
import com.leotoloza.restoapp.Models.ProductoDTO;
import com.leotoloza.restoapp.Models.Restaurante;
import com.leotoloza.restoapp.Servicios.Dialogo;
import com.leotoloza.restoapp.Servicios.ToastPesonalizado;
import com.leotoloza.restoapp.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductoViewModel extends AndroidViewModel {
    private MutableLiveData<List<Producto>> listMutableLiveData;
    private MutableLiveData<List<ProductoDTO>> listMutableLiveData2;

    public ProductoViewModel(@NonNull Application application) {
        super(application);
    }



    public MutableLiveData<List<Producto>> getListMutableLiveData() {
        if (listMutableLiveData == null) listMutableLiveData = new MutableLiveData<>();
        return listMutableLiveData;
    }

    public void getProductos(int id) {
        ApiClient.RestoApp endpoint = ApiClient.getRestoApp();
        Call<List<Producto>> productos = endpoint.getProductos(id);
        productos.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful()) {
                    List<Producto> listaProductos = response.body();
                    if (listaProductos.isEmpty()) {
                        ToastPesonalizado.mostrarMensaje(getApplication().getApplicationContext(), "No hay productos cargados todavia");
                    } else {
                        listMutableLiveData.setValue(listaProductos);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Dialogo.mostrarDialogoInformativo(getApplication().getApplicationContext(), "Error", "Ocurrio un error: " + t.getMessage());
            }
        });
    }
    public void buscarPorNombre(String nombre) {
        ApiClient.RestoApp endpoint = ApiClient.getRestoApp();
        Call<List<Producto>> call = endpoint.buscarProductos(nombre);
        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful()) {
                    listMutableLiveData.setValue(response.body());
                    Log.d("salida", "onResponse: "+response.body().get(0).getNombre_producto());
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Dialogo.mostrarDialogoInformativo(getApplication().getApplicationContext(), "Error", "Ocurrio un error: " + t.getMessage());
            }
        });
    }

}