package com.leotoloza.restoapp.ui.Pedido;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.leotoloza.restoapp.Models.Pedido;
import com.leotoloza.restoapp.Servicios.ToastPesonalizado;
import com.leotoloza.restoapp.databinding.FragmentPedidoBinding;

import java.util.List;

public class PedidoFragment extends Fragment {

    private FragmentPedidoBinding binding;
    private VistaPreviaPedidoViewModel vistaPreviaPedidoViewModel;
    private PedidoViewModel pedidoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       vistaPreviaPedidoViewModel =new ViewModelProvider(this).get(VistaPreviaPedidoViewModel.class);
       pedidoViewModel = new ViewModelProvider(this).get(PedidoViewModel.class);
        binding = FragmentPedidoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        pedidoViewModel.getMensajeMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ToastPesonalizado.mostrarMensaje(getContext(), s);
            }
        });
        pedidoViewModel.getListaPedidosMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Pedido>>() {
            @Override
            public void onChanged(List<Pedido> pedidos) {
                Log.d("salida", "onChanged: "+pedidos.toString());
            }
        });
        pedidoViewModel.getPedidoMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Pedido>() {
            @Override
            public void onChanged(Pedido pedido) {
                Log.d("salida", "onChanged: "+pedido.toString());
            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}