package com.leotoloza.restoapp.ui.home;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.leotoloza.restoapp.Models.Restaurante;
import com.leotoloza.restoapp.R;
import com.leotoloza.restoapp.databinding.FragmentDetalleRestauranteBinding;
import com.leotoloza.restoapp.databinding.FragmentHomeBinding;
import com.leotoloza.restoapp.request.ApiClient;

public class DetalleRestauranteFragment extends Fragment {
    private DetalleRestauranteViewModel detalleRestauranteViewModel;
    private FragmentDetalleRestauranteBinding binding;
    private Restaurante resto;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding =  FragmentDetalleRestauranteBinding.inflate(inflater, container, false);
        detalleRestauranteViewModel =  new ViewModelProvider(this).get(DetalleRestauranteViewModel.class);

        detalleRestauranteViewModel.getRestauranteMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Restaurante>() {
            @Override
            public void onChanged(Restaurante restaurante) {
                resto = restaurante;
                    binding.NombreRestaurante.setText(restaurante.getNombre_restaurante());
                    binding.DireccionRestaurante.setText("Dirección: "+restaurante.getDireccion_restaurante());
                    binding.rubro.setText("Rubro: "+restaurante.getRubro().getNombre_rubro());
                    binding.TelefonoRestaurante.setText("Contacto: "+restaurante.getTelefono_restaurante());
                    binding.EmailRestaurante.setText("Email: "+restaurante.getEmail_restaurante());
                    String urlBase = ApiClient.URLBASE + "uploads/";
                    String urlFoto = urlBase + restaurante.getLogo_url();
                    Glide.with(requireContext())
                            .load(urlFoto)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.error)
                            .into(binding.LogoRestaurante);
            }
        });
            detalleRestauranteViewModel.recuperarRestaurante(getArguments());
        binding.VerProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("restauranteId", resto.getId());
                Navigation.findNavController(requireView()).navigate(R.id.action_detalleRestaurante_to_productoFragment, bundle);
            }
        });

        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}