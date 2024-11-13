package com.example.replica;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorProductos extends RecyclerView.Adapter<AdaptadorProductos.ViewHolder> {

    private final List<Producto> listaProductos;
    private final AdaptadorListener listener;

    public AdaptadorProductos(List<Producto> listaProductos, AdaptadorListener listener) {
        this.listaProductos = listaProductos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_producto, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto producto = listaProductos.get(position);

        holder.tvNombre.setText(producto.getNombre());
        holder.tvPrecio.setText("Precio: $" + producto.getPrecio());
        holder.tvCantidad.setText("Cantidad: " + producto.getCantidad());

        holder.cvProducto.setOnClickListener(v -> listener.onEditItemClick(producto));

        holder.btnBorrar.setOnClickListener(v -> listener.onDeleteItemClick(producto));

        // Configuración del botón de vender
        holder.btnVender.setOnClickListener(v -> {
            if (producto.getCantidad() > 0) {
                producto.setCantidad(producto.getCantidad() - 1);
                listener.onVenderItemClick(producto); // Notificar el cambio al listener para actualizar la base de datos
                notifyItemChanged(position); // Actualizar la vista
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvProducto;
        TextView tvNombre;
        TextView tvPrecio;
        TextView tvCantidad;
        Button btnVender;
        Button btnBorrar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvProducto = itemView.findViewById(R.id.cvProducto);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            btnVender = itemView.findViewById(R.id.btnVender);
            btnBorrar = itemView.findViewById(R.id.btnBorrar);
        }
    }
}
