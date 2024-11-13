package com.example.replica;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Producto.class}, version = 2) // Incrementa la versión
public abstract class DBPrueba extends RoomDatabase {
    public abstract DaoProducto daoProducto();
}
