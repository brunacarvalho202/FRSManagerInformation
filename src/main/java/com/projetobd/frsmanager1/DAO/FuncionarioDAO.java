package com.projetobd.frsmanager1.DAO;

import com.projetobd.frsmanager1.models.Endereco;
import com.projetobd.frsmanager1.models.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class FuncionarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //mapear o objeto que vem do banco de dados para virar objeto no codigo
    // Defina um RowMapper como um método
    private final RowMapper<Funcionario> rowMapper = (ResultSet resultOfBD, int rowNum) -> {

        Funcionario funcionario = new Funcionario(); //criar o objeto para preencher com o mapeamento

        funcionario.setCpf(resultOfBD.getString("cpf")); //pegue o valor da coluna cpf desse resultado e jogue no objeto
        funcionario.setNome(resultOfBD.getString("nome"));
        //verificação de se é surprervisor ou nao para possiveis deleçoes
        funcionario.setCarteira_trabalho(resultOfBD.getString("carteira_trabalho"));

        // Mapeamento do telefone
        List<String> telefones = new ArrayList<>();
        String telefoneString = resultOfBD.getString("telefone");
        if (telefoneString != null) {
            // Aqui você pode ajustar como o telefone está armazenado no banco de dados.
            // Pode ser uma lista de strings separadas por algum caractere, como uma vírgula.
            // Aqui, estou supondo que os telefones estão separados por vírgula.
            telefones = Arrays.asList(telefoneString.split(","));
        }
        funcionario.setTelefone(telefones);

        // Mapeamento do endereco
        funcionario.setEndereco(new Endereco(resultOfBD.getString("endereco_rua"),
                                             resultOfBD.getString("endereco_bairro"),
                                             resultOfBD.getInt("endereco_numero")));

        // Mapeamento do supervisor
        funcionario.setSupervisorCpf(resultOfBD.getString("supervisor_cpf"));

        // Mapeamento dos dependentes (Comentado, adicionar implementação se necessário)
        // List<Dependente> dependentes = dependenteDAO.findByFuncionarioCpf(funcionario.getCpf());
        // funcionario.setDependentes(dependentes);

        return funcionario;
    };

    // Registrar um novo funcionario no banco
//    public boolean save(Funcionario funcionario) {
//        String sql = "INSERT INTO Funcionario (cpf, nome, IsSupervisor, carteira_trabalho, endereco_rua, endereco_bairro, endereco_numero, supervisor_cpf) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//        jdbcTemplate.update(sql,
//                funcionario.getCpf(),
//                funcionario.getNome(),
//                funcionario.getIsSupervisor(),
//                funcionario.getCarteira_trabalho(),
//                funcionario.getEndereco().getRua(),
//                funcionario.getEndereco().getBairro(),
//                funcionario.getEndereco().getNumero(),
//                funcionario.getSupervisorCpf() != null ? funcionario.getSupervisorCpf() : null);
//
//        return true;
//    }

    @Transactional // indicar que o método save deve ser executado dentro de uma transação pois esta com duas queries
    public boolean save(Funcionario funcionario) {
        String sqlFuncionario = "INSERT INTO Funcionario (cpf, nome, carteira_trabalho, endereco_rua, endereco_bairro, endereco_numero, supervisor_cpf) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlTelefone = "INSERT INTO Telefone (cpf_funcionario, telefone) VALUES (?, ?)";

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

        return true;
    }

//    // Atualizar um Funcionario existente
//    public void updateInfo(Funcionario funcionario) {
//        String sql = "UPDATE Funcionario SET nome = ?, carteira_trabalho = ?, endereco_rua = ?, endereco_bairro = ?, endereco_numero = ?, supervisor_cpf = ? WHERE cpf = ?";
//        jdbcTemplate.update(sql,
//                funcionario.getNome(),
//                funcionario.getCarteira_trabalho(),
//                funcionario.getEndereco().getRua(),
//                funcionario.getEndereco().getBairro(),
//                funcionario.getEndereco().getNumero(),
//                funcionario.getSupervisorCpf() != null ? funcionario.getSupervisorCpf() : null,
//                funcionario.getCpf());
//    }
//
//    public void updateTelefone(String cpf, String telefoneAntigo, String telefoneNovo) {
//        String sql = "UPDATE Telefone SET telefone = ? WHERE cpf_funcionario = ? AND telefone = ?";
//        jdbcTemplate.update(sql, telefoneNovo, cpf, telefoneAntigo);
//    }

//    public void updateFuncionario(Funcionario funcionario) {
//        String sql = "UPDATE Funcionario SET nome = ?, carteira_trabalho = ?, endereco_rua = ?, endereco_bairro = ?, endereco_numero = ?, supervisor_cpf = ? WHERE cpf = ?";
//        jdbcTemplate.update(sql, funcionario.getNome(), funcionario.getCarteira_trabalho(), funcionario.getEndereco().getRua(), funcionario.getEndereco().getBairro(), funcionario.getEndereco().getNumero(), funcionario.getSupervisorCpf(), funcionario.getCpf());
//
//        // Atualizar o telefone (se necessário)
//        if (funcionario.getTelefone() != null && !funcionario.getTelefone().isEmpty()) {
//            String sqlTelefone = "UPDATE Telefone SET telefone = ? WHERE cpf_funcionario = ?";
//            for (String telefone : funcionario.getTelefone()) {
//                jdbcTemplate.update(sqlTelefone, telefone, funcionario.getCpf());
//            }
//        }
//    }

    @Transactional
    public void updateFuncionario(Funcionario funcionario) {
        // Buscar o funcionário existente
        Funcionario existingFuncionario = findByCpf(funcionario.getCpf());

        //Condicionais aplicadas para nao sobrepor com nulo valores que nao querem ter atualizações
        // Mesclar os dados
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

        // Executar a atualização com os dados mesclados
        String sql = "UPDATE Funcionario SET nome = ?, carteira_trabalho = ?, endereco_rua = ?, endereco_bairro = ?, endereco_numero = ?, supervisor_cpf = ? WHERE cpf = ?";
        jdbcTemplate.update(sql, existingFuncionario.getNome(), existingFuncionario.getCarteira_trabalho(), existingFuncionario.getEndereco().getRua(), existingFuncionario.getEndereco().getBairro(), existingFuncionario.getEndereco().getNumero(), existingFuncionario.getSupervisorCpf(), existingFuncionario.getCpf());

        // Atualizar o telefone (se necessário)
        if (funcionario.getTelefone() != null && !funcionario.getTelefone().isEmpty()) {
            String sqlTelefone = "UPDATE Telefone SET telefone = ? WHERE cpf_funcionario = ?";
            for (String telefone : funcionario.getTelefone()) {
                jdbcTemplate.update(sqlTelefone, telefone, funcionario.getCpf());
            }
        }
    }



