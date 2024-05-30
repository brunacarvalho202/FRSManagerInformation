package com.projetobd.frsmanager1.controller;

import com.projetobd.frsmanager1.DAO.ClienteDAO;
import com.projetobd.frsmanager1.models.ClienteFisico;
import com.projetobd.frsmanager1.models.ClienteJuridico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteDAO clienteDAO;

    @Autowired
    public ClienteController(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    @GetMapping("/fisico/todos")
    public List<ClienteFisico> listarClientesFisicos() {
        return clienteDAO.findAllClienteFisico();
    }

    @GetMapping("/juridico/todos")
    public List<ClienteJuridico> listarClientesJuridicos() {
        return clienteDAO.findAllClienteJuridico();
    }

    @PostMapping(value = "/fisico", consumes = "multipart/form-data")
    public ResponseEntity<String> criarClienteFisico(@RequestParam("CNH") String CNH,
                                                     @RequestParam("nome") String nome,
                                                     @RequestParam("procuracao") MultipartFile procuracao) throws IOException {
        ClienteFisico clienteFisico = new ClienteFisico();
        clienteFisico.setCNH(CNH);
        clienteFisico.setNome(nome);
        clienteFisico.setProcuracao(procuracao.getBytes());
        clienteDAO.salvarClienteFisico(clienteFisico);
        return ResponseEntity.ok("Cliente fisico salvo com sucesso!");
    }

    @PostMapping(value = "/juridico", consumes = "multipart/form-data")
    public ResponseEntity<String> criarClienteJuridico(@RequestParam("CNH") String CNH,
                                                       @RequestParam("nome") String nome,
                                                       @RequestParam("procuracao") MultipartFile procuracao,
                                                       @RequestParam("contrato_social") MultipartFile contratoSocial) throws IOException {
        ClienteJuridico clienteJuridico = new ClienteJuridico();
        clienteJuridico.setCNH(CNH);
        clienteJuridico.setNome(nome);
        clienteJuridico.setProcuracao(procuracao.getBytes());
        clienteJuridico.setContrato_social(contratoSocial.getBytes());
        clienteDAO.salvarClienteJuridico(clienteJuridico);
        return ResponseEntity.ok("Cliente juridico salvo com sucesso!");
    }

    @DeleteMapping("/fisico/{CNH}")
    public ResponseEntity<String> deletarClienteFisico(@PathVariable("CNH") String CNH) {
        clienteDAO.deletarClienteFisico(CNH);
        return ResponseEntity.ok("Cliente fisico deletado com sucesso!");
    }

    @DeleteMapping("/juridico/{CNH}")
    public ResponseEntity<String> deletarClienteJuridico(@PathVariable("CNH") String CNH) {
        clienteDAO.deletarClienteJuridico(CNH);
        return ResponseEntity.ok("Cliente juridico deletado com sucesso!");
    }

    @PutMapping(value = "/fisico", consumes = "multipart/form-data")
    public ResponseEntity<String> editarClienteFisico(@RequestParam("CNH") String CNH,
                                                      @RequestParam("nome") String nome,
                                                      @RequestParam("procuracao") MultipartFile procuracao) throws IOException {
        ClienteFisico clienteFisico = new ClienteFisico();
        clienteFisico.setCNH(CNH);
        clienteFisico.setNome(nome);
        clienteFisico.setProcuracao(procuracao.getBytes());
        clienteDAO.editarClienteFisico(clienteFisico);
        return ResponseEntity.ok("Cliente fisico editado com sucesso!");
    }

    @PutMapping(value = "/juridico", consumes = "multipart/form-data")
    public ResponseEntity<String> editarClienteJuridico(@RequestParam("CNH") String CNH,
                                                        @RequestParam("nome") String nome,
                                                        @RequestParam("procuracao") MultipartFile procuracao,
                                                        @RequestParam("contrato_social") MultipartFile contratoSocial) throws IOException {
        ClienteJuridico clienteJuridico = new ClienteJuridico();
        clienteJuridico.setCNH(CNH);
        clienteJuridico.setNome(nome);
        clienteJuridico.setProcuracao(procuracao.getBytes());
        clienteJuridico.setContrato_social(contratoSocial.getBytes());
        clienteDAO.editarClienteJuridico(clienteJuridico);
        return ResponseEntity.ok("Cliente juridico editado com sucesso!");
}
}