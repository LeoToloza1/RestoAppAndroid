package com.leotoloza.restoapp.ui.Pedido;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.leotoloza.restoapp.Models.Pedido;
import com.leotoloza.restoapp.Models.PedidoDTO;
import com.leotoloza.restoapp.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoViewModel extends AndroidViewModel {
    private MutableLiveData<Pedido> pedidoMutableLiveData;
    private MutableLiveData<List<Pedido>> listaPedidosMutableLiveData;
    private MutableLiveData<String> mensajeMutableLiveData;

    public PedidoViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getMensajeMutableLiveData() {
        if (mensajeMutableLiveData == null) {
            mensajeMutableLiveData = new MutableLiveData<>();
        }
        return mensajeMutableLiveData;
    }

    public MutableLiveData<Pedido> getPedidoMutableLiveData() {
        if (pedidoMutableLiveData == null) {
            pedidoMutableLiveData = new MutableLiveData<>();
        }
        return pedidoMutableLiveData;
    }

    public MutableLiveData<List<Pedido>> getListaPedidosMutableLiveData() {
        if (listaPedidosMutableLiveData == null) {
            listaPedidosMutableLiveData = new MutableLiveData<>();
        }
        return listaPedidosMutableLiveData;
    }

    public void verPedidos() {
        SharedPreferences sp = getApplication().getSharedPreferences("tokenRestoApp", 0);
        String token = sp.getString("tokenAcceso", "");
        token = "Bearer " + token;
        ApiClient.RestoApp endpoint = ApiClient.getRestoApp();
        Call<List<PedidoDTO>> llamada = endpoint.verPedidos(token);
        llamada.enqueue(new Callback<List<PedidoDTO>>() {
            @Override
            public void onResponse(Call<List<PedidoDTO>> call, Response<List<PedidoDTO>> response) {

            }

            @Override
            public void onFailure(Call<List<PedidoDTO>> call, Throwable t) {

            }
        });


    }
}


