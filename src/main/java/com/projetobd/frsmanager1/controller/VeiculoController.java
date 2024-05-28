package com.projetobd.frsmanager1.controller;

import com.projetobd.frsmanager1.DAO.VeiculoDAO;
import com.projetobd.frsmanager1.models.VeiculoNovo;
import com.projetobd.frsmanager1.models.VeiculoUsado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoDAO veiculoDAO;

    @Autowired
    public VeiculoController(VeiculoDAO veiculoDAO) {
        this.veiculoDAO = veiculoDAO;
    }

    // Salvando um novo veículo
    @PostMapping("/novo") //feito
    public boolean salvarVeiculoNovo(@RequestBody VeiculoNovo veiculoNovo) {
        return veiculoDAO.saveVeiculoNovo(veiculoNovo);
    }

    @PostMapping("/usado") //feito
    public boolean salvarVeiculoUsado(@RequestBody VeiculoUsado veiculoUsado) {
        return veiculoDAO.saveVeiculoUsado(veiculoUsado);
    }

    // Procurando veículo pela placa
    @GetMapping("/novo/{placa}") //feito
    public VeiculoNovo encontrarVeiculoNovoPorPlaca(@PathVariable String placa) {
        return veiculoDAO.findVeiculoNovoByPlaca(placa);
    }

    @GetMapping("/usado/{placa}") //feito
    public VeiculoUsado encontrarVeiculoUsadoPorPlaca(@PathVariable String placa) {
        return veiculoDAO.findVeiculoUsadoByPlaca(placa);
    }

    // Listar todos os veículos
    @GetMapping("/novo/todos") //feito
    public List<VeiculoNovo> listarVeiculosNovos() {
        return veiculoDAO.findAllVeiculoNovo();
    }

    @GetMapping("/usado/todos") //feito
    public List<VeiculoUsado> listarVeiculosUsados() {
        return veiculoDAO.findAllVeiculoUsado();
    }

    // Atualizando veículos
    @PutMapping("/novo/atualizar") //feito
    public void atualizarVeiculoNovo(@RequestBody VeiculoNovo veiculoNovo) {
        veiculoDAO.updateVeiculoNovo(veiculoNovo);
    }

    @PutMapping("/usado/atualizar") //feito
    public void atualizarVeiculoUsado(@RequestBody VeiculoUsado veiculoUsado) {
        veiculoDAO.updateVeiculoUsado(veiculoUsado);
    }

    // Deletando veículos
    @DeleteMapping("/novo/{placa}") //feito
    public void deletarVeiculoNovo(@PathVariable String placa) {
        veiculoDAO.deleteVeiculoNovo(placa);
    }

    @DeleteMapping("/usado/{placa}") //feito
    public void deletarVeiculoUsado(@PathVariable String placa) {
        veiculoDAO.deleteVeiculoUsado(placa);
    }
}
