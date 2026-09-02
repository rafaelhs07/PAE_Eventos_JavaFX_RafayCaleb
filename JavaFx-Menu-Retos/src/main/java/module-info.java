module org.example.javafxmenuretos {

    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.javafxmenuretos to javafx.fxml;

    exports org.example.javafxmenuretos;
}