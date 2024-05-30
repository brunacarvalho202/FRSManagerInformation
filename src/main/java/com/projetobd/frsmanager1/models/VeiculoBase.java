package com.projetobd.frsmanager1.models;

public class VeiculoBase {
    private String placa; //primary key
    private String ATPV;
    private String categoria;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getATPV() {
        return ATPV;
    }

    public void setATPV(String ATPV) {
        this.ATPV = ATPV;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
