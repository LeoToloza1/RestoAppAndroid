package com.leotoloza.restoapp.ui.Perfil;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.leotoloza.restoapp.Models.Cliente;
import com.leotoloza.restoapp.R;
import com.leotoloza.restoapp.Servicios.Dialogo;
import com.leotoloza.restoapp.Servicios.ToastPesonalizado;
import com.leotoloza.restoapp.databinding.FragmentPerfilBinding;
import com.leotoloza.restoapp.request.ApiClient;

public class PerfilFragment extends Fragment {
    private PerfilViewModel perfilViewModel;
    private FragmentPerfilBinding binding;
    private ActivityResultLauncher<Intent> arl;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        perfilViewModel.getClienteMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Cliente>() {
            @Override
            public void onChanged(Cliente cliente) {
                binding.etNombre.setText(cliente.getNombre_cliente());
                binding.etApellido.setText(cliente.getApellido_cliente());
                binding.etEmail.setText(cliente.getEmail_cliente());
                binding.etDireccion.setText(cliente.getDireccion_cliente());
                binding.etTelefono.setText(cliente.getTelefono_cliente());
                Glide.with(getContext())
                        .load(ApiClient.URLBASE + "uploads/" + cliente.getAvatarUrl())
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error)
                        .into(binding.ivFoto);
            }
        });
        perfilViewModel.getCamposEditablesLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.etNombre.setEnabled(aBoolean);
                binding.etApellido.setEnabled(aBoolean);
               binding.etEmail.setEnabled(aBoolean);
                binding.etDireccion.setEnabled(aBoolean);
                binding.etTelefono.setEnabled(aBoolean);
                binding.ivFoto.setEnabled(aBoolean);
            }
        });
        perfilViewModel.getTextoBotonLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btnEditar.setText(s);
            }
        });

        perfilViewModel.getPassword().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
            }
        });
        binding.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perfilViewModel.habilitarCampos();
                String nombre = binding.etNombre.getText().toString();
                String apellido = binding.etApellido.getText().toString();
                String email = binding.etEmail.getText().toString();
                String direccion = binding.etDireccion.getText().toString();
                String telefono = binding.etTelefono.getText().toString();
                perfilViewModel.cambiarTextoBoton(nombre, apellido, email, direccion, telefono);

            }
        });
        binding.ivFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });

        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                perfilViewModel.recibirFoto(result, getContext());
            }
        });
        perfilViewModel.getUriMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.ivFoto.setImageURI(uri);
            }
        });

        binding.btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogo.mostrarDialogoConEntrada(getContext(), "Cambiar contraseña", new Dialogo.CambioContraseñaListener() {
                    @Override
                    public void onAceptar(String nuevaContraseña) {
                        perfilViewModel.cambiarPassword(nuevaContraseña);

                    }
                });
            }
        });

        perfilViewModel.verPerfil();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl.launch(intent);
    }
}