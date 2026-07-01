package com.app.service;

import com.app.database.dao.AutorDAO;
import com.app.database.dao.EmprestimoDAO;
import com.app.database.dao.LivroDAO;
import com.app.database.dao.UsuarioDAO;
import com.app.database.model.Autor;
import com.app.database.model.Emprestimo;
import com.app.database.model.Livro;
import com.app.database.model.Usuario;
import com.app.util.HashUtils;

import java.sql.SQLException;
import java.util.List;

public class LibraryService {

    private final UsuarioDAO usuarioDAO;
    private final LivroDAO livroDAO;
    private final AutorDAO autorDAO;
    private final EmprestimoDAO emprestimoDAO;

    public LibraryService(UsuarioDAO usuarioDAO, LivroDAO livroDAO, AutorDAO autorDAO, EmprestimoDAO emprestimoDAO) {
        this.usuarioDAO = usuarioDAO;
        this.livroDAO = livroDAO;
        this.autorDAO = autorDAO;
        this.emprestimoDAO = emprestimoDAO;
    }

    public Usuario autenticar(String login, String senha) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setLogin(login);
        usuario.setSenha(HashUtils.criarMD5(senha));
        return usuarioDAO.selectByLoginESenha(usuario);
    }

    public List<Livro> listarLivrosDisponiveis() throws SQLException {
        return livroDAO.selectAll();
    }

    public String buscarNomeAutor(Long autorId) throws SQLException {
        Autor autor = autorDAO.selectById(autorId);
        return autor != null ? autor.getNome() : "Autor desconhecido";
    }

    public void realizarEmprestimo(Usuario usuario, Long livroId) throws SQLException {
        if (livroDAO.selectById(livroId) == null) {
            throw new IllegalArgumentException("Livro não encontrado: " + livroId);
        }
        emprestimoDAO.insert(new Emprestimo(livroId, usuario.getId()));
        livroDAO.updateByIdLivroDisponivel(false, livroId);
    }
}
