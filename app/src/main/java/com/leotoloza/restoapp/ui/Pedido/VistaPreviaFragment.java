package com.leotoloza.restoapp.ui.Pedido;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leotoloza.restoapp.MainActivity;
import com.leotoloza.restoapp.Models.Pedido;
import com.leotoloza.restoapp.Models.PedidoDTO;
import com.leotoloza.restoapp.Models.Producto;
import com.leotoloza.restoapp.Models.ProductoDTO;
import com.leotoloza.restoapp.R;
import com.leotoloza.restoapp.Servicios.ToastPesonalizado;
import com.leotoloza.restoapp.databinding.FragmentVistaPreviaBinding;

import java.util.List;

public class VistaPreviaFragment extends Fragment {

    private VistaPreviaPedidoViewModel mViewModel;
    private VistaPreviaAdapter vistaPreviaAdapter;
    private FragmentVistaPreviaBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentVistaPreviaBinding.inflate(inflater, container, false);

        mViewModel = new ViewModelProvider(this).get(VistaPreviaPedidoViewModel.class);

        RecyclerView rvProductos = binding.rvProductos;
        rvProductos.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvProductos.setHasFixedSize(true);
        mViewModel.getCantidadesMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> cantidades) {
                vistaPreviaAdapter = new VistaPreviaAdapter(mViewModel.getProductosMutableLiveData().getValue(), cantidades, getContext());
                rvProductos.setAdapter(vistaPreviaAdapter);
            }
        });
        mViewModel.getMensajeMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ToastPesonalizado.mostrarMensaje(getContext(), s);
            }
        });
        mViewModel.getProductosMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> productos) {
                List<Integer> cantidades = mViewModel.getCantidadesMutableLiveData().getValue();
                vistaPreviaAdapter = new VistaPreviaAdapter(productos, cantidades, getContext());
                rvProductos.setAdapter(vistaPreviaAdapter);
            }
        });
        mViewModel.getMutableLiveDataDTO().observe(getViewLifecycleOwner(), new Observer<List<ProductoDTO>>() {
            @Override
            public void onChanged(List<ProductoDTO> productoDTOS) {
            }
        });
        mViewModel.getMutablePedido().observe(getViewLifecycleOwner(), new Observer<Pedido>() {
            @Override
            public void onChanged(Pedido pedido) {
            }
        });
        binding.btnConfirmarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PedidoDTO pedido = new PedidoDTO();
                pedido.setProductos(mViewModel.getMutableLiveDataDTO().getValue());
                pedido.setDetalle(binding.detalle.getText().toString());
            mViewModel.confirmarPedido(pedido);
//            Intent intent = new Intent(getContext(), MainActivity.class);
//            startActivity(intent);
            }
        });
        mViewModel.recuperarProductos(getArguments());

        return binding.getRoot();
    }
}
