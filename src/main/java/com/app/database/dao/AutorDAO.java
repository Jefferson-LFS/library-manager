package com.app.database.dao;

import com.app.database.model.Autor;
import com.app.database.model.Emprestimo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO implements HelperDAO<Autor> {


    private final Connection conexao;


    private static final String SELECT_BY_ID = "SELECT * FROM autores WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM autores";
    


    private final PreparedStatement psSelectById;
    private final PreparedStatement psSelectAll;



    public AutorDAO(Connection conexao) throws SQLException {
        this.conexao = conexao;
        psSelectById = this.conexao.prepareStatement(SELECT_BY_ID);
        psSelectAll = this.conexao.prepareStatement(SELECT_ALL);


    }


    @Override
    public void insert(Autor autor) throws SQLException {


    }

    @Override
    public Autor selectById(Long id) throws SQLException {
        psSelectById.setLong(1, id);
        try (ResultSet rs = psSelectById.executeQuery()) {
            if (rs.next()) {
                return mapRow(rs);
            }
        }
        return null;
    }

    @Override
    public void deleteById(Long id) throws SQLException {

    }

    @Override
    public void update(Autor livro) throws SQLException {

    }

    @Override
    public List<Autor> selectAll() throws SQLException {
        List<Autor> autores = new ArrayList<>();
        try (ResultSet rs = psSelectAll.executeQuery()) {
            while (rs.next()) {
                autores.add(mapRow(rs));
            }
            return autores;
        }
    }

    private Autor mapRow(ResultSet rs) throws SQLException {
        return new Autor(
                rs.getLong("id"),
                rs.getString("nome")
        );
    }
}


