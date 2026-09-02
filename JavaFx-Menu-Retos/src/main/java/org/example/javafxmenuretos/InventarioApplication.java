package org.example.javafxmenuretos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InventarioApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader =
                new FXMLLoader(
                        InventarioApplication.class.getResource(
                                "inventario-view.fxml"
                        )
                );

        Scene scene =
                new Scene(
                        fxmlLoader.load()
                );

        stage.setTitle(
                "Inventario de Pulpería"
        );

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}