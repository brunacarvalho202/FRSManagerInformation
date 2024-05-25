package com.projetobd.frsmanager1.models;

public class Dependente {
    private String nome;
    private Funcionario funcionario_dependente;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Funcionario getFuncionario_dependente() {
        return funcionario_dependente;
    }

    public void setFuncionario_dependente(Funcionario funcionario_dependente) {
        this.funcionario_dependente = funcionario_dependente;
    }
}
