package com.projetobd.frsmanager1.models;

import java.io.File;

public class ClienteBase {
    private String CNH; //primary key
    private String nome;
    private byte[] procuracao; // Usando byte[] para armazenar arquivos

    public byte[] getProcuracao() {
        return procuracao;
    }

    public void setProcuracao(byte[] procuracao) {
        this.procuracao = procuracao;
    }

    public String getCNH() {
        return CNH;
    }
    public void setCNH(String cNH) {
        CNH = cNH;
    }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome=nome;}

}