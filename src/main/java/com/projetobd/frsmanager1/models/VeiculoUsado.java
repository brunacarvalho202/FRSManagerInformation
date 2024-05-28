package com.projetobd.frsmanager1.models;

import java.io.File;

public class VeiculoUsado extends VeiculoBase {

    private File vistoria;

    public File getVistoria() {
        return vistoria;
    }

    public void setVistoria(File vistoria) {
        this.vistoria = vistoria;
    }
}

