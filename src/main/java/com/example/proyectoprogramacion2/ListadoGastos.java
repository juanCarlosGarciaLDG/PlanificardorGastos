package com.example.proyectoprogramacion2;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.List;

public class ListadoGastos extends VBox {
    private List<Gasto> gastos;
    private List<Gasto> gastosFiltrados;
    private boolean filtro;
    private GastoHandler gastoHandler;

    public ListadoGastos(List<Gasto> gastos, GastoHandler gastoHandler, boolean filtro, List<Gasto> gastosFiltrados) {
        this.gastos = gastos;
        this.gastoHandler = gastoHandler;
        this.filtro = filtro;
        this.gastosFiltrados = gastosFiltrados;

        actualizarListado();
    }



    public ListadoGastos(List<Gasto> gastos, List<Gasto> gastosFiltrados, boolean filtro) {
        this.gastos = gastos;
        this.gastosFiltrados = gastosFiltrados;
        this.filtro = filtro;

        actualizarListado();
    }


    void actualizarListado() {
        getChildren().clear();

        Label titulo = new Label();
        if (filtro) {
            titulo.setText(gastosFiltrados.isEmpty() ? "No hay gastos en esta categoría" : "Gastos");
            getChildren().add(titulo);
            gastosFiltrados.forEach(this::agregarGasto);
        } else {
            titulo.setText(gastos.isEmpty() ? "No hay gastos aún" : "Gastos");
            getChildren().add(titulo);
            gastos.forEach(this::agregarGasto);
        }
    }

    private void agregarGasto(Gasto gasto) {
        HBox gastoBox = new HBox();
        gastoBox.setSpacing(10);

        Label nombreGasto = new Label(gasto.getNombre());
        Label cantidadGasto = new Label("$" + gasto.getCantidad());

        Button editarButton = new Button("Editar");
        editarButton.setOnAction(e -> gastoHandler.editarGasto(gasto));

        Button eliminarButton = new Button("Eliminar");
        eliminarButton.setOnAction(e -> gastoHandler.eliminarGasto(gasto));

        gastoBox.getChildren().addAll(nombreGasto, cantidadGasto, editarButton, eliminarButton);
        getChildren().add(gastoBox);
    }
}
