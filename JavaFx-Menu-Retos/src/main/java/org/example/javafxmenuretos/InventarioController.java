package org.example.javafxmenuretos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.javafxmenuretos.dao.ProductoDAO;

public class InventarioController {

    private final ProductoDAO productoDAO = new ProductoDAO();

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TableColumn<Producto, String> columnaCodigo;

    @FXML
    private TableColumn<Producto, String> columnaNombre;

    @FXML
    private TableColumn<Producto, Double> columnaPrecio;

    @FXML
    private TableColumn<Producto, Integer> columnaCantidad;

    @FXML
    protected void volverMenu(ActionEvent event) {

        Navegacion.cambiarVista(
                (javafx.scene.Node) event.getSource(),
                "menu-view.fxml",
                "Menú de Retos JavaFX"
        );
    }

    @FXML
    public void initialize() {

        columnaCodigo.setCellValueFactory(
                new PropertyValueFactory<>("codigo")
        );

        columnaNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );

        columnaPrecio.setCellValueFactory(
                new PropertyValueFactory<>("precio")
        );

        columnaCantidad.setCellValueFactory(
                new PropertyValueFactory<>("cantidad")
        );
    }

    @FXML
    protected void guardarProducto(ActionEvent event) {

        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String cantidadTexto = txtCantidad.getText().trim();

        if (codigo.isEmpty()
                || nombre.isEmpty()
                || precioTexto.isEmpty()
                || cantidadTexto.isEmpty()) {

            mostrarAlerta(
                    "Datos incompletos",
                    "Debe completar todos los campos."
            );

            return;
        }

        double precio;
        int cantidad;

        try {

            precio = Double.parseDouble(precioTexto);
            cantidad = Integer.parseInt(cantidadTexto);

        } catch (NumberFormatException e) {

            mostrarAlerta(
                    "Datos incorrectos",
                    "El precio y la cantidad deben ser valores numéricos."
            );

            return;
        }

        if (precio <= 0) {

            mostrarAlerta(
                    "Precio incorrecto",
                    "El precio debe ser mayor que 0."
            );

            return;
        }

        if (cantidad < 0) {

            mostrarAlerta(
                    "Cantidad incorrecta",
                    "La cantidad no puede ser negativa."
            );

            return;
        }

        Producto existente =
                productoDAO.buscarPorCodigo(codigo);

        if (existente != null) {

            mostrarAlerta(
                    "Código repetido",
                    "Ya existe un producto con ese código."
            );

            return;
        }

        Producto producto =
                new Producto(
                        codigo,
                        nombre,
                        precio,
                        cantidad
                );

        productoDAO.agregarProducto(producto);

        tablaProductos.getItems().add(producto);

        limpiarCampos();
    }

    @FXML
    protected void buscarProducto(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {

            String codigo =
                    txtBuscar.getText().trim();

            if (codigo.isEmpty()) {

                mostrarAlerta(
                        "Búsqueda",
                        "Ingrese un código para buscar."
                );

                return;
            }

            Producto producto =
                    productoDAO.buscarPorCodigo(codigo);

            if (producto != null) {

                tablaProductos
                        .getSelectionModel()
                        .select(producto);

                tablaProductos.scrollTo(producto);

                Alert alerta =
                        new Alert(
                                Alert.AlertType.INFORMATION
                        );

                alerta.setTitle(
                        "Producto encontrado"
                );

                alerta.setHeaderText(null);

                alerta.setContentText(
                        "Código: " + producto.getCodigo()
                                + "\nNombre: " + producto.getNombre()
                                + "\nPrecio: C$ " + producto.getPrecio()
                                + "\nCantidad: " + producto.getCantidad()
                );

                alerta.showAndWait();

            } else {

                mostrarAlerta(
                        "Producto no encontrado",
                        "No existe un producto con ese código."
                );
            }
        }
    }

    private void limpiarCampos() {

        txtCodigo.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtCantidad.clear();

        txtCodigo.requestFocus();
    }

    private void mostrarAlerta(
            String titulo,
            String mensaje
    ) {

        Alert alerta =
                new Alert(
                        Alert.AlertType.WARNING
                );

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }
}