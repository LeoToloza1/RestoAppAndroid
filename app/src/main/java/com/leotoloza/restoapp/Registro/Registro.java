package com.leotoloza.restoapp.Registro;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.leotoloza.restoapp.Login.Login;
import com.leotoloza.restoapp.Models.Cliente;
import com.leotoloza.restoapp.Servicios.Dialogo;
import com.leotoloza.restoapp.databinding.ActivityRegistroBinding;

public class Registro extends AppCompatActivity {
    private ActivityRegistroBinding binding;
    private ActivityResultLauncher<Intent> arl;
    private RegistroViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(RegistroViewModel.class);

        binding.ivFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });

        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                viewModel.recibirFoto(result, getApplicationContext());
            }
        });

        viewModel.getUriMutable().observe(this, new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.ivFoto.setImageURI(uri);
            }
        });

        viewModel.getClienteMutableLiveData().observe(this, new Observer<Cliente>() {
            @Override
            public void onChanged(Cliente cliente) {
                Intent intent = new Intent(Registro.this, Login.class);
                Dialogo.mostrarDialogoInformativo(Registro.this, "Registro exitoso", "El registro se completó exitosamente.");
                startActivity(intent);
            }
        });

        viewModel.getErrorMutable().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("salida", "onChanged: ERROR: " + s);
                Dialogo.mostrarDialogoInformativo(Registro.this, "Error en el registro", s);
            }
        });

        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = binding.etNombre.getText().toString();
                String apellido = binding.etApellido.getText().toString();
                String email = binding.etEmail.getText().toString();
                String direccion = binding.etDireccion.getText().toString();
                String telefono = binding.etTelefono.getText().toString();
                String pass = binding.etPassword.getText().toString();
                String passConfirm = binding.etConfirmPassword.getText().toString();

                // Mostrar un diálogo para confirmar el registro
                Dialogo.mostrarDialogoConfirmacion(Registro.this, "Confirmar Registro", "¿Desea confirmar el registro?",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                viewModel.onGuardarClick(nombre, apellido, email, direccion, telefono, pass, passConfirm);
                            }
                        });
            }
        });
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl.launch(intent);
    }
}
