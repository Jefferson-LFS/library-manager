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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class LibraryServiceTest {

    private UsuarioDAO usuarioDAOMock;
    private LivroDAO livroDAOMock;
    private AutorDAO autorDAOMock;
    private EmprestimoDAO emprestimoDAOMock;
    private LibraryService service;

    @BeforeEach
    void setUp() {
        usuarioDAOMock    = mock(UsuarioDAO.class);
        livroDAOMock      = mock(LivroDAO.class);
        autorDAOMock      = mock(AutorDAO.class);
        emprestimoDAOMock = mock(EmprestimoDAO.class);

        service = new LibraryService(usuarioDAOMock, livroDAOMock, autorDAOMock, emprestimoDAOMock);
    }

    // ─── autenticar ────────────────────────────────────────────────────────────

    @Test
    void autenticar_deveria_retornar_usuario_com_credenciais_validas() throws SQLException {
        Usuario esperado = new Usuario(1L, "Joao", "joao", "leitor", true);
        when(usuarioDAOMock.selectByLoginESenha(any(Usuario.class))).thenReturn(esperado);

        Usuario resultado = service.autenticar("joao", "senha123");

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void autenticar_deveria_retornar_null_com_credenciais_invalidas() throws SQLException {
        when(usuarioDAOMock.selectByLoginESenha(any(Usuario.class))).thenReturn(null);

        Usuario resultado = service.autenticar("joao", "senhaerrada");

        assertNull(resultado);
    }

    @Test
    void autenticar_deveria_enviar_senha_como_hash_md5() throws SQLException {
        service.autenticar("joao", "senha123");

        verify(usuarioDAOMock).selectByLoginESenha(argThat(u ->
                u.getSenha().equals(HashUtils.criarMD5("senha123"))
        ));
    }

    // ─── listarLivrosDisponiveis ────────────────────────────────────────────────

    @Test
    void listarLivrosDisponiveis_deveria_retornar_lista_do_dao() throws SQLException {
        List<Livro> livros = List.of(new Livro(), new Livro());
        when(livroDAOMock.selectAll()).thenReturn(livros);

        List<Livro> resultado = service.listarLivrosDisponiveis();

        assertEquals(2, resultado.size());
    }

    @Test
    void listarLivrosDisponiveis_deveria_retornar_lista_vazia_quando_nao_ha_livros() throws SQLException {
        when(livroDAOMock.selectAll()).thenReturn(List.of());

        List<Livro> resultado = service.listarLivrosDisponiveis();

        assertTrue(resultado.isEmpty());
    }

    // ─── buscarNomeAutor ───────────────────────────────────────────────────────

    @Test
    void buscarNomeAutor_deveria_retornar_nome_quando_autor_existe() throws SQLException {
        Autor autor = new Autor(1L, "Machado de Assis");
        when(autorDAOMock.selectById(1L)).thenReturn(autor);

        String nome = service.buscarNomeAutor(1L);

        assertEquals("Machado de Assis", nome);
    }

    @Test
    void buscarNomeAutor_deveria_retornar_desconhecido_quando_autor_nao_existe() throws SQLException {
        when(autorDAOMock.selectById(anyLong())).thenReturn(null);

        String nome = service.buscarNomeAutor(99L);

        assertEquals("Autor desconhecido", nome);
    }

    // ─── realizarEmprestimo ────────────────────────────────────────────────────

    @Test
    void realizarEmprestimo_deveria_inserir_emprestimo_e_atualizar_disponibilidade() throws SQLException {
        Usuario usuario = new Usuario(1L, "Joao", "joao", "leitor", true);
        when(livroDAOMock.selectById(10L)).thenReturn(new Livro());

        service.realizarEmprestimo(usuario, 10L);

        verify(emprestimoDAOMock).insert(any(Emprestimo.class));
        verify(livroDAOMock).updateByIdLivroDisponivel(false, 10L);
    }

    @Test
    void realizarEmprestimo_deveria_lancar_excecao_para_livro_inexistente() throws SQLException {
        Usuario usuario = new Usuario(1L, "Joao", "joao", "leitor", true);
        when(livroDAOMock.selectById(99L)).thenReturn(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.realizarEmprestimo(usuario, 99L));

        assertEquals("Livro não encontrado: 99", ex.getMessage());
    }

    @Test
    void realizarEmprestimo_nao_deveria_inserir_quando_livro_nao_existe() throws SQLException {
        Usuario usuario = new Usuario(1L, "Joao", "joao", "leitor", true);
        when(livroDAOMock.selectById(anyLong())).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
                () -> service.realizarEmprestimo(usuario, 99L));

        verify(emprestimoDAOMock, never()).insert(any());
        verify(livroDAOMock, never()).updateByIdLivroDisponivel(anyBoolean(), anyLong());
    }
}
