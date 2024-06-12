package com.leotoloza.restoapp.ui.Pedido;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.leotoloza.restoapp.Models.Envio;
import com.leotoloza.restoapp.Models.Pedido;
import com.leotoloza.restoapp.Models.PedidoDTO;
import com.leotoloza.restoapp.Servicios.Dialogo;
import com.leotoloza.restoapp.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoViewModel extends AndroidViewModel {
    private MutableLiveData<Pedido> pedidoMutableLiveData;
    private MutableLiveData<List<Pedido>> listaPedidosMutableLiveData;
    private MutableLiveData<List<PedidoDTO>> mutableLiveDataDTO;
    private MutableLiveData<String> mensajeMutableLiveData;
    private MutableLiveData<Envio> mutableEnvio;

    public PedidoViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Envio> getMutableEnvio() {
        if (mutableEnvio == null) {
            mutableEnvio = new MutableLiveData<>();
        }
        return mutableEnvio;
    }

    public MutableLiveData<List<PedidoDTO>> getMutableLiveDataDTO() {
        if (mutableLiveDataDTO == null) {
            mutableLiveDataDTO = new MutableLiveData<>();
        }
        return mutableLiveDataDTO;
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

                if (response.isSuccessful()) {
                    mutableLiveDataDTO.setValue(response.body());
                } else {
                    Log.e("API_ERROR", "Error en la respuesta del servidor");
                }


            }

            @Override
            public void onFailure(Call<List<PedidoDTO>> call, Throwable t) {
                Log.d("Error al recuperar pedidos", t.getMessage());
                mensajeMutableLiveData.setValue(t.getMessage());
            }
        });


    }

    public void solicitarEnvio(int id) {
        ApiClient.RestoApp endpoint = ApiClient.getRestoApp();
        Call<Envio> llamada = endpoint.solicitarEnvio(id);
        llamada.enqueue(new Callback<Envio>() {
            @Override
            public void onResponse(Call<Envio> call, Response<Envio> response) {
                if (response.isSuccessful()) {
                    mutableEnvio.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Envio> call, Throwable t) {
                Log.d("Error al solicitar envio", t.getMessage());
                mensajeMutableLiveData.setValue(t.getMessage());
            }
        });
    }


}


