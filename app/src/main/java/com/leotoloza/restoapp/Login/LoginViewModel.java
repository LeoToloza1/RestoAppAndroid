package com.leotoloza.restoapp.Login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.leotoloza.restoapp.MainActivity;
import com.leotoloza.restoapp.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private Context contexto;
    private MutableLiveData<String> mensajeError;
    private MutableLiveData<Boolean> autenticadoPreviamente;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        contexto = application.getApplicationContext();
    }
    public MutableLiveData<Boolean> getAutenticadoPreviamente() {
        if (autenticadoPreviamente == null) {
            autenticadoPreviamente = new MutableLiveData<>();
            verificarAutenticacionPrevia();
        }
        return autenticadoPreviamente;
    }

    public MutableLiveData<String> getMensajeError() {
        if (mensajeError == null) {
            mensajeError = new MutableLiveData<>();
        }
        return mensajeError;
    }

    public void login(String email, String password) {
        ApiClient.RestoApp endpoint = ApiClient.getRestoApp();
        Call<String> llamada = endpoint.login(email,password);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    if (token != null && !token.isEmpty()) {
                        guardarDatosUsuario(email, password);
                        guardarToken(token);
                        establecerAutenticacionPrevia(true);
                        Intent intent = new Intent(contexto, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        contexto.startActivity(intent);
                    } else {
                        mensajeError.setValue("Token inválido recibido.");
                    }
                } else if (response.code() == 404) {
                    mensajeError.setValue("Verifique sus credenciales.");
                } else {
                    mensajeError.setValue("Error de autenticación: " + response.message());
                    Log.d("salida", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                mensajeError.setValue("Error de red: " + t.getMessage());
                Log.d("salida", "onFailure: " + t.getMessage());
            }
        });
    }

    private void guardarToken(String token) {
        SharedPreferences sp = contexto.getSharedPreferences("tokenRestoApp", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("tokenAcceso",token);
        editor.commit();
    }

    private void guardarDatosUsuario(String email, String password) {
        SharedPreferences sp = contexto.getSharedPreferences("datosUsuario", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("emailCliente", email);
        editor.putString("passwordCliente", password);
        editor.commit();
    }


    private void verificarAutenticacionPrevia() {
        SharedPreferences sp = contexto.getSharedPreferences("autenticacionBiometrica", 0);
        boolean autenticado = sp.getBoolean("autenticadoPreviamente", false);
        autenticadoPreviamente.setValue(autenticado);
    }

    private void establecerAutenticacionPrevia(boolean autenticado) {
        SharedPreferences sp = contexto.getSharedPreferences("autenticacionBiometrica", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("autenticadoPreviamente", autenticado);
        editor.commit();
    }
}