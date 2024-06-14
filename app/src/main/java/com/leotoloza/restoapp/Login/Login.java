package com.leotoloza.restoapp.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.leotoloza.restoapp.MainActivity;
import com.leotoloza.restoapp.R;
import com.leotoloza.restoapp.Registro.Registro;
import com.leotoloza.restoapp.Servicios.ToastPesonalizado;
import com.leotoloza.restoapp.databinding.ActivityLoginBinding;
import com.leotoloza.restoapp.ui.home.HomeFragment;

public class Login extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;
    private BiometricaViewModel biometricaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences sp = this.getSharedPreferences("datosUsuario", this.MODE_PRIVATE);
        String email = sp.getString("emailCliente", "");
        String password = sp.getString("passwordCliente", "");

        loginViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        biometricaViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BiometricaViewModel.class);
        loginViewModel.getMensajeError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.mensaje.setText(s);
                ToastPesonalizado.mostrarMensaje(getApplicationContext(), s);
            }
        });
        binding.recuperarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, RecuperarPassActivity.class);
                startActivity(intent);
            }
        });
        Button btnLogin = binding.btnLogin;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.EditEmail.getText().toString();
                String pass = binding.EditPass.getText().toString();
                loginViewModel.login(email, pass);
            }
        });
        binding.btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
            }
        });
        biometricaViewModel.getBiometricoSoportado().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

            }
        });
        biometricaViewModel.getMensajeAutenticacion().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.mensaje.setText(s);
            }
        });
        biometricaViewModel.inicializarBiometria(Login.this);
        biometricaViewModel.getAutenticadoConHuella().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean autenticado) {
            }
        });
    }
}