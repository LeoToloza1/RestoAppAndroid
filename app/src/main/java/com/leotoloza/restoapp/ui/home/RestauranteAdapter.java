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
import com.leotoloza.restoapp.Models.Restaurante;
import com.leotoloza.restoapp.R;
import com.leotoloza.restoapp.request.ApiClient;

import java.util.List;

public class RestauranteAdapter extends RecyclerView.Adapter<RestauranteAdapter.ViewHolder> {

    private List<Restaurante> lista;
    private Context context;
    private ClickListener clickListener;

    public RestauranteAdapter(List<Restaurante> listaRestaurante, Context context) {
        this.lista = listaRestaurante;
        this.context = context;
    }

    @NonNull
    @Override
    public RestauranteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurante, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestauranteAdapter.ViewHolder holder, int position) {
        Restaurante restaurante = lista.get(position);
        holder.direccion.setText("Dirección: " + restaurante.getDireccion_restaurante());
        holder.nombre.setText("Nombre: " + restaurante.getNombre_restaurante());
        holder.rubro.setText("Rubro: " + restaurante.getRubro().getNombre_rubro());
        String urlBase = ApiClient.URLBASE + "uploads/";
        String urlFoto = urlBase + restaurante.getLogo_url();
        Glide.with(context)
                .load(urlFoto)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imagen);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.clickDetalle(restaurante);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }

    public void updateData(List<Restaurante> newList) {
        lista = newList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView direccion;
        TextView nombre;
        TextView rubro;
        ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.DireccionRestaurante);
            nombre = itemView.findViewById(R.id.NombreRestaurante);
            rubro = itemView.findViewById(R.id.rubro);
            imagen = itemView.findViewById(R.id.LogoRestaurante);
        }
    }

    public interface ClickListener {
        void clickDetalle(Restaurante restaurante);
    }

    public void setClickListener(ClickListener listener) {
        clickListener = listener;
    }
}
