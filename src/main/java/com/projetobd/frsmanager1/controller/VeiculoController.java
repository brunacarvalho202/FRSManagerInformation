//package com.projetobd.frsmanager1.controller;
//
//import com.projetobd.frsmanager1.DAO.VeiculoDAO;
//import com.projetobd.frsmanager1.models.VeiculoNovo;
//import com.projetobd.frsmanager1.models.VeiculoUsado;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/veiculos")
//public class VeiculoController {
//
//    private final VeiculoDAO veiculoDAO;
//
//    @Autowired
//    public VeiculoController(VeiculoDAO veiculoDAO) {
//        this.veiculoDAO = veiculoDAO;
//    }
//
//    @PostMapping("/novo")
//    public boolean salvarVeiculoNovo(@RequestBody VeiculoNovo veiculoNovo) {
//        return veiculoDAO.saveVeiculoNovo(veiculoNovo);
//    }
//
//    @PostMapping("/usado")
//    public boolean salvarVeiculoUsado(@RequestBody VeiculoUsado veiculoUsado) {
//        return veiculoDAO.saveVeiculoUsado(veiculoUsado);
//    }
//
//    @GetMapping("/novo/{placa}")
//    public VeiculoNovo encontrarVeiculoNovoPorPlaca(@PathVariable String placa) {
//        return veiculoDAO.findVeiculoNovoByPlaca(placa);
//    }
//
//    @GetMapping("/usado/{placa}")
//    public VeiculoUsado encontrarVeiculoUsadoPorPlaca(@PathVariable String placa) {
//        return veiculoDAO.findVeiculoUsadoByPlaca(placa);
//    }
//
//    @GetMapping("/novo/todos")
//    public List<VeiculoNovo> listarVeiculosNovos() {
//        return veiculoDAO.findAllVeiculoNovo();
//    }
//
//    @GetMapping("/usado/todos")
//    public List<VeiculoUsado> listarVeiculosUsados() {
//        return veiculoDAO.findAllVeiculoUsado();
//    }
//
//    @PutMapping("/novo/atualizar")
//    public void atualizarVeiculoNovo(@RequestBody VeiculoNovo veiculoNovo) {
//        veiculoDAO.updateVeiculoNovo(veiculoNovo);
//    }
//
//    @PutMapping("/usado/atualizar")
//    public void atualizarVeiculoUsado(@RequestBody VeiculoUsado veiculoUsado) {
//        veiculoDAO.updateVeiculoUsado(veiculoUsado);
//    }
//
//    @DeleteMapping("/novo/{placa}")
//    public void deletarVeiculoNovo(@PathVariable String placa) {
//        veiculoDAO.deleteVeiculoNovo(placa);
//    }
//
//    @DeleteMapping("/usado/{placa}")
//    public void deletarVeiculoUsado(@PathVariable String placa) {
//        veiculoDAO.deleteVeiculoUsado(placa);
//    }
//}
