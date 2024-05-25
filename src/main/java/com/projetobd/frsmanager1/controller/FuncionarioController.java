package com.projetobd.frsmanager1.controller;

import com.projetobd.frsmanager1.DAO.FuncionarioDAO;
import com.projetobd.frsmanager1.models.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioDAO funcionarioDAO;

    @Autowired
    public FuncionarioController(FuncionarioDAO funcionarioDAO) {
        this.funcionarioDAO = funcionarioDAO;
    }

    //funcionou com dependentes
    @PostMapping("/novo") //feito - analisar pq msm mandando tel ele nao salva no bd - agr salva mas retorna duplicado
    public boolean salvarFuncionario(@RequestBody Funcionario funcionario) {

        return funcionarioDAO.save(funcionario);
    }

    @GetMapping("/{cpf}") //feito
    public Funcionario encontrarFuncionarioPorId(@PathVariable String cpf) {
        return funcionarioDAO.findByCpf(cpf);
    }

    @GetMapping("/todos") //feito
    public List<Funcionario> listarFuncionarios() {
        return funcionarioDAO.findAll();
    }

    @PutMapping("/atualizar") //tenatr atualizar junto com o teleofne tb
    public void atualizarFuncionario(@RequestBody Funcionario funcionario) {
        funcionarioDAO.updateFuncionario(funcionario);
    }

    @DeleteMapping("/{cpf}")//feito - nao deletava sem deletar os nuemros primeiro - feito - mudança p usar cascade no procedure
    public void deletarFuncionario(@PathVariable String cpf) {
        funcionarioDAO.deleteFuncionario(cpf);
    }
}
