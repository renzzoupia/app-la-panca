package com.braxly.lapancaproject.models;

import java.io.Serializable;

public class Mesa implements Serializable {
    private String mesaId;
    private String mesaRestId;
    private String mesaNumero;
    private String mesaCantidadPersonas;
    private String mesaReferenciaUbicacion;
    private String mesaActivo;
    private String mesaEstado;

    public Mesa(String mesaId, String mesaRestId, String mesaNumero, String mesaCantidadPersonas, String mesaReferenciaUbicacion, String mesaActivo, String mesaEstado) {
        this.mesaId = mesaId;
        this.mesaRestId = mesaRestId;
        this.mesaNumero = mesaNumero;
        this.mesaCantidadPersonas = mesaCantidadPersonas;
        this.mesaReferenciaUbicacion = mesaReferenciaUbicacion;
        this.mesaActivo = mesaActivo;
        this.mesaEstado = mesaEstado;
    }

    public String getMesaId() {
        return mesaId;
    }

    public String getMesaRestId() {
        return mesaRestId;
    }

    public String getMesaNumero() {
        return mesaNumero;
    }

    public String getMesaCantidadPersonas() {
        return mesaCantidadPersonas;
    }

    public String getMesaReferenciaUbicacion() {
        return mesaReferenciaUbicacion;
    }

    public String getMesaActivo() {
        return mesaActivo;
    }

    public String getMesaEstado() {
        return mesaEstado;
    }
}
