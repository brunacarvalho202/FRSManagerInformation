package com.projetobd.frsmanager1.models;

import java.io.File;

public class ClienteJuridico extends ClienteBase {
    private File contrato_social;

    public File getContrato_social() {
        return contrato_social;
    }

    public void setContrato_social(File contrato_social) {
        this.contrato_social = contrato_social;
    }
}
