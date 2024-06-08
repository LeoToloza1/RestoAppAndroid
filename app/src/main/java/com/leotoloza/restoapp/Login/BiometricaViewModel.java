package com.leotoloza.restoapp.Login;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;

public class BiometricaViewModel extends AndroidViewModel {
    private Context contexto;
    private MutableLiveData<Boolean> biometricoSoportado;
    private MutableLiveData<String> mensajeAutenticacion;
    private MutableLiveData<Boolean> autenticadoConHuella;
    private BiometricPrompt biometricPrompt;
    public BiometricaViewModel(@NonNull Application application) {
        super(application);
        contexto = application.getApplicationContext();
    }

    public MutableLiveData<Boolean> getAutenticadoConHuella() {
        if (autenticadoConHuella == null) autenticadoConHuella = new MutableLiveData<>();
        return autenticadoConHuella;
    }

    public MutableLiveData<Boolean> getBiometricoSoportado() {
        if (biometricoSoportado == null) biometricoSoportado = new MutableLiveData<>();
        return biometricoSoportado;
    }

    public MutableLiveData<String> getMensajeAutenticacion() {
        if (mensajeAutenticacion == null) mensajeAutenticacion = new MutableLiveData<>();
        return mensajeAutenticacion;
    }

    public void inicializarBiometria(AppCompatActivity actividad) {
        Executor executor = ContextCompat.getMainExecutor(contexto);
        biometricPrompt = new BiometricPrompt(actividad, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                mensajeAutenticacion.setValue("Error de autenticación: " + errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                autenticadoConHuella.setValue(true);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                mensajeAutenticacion.setValue("Autenticación fallida");
            }
        });

        verificarDisponibilidadBiometrica();
    }

    private void verificarDisponibilidadBiometrica() {
        BiometricManager biometricManager = BiometricManager.from(contexto);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
                case BiometricManager.BIOMETRIC_SUCCESS:
                    biometricoSoportado.setValue(true);
                    BiometricPrompt.PromptInfo promptInfo = crearPromptInfo();
                    biometricPrompt.authenticate(promptInfo);
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                    mensajeAutenticacion.setValue("No hay hardware biométrico disponible en este dispositivo.");
                    biometricoSoportado.setValue(false);
                    break;
                case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                    mensajeAutenticacion.setValue("El hardware biométrico no está disponible actualmente.");
                    biometricoSoportado.setValue(false);
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                    mensajeAutenticacion.setValue("No hay datos biométricos registrados. Registre al menos una huella digital en la configuración del dispositivo.");
                    biometricoSoportado.setValue(false);
                    break;
            }
        }
    }

    private BiometricPrompt.PromptInfo crearPromptInfo() {
        return new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticación Biométrica")
                .setSubtitle("Inicie sesión utilizando su huella digital")
                .setNegativeButtonText("Cancelar")
                .build();
    }

}