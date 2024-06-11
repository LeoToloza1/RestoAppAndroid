package com.leotoloza.restoapp.ui.home;

import android.content.Context;
import android.os.ProxyFileDescriptorCallback;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.leotoloza.restoapp.Models.Producto;
import com.leotoloza.restoapp.Models.ProductoSeleccionado;
import com.leotoloza.restoapp.Models.Restaurante;
import com.leotoloza.restoapp.R;
import com.leotoloza.restoapp.request.ApiClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    private List<Producto> lista;
    private Map<Producto, Integer> seleccionados = new HashMap<>();
    private Context context;
    private ProductoAdapter.ClickListener clickListener;

    public ProductoAdapter(List<Producto> listaProducto, Context context) {
        this.lista = listaProducto;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ProductoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoAdapter.ViewHolder holder, int position) {
        Producto producto = lista.get(position);
        holder.descripcion.setText("Descripción: " + producto.getDescripcion());
        holder.nombre.setText("Nombre: " + producto.getNombre_producto());
        holder.precio.setText("Precio: $" + producto.getPrecio());
        String urlBase = ApiClient.URLBASE + "uploads/";
        String urlFoto = urlBase + producto.getImagenUrl();
        Glide.with(context)
                .load(urlFoto)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imagen);

        holder.itemView.setOnClickListener(view -> {
            if (clickListener != null) {
                clickListener.clickDetalle(producto);
            }
        });

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                String cantidadTexto = holder.cantidadProducto.getText().toString();
                int cantidad = cantidadTexto.isEmpty() ? 1 : Integer.parseInt(cantidadTexto);
                seleccionados.put(producto, cantidad);
                if (cantidadTexto.isEmpty()) {
                    holder.cantidadProducto.setText(String.valueOf(cantidad));
                }
            } else {
                seleccionados.remove(producto);
                holder.cantidadProducto.setText(""); // Reset the EditText
            }

            if (clickListener != null) {
                clickListener.clickCompra(producto);
            }
        });

        holder.cantidadProducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (holder.checkBox.isChecked()) {
                    String cantidadTexto = s.toString();
                    int cantidad = cantidadTexto.isEmpty() ? 1 : Integer.parseInt(cantidadTexto);
                    seleccionados.put(producto, cantidad);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }

    public void updateData(List<Producto> newList) {
        lista = newList;
        notifyDataSetChanged();
    }

    public Map<Producto, Integer> getSeleccionados() {
        return seleccionados;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView descripcion;
        TextView nombre;
        TextView precio;
        ImageView imagen;
        private CheckBox checkBox;
        private EditText cantidadProducto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre_prod);
            descripcion = itemView.findViewById(R.id.descripcion_prod);
            precio = itemView.findViewById(R.id.precio_prod);
            imagen = itemView.findViewById(R.id.imagen_prod);
            checkBox = itemView.findViewById(R.id.checkbox_prod);
            cantidadProducto = itemView.findViewById(R.id.CantidaProductos);
        }
    }

    public interface ClickListener {
        void clickDetalle(Producto producto);

        void clickCompra(Producto producto);
    }

    public void setClickListener(ProductoAdapter.ClickListener listener) {
        clickListener = listener;
    }
}