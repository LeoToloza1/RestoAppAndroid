package com.leotoloza.restoapp.Login;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.leotoloza.restoapp.R;
import com.leotoloza.restoapp.databinding.ActivityRecuperarPassBinding;

public class RecuperarPassActivity extends AppCompatActivity {
    private ActivityRecuperarPassBinding binding;
    private RecuperarPassViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRecuperarPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RecuperarPassViewModel.class);
        viewModel.getMsjError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.mensaje.setText(s);
            }
        });
        viewModel.getRespuesta().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.mensaje.setText(s);
            }
        });
        binding.btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= binding.email.getText().toString();
                viewModel.recuperarPass(email);
            }
        });

    }
}