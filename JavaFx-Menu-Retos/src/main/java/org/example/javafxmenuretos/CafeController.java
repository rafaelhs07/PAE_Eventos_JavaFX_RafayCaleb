package org.example.javafxmenuretos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CafeController {

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtProductor;

    @FXML
    private TextField txtMunicipio;

    @FXML
    private TextField txtPeso;

    @FXML
    private ComboBox<String> comboCalidad;

    @FXML
    private TableView<LoteCafe> tablaLotes;

    @FXML
    private TableColumn<LoteCafe, String> colCodigo;

    @FXML
    private TableColumn<LoteCafe, String> colProductor;

    @FXML
    private TableColumn<LoteCafe, String> colMunicipio;

    @FXML
    private TableColumn<LoteCafe, Double> colPeso;

    @FXML
    private TableColumn<LoteCafe, String> colCalidad;
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
        colCodigo.setCellValueFactory(
                new PropertyValueFactory<>("codigo")
        );

        colProductor.setCellValueFactory(
                new PropertyValueFactory<>("productor")
        );

        colMunicipio.setCellValueFactory(
                new PropertyValueFactory<>("municipio")
        );

        colPeso.setCellValueFactory(
                new PropertyValueFactory<>("peso")
        );

        colCalidad.setCellValueFactory(
                new PropertyValueFactory<>("calidad")
        );

        comboCalidad.getItems().addAll(
                "Alta",
                "Media",
                "Baja"
        );

        crearMenuContextual();
    }

    @FXML
    protected void guardarLote(ActionEvent event) {
        String codigo = txtCodigo.getText().trim();
        String productor = txtProductor.getText().trim();
        String municipio = txtMunicipio.getText().trim();
        String pesoTexto = txtPeso.getText().trim();
        String calidad = comboCalidad.getValue();

        if (codigo.isEmpty()
                || productor.isEmpty()
                || municipio.isEmpty()
                || pesoTexto.isEmpty()
                || calidad == null) {

            mostrarAlerta(
                    "Datos incompletos",
                    "Debe completar todos los campos."
            );
            return;
        }

        double peso;

        try {
            peso = Double.parseDouble(pesoTexto);
        } catch (NumberFormatException e) {
            mostrarAlerta(
                    "Peso incorrecto",
                    "El peso debe ser un valor numérico."
            );
            return;
        }

        if (peso <= 0) {
            mostrarAlerta(
                    "Peso inválido",
                    "El peso debe ser mayor que 0."
            );
            return;
        }

        LoteCafe lote = new LoteCafe(
                codigo,
                productor,
                municipio,
                peso,
                calidad
        );

        tablaLotes.getItems().add(lote);
        limpiarCampos();
    }

    @FXML
    protected void mostrarDetalles(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            LoteCafe lote =
                    tablaLotes.getSelectionModel().getSelectedItem();

            if (lote != null) {
                Alert alerta =
                        new Alert(Alert.AlertType.INFORMATION);

                alerta.setTitle("Detalles del lote");
                alerta.setHeaderText(null);
                alerta.setContentText(
                        "Código: " + lote.getCodigo()
                                + "\nProductor: " + lote.getProductor()
                                + "\nMunicipio: " + lote.getMunicipio()
                                + "\nPeso: " + lote.getPeso() + " kg"
                                + "\nCalidad: " + lote.getCalidad()
                );

                alerta.showAndWait();
            }
        }
    }

    private void crearMenuContextual() {
        MenuItem opcionEditar = new MenuItem("Editar");
        MenuItem opcionEliminar = new MenuItem("Eliminar");

        ContextMenu menu = new ContextMenu(
                opcionEditar,
                opcionEliminar
        );

        tablaLotes.setContextMenu(menu);

        opcionEditar.setOnAction(event -> editarLote());
        opcionEliminar.setOnAction(event -> eliminarLote());
    }

    private void editarLote() {
        LoteCafe loteSeleccionado =
                tablaLotes.getSelectionModel().getSelectedItem();

        if (loteSeleccionado == null) {
            mostrarAlerta(
                    "Editar lote",
                    "Seleccione un lote primero."
            );
            return;
        }

        txtCodigo.setText(loteSeleccionado.getCodigo());
        txtProductor.setText(loteSeleccionado.getProductor());
        txtMunicipio.setText(loteSeleccionado.getMunicipio());
        txtPeso.setText(String.valueOf(loteSeleccionado.getPeso()));
        comboCalidad.setValue(loteSeleccionado.getCalidad());

        tablaLotes.getItems().remove(loteSeleccionado);
    }

    private void eliminarLote() {
        LoteCafe lote =
                tablaLotes.getSelectionModel().getSelectedItem();

        if (lote == null) {
            mostrarAlerta(
                    "Eliminar lote",
                    "Seleccione un lote primero."
            );
            return;
        }

        Alert confirmacion =
                new Alert(Alert.AlertType.CONFIRMATION);

        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(
                "¿Está seguro de eliminar el lote "
                        + lote.getCodigo() + "?"
        );

        ButtonType respuesta =
                confirmacion.showAndWait()
                        .orElse(ButtonType.CANCEL);

        if (respuesta == ButtonType.OK) {
            tablaLotes.getItems().remove(lote);
        }
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtProductor.clear();
        txtMunicipio.clear();
        txtPeso.clear();
        comboCalidad.setValue(null);
        txtCodigo.requestFocus();
    }

    private void mostrarAlerta(
            String titulo,
            String mensaje
    ) {
        Alert alerta =
                new Alert(Alert.AlertType.WARNING);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}