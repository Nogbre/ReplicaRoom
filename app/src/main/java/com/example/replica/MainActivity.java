package com.example.replica;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdaptadorListener {

    private RecyclerView rvProductos;
    private AdaptadorProductos adaptador;
    private DBPrueba room;
    private FloatingActionButton fabAgregar;
    private Producto productoActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuración de RecyclerView y FloatingActionButton
        rvProductos = findViewById(R.id.rvProductos);
        fabAgregar = findViewById(R.id.fabAgregar);

        // Construcción de la instancia de Room
        room = Room.databaseBuilder(this, DBPrueba.class, "dbPruebas")
                .fallbackToDestructiveMigration() // Borra y recrea la base de datos si detecta cambios de esquema
                .allowMainThreadQueries() // Usar solo en desarrollo, no en producción
                .build();

        // Configuración del RecyclerView
        rvProductos.setLayoutManager(new LinearLayoutManager(this));
        cargarProductos();

        // Listener para el botón flotante de agregar producto
        fabAgregar.setOnClickListener(v -> mostrarDialogoAgregar());
    }

    private void cargarProductos() {
        List<Producto> listaProductos = room.daoProducto().obtenerProductos();
        adaptador = new AdaptadorProductos(new ArrayList<>(listaProductos), this);
        rvProductos.setAdapter(adaptador);
    }

    private void mostrarDialogoAgregar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogo_producto, null);
        builder.setView(dialogView);

        EditText etNombre = dialogView.findViewById(R.id.etNombre);
        EditText etPrecio = dialogView.findViewById(R.id.etPrecio);
        EditText etCantidad = dialogView.findViewById(R.id.etCantidad);
        Button btnGuardar = dialogView.findViewById(R.id.btnGuardar);

        AlertDialog dialog = builder.create();

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String precioStr = etPrecio.getText().toString().trim();
            String cantidadStr = etCantidad.getText().toString().trim();

            if (nombre.isEmpty() || precioStr.isEmpty() || cantidadStr.isEmpty()) {
                Toast.makeText(this, "DEBES LLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();
                return;
            }

            double precio = Double.parseDouble(precioStr);
            int cantidad = Integer.parseInt(cantidadStr);

            Producto nuevoProducto = new Producto(nombre, precio, cantidad);
            room.daoProducto().agregarProducto(nuevoProducto);

            cargarProductos();
            dialog.dismiss();
        });

        dialog.show();
    }


    @Override
    public void onEditItemClick(Producto producto) {
        productoActual = producto;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogo_producto, null);
        builder.setView(dialogView);

        EditText etNombre = dialogView.findViewById(R.id.etNombre);
        EditText etPrecio = dialogView.findViewById(R.id.etPrecio);
        EditText etCantidad = dialogView.findViewById(R.id.etCantidad);
        Button btnGuardar = dialogView.findViewById(R.id.btnGuardar);

        // Llenar campos con los datos actuales
        etNombre.setText(producto.getNombre());
        etPrecio.setText(String.valueOf(producto.getPrecio()));
        etCantidad.setText(String.valueOf(producto.getCantidad()));

        AlertDialog dialog = builder.create();

        btnGuardar.setOnClickListener(v -> {
            String nuevoNombre = etNombre.getText().toString().trim();
            String precioStr = etPrecio.getText().toString().trim();
            String cantidadStr = etCantidad.getText().toString().trim();

            if (nuevoNombre.isEmpty() || precioStr.isEmpty() || cantidadStr.isEmpty()) {
                Toast.makeText(this, "DEBES LLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();
                return;
            }

            double precio = Double.parseDouble(precioStr);
            int cantidad = Integer.parseInt(cantidadStr);

            productoActual.setNombre(nuevoNombre);
            productoActual.setPrecio(precio);
            productoActual.setCantidad(cantidad);

            // Usar el método actualizarProductoCompleto para actualizar todos los campos, incluyendo el nombre
            room.daoProducto().actualizarProductoCompleto(productoActual);

            cargarProductos();
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void onDeleteItemClick(Producto producto) {
        room.daoProducto().borrarProducto(producto); // Borrado usando el producto completo que incluye el ID
        cargarProductos();
    }

    @Override
    public void onVenderItemClick(Producto producto) {
        // Actualizar la cantidad en la base de datos
        room.daoProducto().actualizarPrecioYCantidad(producto.getId(), producto.getPrecio(), producto.getCantidad());
        cargarProductos(); // Recargar la lista para reflejar el cambio
    }

}
