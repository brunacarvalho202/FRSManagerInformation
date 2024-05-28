package com.projetobd.frsmanager1.models;

import java.io.File;

public class VeiculoNovo extends VeiculoBase {

    private File nota_fiscal;

    public File getNota_fiscal() { return nota_fiscal; }

    public void setNota_fiscal(File nota_fiscal) {
        this.nota_fiscal = nota_fiscal;
    }
}
