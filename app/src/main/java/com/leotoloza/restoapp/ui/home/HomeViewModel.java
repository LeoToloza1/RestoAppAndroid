package com.leotoloza.restoapp.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.leotoloza.restoapp.Models.Restaurante;
import com.leotoloza.restoapp.Servicios.Dialogo;
import com.leotoloza.restoapp.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<List<Restaurante>> listMutableLiveData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Restaurante>> getListMutableLiveData() {
        if (listMutableLiveData == null) listMutableLiveData = new MutableLiveData<>();
        return listMutableLiveData;
    }

    public void getRestaurantes() {
        ApiClient.RestoApp endpoint = ApiClient.getRestoApp();
        Call<List<Restaurante>> llamada = endpoint.getRestaurantes();
        llamada.enqueue(new Callback<List<Restaurante>>() {
            @Override
            public void onResponse(Call<List<Restaurante>> call, Response<List<Restaurante>> response) {
                if (response.isSuccessful()) {
                    listMutableLiveData.setValue(response.body());
//                    Log.d("salida", "onResponse: " + response.body().get(0).getNombre_restaurante());
                } else {
//                    Log.d("salida", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Restaurante>> call, Throwable t) {
                Dialogo.mostrarDialogoInformativo(getApplication().getApplicationContext(), "Error", "Ocurrio un error: " + t.getMessage());
            }
        });

    }

    public void buscarPorNombre(String nombre) {
        ApiClient.RestoApp endpoint = ApiClient.getRestoApp();
        Call<List<Restaurante>> call = endpoint.buscarRestaurante(nombre);
        call.enqueue(new Callback<List<Restaurante>>() {
            @Override
            public void onResponse(Call<List<Restaurante>> call, Response<List<Restaurante>> response) {
                if (response.isSuccessful()) {
                    listMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Restaurante>> call, Throwable t) {
                Dialogo.mostrarDialogoInformativo(getApplication().getApplicationContext(), "Error", "Ocurrio un error: " + t.getMessage());
            }
        });
    }
}