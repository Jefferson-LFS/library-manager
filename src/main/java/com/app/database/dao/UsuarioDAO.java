package com.app.database.dao;

import com.app.database.model.Emprestimo;
import com.app.database.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements HelperDAO<Usuario> {


    private final Connection conexao;

    private static final String INSERT_USUARIO = "INSERT INTO usuarios (nome, login, senha, perfil, email, telefone, status, data_nascimento) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID = "SELECT * FROM usuarios WHERE id = ?";

    private static final String SELECT_ALL = "SELECT * FROM usuarios";

    private static final String SELECT_BY_LOGIN_SENHA = "SELECT * FROM usuarios WHERE login = ? AND senha = ?";

    private static final String UPDATE_USUARIO = "UPDATE usuarios SET senha = ?, perfil = ? WHERE id = ?";

    private static final String DELETE_USUARIO = "DELETE FROM usuarios WHERE id = ?";
    
    

    private final PreparedStatement psInsertUsuario;
    private final PreparedStatement psSelectById;
    private final PreparedStatement psSelectAll;
    private final PreparedStatement psSelectByLoginESenha;
    private final PreparedStatement psUpdateUsuario;
    private final PreparedStatement psDeleteUsuario;

    public UsuarioDAO(Connection conexao) throws SQLException {
        this.conexao = conexao;
        psInsertUsuario = this.conexao.prepareStatement(INSERT_USUARIO);
        psSelectById = this.conexao.prepareStatement(SELECT_BY_ID);
        psSelectAll = this.conexao.prepareStatement(SELECT_ALL);
        psSelectByLoginESenha = this.conexao.prepareStatement(SELECT_BY_LOGIN_SENHA);
        psUpdateUsuario = this.conexao.prepareStatement(UPDATE_USUARIO);
        psDeleteUsuario = this.conexao.prepareStatement(DELETE_USUARIO);
    }


    @Override
    public void insert(Usuario usuario) throws SQLException {
        psInsertUsuario.setString(1, usuario.getNome());
        psInsertUsuario.setString(2, usuario.getSenha());
        psInsertUsuario.setString(3, usuario.getLogin());
        psInsertUsuario.setString(4, usuario.getPerfil());
        psInsertUsuario.setString(5, usuario.getEmail());
        psInsertUsuario.setString(6, usuario.getTelefone());
        psInsertUsuario.setBoolean(7, usuario.getStatus());
        psInsertUsuario.setDate(8, Date.valueOf(usuario.getDataNascimento()));
        psInsertUsuario.executeUpdate();
    }

    public boolean selectByLoginESenha(Usuario usuario) throws SQLException {

        psSelectByLoginESenha.setString(1, usuario.getLogin());
        psSelectByLoginESenha.setString(2, usuario.getSenha());
        ResultSet resultado = psSelectByLoginESenha.executeQuery();

        return resultado != null && resultado.next();
    }

    @Override
    public Usuario selectById(Long id) throws SQLException {
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
        psDeleteUsuario.setLong(1, id);
    }

    @Override
    public void update(Usuario usuario) throws SQLException {
        psUpdateUsuario.setString(1, usuario.getSenha());
        psUpdateUsuario.setString(2, usuario.getPerfil());
        psUpdateUsuario.setLong(3, usuario.getId());
        psUpdateUsuario.executeUpdate();
    }

    @Override
    public List<Usuario> selectAll() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        try (ResultSet rs = psSelectAll.executeQuery()) {
            while (rs.next()) {
                usuarios.add(mapRow(rs));
            }
            return usuarios;
        }
    }

    private Usuario mapRow(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getLong("id"),
                rs.getString("nome"),
                rs.getString("login"),
                rs.getString("perfil"),
                rs.getBoolean("status")
        );
    }
}
