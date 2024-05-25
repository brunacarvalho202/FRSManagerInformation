package com.projetobd.frsmanager1.models;

import java.io.File;

public class VeiculoBase {
    private String placa; //primary key
    private File ATPV;
    private String categoria;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public File getATPV() {
        return ATPV;
    }

    public void setATPV(File ATPV) {
        this.ATPV = ATPV;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
