package com.app.database.dao;

import com.app.database.model.Emprestimo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO implements HelperDAO<Emprestimo> {


    private final Connection conexao;



    private static final String SELECT_BY_ID = "SELECT * FROM usuarios WHERE id = ?";

    private static final String INSERT_EMPRESTIMO = "INSERT INTO emprestimos (livro_id, usuario_id) VALUES (?, ?)";
    
    private final PreparedStatement psSelectById;
    private final PreparedStatement psInsertEmprestimo;
    private final PreparedStatement psSelectAllBooksLoans;

    private static final String SELECT_ALL_BOOKS_LOANS = "SELECT\n" +
            "    s.id,\n" +
            "    s.livro_id,\n" +
            "    l.titulo AS tituloLivro\n" +
            "    u.titulo AS nomeUsuario\n" +
            "FROM emprestimos s\n" +
            "INNER JOIN usuarios u ON u.id = s.usuario_id\n" +
            "INNER JOIN livros l  ON l.id = s.livro_id\n" +
            "ORDER BY s.id;";


    public EmprestimoDAO(Connection conexao) throws SQLException {
        this.conexao = conexao;
        psSelectById = this.conexao.prepareStatement(SELECT_BY_ID);
        psInsertEmprestimo = this.conexao.prepareStatement(INSERT_EMPRESTIMO);
        psSelectAllBooksLoans = this.conexao.prepareStatement(SELECT_ALL_BOOKS_LOANS);

    }


    @Override
    public void insert(Emprestimo emprestimo) throws SQLException {
        psInsertEmprestimo .setLong(1, emprestimo.getLivroId());
        psInsertEmprestimo .setLong(2, emprestimo.getUsuarioId());
        psInsertEmprestimo .executeUpdate();

    }


    @Override
    public Emprestimo selectById(Long id) throws SQLException {
        return null;
    }

    @Override
    public void deleteById(Long id) throws SQLException {

    }

    @Override
    public void update(Emprestimo emprestimo) throws SQLException {

    }

    @Override
    public List<Emprestimo> selectAll() throws SQLException {
        List<Emprestimo> emprestimos = new ArrayList<>();
        try (ResultSet rs = psSelectAllBooksLoans.executeQuery()) {
            while (rs.next()) {
                emprestimos.add(mapRow(rs));
            }
            return emprestimos;
        }
    }

    private Emprestimo mapRow(ResultSet rs) throws SQLException {
        return new Emprestimo(
                rs.getLong("id"),
                rs.getLong("livro_id"),
                rs.getLong("usuario_id")
        );
    }


}