//    public void updateFuncionario(Funcionario funcionario) {
//        // Consulta o funcionário atual no banco de dados
//        Funcionario funcionarioAtual = findByCpf(funcionario.getCpf());
//
//        // Verifica se a consulta retornou um funcionário válido
//        if (funcionarioAtual != null) {
//            // Atualiza apenas os campos fornecidos no JSON
//            if (funcionario.getNome() == null) {
//                funcionario.setNome(funcionarioAtual.getNome());
//            }
//            if (funcionario.getCarteira_trabalho() == null) {
//                funcionario.setCarteira_trabalho(funcionarioAtual.getCarteira_trabalho());
//            }
//            if (funcionario.getEndereco() == null) {
//                funcionario.setEndereco(funcionarioAtual.getEndereco());
//            }
//            if (funcionario.getTelefone() == null) {
//                funcionario.setTelefone(funcionarioAtual.getTelefone());
//            }
//            if (funcionario.getSupervisorCpf() == null) {
//                funcionario.setSupervisorCpf(funcionarioAtual.getSupervisorCpf());
//            }
//
//            // Atualiza o funcionário no banco de dados
//            updateFuncionario(funcionario);
//        }
//    }



//    public void deleteById(String cpf) {
//        // Verifica se o funcionário é um supervisor
//        if (isSupervisor(cpf)) {
//            deletarSupervisor(cpf);
//        } else {
//            // Exclui os números de telefone associados ao funcionário pelo CPF
//            deleteTelefonesByCpf(cpf);
//
//            // Em seguida, exclui o funcionário da tabela Funcionario
//            String sql = "DELETE FROM Funcionario WHERE cpf = ?";
//            jdbcTemplate.update(sql, cpf);
//        }
//    }
//
//    public List<Funcionario> findFuncionariosBySupervisorCpf(String cpfSupervisor) {
//        String sql = "SELECT * FROM Funcionario WHERE supervisor_cpf = ?";
//        return jdbcTemplate.query(sql, new Object[]{cpfSupervisor}, new BeanPropertyRowMapper<>(Funcionario.class));
//    }
//
//    public void deletarSupervisor(String cpfSupervisor) {
//        // 1. Identificar os funcionários que têm o supervisor que está sendo excluído
//        List<Funcionario> funcionariosComSupervisor = findFuncionariosBySupervisorCpf(cpfSupervisor);
//
//        // 2. Atualizar o campo de supervisor para nulo nos funcionários identificados
//        for (Funcionario funcionario : funcionariosComSupervisor) {
//            funcionario.setSupervisorCpf(null);
//            this.updateInfo(funcionario);
//        }
//
//        // Agora você pode excluir o supervisor
//        deleteTelefonesByCpf(cpfSupervisor); // Exclui os números de telefone associados ao supervisor
//        String sql = "DELETE FROM Funcionario WHERE cpf = ?";
//        jdbcTemplate.update(sql, cpfSupervisor);
//    }

    //uso de procedure
    //na exclusão de um funcionário que é supervisor,os funcionários supervisionados por ele sao atualizaodos
    public void deleteFuncionario(String cpf) {
        String sql = "CALL DeleteSupervisor(?)";
        jdbcTemplate.update(sql, cpf);
    }

    public Funcionario findByCpf(String cpf) {
        String sql = "SELECT f.cpf, f.nome, f.carteira_trabalho, f.endereco_rua, f.endereco_bairro, f.endereco_numero, f.supervisor_cpf, t.telefone " +
                "FROM Funcionario f " +
                "LEFT JOIN Telefone t ON f.cpf = t.cpf_funcionario " +
                "WHERE f.cpf = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, cpf);
    }

    public List<Funcionario> findAll() {
        String sql = "SELECT f.cpf, f.nome, f.carteira_trabalho, f.endereco_rua, f.endereco_bairro, f.endereco_numero, f.supervisor_cpf, t.telefone " +
                "FROM Funcionario f " +
                "LEFT JOIN Telefone t ON f.cpf = t.cpf_funcionario";

        return jdbcTemplate.query(sql, rowMapper);
    }

    //sql: A consulta SQL a ser executada.
    //rowMapper: Um RowMapper que sabe como converter uma linha do ResultSet em um objeto do tipo Funcionario.
    //cpf: O valor do parâmetro a ser inserido no ? da consulta SQL.

    //O RowMapper é usado para converter a linha resultante do ResultSet em um objeto Funcionario.
    //Retorno do Objeto: O método retorna o objeto Funcionario mapeado.
}
