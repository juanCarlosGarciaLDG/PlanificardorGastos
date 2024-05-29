package com.example.proyectoprogramacion2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.text.DecimalFormat;
import java.util.function.Consumer;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.function.Consumer;

public class NuevoPresupuesto extends VBox {
    private Label mensajeLabel;
    private TextField presupuestoField;
    private Button agregarButton;
    private Consumer<Integer> onPresupuestoAdded;

    public NuevoPresupuesto(Consumer<Integer> onPresupuestoAdded, Consumer<Boolean> setIsValidPresupuesto) {
        super();
        this.onPresupuestoAdded = onPresupuestoAdded;
        // encabezado para planificar gastos
        Label enunciado = new Label("PLANIFICADOR DE GASTOS ");
        enunciado.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
        enunciado.setAlignment(Pos.CENTER);
        // fondo azul para el encabezado
        VBox encabezadoFondo = new VBox(enunciado);
        encabezadoFondo.setStyle("-fx-background-color: #1976D2;");
        encabezadoFondo.setAlignment(Pos.CENTER);
        encabezadoFondo.setPadding(new Insets(20));
        encabezadoFondo.setPrefWidth(600);
        encabezadoFondo.setPrefHeight(100);

        //contenedor principal con el formulario
        Label enunciadoLabel = new Label("DEFINIR PRESUPUESTO");
        enunciadoLabel.setTextFill(Color.BLUE);
        enunciadoLabel.setStyle("-fx-font-size: 18px;");

        mensajeLabel = new Label();
        mensajeLabel.setStyle("-fx-text-fill: red");// Color rojo para mensajes de error

        presupuestoField = new TextField("0");
        presupuestoField.setStyle("-fx-background-color: #E0E0E0; -fx-border-color: transparent;");
        presupuestoField.setMaxWidth(400);// Ancho máximo para el campo de texto
        presupuestoField.setAlignment(Pos.CENTER);

        //para que el campo solo acepte numeros enteros
        DecimalFormat decimalFormat = new DecimalFormat("#");
        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().isEmpty()) {
                return change;
            }
            try {
                Integer.parseInt(change.getControlNewText());
                return change;
            } catch (NumberFormatException e) {
                return null; // Rechaza el cambio si no es un número entero
            }
        });
        presupuestoField.setTextFormatter(textFormatter);

        agregarButton = new Button("AÑADIR");
        agregarButton.setStyle("-fx-background-color: #1976D2; -fx-text-fill: white; -fx-background-radius: 5;");
        agregarButton.setPrefWidth(400); // Establece el ancho del botón
        agregarButton.setPrefHeight(40); // Establece la altura del botón

        //        agregarButton.getStyleClass().add("boton-agregar"); // Estilo CSS para el botón

        agregarButton.setOnAction(e -> {
            String presupuestoText = presupuestoField.getText();
            if (presupuestoText.isEmpty() || Integer.parseInt(presupuestoText) < 0) {
                mensajeLabel.setText("No es un presupuesto válido");
                setIsValidPresupuesto.accept(false);
            } else {
                onPresupuestoAdded.accept(Integer.parseInt(presupuestoText)); // Llamada al callback
                setIsValidPresupuesto.accept(true);
            }
        });
        VBox formulario = new VBox(10, enunciadoLabel, presupuestoField, agregarButton, mensajeLabel);
        formulario.setAlignment(Pos.CENTER);
        formulario.setStyle("-fx-background-color: white; -fx-padding: 20px; -fx-border-radius: 10; -fx-background-radius: 10;");
        formulario.setMaxWidth(500);

        VBox.setVgrow(formulario,Priority.ALWAYS);
        //fondo formulario
        VBox contenedorPrincipal = new VBox(20, encabezadoFondo, formulario);
        contenedorPrincipal.setAlignment(Pos.TOP_CENTER);
        contenedorPrincipal.setPadding(new Insets(50, 0, 0, 0));
        contenedorPrincipal.setStyle("-fx-background-color: #F5F5F5;"); // Color de fondo del contenedor principal
        contenedorPrincipal.setPrefHeight(600);

        VBox.setVgrow(contenedorPrincipal,Priority.ALWAYS);
        getChildren().addAll(contenedorPrincipal);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));


    }

    public NuevoPresupuesto() {

    }

    public NuevoPresupuesto(Object o) {
    }
}

