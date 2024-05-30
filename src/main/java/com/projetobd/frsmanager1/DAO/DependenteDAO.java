package com.projetobd.frsmanager1.DAO;

import com.projetobd.frsmanager1.models.Dependente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DependenteDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper para Dependente
    private final RowMapper<Dependente> rowMapper = (resultSet, rowNum) -> {
        Dependente dependente = new Dependente();

        dependente.setCpfDependente(resultSet.getString("cpfDependente"));
        dependente.setNome(resultSet.getString("nome"));
        dependente.setCpfFuncionario(resultSet.getString("cpfFuncionario"));

        return dependente;
    };

    public boolean addDependente(Dependente dependente) {
        String sql = "INSERT INTO Dependente (cpfDependente, nome, cpfFuncionario) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, dependente.getCpfDependente(), dependente.getNome(), dependente.getCpfFuncionario());

        return true;
    }

    public boolean updateDependente(String cpf, Dependente dependente) {
        String sql = "UPDATE Dependente SET nome = ? WHERE cpfDependente = ?";

        jdbcTemplate.update(sql, dependente.getNome(), cpf);

        return true;
    }

    // Deletar um dependente pelo ID
    public boolean deleteDependente(String cpf) {
        String sql = "DELETE FROM Dependente WHERE cpfDependente = ?";
        jdbcTemplate.update(sql, cpf);
        return true;
    }

    // Buscar um dependente pelo CPF
    public Dependente findByCpf(String cpfDependente) {
        String sql = "SELECT d.nome, d.cpfDependente, d.cpfFuncionario " +
                "FROM Dependente d " +
                "INNER JOIN Funcionario f ON d.cpfFuncionario = f.cpf " +
                "WHERE d.cpfDependente = ?";

        return jdbcTemplate.queryForObject(sql, rowMapper, cpfDependente);
    }

    // Listar todos os dependentes
    public List<Dependente> getAllDependentes() {
        String sql = "SELECT * FROM Dependente";

        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<Dependente> findAllByCpfFuncionario(String cpfFuncionario) {
        String sql = "SELECT d.nome, d.cpfDependente, d.cpfFuncionario " +
                "FROM Dependente d " +
                "WHERE d.cpfFuncionario = ?";
        return jdbcTemplate.query(sql, rowMapper, cpfFuncionario);
    }


}
