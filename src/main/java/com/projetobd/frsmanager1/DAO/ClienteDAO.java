package com.projetobd.frsmanager1.DAO;

//import com.projetobd.frsmanager1.models.ClienteBase;
import com.projetobd.frsmanager1.models.ClienteFisico;
import com.projetobd.frsmanager1.models.ClienteJuridico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.sql.ResultSet;
import java.util.List;

@Repository
public class ClienteDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //rowmapper para cliente físico
    private final RowMapper<ClienteFisico> rowMapperFisico = (ResultSet resultOfBD, int rowNum) -> {
        ClienteFisico clienteFisico = new ClienteFisico();

        clienteFisico.setCNH(resultOfBD.getString("CNH"));
        clienteFisico.setNome(resultOfBD.getString("nome"));
        clienteFisico.setProcuracao(new File(resultOfBD.getString("procuracao")));

        return clienteFisico;
    };

    //rowmapper para cliente juridico
    private final RowMapper<ClienteJuridico> rowMapperJuridico = (ResultSet resultOfBD, int rowNum) -> {
        ClienteJuridico clienteJuridico = new ClienteJuridico();

        clienteJuridico.setCNH(resultOfBD.getString("CNH"));
        clienteJuridico.setNome(resultOfBD.getString("nome"));
        clienteJuridico.setProcuracao(new File(resultOfBD.getString("procuracao")));
        clienteJuridico.setContrato_social(new File(resultOfBD.getString("contrato_social")));

        return clienteJuridico;
    };

    //salvando um novo cliente
    @Transactional
    public boolean saveClienteFisico(ClienteFisico clienteFisico){
        String sql = "INSERT INTO ClienteFisico (cnh, nome, procuracao) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, clienteFisico.getCNH(), clienteFisico.getNome(), clienteFisico.getProcuracao().getPath());
        return true;
    }

    @Transactional
    public boolean saveClienteJuridico(ClienteJuridico clienteJuridico){
        String sql = "INSERT INTO ClienteJuridico (cnh, nome, procuracao, contrato_social) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, clienteJuridico.getCNH(), clienteJuridico.getNome(), clienteJuridico.getProcuracao().getPath(), clienteJuridico.getContrato_social().getPath());
        return true;
    }

    //procurando pelo cnh do cliente
    public ClienteFisico findClienteFisicoByCNH(String cnh){
        String sql = "SELECT * FROM ClienteFisico WHERE cnh = ?";
        return jdbcTemplate.queryForObject(sql, rowMapperFisico, cnh);
    }

    public ClienteJuridico findClienteJuridicoByCNH(String cnh){
        String sql = "SELECT * FROM ClienteJuridico WHERE cnh = ?";
        return jdbcTemplate.queryForObject(sql, rowMapperJuridico, cnh);
    }

    //listar todos os clientes
    public List<ClienteFisico> findAllClienteFisico(){
        String sql = "SELECT * FROM ClienteFisico";
        return jdbcTemplate.query(sql, rowMapperFisico);
    }

    public List<ClienteJuridico> findAllClienteJuridico(){
        String sql = "SELECT * FROM ClienteJuridico";
        return jdbcTemplate.query(sql, rowMapperJuridico);
    }

    //atualizando os clientes
    @Transactional
    public void updateClienteFisico(ClienteFisico clienteFisico){
        String sql = "UPDATE ClienteFisico SET nome = ?, procuracao = ? WHERE cnh = ?";
        jdbcTemplate.update(sql, clienteFisico.getNome(), clienteFisico.getProcuracao().getPath(), clienteFisico.getCNH());
    }

    @Transactional
    public void updateClienteJuridico(ClienteJuridico clienteJuridico) {
        String sql = "UPDATE ClienteJuridico SET nome = ?, procuracao = ?, contrato_social = ? WHERE cnh = ?";
        jdbcTemplate.update(sql, clienteJuridico.getNome(), clienteJuridico.getProcuracao().getPath(), clienteJuridico.getContrato_social().getPath(), clienteJuridico.getCNH());
    }

    //deletando os clientes
    @Transactional
    public void deleteClienteFisico(String cnh) {
        String sql = "DELETE FROM ClienteFisico WHERE cnh = ?";
        jdbcTemplate.update(sql, cnh);
    }

    @Transactional
    public void deleteClienteJuridico(String cnh) {
        String sql = "DELETE FROM ClienteJuridico WHERE cnh = ?";
        jdbcTemplate.update(sql, cnh);
    }
}