package com.projetobd.frsmanager1.models;

import java.io.File;

public class ClienteBase {
    private String CNH; //primary key
    private File procuracao;

    public String getCNH() {
        return CNH;
    }
    public void setCNH(String cNH) {
        CNH = cNH;
    }
    public File getProcuracao() {
        return procuracao;
    }
    public void setProcuracao(File procuracao) {
        this.procuracao = procuracao;
    }
}
