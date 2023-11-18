package com.braxly.lapancaproject.models;

import java.io.Serializable;

public class DetallePedido implements Serializable {
    private String depeId;
    private String dedePediId;
    private String dedeProdId;
    private String dedeCantidad;
    private String dedeSubtotal;
    private String depeProdNombre;
    private String dedeProdPrecio;

    public DetallePedido(String depeId, String dedePediId, String dedeProdId, String dedeCantidad, String dedeSubtotal, String depeProdNombre, String dedeProdPrecio) {
        this.depeId = depeId;
        this.dedePediId = dedePediId;
        this.dedeProdId = dedeProdId;
        this.dedeCantidad = dedeCantidad;
        this.dedeSubtotal = dedeSubtotal;
        this.depeProdNombre = depeProdNombre;
        this.dedeProdPrecio = dedeProdPrecio;
    }

    public String getDepeId() {
        return depeId;
    }

    public String getDedePediId() {
        return dedePediId;
    }

    public String getDedeProdId() {
        return dedeProdId;
    }

    public String getDedeCantidad() {
        return dedeCantidad;
    }

    public String getDedeSubtotal() {
        return dedeSubtotal;
    }

    public String getDepeProdNombre() {
        return depeProdNombre;
    }

    public String getDedeProdPrecio() {
        return dedeProdPrecio;
    }
}
