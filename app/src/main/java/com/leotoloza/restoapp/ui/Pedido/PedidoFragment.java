package com.leotoloza.restoapp.ui.Pedido;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.leotoloza.restoapp.Models.Envio;
import com.leotoloza.restoapp.Models.Pedido;
import com.leotoloza.restoapp.Models.PedidoDTO;
import com.leotoloza.restoapp.Servicios.Dialogo;
import com.leotoloza.restoapp.Servicios.ToastPesonalizado;
import com.leotoloza.restoapp.databinding.FragmentPedidoBinding;

import java.util.List;

public class PedidoFragment extends Fragment {

    private FragmentPedidoBinding binding;
    private PedidoViewModel pedidoViewModel;
    private PedidoAdapter pedidoAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pedidoViewModel = new ViewModelProvider(this).get(PedidoViewModel.class);
        binding = FragmentPedidoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        pedidoViewModel.getMensajeMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("salida", "onChanged: "+s);
                ToastPesonalizado.mostrarMensaje(getContext(), s);
            }
        });
        pedidoViewModel.getListaPedidosMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Pedido>>() {
            @Override
            public void onChanged(List<Pedido> pedidos) {
                Log.d("salida", "onChanged: " + pedidos.toString());
            }
        });
        pedidoViewModel.getPedidoMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Pedido>() {
            @Override
            public void onChanged(Pedido pedido) {
                Log.d("salida", "onChanged: " + pedido.toString());
            }
        });
        pedidoViewModel.getMutableEnvio().observe(getViewLifecycleOwner(), new Observer<Envio>() {
            @Override
            public void onChanged(Envio envio) {
                Dialogo.mostrarDialogoConfirmacion(getContext(),
                        "Envio a cargo de: " + envio.getRepartidor().getNombre_repartidor(),
                        "El envio tiene un costo de $" + envio.getCosto()+"\n"
                        +"y el pago es de: $"+envio.getPedido().getTotal(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            ToastPesonalizado.mostrarMensaje(getContext(), "Envio en camino \n "+"Gracias por su compra!");
                            }
                        });
            }
        });
        pedidoViewModel.getMutableLiveDataDTO().observe(getViewLifecycleOwner(), new Observer<List<PedidoDTO>>() {
            @Override
            public void onChanged(List<PedidoDTO> pedidoDTOS) {
                binding.recyclerViewPedidos.setLayoutManager(new LinearLayoutManager(getContext()));
                pedidoAdapter = new PedidoAdapter(pedidoDTOS, getContext(), new PedidoAdapter.OnPedidoClickListener() {
                    @Override
                    public void onPedidoClick(int position) {
                        PedidoDTO pedido = pedidoAdapter.getPedidoList().get(position);
                        int pedidoId = pedido.getId();
                        pedidoViewModel.solicitarEnvio(pedidoId);
                    }
                });
                binding.recyclerViewPedidos.setAdapter(pedidoAdapter);

            }
        });
        pedidoViewModel.verPedidos();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}