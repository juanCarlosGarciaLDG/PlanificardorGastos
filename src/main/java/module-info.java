module com.example.proyectoprogramacion2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.example.proyectoprogramacion2 to javafx.fxml;
    exports com.example.proyectoprogramacion2;
}