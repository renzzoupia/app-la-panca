package com.braxly.lapancaproject.models;

import java.io.Serializable;

public class Cliente implements Serializable {
    private String clieId;
    private String clieUsuaId;
    private String clieNombres;
    private String clieApellidos;
    private String clieDni;
    private String clieCelular;
    private String clieCorreo;


    public Cliente(String clieId, String clieUsuaId, String clieNombres, String clieApellidos, String clieDni, String clieCelular, String clieCorreo) {
        this.clieId = clieId;
        this.clieUsuaId = clieUsuaId;
        this.clieNombres = clieNombres;
        this.clieApellidos = clieApellidos;
        this.clieDni = clieDni;
        this.clieCelular = clieCelular;
        this.clieCorreo = clieCorreo;
    }

    public String getClieId() {
        return clieId;
    }

    public String getClieUsuaId() {
        return clieUsuaId;
    }

    public String getClieNombres() {
        return clieNombres;
    }

    public String getClieApellidos() {
        return clieApellidos;
    }

    public String getClieDni() {
        return clieDni;
    }

    public String getClieCelular() {
        return clieCelular;
    }

    public String getClieCorreo() {
        return clieCorreo;
    }

}
