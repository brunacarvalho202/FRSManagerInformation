package com.projetobd.frsmanager1.DAO;

import com.projetobd.frsmanager1.models.ClienteFisico;
import com.projetobd.frsmanager1.models.ClienteJuridico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClienteDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<ClienteFisico> rowMapperFisico = (ResultSet rs, int rowNum) -> {
        ClienteFisico clienteFisico = new ClienteFisico();
        clienteFisico.setCNH(rs.getString("cnh"));
        clienteFisico.setNome(rs.getString("nome"));
        clienteFisico.setProcuracao(rs.getBytes("procuracao"));
        return clienteFisico;
    };

    private final RowMapper<ClienteJuridico> rowMapperJuridico = (ResultSet rs, int rowNum) -> {
        ClienteJuridico clienteJuridico = new ClienteJuridico();
        clienteJuridico.setCNH(rs.getString("cnh"));
        clienteJuridico.setNome(rs.getString("nome"));
        clienteJuridico.setProcuracao(rs.getBytes("procuracao"));
        clienteJuridico.setContrato_social(rs.getBytes("contrato_social"));
        return clienteJuridico;
    };

    public List<ClienteFisico> findAllClienteFisico() {
        String sql = "SELECT * FROM ClienteFisico";
        return jdbcTemplate.query(sql, rowMapperFisico);
    }

    public List<ClienteJuridico> findAllClienteJuridico() {
        String sql = "SELECT * FROM ClienteJuridico";
        return jdbcTemplate.query(sql, rowMapperJuridico);
    }

    public void salvarClienteFisico(ClienteFisico cliente) {
        String sql = "INSERT INTO ClienteFisico (cnh, nome, procuracao) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, cliente.getCNH(), cliente.getNome(), cliente.getProcuracao());
    }

    public void salvarClienteJuridico(ClienteJuridico cliente) {
        String sql = "INSERT INTO ClienteJuridico (cnh, nome, procuracao, contrato_social) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, cliente.getCNH(), cliente.getNome(), cliente.getProcuracao(), cliente.getContrato_social());
    }

    public void deletarClienteFisico(String CNH) {
        String sql = "DELETE FROM ClienteFisico WHERE cnh = ?";
        jdbcTemplate.update(sql, CNH);
    }

    public void deletarClienteJuridico(String CNH) {
        String sql = "DELETE FROM ClienteJuridico WHERE cnh = ?";
        jdbcTemplate.update(sql, CNH);
    }

    public void editarClienteFisico(ClienteFisico cliente) {
        String sql = "UPDATE ClienteFisico SET nome = ?, procuracao = ? WHERE cnh = ?";
        jdbcTemplate.update(sql, cliente.getNome(), cliente.getProcuracao(), cliente.getCNH());
    }

    public void editarClienteJuridico(ClienteJuridico cliente) {
        String sql = "UPDATE ClienteJuridico SET nome = ?, procuracao = ?, contrato_social = ? WHERE cnh = ?";
        jdbcTemplate.update(sql, cliente.getNome(), cliente.getProcuracao(), cliente.getContrato_social(), cliente.getCNH());
}
}