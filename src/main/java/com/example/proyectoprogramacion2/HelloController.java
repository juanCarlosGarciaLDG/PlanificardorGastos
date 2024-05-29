package com.example.proyectoprogramacion2;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloController extends VBox {
    private final ControlPresupuesto controlPresupuesto;
    private final NuevoPresupuesto nuevoPresupuesto;

    // Constructor por defecto
    public HelloController() {
        super();
        controlPresupuesto = null; // O inicializa con un valor adecuado si es necesario
        nuevoPresupuesto = null; // O inicializa con un valor adecuado si es necesario
    }

    public HelloController(int presupuesto, boolean isValidPresupuesto) {
        super();

        // Inicializar los componentes hijos
        Stage stage = null;
        controlPresupuesto = new ControlPresupuesto(presupuesto, stage);
        nuevoPresupuesto = new NuevoPresupuesto();

        // Agregar los componentes al layout
        getChildren().add(new Label("Planificador de Gastos"));
        if (isValidPresupuesto) {
            getChildren().add(controlPresupuesto);
        } else {
            getChildren().add(nuevoPresupuesto);
        }
    }


}





