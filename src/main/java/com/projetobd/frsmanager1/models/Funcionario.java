package com.projetobd.frsmanager1.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Funcionario {

    //adicionar os mesmos atributos do modelo conceitual

    private String cpf; //primary key

    private String nome;

    private String carteira_trabalho;

    private List<String> telefone; //multivalorado

    private Endereco endereco;

    @JsonProperty("supervisor_cpf") //usar pois o nome no json esta diferente, para serializar corretamente
    private String supervisorCpf; // CPF do supervisor / auto-relacionamento

    private List<Dependente> dependentes = new ArrayList<>();

    public void addDependente(Dependente dependente) {
        this.dependentes.add(dependente);
    }//dependencia

    //padrao
    public Funcionario(){}

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCarteira_trabalho() {
        return carteira_trabalho;
    }
    public void setCarteira_trabalho(String carteira_trabalho) {
        this.carteira_trabalho = carteira_trabalho;
    }

    public List<String> getTelefone() {
        return telefone;
    }
    public void setTelefone(List<String> telefone) {
        this.telefone = telefone;
    }

    public String getSupervisorCpf() {
        return supervisorCpf;
    }

    public void setSupervisorCpf(String supervisorCpf) {
        this.supervisorCpf = supervisorCpf;
    }

    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Dependente> getDependentes() {
        return dependentes;
    }
    public void setDependentes(List<Dependente> dependentes) {
        this.dependentes = dependentes;
    }

}
