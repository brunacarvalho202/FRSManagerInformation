package com.projetobd.frsmanager1.models;

import java.io.File;

public class ClienteJuridico extends ClienteBase {
    private byte[] contrato_social; // Usando byte[] para armazenar arquivos

    public byte[] getContrato_social() {
        return contrato_social;
    }

    public void setContrato_social(byte[] contrato_social) {
        this.contrato_social = contrato_social;
}
}