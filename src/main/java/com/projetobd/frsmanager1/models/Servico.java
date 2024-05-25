package com.projetobd.frsmanager1.models;

public class Servico {
    private int codigo_servico; //primary key
    private String descricao;
    private double valor;

    public int getCodigo_servico() {
        return codigo_servico;
    }

    public void setCodigo_servico(int codigo_servico) {
        this.codigo_servico = codigo_servico;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
