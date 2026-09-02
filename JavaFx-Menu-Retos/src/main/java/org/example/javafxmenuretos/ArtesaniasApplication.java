package org.example.javafxmenuretos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ArtesaniasApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                ArtesaniasApplication.class.getResource(
                        "artesanias-view.fxml"
                )
        );

        Scene scene = new Scene(loader.load());

        stage.setTitle("Reto 3 - Tienda de Artesanías");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}