package uamv.edu.ni.distribuidoraelgueguense;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductoDAO {

    private final ObservableList<Producto> productos;

    public ProductoDAO() {
        productos = FXCollections.observableArrayList();

        productos.add(new Producto(
                "P001",
                "Arroz 5 lb",
                "Granos básicos",
                95.00,
                30
        ));

        productos.add(new Producto(
                "P002",
                "Aceite vegetal",
                "Abarrotes",
                78.50,
                20
        ));

        productos.add(new Producto(
                "P003",
                "Jabón líquido",
                "Limpieza",
                120.00,
                12
        ));
    }

    public ObservableList<Producto> listarProductos() {
        return productos;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
    }

    public boolean existeCodigo(String codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo().equalsIgnoreCase(codigo)) {
                return true;
            }
        }

        return false;
    }
}