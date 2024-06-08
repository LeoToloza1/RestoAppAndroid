package com.leotoloza.restoapp.ui.home;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.leotoloza.restoapp.Models.Restaurante;

public class DetalleRestauranteViewModel extends AndroidViewModel {
    private MutableLiveData<Restaurante> restauranteMutableLiveData;
    public DetalleRestauranteViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Restaurante> getRestauranteMutableLiveData() {
        if(restauranteMutableLiveData==null)restauranteMutableLiveData= new MutableLiveData<>();
        return restauranteMutableLiveData;
    }

    public void recuperarRestaurante(Bundle bundle) {
        if (bundle != null && bundle.containsKey("restaurante")) {
            Restaurante r = (Restaurante) bundle.get("restaurante");
            if (r != null) {
                restauranteMutableLiveData.setValue(r);
            }
        }
    }
}