package org.example.javafxmenuretos.dao;

import org.example.javafxmenuretos.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    private final List<Producto> productos;

    public ProductoDAO() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public List<Producto> listarProductos() {
        return productos;
    }

    public Producto buscarPorCodigo(String codigo) {

        for (Producto producto : productos) {

            if (producto.getCodigo().equalsIgnoreCase(codigo)) {
                return producto;
            }
        }

        return null;
    }
}