package com.leotoloza.restoapp.ui.home;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ViewFlipper;

import com.leotoloza.restoapp.Models.Producto;
import com.leotoloza.restoapp.R;
import com.leotoloza.restoapp.Servicios.ToastPesonalizado;
import com.leotoloza.restoapp.databinding.FragmentProductoBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoFragment extends Fragment {
    private ProductoViewModel productoViewModel;
    private FragmentProductoBinding binding;

    private ProductoAdapter productoAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProductoBinding.inflate(inflater, container, false);
        productoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);
        int restauranteId = getArguments().getInt("restauranteId", 0);
        RecyclerView listaProd = binding.listaProductos;
        GridLayoutManager glm = new GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false);
        productoViewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> productos) {
                productoAdapter = new ProductoAdapter(productos, getContext());
                listaProd.setLayoutManager(glm);
                listaProd.setHasFixedSize(true);
                listaProd.setAdapter(productoAdapter);

                productoAdapter.setClickListener(new ProductoAdapter.ClickListener() {
                    @Override
                    public void clickDetalle(Producto producto) {
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("producto", producto);
//                        esto envia el producto al DETALLE Producto-FRAGMENT
//                        Navigation.findNavController(requireView()).navigate(R.id.action_navigation_home_to_detalleProducto, bundle);
//                        ToastPesonalizado.mostrarMensaje(getContext(), producto.getNombre_producto());
                    }

                    @Override
                    public void clickCompra(Producto producto) {
                        //ya pude acceder al checkbox del item, ahora deberia agregarlo a una lista
                        ToastPesonalizado.mostrarMensaje(getContext(), "Seleccionado: "+producto.getNombre_producto());
                    }
                });
            }
        });
        binding.btnVistaPrevia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<Producto, Integer> productosSeleccionados = productoAdapter.getSeleccionados();
                Bundle bundle = new Bundle();
                bundle.putSerializable("productosSeleccionados", (HashMap<Producto, Integer>) productosSeleccionados);
                Navigation.findNavController(requireView()).navigate(R.id.action_productoFragment_to_vistaPreviaFragment, bundle);
            }
        });
        binding.searchViewProd.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productoViewModel.buscarPorNombre(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //busqueda automatica
                productoViewModel.buscarPorNombre(newText);
                return false;
            }
        });
        productoViewModel.getProductos(restauranteId);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}