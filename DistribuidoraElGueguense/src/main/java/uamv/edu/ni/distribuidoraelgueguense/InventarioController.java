package uamv.edu.ni.distribuidoraelgueguense;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.util.Optional;

public class InventarioController {

    private final ProductoDAO productoDAO = new ProductoDAO();

    private Producto productoEnEdicion;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCategoria;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtExistencia;

    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TableColumn<Producto, String> colCodigo;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, String> colCategoria;

    @FXML
    private TableColumn<Producto, Number> colPrecio;

    @FXML
    private TableColumn<Producto, Number> colExistencia;

    @FXML
    private Label lblResultado;

    @FXML
    public void initialize() {
        colCodigo.setCellValueFactory(
                dato -> new SimpleStringProperty(
                        dato.getValue().getCodigo()
                )
        );

        colNombre.setCellValueFactory(
                dato -> new SimpleStringProperty(
                        dato.getValue().getNombre()
                )
        );

        colCategoria.setCellValueFactory(
                dato -> new SimpleStringProperty(
                        dato.getValue().getCategoria()
                )
        );

        colPrecio.setCellValueFactory(
                dato -> new SimpleDoubleProperty(
                        dato.getValue().getPrecio()
                )
        );

        colExistencia.setCellValueFactory(
                dato -> new SimpleIntegerProperty(
                        dato.getValue().getExistencia()
                )
        );

        tablaProductos.setItems(
                productoDAO.listarProductos()
        );

        configurarMenuContextual();

        lblResultado.setText(
                "Sistema listo. Productos cargados: "
                        + productoDAO.listarProductos().size()
        );

        configurarAtajosTeclado();
    }

    private void configurarAtajosTeclado() {
        Platform.runLater(() -> {
            Scene escena = tablaProductos.getScene();

            if (escena == null) {
                return;
            }

            escena.getAccelerators().put(
                    new KeyCodeCombination(
                            KeyCode.N,
                            KeyCombination.CONTROL_DOWN
                    ),
                    this::nuevoProducto
            );

            escena.getAccelerators().put(
                    new KeyCodeCombination(
                            KeyCode.G,
                            KeyCombination.CONTROL_DOWN
                    ),
                    this::guardarProducto
            );

            escena.getAccelerators().put(
                    new KeyCodeCombination(
                            KeyCode.Q,
                            KeyCombination.CONTROL_DOWN
                    ),
                    this::salir
            );
        });
    }

    private void configurarMenuContextual() {
        MenuItem itemEditar = new MenuItem("Editar producto");
        MenuItem itemEliminar = new MenuItem("Eliminar producto");
        MenuItem itemDetalle = new MenuItem("Ver detalle");

        itemEditar.setOnAction(event -> editarProducto());
        itemEliminar.setOnAction(event -> eliminarProducto());
        itemDetalle.setOnAction(event -> verDetalle());

        ContextMenu menuContextual = new ContextMenu(
                itemEditar,
                itemEliminar,
                itemDetalle
        );

        tablaProductos.setContextMenu(menuContextual);
    }

    @FXML
    private void nuevoProducto() {
        limpiarCampos();
        productoEnEdicion = null;

        lblResultado.setText(
                "Formulario listo para registrar un nuevo producto."
        );
    }

    @FXML
    private void limpiarFormulario() {
        limpiarCampos();
        productoEnEdicion = null;

        lblResultado.setText("Campos limpiados correctamente.");
    }

    @FXML
    private void guardarProducto() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String existenciaTexto = txtExistencia.getText().trim();

