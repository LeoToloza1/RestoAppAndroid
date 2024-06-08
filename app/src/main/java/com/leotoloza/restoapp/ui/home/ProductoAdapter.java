package com.leotoloza.restoapp.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.leotoloza.restoapp.Models.Producto;
import com.leotoloza.restoapp.Models.Restaurante;
import com.leotoloza.restoapp.R;
import com.leotoloza.restoapp.request.ApiClient;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

private List<Producto> lista;
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
    holder.precio.setText("Precio: $" + producto.getPrecio()+"");
    String urlBase = ApiClient.URLBASE + "uploads/";
    String urlFoto = urlBase + producto.getImagenUrl();
    Glide.with(context)
            .load(urlFoto)
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(holder.imagen);

    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.clickDetalle(producto);
            }
        }
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

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView descripcion;
    TextView nombre;
    TextView precio;
    ImageView imagen;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        nombre = itemView.findViewById(R.id.nombre_prod);
        descripcion = itemView.findViewById(R.id.descripcion_prod);
        precio = itemView.findViewById(R.id.precio_prod);
        imagen = itemView.findViewById(R.id.imagen_prod);
    }
}

public interface ClickListener {
    void clickDetalle(Producto producto);
}

public void setClickListener(ProductoAdapter.ClickListener listener) {
    clickListener = listener;
}
}


