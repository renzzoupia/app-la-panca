package com.braxly.lapancaproject.models;

import java.io.Serializable;

public class Reserva implements Serializable {
    private String reseId;
    private String reseMesaId;
    private String reseClieId;
    private String reseFecha;
    private String reseHora;
    private String resePersonas;
    private String mesaNumero;

    public Reserva(String reseId, String reseMesaId, String reseClieId, String reseFecha, String reseHora, String resePersonas, String mesaNumero) {
        this.reseId = reseId;
        this.reseMesaId = reseMesaId;
        this.reseClieId = reseClieId;
        this.reseFecha = reseFecha;
        this.reseHora = reseHora;
        this.resePersonas = resePersonas;
        this.mesaNumero = mesaNumero;
    }

    public String getReseId() {
        return reseId;
    }

    public String getReseMesaId() {
        return reseMesaId;
    }

    public String getReseClieId() {
        return reseClieId;
    }

    public String getReseFecha() {
        return reseFecha;
    }

    public String getReseHora() {
        return reseHora;
    }

    public String getResePersonas() {
        return resePersonas;
    }

    public String getMesaNumero() {
        return mesaNumero;
    }
}
