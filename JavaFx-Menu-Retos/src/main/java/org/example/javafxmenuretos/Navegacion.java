package org.example.javafxmenuretos;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navegacion {

    public static void cambiarVista(
            Node componente,
            String archivo,
            String titulo
    ) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    Navegacion.class.getResource(archivo)
            );

            Scene scene = new Scene(loader.load());

            Stage ventana =
                    (Stage) componente.getScene().getWindow();

            ventana.setScene(scene);
            ventana.setTitle(titulo);
            ventana.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}