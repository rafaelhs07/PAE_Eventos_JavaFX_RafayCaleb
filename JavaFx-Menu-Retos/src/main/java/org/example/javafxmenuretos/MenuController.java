package org.example.javafxmenuretos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    protected void abrirReto1() {
        abrirVista(
                "inventario-view.fxml",
                "Reto 1 - Inventario de Pulpería"
        );
    }

    @FXML
    protected void abrirReto2() {
        abrirVista(
                "cafe-view.fxml",
                "Reto 2 - Recepción de Café"
        );
    }

    @FXML
    protected void abrirReto3() {
        System.out.println("Reto 3 pendiente");
    }

    private void abrirVista(String archivo, String titulo) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(archivo)
            );

            Scene scene = new Scene(loader.load());

            Stage ventana = new Stage();

            ventana.setTitle(titulo);
            ventana.setScene(scene);
            ventana.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}