package com.example.proyectoprogramacion2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class Filtro extends HBox {
    private MenuButton menuFiltro;
    private ControlPresupuesto controlPresupuesto;
    private ObservableList<String> categoriasSeleccionadas;
    private List<Gasto> listaGastos; // Variable para almacenar la lista de gastos

    public Filtro(List<Gasto> listaGastos,ControlPresupuesto controlPresupuesto) {
        this.listaGastos = listaGastos; // Inicializar la lista de gastos
        this.controlPresupuesto = controlPresupuesto;
        Label tipoGasto = new Label("Filtrar Gastos");
        getChildren().add(tipoGasto);

        menuFiltro = new MenuButton("Todas las categorias");
        CheckMenuItem menuFiltro1 = new CheckMenuItem("Ahorro");
        CheckMenuItem menuFiltro2 = new CheckMenuItem("Comida");
        CheckMenuItem menuFiltro3 = new CheckMenuItem("Casa");
        //CheckMenuItem menuFiltro4 = new CheckMenuItem("Gastos Variados");
        CheckMenuItem menuFiltro5 = new CheckMenuItem("Ocio");
        CheckMenuItem menuFiltro6 = new CheckMenuItem("Salud");
        CheckMenuItem menuFiltro7 = new CheckMenuItem("Suscripciones");

        menuFiltro.getItems().addAll(menuFiltro1, menuFiltro2, menuFiltro3, menuFiltro5, menuFiltro6, menuFiltro7);

        // Inicializar lista de categorías seleccionadas
        categoriasSeleccionadas = FXCollections.observableArrayList();

        // Evento para mostrar la categoría seleccionada
        menuFiltro.getItems().forEach(item -> {
            ((CheckMenuItem) item).setOnAction(actionEvent -> {
                if (((CheckMenuItem) item).isSelected()) {
                    categoriasSeleccionadas.add(item.getText());
                } else {
                    categoriasSeleccionadas.remove(item.getText());
                }
                filtrarGastos();
            });
        });

        getChildren().add(menuFiltro);
    }

    private void filtrarGastos() {
        // Aquí implementa la lógica para filtrar los gastos según las categorías seleccionadas
        List<Gasto> gastosFiltrados = new ArrayList<>();
        for (Gasto gasto : listaGastos) {
            if (categoriasSeleccionadas.isEmpty() || categoriasSeleccionadas.contains(gasto.getCategoria())) {
                gastosFiltrados.add(gasto);
            }
        }
        // Actualizar la vista con los gastos filtrados
        controlPresupuesto.actualizarListado(gastosFiltrados);
    }
}
