package com.projetobd.frsmanager1.DAO;

//import com.projetobd.frsmanager1.models.VeiculoBase;
import com.projetobd.frsmanager1.models.VeiculoNovo;
import com.projetobd.frsmanager1.models.VeiculoUsado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.sql.ResultSet;
import java.util.List;

@Repository
public class VeiculoDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper para VeiculoNovo
    private final RowMapper<VeiculoNovo> rowMapperNovo = (ResultSet resultSet, int rowNum) -> {
        VeiculoNovo veiculoNovo = new VeiculoNovo();
        veiculoNovo.setPlaca(resultSet.getString("placa"));
        veiculoNovo.setCategoria(resultSet.getString("categoria"));
        veiculoNovo.setATPV(new File(resultSet.getString("ATPV")));
        veiculoNovo.setNota_fiscal(new File(resultSet.getString("nota_fiscal")));
        return veiculoNovo;
    };

    // RowMapper para VeiculoUsado
    private final RowMapper<VeiculoUsado> rowMapperUsado = (ResultSet resultSet, int rowNum) -> {
        VeiculoUsado veiculoUsado = new VeiculoUsado();
        veiculoUsado.setPlaca(resultSet.getString("placa"));
        veiculoUsado.setCategoria(resultSet.getString("categoria"));
        veiculoUsado.setATPV(new File(resultSet.getString("ATPV")));
        veiculoUsado.setVistoria(new File(resultSet.getString("vistoria")));
        return veiculoUsado;
    };

    // Salvando um novo veículo
    @Transactional
    public boolean saveVeiculoNovo(VeiculoNovo veiculoNovo) {
        String sql = "INSERT INTO VeiculoNovo (placa, categoria, ATPV, nota_fiscal) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, veiculoNovo.getPlaca(), veiculoNovo.getCategoria(), veiculoNovo.getATPV().getPath(), veiculoNovo.getNota_fiscal().getPath());
        return true;
    }

    @Transactional
    public boolean saveVeiculoUsado(VeiculoUsado veiculoUsado) {
        String sql = "INSERT INTO VeiculoUsado (placa, categoria, ATPV, vistoria) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, veiculoUsado.getPlaca(), veiculoUsado.getCategoria(), veiculoUsado.getATPV().getPath(), veiculoUsado.getVistoria().getPath());
        return true;
    }

    // Procurando veículo pela placa
    public VeiculoNovo findVeiculoNovoByPlaca(String placa) {
        String sql = "SELECT * FROM VeiculoNovo WHERE placa = ?";
        return jdbcTemplate.queryForObject(sql, rowMapperNovo, placa);
    }

    public VeiculoUsado findVeiculoUsadoByPlaca(String placa) {
        String sql = "SELECT * FROM VeiculoUsado WHERE placa = ?";
        return jdbcTemplate.queryForObject(sql, rowMapperUsado, placa);
    }

    // Listar todos os veículos
    public List<VeiculoNovo> findAllVeiculoNovo() {
        String sql = "SELECT * FROM VeiculoNovo";
        return jdbcTemplate.query(sql, rowMapperNovo);
    }

    public List<VeiculoUsado> findAllVeiculoUsado() {
        String sql = "SELECT * FROM VeiculoUsado";
        return jdbcTemplate.query(sql, rowMapperUsado);
    }

    // Atualizando veículos
    @Transactional
    public void updateVeiculoNovo(VeiculoNovo veiculoNovo) {
        String sql = "UPDATE VeiculoNovo SET categoria = ?, ATPV = ?, nota_fiscal = ? WHERE placa = ?";
        jdbcTemplate.update(sql, veiculoNovo.getCategoria(), veiculoNovo.getATPV().getPath(), veiculoNovo.getNota_fiscal().getPath(), veiculoNovo.getPlaca());
    }

    @Transactional
    public void updateVeiculoUsado(VeiculoUsado veiculoUsado) {
        String sql = "UPDATE VeiculoUsado SET categoria = ?, ATPV = ?, vistoria = ? WHERE placa = ?";
        jdbcTemplate.update(sql, veiculoUsado.getCategoria(), veiculoUsado.getATPV().getPath(), veiculoUsado.getVistoria().getPath(), veiculoUsado.getPlaca());
    }

    // Deletando veículos
    @Transactional
    public void deleteVeiculoNovo(String placa) {
        String sql = "DELETE FROM VeiculoNovo WHERE placa = ?";
        jdbcTemplate.update(sql, placa);
    }

    @Transactional
    public void deleteVeiculoUsado(String placa) {
        String sql = "DELETE FROM VeiculoUsado WHERE placa = ?";
        jdbcTemplate.update(sql, placa);
    }
}