        if (codigo.isEmpty()
                || nombre.isEmpty()
                || categoria.isEmpty()
                || precioTexto.isEmpty()
                || existenciaTexto.isEmpty()) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Campos incompletos",
                    "Debe completar todos los datos del producto."
            );

            lblResultado.setText(
                    "No se pudo guardar: complete todos los campos."
            );
            return;
        }

        try {
            double precio = Double.parseDouble(precioTexto);
            int existencia = Integer.parseInt(existenciaTexto);

            if (precio < 0 || existencia < 0) {
                mostrarAlerta(
                        Alert.AlertType.WARNING,
                        "Datos inválidos",
                        "El precio y la existencia no pueden ser negativos."
                );

                lblResultado.setText(
                        "No se pudo guardar: precio o existencia inválidos."
                );
                return;
            }

            if (productoEnEdicion == null) {
                if (productoDAO.existeCodigo(codigo)) {
                    mostrarAlerta(
                            Alert.AlertType.WARNING,
                            "Código duplicado",
                            "Ya existe un producto registrado con el código: "
                                    + codigo
                    );

                    lblResultado.setText(
                            "No se pudo guardar: el código ya existe."
                    );
                    return;
                }

                Producto nuevoProducto = new Producto(
                        codigo,
                        nombre,
                        categoria,
                        precio,
                        existencia
                );

                productoDAO.agregarProducto(nuevoProducto);

                lblResultado.setText(
                        "Producto guardado correctamente: " + nombre
                );

            } else {
                productoEnEdicion.setCodigo(codigo);
                productoEnEdicion.setNombre(nombre);
                productoEnEdicion.setCategoria(categoria);
                productoEnEdicion.setPrecio(precio);
                productoEnEdicion.setExistencia(existencia);

                tablaProductos.refresh();

                lblResultado.setText(
                        "Producto actualizado correctamente: " + nombre
                );
            }

            limpiarCampos();
            productoEnEdicion = null;

        } catch (NumberFormatException e) {
            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Formato incorrecto",
                    "El precio debe ser numérico y la existencia debe ser un número entero."
            );

            lblResultado.setText(
                    "No se pudo guardar: revise precio y existencia."
            );
        }
    }

    @FXML
    private void editarProducto() {
        Producto seleccionado = tablaProductos
                .getSelectionModel()
                .getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Editar producto",
                    "Seleccione un producto de la tabla antes de editar."
            );

            lblResultado.setText(
                    "No se puede editar: no hay producto seleccionado."
            );
            return;
        }

        productoEnEdicion = seleccionado;

        txtCodigo.setText(seleccionado.getCodigo());
        txtNombre.setText(seleccionado.getNombre());
        txtCategoria.setText(seleccionado.getCategoria());
        txtPrecio.setText(
                String.format("%.2f", seleccionado.getPrecio())
        );
        txtExistencia.setText(
                String.valueOf(seleccionado.getExistencia())
        );

        lblResultado.setText(
                "Editando producto: " + seleccionado.getNombre()
                        + ". Modifique los datos y presione Guardar."
        );

        txtCodigo.requestFocus();
    }

    @FXML
    private void eliminarProducto() {
        Producto seleccionado = tablaProductos
                .getSelectionModel()
                .getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Eliminar producto",
                    "Seleccione un producto de la tabla antes de eliminar."
            );

            lblResultado.setText(
                    "No se puede eliminar: no hay producto seleccionado."
            );
            return;
        }

        Alert confirmacion = new Alert(
                Alert.AlertType.CONFIRMATION
        );

        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(
                "¿Desea eliminar el producto \""
                        + seleccionado.getNombre()
                        + "\"?"
        );

        Optional<ButtonType> respuesta =
                confirmacion.showAndWait();

        if (respuesta.isPresent()
                && respuesta.get() == ButtonType.OK) {

            productoDAO.eliminarProducto(seleccionado);

            if (seleccionado == productoEnEdicion) {
                productoEnEdicion = null;
                limpiarCampos();
            }

            lblResultado.setText(
                    "Producto eliminado correctamente: "
                            + seleccionado.getNombre()
            );

        } else {
            lblResultado.setText(
                    "Eliminación cancelada."
            );
        }
    }

    @FXML
    private void verDetalle() {
        Producto seleccionado = tablaProductos
                .getSelectionModel()
                .getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Ver detalle",
                    "Seleccione un producto de la tabla para ver su información."
            );

            lblResultado.setText(
                    "No se puede mostrar detalle: no hay producto seleccionado."
            );
            return;
        }

        Alert detalle = new Alert(
                Alert.AlertType.INFORMATION
        );

        detalle.setTitle("Detalle del producto");
        detalle.setHeaderText(
                "Información de: " + seleccionado.getNombre()
        );

        detalle.setContentText(
                "Código: " + seleccionado.getCodigo()
                        + "\nNombre: " + seleccionado.getNombre()
                        + "\nCategoría: " + seleccionado.getCategoria()
                        + "\nPrecio: C$ "
                        + String.format("%.2f", seleccionado.getPrecio())
                        + "\nExistencia: "
                        + seleccionado.getExistencia()
        );

        detalle.showAndWait();

        lblResultado.setText(
                "Detalle mostrado: " + seleccionado.getNombre()
        );
    }

    @FXML
    private void mostrarAcercaDe() {
        Alert acercaDe = new Alert(
                Alert.AlertType.INFORMATION
        );

        acercaDe.setTitle("Acerca de");
        acercaDe.setHeaderText(
                "Distribuidora El Güegüense"
        );

        acercaDe.setContentText(
                "Sistema de control de inventario.\n\n"
                        + "Versión: 1.0\n"
                        + "Tecnologías: Java 21, JavaFX y FXML.\n\n"
                        + "Autores:\n"
                        + "- Caleb\n"
                        + "- Escriba el nombre del segundo autor"
        );

        acercaDe.showAndWait();

        lblResultado.setText(
                "Se mostró la información de la aplicación."
        );
    }

    @FXML
    private void salir() {
        Alert confirmacion = new Alert(
                Alert.AlertType.CONFIRMATION
        );

        confirmacion.setTitle("Salir");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(
                "¿Está seguro de que desea salir de la aplicación?"
        );

        Optional<ButtonType> respuesta =
                confirmacion.showAndWait();

        if (respuesta.isPresent()
                && respuesta.get() == ButtonType.OK) {

            Stage ventana = (Stage) tablaProductos
                    .getScene()
                    .getWindow();

            ventana.close();
        } else {
            lblResultado.setText("Salida cancelada.");
        }
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtNombre.clear();
        txtCategoria.clear();
        txtPrecio.clear();
        txtExistencia.clear();

        txtCodigo.requestFocus();
    }

    private void mostrarAlerta(
            Alert.AlertType tipo,
            String titulo,
            String mensaje
    ) {
        Alert alerta = new Alert(tipo);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }
}