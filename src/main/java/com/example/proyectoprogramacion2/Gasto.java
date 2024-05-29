package com.example.proyectoprogramacion2;

public class Gasto {
    private String nombre;
    private double cantidad;
    private String categoria;
    private String fecha;
    private String imagen;
    private String id;

    // Rutas de las imágenes para cada categoría de gasto
    private static final String ICONO_AHORRO = "/img/icono_ahorro.png";
    private static final String ICONO_CASA = "/img/icono_casa.png";
    private static final String ICONO_COMIDA = "/img/icono_comida.png";
    //private static final String ICONO_GASTOS = "/img/icono_gastos.png";
    private static final String ICONO_OCIO = "/img/icono_ocio.png";
    private static final String ICONO_SALUD = "/img/icono_salud.png";
    private static final String ICONO_SUSCRIPCIONES = "/img/icono_suscripciones.png";

    // Constructor
    public Gasto(String id, String nombre, double cantidad, String categoria, String fecha) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.fecha = fecha;
        // Asignar la imagen correspondiente a la categoría
        switch (categoria.toLowerCase()) {
            case "ahorro":
                this.imagen = ICONO_AHORRO;
                break;
            case "casa":
                this.imagen = ICONO_CASA;
                break;
            case "comida":
                this.imagen = ICONO_COMIDA;
                break;
           /* case "gastos":
                this.imagen = ICONO_GASTOS;
                break;*/
            case "ocio":
                this.imagen = ICONO_OCIO;
                break;
            case "salud":
                this.imagen = ICONO_SALUD;
                break;
            case "suscripciones":
                this.imagen = ICONO_SUSCRIPCIONES;
                break;
            default:
                // Si la categoría no coincide, se asigna una imagen genérica
                this.imagen = "/img/icono_default.svg";
                break;
        }
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public double getCantidad() { return cantidad; }
    public String getCategoria() { return categoria; }
    public String getFecha() { return fecha; }
    public String getImagen() { return imagen; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setId(String id) { this.id = id; }
    public void setImagen(String imagen) { this.imagen = imagen; }


}
