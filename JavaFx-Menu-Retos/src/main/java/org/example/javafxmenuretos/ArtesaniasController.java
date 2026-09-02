package org.example.javafxmenuretos;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.javafxmenuretos.dao.ArtesaniaDAO;

import java.io.InputStream;

public class ArtesaniasController {

    private final ArtesaniaDAO artesaniaDAO = new ArtesaniaDAO();

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCategoria;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtStock;

    @FXML
    private TextField txtImagen;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TableView<Artesania> tablaArtesanias;

    @FXML
    private TableColumn<Artesania, String> colImagen;

    @FXML
    private TableColumn<Artesania, String> colNombre;

    @FXML
    private TableColumn<Artesania, String> colCategoria;

    @FXML
    private TableColumn<Artesania, Number> colPrecio;

    @FXML
    private TableColumn<Artesania, Number> colStock;

    @FXML
    private Label lblCantidad;

    @FXML
    private Label lblEstado;


    // BOTÓN PARA VOLVER AL MENÚ

    @FXML
    private void volverMenu(ActionEvent event) {

        Navegacion.cambiarVista(
                (Node) event.getSource(),
                "menu-view.fxml",
                "Menú de Retos JavaFX"
        );
    }


    @FXML
    public void initialize() {

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

        colStock.setCellValueFactory(
                dato -> new SimpleIntegerProperty(
                        dato.getValue().getStock()
                )
        );

        colImagen.setCellValueFactory(
                dato -> new SimpleStringProperty(
                        dato.getValue().getImagen()
                )
        );

        configurarColumnaImagen();
        actualizarTabla();

        lblEstado.setText(
                "Catálogo de artesanías cargado."
        );
    }


    private void configurarColumnaImagen() {

        colImagen.setCellFactory(
                columna -> new TableCell<>() {

                    private final ImageView imageView =
                            new ImageView();

                    @Override
                    protected void updateItem(
                            String nombreImagen,
                            boolean vacio
                    ) {

                        super.updateItem(
                                nombreImagen,
                                vacio
                        );

                        if (vacio
                                || nombreImagen == null
                                || nombreImagen.trim().isEmpty()) {

                            setGraphic(null);
                            setText(null);
                            return;
                        }

                        String ruta =
                                nombreImagen.startsWith("/")
                                        ? nombreImagen
                                        : "/" + nombreImagen;

                        try (
                                InputStream stream =
                                        getClass()
                                                .getResourceAsStream(ruta)
                        ) {

                            if (stream == null) {

                                setGraphic(null);
                                setText("Sin imagen");
                                return;
                            }

                            Image imagen =
                                    new Image(stream);

                            imageView.setImage(imagen);
                            imageView.setFitWidth(55);
                            imageView.setFitHeight(55);
                            imageView.setPreserveRatio(true);

                            setText(null);
                            setGraphic(imageView);

                        } catch (Exception e) {

                            setGraphic(null);
                            setText("Sin imagen");
                        }
                    }
                }
        );
    }


    @FXML
    private void nuevo() {

        limpiarCampos();

        lblEstado.setText(
                "Formulario listo para registrar una nueva artesanía."
        );
    }


    @FXML
    private void guardar() {

        String nombre =
                txtNombre.getText().trim();

        String categoria =
                txtCategoria.getText().trim();

        String precioTexto =
                txtPrecio.getText().trim();

        String stockTexto =
                txtStock.getText().trim();

        String imagen =
                txtImagen.getText().trim();


        if (nombre.isEmpty()
                || categoria.isEmpty()
                || precioTexto.isEmpty()
                || stockTexto.isEmpty()) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Campos incompletos",
                    "Debe completar nombre, categoría, precio y stock."
            );

