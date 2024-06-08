package com.leotoloza.restoapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.leotoloza.restoapp.Models.Restaurante;
import com.leotoloza.restoapp.R;
import com.leotoloza.restoapp.databinding.FragmentHomeBinding;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
private  RestauranteAdapter restauranteAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);


        RecyclerView listaRestaurantes = binding.restaurantRecyclerView;
        homeViewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Restaurante>>() {
            @Override
            public void onChanged(List<Restaurante> restaurantes) {
                restauranteAdapter = new RestauranteAdapter(restaurantes, requireContext());
                GridLayoutManager glm = new GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false);
                listaRestaurantes.setLayoutManager(glm);
                listaRestaurantes.setAdapter(restauranteAdapter);
                restauranteAdapter.setClickListener(new RestauranteAdapter.ClickListener() {
                    @Override
                    public void clickDetalle(Restaurante restaurante) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("restaurante", restaurante);
                        //esto envia el restaurante al DETALLE-FRAGMENT
                        Navigation.findNavController(requireView()).navigate(R.id.action_navigation_home_to_detalleRestaurante, bundle);
                    }
                });
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //busqueda cuando presionan enter
                homeViewModel.buscarPorNombre(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //busqueda automatica
                homeViewModel.buscarPorNombre(newText);
                return false;
            }
        });

        homeViewModel.getRestaurantes();

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        homeViewModel.getListMutableLiveData().removeObservers(getViewLifecycleOwner());
    }
}
