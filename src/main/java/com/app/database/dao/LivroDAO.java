package com.app.database.dao;

import com.app.database.model.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO implements HelperDAO<Livro> {


    private final Connection conexao;



    private static final String SELECT_BY_ID = "SELECT * FROM usuarios WHERE id = ?";

    private static final String INSERT_LIVRO = "INSERT INTO livros (titulo, autor_id) VALUES (?, ?)";

    private static final String INSERT_LIVRO_AUTORES = "INSERT INTO livros_autores (livro_id, autor_id) VALUES (?, ?)";

    private static final String UPDATE_BY_ID_LIVRO_DISPONIVEL = "UPDATE livros SET disponivel = ? WHERE id = ?";

    private static final String SELECT_ALL_BOOKS_AVAILABLE = "SELECT\n" +
            "    l.id,\n" +
            "    l.titulo,\n" +
            "    a.id AS autorId\n" +
            "FROM livros l\n" +
            "JOIN livros_autores la ON la.livro_id = l.id\n" +
            "JOIN autores a ON a.id = la.autor_id\n" +
            "WHERE disponivel = true\n" +
            "ORDER BY l.id;";




    private final PreparedStatement psSelectById;
    private final PreparedStatement psSelectAllBooksAvailble;
    private final PreparedStatement psInsertLivro;
    private final PreparedStatement psUpdateByIdLivroDisponivel;


    public LivroDAO(Connection conexao) throws SQLException {
        this.conexao = conexao;
        psSelectById = this.conexao.prepareStatement(SELECT_BY_ID);
        psSelectAllBooksAvailble = this.conexao.prepareStatement(SELECT_ALL_BOOKS_AVAILABLE);
        psInsertLivro = this.conexao.prepareStatement(INSERT_LIVRO);
        psUpdateByIdLivroDisponivel = this.conexao.prepareStatement(UPDATE_BY_ID_LIVRO_DISPONIVEL);

    }


    @Override
    public void insert(Livro livro) throws SQLException {
        psInsertLivro.setString(1, livro.getTitulo());
        psInsertLivro.setLong(2, livro.getAutorId());
        psInsertLivro.executeUpdate();

    }

    @Override
    public Livro selectById(Long id) throws SQLException {
        return null;
    }

    @Override
    public void deleteById(Long id) throws SQLException {

    }

    @Override
    public void update(Livro livro) throws SQLException {

    }


    public void updateByIdLivroDisponivel(Boolean disponivel, Long id) throws SQLException {
        psUpdateByIdLivroDisponivel.setBoolean(1, disponivel);
        psUpdateByIdLivroDisponivel.setLong(2, id);
        psUpdateByIdLivroDisponivel.executeUpdate();
    }

    @Override
    public List<Livro> selectAll() throws SQLException {
        List<Livro> livros = new ArrayList<>();
        try (ResultSet rs = psSelectAllBooksAvailble.executeQuery()) {
            while (rs.next()) {
                livros.add(mapRow(rs));
            }
            return livros;
        }
    }

    private Livro mapRow(ResultSet rs) throws SQLException {
        return new Livro(
                rs.getLong("id"),
                rs.getString("titulo"),
                rs.getLong("autorId")
        );
    }
}


