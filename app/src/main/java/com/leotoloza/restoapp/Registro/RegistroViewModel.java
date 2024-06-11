package com.leotoloza.restoapp.Registro;

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

import com.leotoloza.restoapp.Login.Login;
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

public class RegistroViewModel extends AndroidViewModel {
    private MutableLiveData<Cliente> clienteMutableLiveData;
    private MutableLiveData<Uri> uriMutableLiveData;
    private MutableLiveData<String> errorMutable;
    private Context context;

    public RegistroViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<String> getErrorMutable() {
        if (errorMutable == null) errorMutable = new MutableLiveData<>();
        return errorMutable;
    }

    public LiveData<Uri> getUriMutable() {
        if (uriMutableLiveData == null) {
            uriMutableLiveData = new MutableLiveData<>();
        }
        return uriMutableLiveData;
    }

    public MutableLiveData<Cliente> getClienteMutableLiveData() {
        if (clienteMutableLiveData == null) clienteMutableLiveData = new MutableLiveData<>();
        return clienteMutableLiveData;
    }

    public void recibirFoto(ActivityResult result, Context context) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
            uriMutableLiveData.setValue(uri);
        }
    }

    public boolean compararPass(String pass, String passConfirm) {
        return pass.equals(passConfirm);
    }

    public void onGuardarClick(String nombre, String apellido, String email, String direccion, String telefono, String pass, String passConfirm) {
        if (compararPass(pass, passConfirm)) {
            Cliente cliente = crearCliente(nombre, apellido, email, direccion, telefono, pass);
            Uri uriImage = uriMutableLiveData.getValue();
            registrarse(cliente, uriImage);
        } else {
            errorMutable.setValue("Las contraseñas no coinciden.");
        }
    }

    private Cliente crearCliente(String nombre, String apellido, String email, String direccion, String telefono, String pass) {
        Cliente cliente = new Cliente();
        cliente.setNombre_cliente(nombre);
        cliente.setApellido_cliente(apellido);
        cliente.setEmail_cliente(email);
        cliente.setDireccion_cliente(direccion);
        cliente.setTelefono_cliente(telefono);
        cliente.setPassword(pass);
        return cliente;
    }

    public void registrarse(Cliente cliente, Uri imagenUri) {
        RequestBody nombreCliente = RequestBody.create(MediaType.parse("multipart/form-data"), cliente.getNombre_cliente());
        RequestBody apellidoCliente = RequestBody.create(MediaType.parse("multipart/form-data"), cliente.getApellido_cliente());
        RequestBody emailCliente = RequestBody.create(MediaType.parse("multipart/form-data"), cliente.getEmail_cliente());
        RequestBody password = RequestBody.create(MediaType.parse("multipart/form-data"), cliente.getPassword());
        RequestBody direccionCliente = RequestBody.create(MediaType.parse("multipart/form-data"), cliente.getDireccion_cliente());
        RequestBody telefonoCliente = RequestBody.create(MediaType.parse("multipart/form-data"), cliente.getTelefono_cliente());

        String path = RealPathUtil.getRealPath(context, imagenUri);
        File file = new File(path);
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part avatarFile = MultipartBody.Part.createFormData("AvatarFile", file.getName(), fileBody);

        ApiClient.RestoApp endpoint = ApiClient.getRestoApp();
        Call<Cliente> llamada = endpoint.registro(nombreCliente, apellidoCliente, emailCliente, password, direccionCliente, telefonoCliente, avatarFile);
        llamada.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (response.isSuccessful()) {
                    Cliente registrado = response.body();
                    clienteMutableLiveData.postValue(registrado);
                } else {
                    Log.d("SALIDA", "onResponse: " + response.message());
                    errorMutable.setValue("Error en el registro: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                errorMutable.setValue("Error en el registro: " + t.getMessage());
            }
        });
    }





}
