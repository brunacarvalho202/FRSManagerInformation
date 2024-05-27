package com.projetobd.frsmanager1.DAO;

import com.projetobd.frsmanager1.models.Servico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import java.sql.ResultSet;
import java.util.*;

@Repository
public class ServicoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Servico> rowMapper = (ResultSet resultOfBD, int rowNum) -> {
        Servico servico = new Servico();

        servico.setCodigo_servico(resultOfBD.getInt("codigo_servico"));
        servico.setDescricao(resultOfBD.getString("descricao"));
        servico.setValor(resultOfBD.getDouble("valor"));

        return servico;
    };

    @Transactional
    public boolean save(Servico servico){ //funcionou
        String sqlServico = "INSERT INTO Servico (codigo_servico, descricao, valor) VALUES (?, ?, ?)";

        jdbcTemplate.update(sqlServico,
                servico.getCodigo_servico(),
                servico.getDescricao(),
                servico.getValor());

        return true;
    }

    @Transactional
    public void updateServico(Servico servico){ //funcionou
        Servico existingServico = findByCodigo_servico(servico.getCodigo_servico());

        if(servico.getDescricao() != null){
            existingServico.setDescricao(servico.getDescricao());
        }
        if(servico.getValor() != 0){
            existingServico.setValor(servico.getValor());
        }

        String sql = "UPDATE Servico SET descricao = ?, valor = ? WHERE codigo_servico = ? ";
        jdbcTemplate.update(sql, existingServico.getDescricao(), existingServico.getValor(), existingServico.getCodigo_servico());
    }


    public Servico findByCodigo_servico(int codigo_servico){ //funcionou, mas a id precisou ser explicita na URL
        String sql = "SELECT * FROM Servico WHERE codigo_servico = ?";

        return jdbcTemplate.queryForObject(sql, rowMapper, codigo_servico);
    }

    public List<Servico> findAll(){ //funcionou
        String sql = "SELECT * FROM Servico";

        return jdbcTemplate.query(sql, rowMapper);
    }

    public void deleteServico(int codigo_servico){ //funcionou, mas o delete tem que ta com o proprio id do que é p ser apagado, se passar o parametro {codigo_servico} ele dá erro
        String sql = "DELETE FROM Servico WHERE codigo_servico = ?";
        jdbcTemplate.update(sql, codigo_servico);
    }

    //sql: A consulta SQL a ser executada.
    //rowMapper: Um RowMapper que sabe como converter uma linha do ResultSet em um objeto do tipo Funcionario.
    //cpf: O valor do parâmetro a ser inserido no ? da consulta SQL.

    //O RowMapper é usado para converter a linha resultante do ResultSet em um objeto Funcionario.
    //Retorno do Objeto: O método retorna o objeto Funcionario mapeado.

}