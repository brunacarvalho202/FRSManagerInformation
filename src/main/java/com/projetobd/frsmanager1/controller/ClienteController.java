package com.projetobd.frsmanager1.controller;

import com.projetobd.frsmanager1.DAO.ClienteDAO;
//import com.projetobd.frsmanager1.models.ClienteBase;
import com.projetobd.frsmanager1.models.ClienteFisico;
import com.projetobd.frsmanager1.models.ClienteJuridico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteDAO clienteDAO;

    @Autowired
    public ClienteController(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    //salvando um novo cliente
    @PostMapping("/fisico/novo") //feito
    public boolean salvarClienteFisico(@RequestBody ClienteFisico clienteFisico) {
        return clienteDAO.saveClienteFisico(clienteFisico);
    }

    @PostMapping("/juridico/novo") //feito
    public boolean salvarClienteJuridico(@RequestBody ClienteJuridico clienteJuridico) {
        return clienteDAO.saveClienteJuridico(clienteJuridico);
    }

    //procurando pelo cnh do cliente
    @GetMapping("/fisico/{cnh}") //feito
    public ClienteFisico encontrarClienteFisicoPorCNH(@PathVariable String cnh) {
        return clienteDAO.findClienteFisicoByCNH(cnh);
    }

    @GetMapping("/juridico/{cnh}")
    public ClienteJuridico encontrarClienteJuridicoPorCNH(@PathVariable String cnh) {
        return clienteDAO.findClienteJuridicoByCNH(cnh);
    }

    //listar todos os clientes
    @GetMapping("/fisico/todos") //feito
    public List<ClienteFisico> listarClientesFisicos() {
        return clienteDAO.findAllClienteFisico();
    }

    @GetMapping("/juridico/todos") //feito
    public List<ClienteJuridico> listarClientesJuridicos() {
        return clienteDAO.findAllClienteJuridico();
    }

    //atualizando os clientes
    @PutMapping("/fisico/atualizar") //feito
    public void atualizarClienteFisico(@RequestBody ClienteFisico clienteFisico) {
        clienteDAO.updateClienteFisico(clienteFisico);
    }

    @PutMapping("/juridico/atualizar") //feito
    public void atualizarClienteJuridico(@RequestBody ClienteJuridico clienteJuridico) {
        clienteDAO.updateClienteJuridico(clienteJuridico);
    }

    //deletando os clientes
    @DeleteMapping("/fisico/{cnh}") //feito
    public void deletarClienteFisico(@PathVariable String cnh) {
        clienteDAO.deleteClienteFisico(cnh);
    }

    @DeleteMapping("/juridico/{cnh}")
    public void deletarClienteJuridico(@PathVariable String cnh) {
        clienteDAO.deleteClienteJuridico(cnh);
    }
}

