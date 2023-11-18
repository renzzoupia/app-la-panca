package com.braxly.lapancaproject.models;

import java.io.Serializable;

public class Pedido implements Serializable{
    private String pediId;
    private String pediMesaId;
    private String pediClieId;
    private String pediTipoPedido;
    private String pediFecha;
    private String pediTotal;

    public Pedido(String pediId, String pediMesaId, String pediClieId, String pediTipoPedido, String pediFecha, String pediTotal) {
        this.pediId = pediId;
        this.pediMesaId = pediMesaId;
        this.pediClieId = pediClieId;
        this.pediTipoPedido = pediTipoPedido;
        this.pediFecha = pediFecha;
        this.pediTotal = pediTotal;
    }

    public String getPediId() {
        return pediId;
    }

    public String getPediMesaId() {
        return pediMesaId;
    }

    public String getPediClieId() {
        return pediClieId;
    }

    public String getPediTipoPedido() {
        return pediTipoPedido;
    }

    public String getPediFecha() {
        return pediFecha;
    }

    public String getPediTotal() {
        return pediTotal;
    }
}
