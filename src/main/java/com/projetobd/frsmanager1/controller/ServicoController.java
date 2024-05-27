package com.projetobd.frsmanager1.controller;

import com.projetobd.frsmanager1.DAO.ServicoDAO;
import com.projetobd.frsmanager1.models.Servico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")

public class ServicoController {

    private final ServicoDAO servicoDAO;

    @Autowired
    public ServicoController(ServicoDAO servicoDAO) {
        this.servicoDAO = servicoDAO;
    }

    @PostMapping("/novo_servico") //FEITO
    public boolean salvarServico(@RequestBody Servico servico) {
        return servicoDAO.save(servico);
    }

    @GetMapping("/{codigo_servico}") //funcionou, mas a id precisou ser explicita na URL
    public Servico buscarServico(@PathVariable int codigo_servico) {
        return servicoDAO.findByCodigo_servico(codigo_servico);
    }

    @GetMapping("/todos_servicos") //funcionou
    public List<Servico> buscarTodosServicos() {
        return servicoDAO.findAll();
    }

    @PutMapping("/atualizar_servico") //FEITO
    public void atualizarServico(@RequestBody Servico servico) {
        servicoDAO.updateServico(servico);
    }

    @DeleteMapping("/{codigo_servico}") //funcionou, mas o delete tem que ta com o proprio id do que é p ser apagado, se passar o parametro {codigo_servico} ele dá erro
    public void deletarServico(@PathVariable int codigo_servico) {
        servicoDAO.deleteServico(codigo_servico);
    }

}