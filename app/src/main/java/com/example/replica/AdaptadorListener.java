package com.example.replica;

public interface AdaptadorListener {
    void onEditItemClick(Producto producto);
    void onDeleteItemClick(Producto producto);
    void onVenderItemClick(Producto producto); // Nuevo método para el botón vender
}
