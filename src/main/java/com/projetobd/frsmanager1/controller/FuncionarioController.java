package com.projetobd.frsmanager1.controller;

import com.projetobd.frsmanager1.DAO.DependenteDAO;
import com.projetobd.frsmanager1.DAO.FuncionarioDAO;
import com.projetobd.frsmanager1.models.Dependente;
import com.projetobd.frsmanager1.models.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioDAO funcionarioDAO;

    @Autowired
    private DependenteDAO dependenteDAO;

    @Autowired
    public FuncionarioController(FuncionarioDAO funcionarioDAO) {
        this.funcionarioDAO = funcionarioDAO;
    }

    //funcionou com dependentes
    @PostMapping("/novo") //feito
    public boolean salvarFuncionario(@RequestBody Funcionario funcionario) {

        return funcionarioDAO.save(funcionario);
    }

    @PutMapping("/atualizar") //feito
    public void atualizarFuncionario(@RequestBody Funcionario funcionario) {

        funcionarioDAO.updateFuncionario(funcionario);
    }

    @DeleteMapping("/{cpf}")//feito
    public void deletarFuncionario(@PathVariable String cpf) {
        funcionarioDAO.deleteFuncionario(cpf);
    }

    @GetMapping("/{cpf}") //feito
    public Funcionario encontrarFuncionarioPorId(@PathVariable String cpf) {

        return funcionarioDAO.findByCpf(cpf);
    }

    @GetMapping("/todos") //feito
    public List<Funcionario> listarFuncionarios() {

        return funcionarioDAO.findAll();
    }

    @GetMapping("/{cpfFuncionario}/dependentes") //feito
    public List<Dependente> listarDependentesDoFuncionario(@PathVariable String cpfFuncionario) {
        return dependenteDAO.findAllByCpfFuncionario(cpfFuncionario);
    }

}






