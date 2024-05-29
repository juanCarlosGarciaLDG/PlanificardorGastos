package com.example.proyectoprogramacion2;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ControlPresupuesto extends VBox implements GastoHandler{
    private final Label mensajeNoGastos;
    private List<Gasto> gastos;
    private VBox gastosLista;
    private List<Gasto> gastosFiltrados;
    private ListadoGastos listadoGastos;
    private List<Filtro> categorias;
    private  double presupuesto;
    private double presupuestoDisponible;
    private double presupuestoGastado;
    private Label presupuestoDis;
    private Label presupuestoGas;
    private Arc arc;
    private Label porcentajeDisponibleLabel;

    private Stage stage;
    private boolean filtro;
    public ControlPresupuesto(int presupuesto, Stage stage) {
        this.stage = stage;
        this.presupuesto =presupuesto;
        this.presupuestoDisponible = presupuesto;
        this.presupuestoGastado = 0;
        this.gastos = new ArrayList<>(); // Inicialización de la lista gastos
        this.gastosFiltrados = new ArrayList<>(); // Inicialización de la lista gastosFiltrados
        this.filtro = false;

        // Crear el arc de la gráfica
        arc = new Arc(200, 200, 80, 80, 90, 0);
        arc.setType(ArcType.OPEN);
        arc.setFill(Color.WHITE);
        arc.setStroke(Color.LIGHTGRAY);
        arc.setStrokeWidth(10);

        // Contenedor para la gráfica
        StackPane graficaArc = new StackPane();
        graficaArc.getChildren().add(arc);

        this.porcentajeDisponibleLabel = new Label();
        graficaArc.getChildren().add(porcentajeDisponibleLabel);


        // Establecer las propiedades de la VBox
        VBox tituloBox = new VBox();
        tituloBox.setStyle("-fx-background-color: skyblue;");
        tituloBox.setSpacing(10);
        tituloBox.setPadding(new Insets(20));
        Label titleLabel = new Label("PLANIFICADOR DE GASTOS");
        titleLabel.setAlignment(Pos.TOP_CENTER);
        titleLabel.setStyle("-fx-text-fill: white;");
        tituloBox.getChildren().add(titleLabel);

        // VBox con los primeros elementos
        VBox elementos1 = new VBox();
        elementos1.setAlignment(Pos.CENTER_LEFT);
        elementos1.setSpacing(10);
        elementos1.setPadding(new Insets(20));

        // Botón de reinicio
        Button botonReinicio = new Button("REINICIAR APP");
        botonReinicio.setPrefHeight(20);
        botonReinicio.setPrefWidth(200);
        botonReinicio.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-background-radius: 15;");
        botonReinicio.setOnAction(e -> reiniciarApp(stage));
        elementos1.getChildren().add(botonReinicio);

        Label presupuestoLabel = new Label("Presupuesto: $" + presupuesto);
        presupuestoLabel.setAlignment(Pos.CENTER_LEFT);
        elementos1.getChildren().add(presupuestoLabel);


         this.presupuestoDis = new Label("Disponible: $" + presupuestoDisponible);
        presupuestoDis.setAlignment(Pos.CENTER_LEFT);
        elementos1.getChildren().add(presupuestoDis);

         this.presupuestoGas = new Label("Gastado: $" + presupuestoGastado);
        presupuestoGas.setAlignment(Pos.CENTER_LEFT);
        elementos1.getChildren().add(presupuestoGas);

        // HBox para los elementos
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(graficaArc, elementos1);

        // VBox filtrar gastos
        Filtro filtroGastos = new Filtro(gastos,this);
        VBox listadoGastosBox = new VBox();
        listadoGastosBox.setSpacing(10);
        listadoGastosBox.setPadding(new Insets(20));
        listadoGastosBox.setAlignment(Pos.TOP_LEFT);

        //listadoGastosBox.getChildren().add(new Label("Gastos:"));
        VBox vboxButton = new VBox();
        InputStream inputStream = getClass().getResourceAsStream("/img/nuevo-gasto.png");
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);

        // Ajustar el tamaño del ImageView
        imageView.setFitWidth(40);  // Ajusta el ancho según sea necesario
        imageView.setFitHeight(40); // Ajusta la altura según sea necesario
        imageView.setPreserveRatio(true);

        // Crear el Button y establecer la imagen como su contenido
        Button nuevoGastoButton = new Button();
        nuevoGastoButton.setGraphic(imageView);

        // Establecer el tamaño del Button
        nuevoGastoButton.setMinWidth(50);  // Ancho mínimo del botón
        nuevoGastoButton.setMinHeight(50); // Altura mínima del botón
        nuevoGastoButton.setMaxWidth(50);  // Ancho máximo del botón
        nuevoGastoButton.setMaxHeight(50); // Altura máxima del botón

        // Asignar el evento al botón
        nuevoGastoButton.setOnAction(e -> mostrarModal(null));
        nuevoGastoButton.setAlignment(Pos.CENTER_RIGHT);
        vboxButton.getChildren().add(nuevoGastoButton);
        // Agregar el botón al contenedor
        //listadoGastosBox.getChildren().add(nuevoGastoButton);

        // Asignar al miembro de la clase gastosLista y agregar al VBox
        this.gastosLista = new VBox();
        this.gastosLista.setSpacing(10);
        this.mensajeNoGastos = new Label("No hay gastos aún");
        this.mensajeNoGastos.setPadding(new Insets(20));
        this.mensajeNoGastos.setStyle("-fx-font-size: 16px; -fx-text-fill: grey;");
        gastosLista.getChildren().add(mensajeNoGastos);

        //listadoGastosBox.getChildren().addAll(this.gastosLista,nuevoGastoButton);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(this.gastosLista);
        scrollPane.setFitToWidth(true); // Para que el contenido se ajuste al ancho del ScrollPane

        listadoGastosBox.getChildren().addAll(this.gastosLista,scrollPane,vboxButton);
        vboxButton.setAlignment(Pos.CENTER_RIGHT);

        // Mostrar si hay gastos
        listadoGastos = new ListadoGastos(gastos, gastosFiltrados, filtro);
        // Añadir todo a la escena
        getChildren().addAll(tituloBox, hbox, filtroGastos, listadoGastosBox);
        tituloBox.setAlignment(Pos.CENTER);
        filtroGastos.setAlignment(Pos.CENTER);
        listadoGastosBox.setAlignment(Pos.CENTER);

        // Llamar a actualizarListado para inicializar la lista de gastos
        actualizarListado();


    }
    private void mostrarModal(Gasto gastoEditar) {
        Modal modal = new Modal(stage, (GastoHandler) this, gastoEditar);
        modal.showAndWait();
    }

   public void actualizarPresupuesto() {
       // Calcular el presupuesto gastado
       presupuestoGastado = gastos.stream().mapToDouble(Gasto::getCantidad).sum();
       // Calcular el presupuesto disponible
       presupuestoDisponible = presupuesto - presupuestoGastado;
       double porcentajeDisponible = (presupuestoDisponible / presupuesto) * 100;
       // Actualizar etiquetas
       presupuestoDis.setText("Disponible: $" + presupuestoDisponible);
       presupuestoGas.setText("Gastado: $" + presupuestoGastado);
       porcentajeDisponibleLabel.setText(String.format(" Disponible: %.2f%%", porcentajeDisponible));
       // Actualizar la gráfica del arc
       double porcentajeDisponible2 = (presupuestoDisponible / presupuesto) * 360;
       arc.setLength(-porcentajeDisponible2);
   }

    private void reiniciarApp(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            NuevoPresupuesto nuevoPresupuesto = new NuevoPresupuesto(
                    presupuesto -> {
                        System.out.println("Presupuesto: " + presupuesto);
                        ControlPresupuesto controlPresupuesto = new ControlPresupuesto(presupuesto, stage );
                        Scene nuevaEscena = new Scene(controlPresupuesto, 600, 400);
                        stage.setScene(nuevaEscena);
                    },
                    isValid -> System.out.println("Presupuesto válido: " + isValid)
            );
            ((VBox) root).getChildren().add(nuevoPresupuesto);
            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardarGasto(Gasto gasto) {
        if (gasto != null) {
            if (gasto.getId() == null) {
                gasto.setId(String.valueOf(System.currentTimeMillis())); // Generar un ID simple basado en el tiempo actual
                gastos.add(gasto);
            } else {
                for (int i = 0; i < gastos.size(); i++) {
                    if (gastos.get(i).getId().equals(gasto.getId())) {
                        gastos.set(i, gasto);
                        break;
                    }
                }
            }
            actualizarListado();
        }
    }
    public void actualizarListado() {
        actualizarListado(gastos);
    }

    public void actualizarListado(List<Gasto> listaGastos) {
        gastosLista.getChildren().clear();
        if (listaGastos.isEmpty()) {
            gastosLista.getChildren().add(mensajeNoGastos);
        } else {
            for (Gasto gasto : listaGastos) {
                agregarGasto(gasto);
            }
        }
        actualizarPresupuesto();
    }

    private void agregarGasto(Gasto gasto) {
        if (gasto.getCantidad() > presupuestoDisponible) {
            // Mostrar mensaje de notificación
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("Gasto excede el presupuesto disponible");
            alert.setContentText("El gasto que intenta agregar supera el presupuesto disponible. Por favor, ajuste su gasto.");

            alert.showAndWait();
            return; // No agregar el gasto
        } else {


            // Crear etiquetas para cada parte del gasto
            Label categoriaLabel = new Label(gasto.getCategoria());
            categoriaLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #888888;");
            Label nombreLabel = new Label(gasto.getNombre());
            nombreLabel.setStyle("-fx-font-size: 16px;");
            Label fechaLabel = new Label("agregado el :" + gasto.getFecha());
            fechaLabel.setStyle("-fx-text-fill: #aaaaaa;");
            Label cantidadLabel = new Label("$" + gasto.getCantidad());
            cantidadLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #000000;");
            cantidadLabel.setAlignment(Pos.CENTER_RIGHT);
            HBox.setHgrow(cantidadLabel, Priority.ALWAYS); // Permitir que la cantidad ocupe el espacio restante
            // Establecer estilos si es necesario
            categoriaLabel.setStyle("-fx-font-weight: bold;");
            cantidadLabel.setStyle("-fx-alignment: CENTER-RIGHT;");

            //crear imagenes
            ImageView imagenGasto = new ImageView(new Image(getClass().getResourceAsStream(gasto.getImagen())));
            imagenGasto.setFitWidth(30);
            imagenGasto.setFitHeight(30);

            // Crear contenedor horizontal para la categoría y el nombre del gasto
            VBox categoriaNombreBox = new VBox(10);
            categoriaNombreBox.getChildren().addAll(categoriaLabel, nombreLabel, fechaLabel);
            categoriaNombreBox.setAlignment(Pos.CENTER_LEFT);

            // Crear contenedor vertical para la fecha y la cantidad
            HBox CantidadBox = new HBox(5);
            CantidadBox.getChildren().addAll(cantidadLabel);
            CantidadBox.setAlignment(Pos.TOP_RIGHT);

            // Crear contenedor horizontal principal para alinear todo correctamente
            HBox gastoBox = new HBox(20);
            gastoBox.setSpacing(20);
            //button editar
            Button editarButton = new Button("Editar");
            editarButton.getStyleClass().add("editar-button");
            editarButton.setOnAction(e -> mostrarModal(gasto)); // Lógica para editar el gasto

            Button eliminarButton = new Button("Eliminar");
            eliminarButton.getStyleClass().add("eliminar-button");
            eliminarButton.setOnAction(e -> eliminarGasto(gasto));

            gastoBox.getChildren().addAll(editarButton, imagenGasto, categoriaLabel, nombreLabel, fechaLabel, cantidadLabel, eliminarButton);

            // Agregar el contenedor principal a la lista de gastos
            gastosLista.getChildren().add(gastoBox);
            gastoBox.setAlignment(Pos.CENTER);
            CantidadBox.setAlignment(Pos.CENTER_RIGHT);

            gastoBox.setOnMouseEntered(event -> {
                double middleX = gastoBox.getBoundsInLocal().getWidth() / 2;
                if (event.getX() < middleX) {
                    editarButton.setVisible(true);
                    eliminarButton.setVisible(false);
                } else {
                    editarButton.setVisible(false);
                    eliminarButton.setVisible(true);
                }
            });

            // Ocultar los botones cuando el cursor sale del HBox
            gastoBox.setOnMouseExited(event -> {
                editarButton.setVisible(false);
                eliminarButton.setVisible(false);
            });
            presupuestoDisponible -= gasto.getCantidad();

            // Actualizar la información del presupuesto
            actualizarPresupuesto();
        }
    }

    @Override
    public void editarGasto(Gasto gasto) {
        guardarGasto(gasto);
    }

    @Override
    public void eliminarGasto(Gasto gasto) {
        gastos.remove(gasto);
        actualizarListado();

    }
    public VBox getGastosLista() {
        return this.gastosLista;
    }

    public Label getMensajeNoGastos() {
        return this.mensajeNoGastos;
    }
}
