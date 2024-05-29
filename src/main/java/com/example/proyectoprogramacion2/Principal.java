package com.example.proyectoprogramacion2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class Principal extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new VBox(), 600, 400);


        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));

        // Crear y agregar el componente NuevoPresupuesto
        NuevoPresupuesto nuevoPresupuesto = new NuevoPresupuesto(
                presupuesto -> {
                    //System.out.println("Presupuesto: " + presupuesto);
                    // Crear la nueva escena y establecerla en el stage
                    ControlPresupuesto controlPresupuesto = new ControlPresupuesto(presupuesto, stage);
                    Scene nuevaEscena = new Scene(controlPresupuesto, 600, 400);
                    stage.setScene(nuevaEscena);
                },
                isValid -> System.out.println(" " + isValid)

        );
        ((VBox) root).getChildren().add(nuevoPresupuesto);

        scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("estilos.css").toExternalForm());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("PLANIFICADOR DE GASTOS");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}