            return;
        }


        try {

            double precio =
                    Double.parseDouble(precioTexto);

            int stock =
                    Integer.parseInt(stockTexto);


            if (precio < 0 || stock < 0) {

                mostrarAlerta(
                        Alert.AlertType.WARNING,
                        "Datos inválidos",
                        "El precio y el stock no pueden ser negativos."
                );

                return;
            }


            Artesania artesania =
                    new Artesania(
                            nombre,
                            categoria,
                            precio,
                            stock,
                            imagen
                    );


            artesaniaDAO.agregarArtesania(
                    artesania
            );

            actualizarTabla();
            limpiarCampos();

            lblEstado.setText(
                    "Artesanía guardada correctamente: "
                            + nombre
            );


        } catch (NumberFormatException e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Datos inválidos",
                    "Precio debe ser un número. Stock debe ser un número entero."
            );
        }
    }


    @FXML
    private void buscar() {

        String texto =
                txtBuscar.getText()
                        .trim()
                        .toLowerCase();


        if (texto.isEmpty()) {

            actualizarTabla();

            lblEstado.setText(
                    "Mostrando todas las artesanías."
            );

            return;
        }


        ObservableList<Artesania> resultados =
                FXCollections.observableArrayList();


        for (
                Artesania artesania :
                artesaniaDAO.listarArtesanias()
        ) {

            boolean coincideNombre =
                    artesania.getNombre()
                            .toLowerCase()
                            .contains(texto);


            boolean coincideCategoria =
                    artesania.getCategoria()
                            .toLowerCase()
                            .contains(texto);


            if (
                    coincideNombre
                            || coincideCategoria
            ) {

                resultados.add(
                        artesania
                );
            }
        }


        tablaArtesanias.setItems(
                resultados
        );


        lblCantidad.setText(
                "Artesanías encontradas: "
                        + resultados.size()
        );


        lblEstado.setText(
                "Búsqueda realizada: "
                        + resultados.size()
                        + " resultado(s)."
        );
    }


    @FXML
    private void mostrarCatalogo() {

        actualizarTabla();

        lblEstado.setText(
                "Mostrando el catálogo completo de artesanías."
        );
    }


    @FXML
    private void registrarVenta() {

        Artesania seleccionada =
                tablaArtesanias
                        .getSelectionModel()
                        .getSelectedItem();


        if (seleccionada == null) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Venta",
                    "Seleccione una artesanía de la tabla para registrar la venta."
            );

            return;
        }


        if (seleccionada.getStock() <= 0) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Sin existencias",
                    "La artesanía seleccionada no tiene unidades disponibles."
            );

            return;
        }


        seleccionada.setStock(
                seleccionada.getStock() - 1
        );


        actualizarTabla();


        lblEstado.setText(
                "Venta registrada: "
                        + seleccionada.getNombre()
                        + ". Stock restante: "
                        + seleccionada.getStock()
        );


        mostrarAlerta(
                Alert.AlertType.INFORMATION,
                "Venta registrada",
                "Se vendió una unidad de: "
                        + seleccionada.getNombre()
        );
    }


    @FXML
    private void mostrarAyuda() {

        mostrarAlerta(
                Alert.AlertType.INFORMATION,
                "Ayuda del sistema",
                "Use Nuevo para limpiar el formulario.\n"
                        + "Complete los campos y presione Guardar para registrar una artesanía.\n"
                        + "Escriba un nombre o categoría y presione Buscar.\n"
                        + "Seleccione un producto de la tabla y use Ventas para reducir una unidad del stock."
        );
    }


    private void actualizarTabla() {

        ObservableList<Artesania> lista =
                FXCollections.observableArrayList(
                        artesaniaDAO.listarArtesanias()
                );


        tablaArtesanias.setItems(
                lista
        );


        lblCantidad.setText(
                "Artesanías registradas: "
                        + lista.size()
        );
    }


    private void limpiarCampos() {

        txtNombre.clear();
        txtCategoria.clear();
        txtPrecio.clear();
        txtStock.clear();
        txtImagen.clear();

        txtNombre.requestFocus();
    }


    private void mostrarAlerta(
            Alert.AlertType tipo,
            String titulo,
            String mensaje
    ) {

        Alert alerta =
                new Alert(tipo);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }
}