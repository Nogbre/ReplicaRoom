package com.example.replica;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoProducto {

    @Query("SELECT * FROM productos")
    List<Producto> obtenerProductos();

    @Insert
    void agregarProducto(Producto producto);

    // Método para actualizar solo precio y cantidad usando el id como referencia
    @Query("UPDATE productos SET precio = :precio, cantidad = :cantidad WHERE id = :id")
    void actualizarPrecioYCantidad(int id, double precio, int cantidad);




    @Delete
    void borrarProducto(Producto producto);

    @Update
    void actualizarProductoCompleto(Producto producto); // Método para actualizar todo el producto
}
