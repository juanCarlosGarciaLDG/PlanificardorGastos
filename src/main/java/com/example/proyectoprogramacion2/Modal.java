package com.example.proyectoprogramacion2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Modal extends Stage {
    private TextField tipoField;
    private TextField nombreField;
    private TextField cantidadField;
    private ComboBox<String> categoriaComboBox;
    private Label mensajeLabel;
    private Gasto gastoEditar;
    private GastoHandler gastoHandler;

    public Modal(Stage owner, GastoHandler gastoHandler, Gasto gastoEditar) {
        this.gastoHandler = gastoHandler;
        this.gastoEditar = gastoEditar;

        initModality(Modality.APPLICATION_MODAL);
        initOwner(owner);

        VBox vbox = new VBox(10);
        vbox.getStyleClass().add("vbox");

        vbox.setPadding(new Insets(20));
       // vbox.setStyle("-fx-background-color: transparent; -fx-border-radius: 10; -fx-background-radius: 10;");

        // mensajeLabel = new Label();
        //vbox.getChildren().add(mensajeLabel);

        Label tituloLabel = new Label(gastoEditar != null ? "EDITAR GASTO" : "NUEVO GASTO");
        tituloLabel.getStyleClass().add("modal-title");
        tituloLabel.setAlignment(Pos.CENTER);
        vbox.getChildren().add(tituloLabel);


        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label nombreLabel = new Label("Nombre Gasto:");
        nombreLabel.getStyleClass().add("modal-label");
        gridPane.add(nombreLabel, 0, 0);

        nombreField = new TextField();
        nombreField.getStyleClass().add("modal-text-field");
        gridPane.add(nombreField, 0, 1);

        Label cantidadLabel = new Label("Cantidad:");
        cantidadLabel.getStyleClass().add("modal-label");
        gridPane.add(cantidadLabel, 0, 2);

        cantidadField = new TextField();
        cantidadField.getStyleClass().add("modal-text-field");
        gridPane.add(cantidadField, 0, 3);

        Label categoriaLabel = new Label("Categoría:");
        categoriaLabel.getStyleClass().add("modal-label");
        gridPane.add(categoriaLabel, 0, 4);

        categoriaComboBox = new ComboBox<>();
        categoriaComboBox.getItems().addAll("Ahorro", "Comida", "Casa", "Ocio", "Salud", "Suscripciones");
        categoriaComboBox.getStyleClass().add("modal-combo-box");
        gridPane.add(categoriaComboBox, 0, 5);

        GridPane.setColumnSpan(nombreField, 2);
        GridPane.setColumnSpan(cantidadField, 2);
        GridPane.setColumnSpan(categoriaComboBox, 2);


        vbox.getChildren().add(gridPane);

        Button guardarButton = new Button(gastoEditar != null ? "Editar Gasto" : "Añadir Gasto");
        guardarButton.getStyleClass().add("modal-button");
        guardarButton.setOnAction(e -> handleSubmit());
        guardarButton.setMaxWidth(Double.MAX_VALUE); // Estirar el botón al ancho disponible

        HBox buttonBox = new HBox(guardarButton);
        buttonBox.setAlignment(Pos.CENTER);

        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        //buttonBox.setMaxWidth(Double.MAX_VALUE);
        vbox.getChildren().add(buttonBox);

        mensajeLabel = new Label();
        mensajeLabel.getStyleClass().add("modal-error-label");
        vbox.getChildren().add(mensajeLabel);

        if (gastoEditar != null) {
            llenarCampos();
        }

        Scene scene = new Scene(vbox);
        scene.getStylesheets().add(getClass().getResource("/com/example/proyectoprogramacion2/estilos.css").toExternalForm());

        setScene(scene);
        setTitle(gastoEditar != null ? "Editar Gasto" : "Nuevo Gasto");
        setWidth(300);
        setHeight(400);
    }

    private void llenarCampos() {
        nombreField.setText(gastoEditar.getNombre());
        cantidadField.setText(String.valueOf(gastoEditar.getCantidad()));
        categoriaComboBox.setValue(gastoEditar.getCategoria());

    }

    private void handleSubmit() {

        String nombre = nombreField.getText();
        String cantidad = cantidadField.getText();
        String categoria = categoriaComboBox.getValue();

        if (nombre.isEmpty() || cantidad.isEmpty() || categoria == null ) {
            mensajeLabel.setText("Todos los campos son obligatorios");
            return;
        }

        try {
            double cantidadDouble = Double.parseDouble(cantidad);
            String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (gastoEditar == null) {
                // Nuevo gasto
                Gasto nuevoGasto = new Gasto(null, nombre, cantidadDouble, categoria, fecha);

                gastoHandler.guardarGasto(nuevoGasto);
            } else {
                // Editar gasto
                gastoEditar.setNombre(nombre);
                gastoEditar.setCantidad(cantidadDouble);
                gastoEditar.setCategoria(categoria);

                gastoEditar.setFecha(fecha);
                 // Asume que hay un setter para el tipo en la clase Gasto
                gastoHandler.guardarGasto(gastoEditar);
            }

            close();
        } catch (NumberFormatException e) {
            mensajeLabel.setText("La cantidad debe ser un número válido");
        }
    }
}
