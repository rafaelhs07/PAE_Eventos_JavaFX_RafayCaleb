package uamv.edu.ni.distribuidoraelgueguense;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InventarioApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                InventarioApplication.class.getResource(
                        "inventario-view.fxml"
                )
        );

        Scene scene = new Scene(loader.load());

        stage.setTitle("Distribuidora El Güegüense - Inventario");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}