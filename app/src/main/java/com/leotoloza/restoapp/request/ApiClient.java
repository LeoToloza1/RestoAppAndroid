package com.leotoloza.restoapp.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leotoloza.restoapp.Models.Cliente;
import com.leotoloza.restoapp.Models.Producto;
import com.leotoloza.restoapp.Models.Restaurante;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ApiClient {
    public static final String URLBASE = "http://192.168.0.106:5000/";
    private static RestoApp api;

    public static RestoApp getRestoApp() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        api = retrofit.create(RestoApp.class);
        return api;
    }


    public interface RestoApp {
        @POST("Cliente/Login")
        @FormUrlEncoded
        Call<String> login(@Field("email") String email, @Field("password") String password);

        @Multipart //enviar los campos igual que como esta en el backend
        @POST("Cliente/registro")
        Call<Cliente> registro(
                @Part("nombre_cliente") RequestBody nombreCliente,
                @Part("apellido_cliente") RequestBody apellidoCliente,
                @Part("email_cliente") RequestBody emailCliente,
                @Part("password") RequestBody password,
                @Part("direccion_cliente") RequestBody direccionCliente,
                @Part("telefono_cliente") RequestBody telefonoCliente,
                @Part MultipartBody.Part avatarFile);

        @GET("restaurante/todos")
        Call<List<Restaurante>> getRestaurantes();

        @GET("restaurante/buscar")
        Call<List<Restaurante>> buscarRestaurante(@Query("nombre") String nombre);

        @GET("Producto/activos/{id}")
        Call<List<Producto>> getProductos(@Path("id") int id);

        @GET("Producto/buscar")
        Call<List<Producto>> buscarProductos(@Query("nombre") String nombre);

        @GET("Cliente")
        Call<Cliente> verPerfil(@Header("Authorization") String token);
        @Multipart
        @POST("cliente/editar")
        Call<Cliente> editarPerfil(@Header("Authorization") String token,
                                   @Part("nombre_cliente") RequestBody nombreCliente,
                                   @Part("apellido_cliente") RequestBody apellidoCliente,
                                   @Part("email_cliente") RequestBody emailCliente,
                                   @Part("direccion_cliente") RequestBody direccionCliente,
                                   @Part("telefono_cliente") RequestBody telefonoCliente,
                                   @Part MultipartBody.Part avatarFile);

        @POST("cliente/password")
        @FormUrlEncoded
        Call<Cliente> editarPerfil(String token,@Field("password") String pass);

//        @GET("api/pago/{id}")
//        Call<List<Pago>> pagosPorContrato(@Header("Authorization") String token, @Path("id") int id);
//        @POST("api/recovery")
//        @FormUrlEncoded
//        Call<String> enviarMail(@Field("email") String email);
//        @PATCH("api/Propietario/actualizar/pass")
//        @FormUrlEncoded
//        Call<String> cambiarPass(@Header("Authorization")String token, @Field("pass") String pass);
    }
}
