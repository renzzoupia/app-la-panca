package com.braxly.lapancaproject.models;

import java.io.Serializable;

public class Producto implements Serializable {
    private String prodId;
    private String prodTiprId;
    private String tiprNombre;
    private String prodNombre;
    private String prodDescripcion;
    private String prodStock;
    private Double prodPrecio;
    private String prodFoto;
    private int numberInCart = 0;
    public Producto(String prodId, String prodTiprId, String tiprNombre, String prodNombre, String prodDescripcion, String prodStock, Double prodPrecio, String prodFoto) {
        this.prodId = prodId;
        this.prodTiprId = prodTiprId;
        this.tiprNombre = tiprNombre;
        this.prodNombre = prodNombre;
        this.prodDescripcion = prodDescripcion;
        this.prodStock = prodStock;
        this.prodPrecio = prodPrecio;
        this.prodFoto = prodFoto;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdTiprId() {
        return prodTiprId;
    }

    public void setProdTiprId(String prodTiprId) {
        this.prodTiprId = prodTiprId;
    }

    public String getTiprNombre() {
        return tiprNombre;
    }

    public void setTiprNombre(String tiprNombre) {
        this.tiprNombre = tiprNombre;
    }

    public String getProdNombre() {
        return prodNombre;
    }

    public void setProdNombre(String prodNombre) {
        this.prodNombre = prodNombre;
    }

    public String getProdDescripcion() {
        return prodDescripcion;
    }

    public void setProdDescripcion(String prodDescripcion) {
        this.prodDescripcion = prodDescripcion;
    }

    public String getProdStock() {
        return prodStock;
    }

    public void setProdStock(String prodStock) {
        this.prodStock = prodStock;
    }

    public Double getProdPrecio() {
        return prodPrecio;
    }

    public void setProdPrecio(Double prodPrecio) {
        this.prodPrecio = prodPrecio;
    }

    public String getProdFoto() {
        return prodFoto;
    }

    public void setProdFoto(String prodFoto) {
        this.prodFoto = prodFoto;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }
}
