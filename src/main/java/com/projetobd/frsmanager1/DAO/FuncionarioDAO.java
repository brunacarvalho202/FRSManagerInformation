package com.projetobd.frsmanager1.DAO;

import com.projetobd.frsmanager1.models.Dependente;
import com.projetobd.frsmanager1.models.Endereco;
import com.projetobd.frsmanager1.models.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import java.sql.ResultSet;
import java.util.*;

@Repository
public class FuncionarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //mapear o objeto que vem do banco de dados para virar objeto no codigo
    // Defina um RowMapper como um método
    //o resultSet é o sql: resultOfBD
    // Defina um RowMapper como um método
    private final RowMapper<Funcionario> rowMapper = (ResultSet resultOfBD, int rowNum) -> {
        Funcionario funcionario = new Funcionario(); // Criar o objeto para preencher com o mapeamento

        funcionario.setCpf(resultOfBD.getString("cpf")); // Pegue o valor da coluna cpf desse resultado e jogue no objeto
        funcionario.setNome(resultOfBD.getString("nome"));
        funcionario.setCarteira_trabalho(resultOfBD.getString("carteira_trabalho"));

        // Mapeamento do telefone
        List<String> telefones = new ArrayList<>();
        String telefoneString = resultOfBD.getString("telefone");
        if (telefoneString != null) {
            telefones = Arrays.asList(telefoneString.split(","));
        }
        funcionario.setTelefone(telefones);

        // Mapeamento do endereco
        funcionario.setEndereco(new Endereco(resultOfBD.getString("endereco_rua"),
                resultOfBD.getString("endereco_bairro"),
                resultOfBD.getInt("endereco_numero")));

        // Mapeamento do supervisor
        funcionario.setSupervisorCpf(resultOfBD.getString("supervisor_cpf"));

        // Mapeamento dos dependentes
        List<Dependente> dependentes = new ArrayList<>();

        String dependentesString = resultOfBD.getString("dependentes");

        if (dependentesString != null) {
            String[] dependentesArray = dependentesString.split(",");

            for (String dependenteNome : dependentesArray) {
                Dependente dependente = new Dependente();
                dependente.setNome(dependenteNome.trim()); // Remova os espaços em branco ao redor do nome
                dependentes.add(dependente);
            }
        }
        funcionario.setDependentes(dependentes); // Defina a lista de dependentes no funcionário

        return funcionario;
    };

    @Transactional
    public boolean save(Funcionario funcionario) {
        String sqlFuncionario = "INSERT INTO Funcionario (cpf, nome, carteira_trabalho, endereco_rua, endereco_bairro, endereco_numero, supervisor_cpf) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlTelefone = "INSERT INTO Telefone (cpf_funcionario, telefone) VALUES (?, ?)";
        String sqlDependente = "INSERT INTO Dependente (nome, cpfFuncionario) VALUES (?, ?)";

        jdbcTemplate.update(sqlFuncionario,
                funcionario.getCpf(),
                funcionario.getNome(),
                funcionario.getCarteira_trabalho(),
                funcionario.getEndereco().getRua(),
                funcionario.getEndereco().getBairro(),
                funcionario.getEndereco().getNumero(),
                funcionario.getSupervisorCpf() != null ? funcionario.getSupervisorCpf() : null);

        for (String telefone : funcionario.getTelefone()) {
            jdbcTemplate.update(sqlTelefone, funcionario.getCpf(), telefone);
        }

        for (Dependente dependente : funcionario.getDependentes()) {
            jdbcTemplate.update(sqlDependente, dependente.getNome(), funcionario.getCpf());
        }

        return true;
    }

    @Transactional
    public void updateFuncionario(Funcionario funcionario) {
        try {
            // Buscar o funcionário existente
            System.out.println("Buscando funcionário com CPF: " + funcionario.getCpf());
            Funcionario existingFuncionario = findByCpf(funcionario.getCpf());
            System.out.println("Funcionário encontrado: " + existingFuncionario);

            // Mesclar os dados do funcionário
            if (funcionario.getNome() != null) {
                existingFuncionario.setNome(funcionario.getNome());
            }
            if (funcionario.getCarteira_trabalho() != null) {
                existingFuncionario.setCarteira_trabalho(funcionario.getCarteira_trabalho());
            }
            if (funcionario.getEndereco() != null) {
                if (funcionario.getEndereco().getRua() != null) {
                    existingFuncionario.getEndereco().setRua(funcionario.getEndereco().getRua());
                }
                if (funcionario.getEndereco().getBairro() != null) {
                    existingFuncionario.getEndereco().setBairro(funcionario.getEndereco().getBairro());
                }
                if (funcionario.getEndereco().getNumero() != 0) {
                    existingFuncionario.getEndereco().setNumero(funcionario.getEndereco().getNumero());
                }
            }
            if (funcionario.getSupervisorCpf() != null) {
                existingFuncionario.setSupervisorCpf(funcionario.getSupervisorCpf());
            }

            // Executar a atualização dos dados do funcionário
            String sql = "UPDATE Funcionario SET nome = ?, carteira_trabalho = ?, endereco_rua = ?, endereco_bairro = ?, endereco_numero = ?, supervisor_cpf = ? WHERE cpf = ?";
            System.out.println("Executando SQL: " + sql);
            jdbcTemplate.update(sql, existingFuncionario.getNome(), existingFuncionario.getCarteira_trabalho(), existingFuncionario.getEndereco().getRua(), existingFuncionario.getEndereco().getBairro(), existingFuncionario.getEndereco().getNumero(), existingFuncionario.getSupervisorCpf(), existingFuncionario.getCpf());

            // Atualizar telefones
            String sqlDeleteTelefone = "DELETE FROM Telefone WHERE cpf_funcionario = ?";
            System.out.println("Deletando telefones para CPF: " + existingFuncionario.getCpf());
            jdbcTemplate.update(sqlDeleteTelefone, existingFuncionario.getCpf());

            String sqlInsertTelefone = "INSERT INTO Telefone (cpf_funcionario, telefone) VALUES (?, ?)";
            for (String telefone : funcionario.getTelefone()) {
                System.out.println("Inserindo telefone: " + telefone + " para CPF: " + existingFuncionario.getCpf());
                jdbcTemplate.update(sqlInsertTelefone, existingFuncionario.getCpf(), telefone);
            }

            // Atualizar os dependentes (se necessário)
            if (funcionario.getDependentes() != null && !funcionario.getDependentes().isEmpty()) {
                String sqlDependente = "INSERT INTO Dependente (nome, cpfFuncionario) VALUES (?, ?) ON DUPLICATE KEY UPDATE nome = VALUES(nome)";
                System.out.println("Executando SQL para dependentes: " + sqlDependente);
                for (Dependente dependente : funcionario.getDependentes()) {
                    System.out.println("Inserindo/Atualizando dependente: " + dependente.getNome() + " para CPF: " + funcionario.getCpf());
                    jdbcTemplate.update(sqlDependente, dependente.getNome(), funcionario.getCpf());
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar funcionário: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    //uso de procedure
    //na exclusão de um funcionário que é supervisor,os funcionários supervisionados por ele sao atualizaodos
    public void deleteFuncionario(String cpf) {
        String sql = "CALL DeleteSupervisor(?)";
        jdbcTemplate.update(sql, cpf);
    }

    public Funcionario findByCpf(String cpf) {
        String sql = "SELECT f.cpf, f.nome, f.carteira_trabalho, f.endereco_rua, f.endereco_bairro, f.endereco_numero, f.supervisor_cpf, " +
                "(SELECT GROUP_CONCAT(t.telefone SEPARATOR ',') FROM Telefone t WHERE t.cpf_funcionario = f.cpf) AS telefone, " +
                "(SELECT GROUP_CONCAT(d.nome SEPARATOR ',') FROM Dependente d WHERE d.cpfFuncionario = f.cpf) AS dependentes " +
                "FROM Funcionario f " +
                "WHERE f.cpf = ?";

        //retorno dessa consulta sql que precisamos tratar p objeto
        return jdbcTemplate.queryForObject(sql, rowMapper, cpf);
    }

    public List<Funcionario> findAll() {
        String sql = "SELECT f.cpf, f.nome, f.carteira_trabalho, f.endereco_rua, f.endereco_bairro, f.endereco_numero, f.supervisor_cpf, " +
                "(SELECT GROUP_CONCAT(t.telefone SEPARATOR ',') FROM Telefone t WHERE t.cpf_funcionario = f.cpf) AS telefone, " +
                "(SELECT GROUP_CONCAT(d.nome SEPARATOR ',') FROM Dependente d WHERE d.cpfFuncionario = f.cpf) AS dependentes, " +
                "(SELECT GROUP_CONCAT(d.idDependente SEPARATOR ',') FROM Dependente d WHERE d.cpfFuncionario = f.cpf) AS idsDependentes, " +
                "(SELECT GROUP_CONCAT(d.cpfFuncionario SEPARATOR ',') FROM Dependente d WHERE d.cpfFuncionario = f.cpf) AS cpfsDependentes " +
                "FROM Funcionario f";

        return jdbcTemplate.query(sql, rowMapper);
    }

    //sql: A consulta SQL a ser executada.
    //rowMapper: Um RowMapper que sabe como converter uma linha do ResultSet em um objeto do tipo Funcionario.
    //cpf: O valor do parâmetro a ser inserido no ? da consulta SQL.

    //O RowMapper é usado para converter a linha resultante do ResultSet em um objeto Funcionario.
    //Retorno do Objeto: O método retorna o objeto Funcionario mapeado.

    //metodo para listar os dependentes desse funcionario por aqui
}
