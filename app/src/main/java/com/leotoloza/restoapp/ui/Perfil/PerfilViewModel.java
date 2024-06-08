package com.leotoloza.restoapp.ui.Perfil;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.leotoloza.restoapp.Models.Cliente;
import com.leotoloza.restoapp.Servicios.Dialogo;
import com.leotoloza.restoapp.Servicios.RealPathUtil;
import com.leotoloza.restoapp.request.ApiClient;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {
    private MutableLiveData<Cliente> clienteMutableLiveData;
    private MutableLiveData<Boolean> camposEditablesLiveData;
    private MutableLiveData<String> textoBotonLiveData;
    private MutableLiveData<Uri> uriMutableLiveData;
    private boolean editable;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        editable = false;
    }

    public MutableLiveData<Uri> getUriMutableLiveData() {
        if (uriMutableLiveData == null) uriMutableLiveData = new MutableLiveData<>();
        return uriMutableLiveData;
    }

    public void recibirFoto(ActivityResult result, Context context) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
            uriMutableLiveData.setValue(uri);
        }
    }

    public LiveData<Boolean> getCamposEditablesLiveData() {
        if (camposEditablesLiveData == null) {
            camposEditablesLiveData = new MutableLiveData<>();
            camposEditablesLiveData.setValue(editable);
        }
        return camposEditablesLiveData;
    }

    public MutableLiveData<String> getTextoBotonLiveData() {
        if (textoBotonLiveData == null) {
            textoBotonLiveData = new MutableLiveData<>();
            textoBotonLiveData.setValue("Editar");
        }
        return textoBotonLiveData;
    }

    public void habilitarCampos() {
        editable = !editable;
        camposEditablesLiveData.setValue(editable);
    }

    public void cambiarTextoBoton(String nombre, String apellido, String email, String direccion, String telefono) {
        if (editable) {
            textoBotonLiveData.setValue("Guardar");
            if(textoBotonLiveData.getValue().equals("Guardar")) onGuardarClick(nombre, apellido, email, direccion, telefono);
        } else {
            textoBotonLiveData.setValue("Editar");
        }
    }

    public MutableLiveData<Cliente> getClienteMutableLiveData() {
        if (clienteMutableLiveData == null) clienteMutableLiveData = new MutableLiveData<>();
        return clienteMutableLiveData;
    }

    public void verPerfil() {
        SharedPreferences sp = getApplication().getSharedPreferences("tokenRestoApp", 0);
        String token = sp.getString("tokenAcceso", "");
        token = "Bearer " + token;
        ApiClient.RestoApp endpoint = ApiClient.getRestoApp();
        Call<Cliente> call = endpoint.verPerfil(token);
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (response.isSuccessful()) {
                    clienteMutableLiveData.setValue(response.body());
                    Log.d("salida", "onResponse: " + response.body().getNombre_cliente());
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Dialogo.mostrarDialogoInformativo(getApplication().getApplicationContext(), "Error", "Ocurrió un error: " + t.getMessage());
            }
        });
    }

    public boolean compararPass(String pass, String passConfirm) {
        return pass.equals(passConfirm);
    }

    public void onGuardarClick(String nombre, String apellido, String email, String direccion, String telefono) {
            Cliente cliente = crearCliente(nombre, apellido, email, direccion, telefono);
            Uri uriImage = uriMutableLiveData.getValue();
            editarPerfil(cliente, uriImage);
    }

    private Cliente crearCliente(String nombre, String apellido, String email, String direccion, String telefono) {
        Cliente cliente = new Cliente();
        cliente.setNombre_cliente(nombre);
        cliente.setApellido_cliente(apellido);
        cliente.setEmail_cliente(email);
        cliente.setDireccion_cliente(direccion);
        cliente.setTelefono_cliente(telefono);
        return cliente;
    }

    public void editarPerfil(Cliente cliente, Uri imagenUri) {
        SharedPreferences sp = getApplication().getSharedPreferences("tokenRestoApp", 0);
        String token = sp.getString("tokenAcceso", "");
        token = "Bearer " + token;

        RequestBody nombreCliente = RequestBody.create(MediaType.parse("multipart/form-data"), cliente.getNombre_cliente());
        RequestBody apellidoCliente = RequestBody.create(MediaType.parse("multipart/form-data"), cliente.getApellido_cliente());
        RequestBody emailCliente = RequestBody.create(MediaType.parse("multipart/form-data"), cliente.getEmail_cliente());
        RequestBody direccionCliente = RequestBody.create(MediaType.parse("multipart/form-data"), cliente.getDireccion_cliente());
        RequestBody telefonoCliente = RequestBody.create(MediaType.parse("multipart/form-data"), cliente.getTelefono_cliente());

        MultipartBody.Part imagenPart = null;
        if (imagenUri != null) {
            String path = RealPathUtil.getRealPath(getApplication().getApplicationContext(), imagenUri);
            File file = new File(path);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            imagenPart = MultipartBody.Part.createFormData("avatarFile", file.getName(), requestFile);
        }

        ApiClient.RestoApp endpoint = ApiClient.getRestoApp();
        Call<Cliente> call = endpoint.editarPerfil(token, nombreCliente, apellidoCliente, emailCliente, direccionCliente, telefonoCliente, imagenPart);
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (response.isSuccessful()) {
                    clienteMutableLiveData.setValue(response.body());
                    Log.d("salida", "onResponse: " + response.body().getNombre_cliente());
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Dialogo.mostrarDialogoInformativo(getApplication().getApplicationContext(), "Error", "Ocurrió un error: " + t.getMessage());
            }
        });
    }

}
