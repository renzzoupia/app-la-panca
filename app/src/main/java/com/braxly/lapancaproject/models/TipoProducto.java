package com.braxly.lapancaproject.models;

import java.io.Serializable;

public class TipoProducto implements Serializable {
    private String tipoProductoNombre;
    private int tipoProductoImg;
    private String tipoProductoId;

    public TipoProducto(String tipoProductoNombre, int tipoProductoImg, String tipoProductoId) {
        this.tipoProductoNombre = tipoProductoNombre;
        this.tipoProductoImg = tipoProductoImg;
        this.tipoProductoId = tipoProductoId;
    }

    public String getTipoProductoNombre() {
        return tipoProductoNombre;
    }

    public void setTipoProductoNombre(String tipoProductoNombre) {
        this.tipoProductoNombre = tipoProductoNombre;
    }

    public int getTipoProductoImg() {
        return tipoProductoImg;
    }

    public void setTipoProductoImg(int tipoProductoImg) {
        this.tipoProductoImg = tipoProductoImg;
    }

    public String getTipoProductoId() {
        return tipoProductoId;
    }

    public void setTipoProductoId(String tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }
}
