package com.app.view;

import com.app.database.ConnectionFactory;
import com.app.database.dao.*;
import com.app.database.model.Autor;
import com.app.database.model.Emprestimo;
import com.app.database.model.Livro;
import com.app.database.model.Usuario;
import com.app.util.HashUtils;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import java.util.logging.Logger;

public class Main {


    private static final Logger LOGGER =
            Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws SQLException {


        Scanner scanner = new Scanner(System.in);
        Connection conexao = null;

        Connection getConnectionDataBase = requestConnectionDataBase(conexao);

        Boolean solicitouEncerramentoDoProgama = false;

        System.out.println("Informe seu login e senha: ");

        System.out.print("login:");
        String login = scanner.nextLine();

        System.out.print("senha:");
        String password = scanner.nextLine();

        Usuario usuarioLogin = setUserAndPassword(login, password);

        UsuarioDAO usuarioDAO = new UsuarioDAO(getConnectionDataBase);

        Boolean isValidCredentials = checkLoginAndPassword(usuarioLogin, usuarioDAO);

        while(!isValidCredentials) {
            System.out.println("Usuário ou senha inválidos, preencha novamente!");

            System.out.print("login:");
            login = scanner.nextLine();

            System.out.print("senha:");
            password = scanner.nextLine();

            usuarioLogin = setUserAndPassword(login, password);

            isValidCredentials = checkLoginAndPassword(usuarioLogin, usuarioDAO);
        }


        System.out.println("##### Bem-vindo Library Manager! #### ");

        System.out.println("Gostaria de ver os livros disponíveis? Digite 'SIM', ou 'NÃO' para sair do programa: ");

        LivroDAO livroDAO = new LivroDAO(getConnectionDataBase);
        AutorDAO autorDAO = new AutorDAO(getConnectionDataBase);
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO(getConnectionDataBase);

        while (!solicitouEncerramentoDoProgama) {

            String input = scanner.nextLine();
            solicitouEncerramentoDoProgama = input.equals("NÃO") ?
                    solicitouEncerramentoDoProgama = true : solicitouEncerramentoDoProgama;
            if (solicitouEncerramentoDoProgama) {
                break;
            }
            ShowAvailableBooks (livroDAO, autorDAO);
            System.out.println("Digite o id do livro que deseja realizar o empréstimo: ");
            Long inputIdBook = scanner.nextLong();
            checkoutBook(usuarioLogin, inputIdBook, livroDAO, emprestimoDAO);
            break;
        }

    }

    private static Connection connectToDataBase (Connection conexao) throws SQLException {
        conexao = ConnectionFactory.getConnection(
                "192.168.1.71",
                5432,
                "livraria",
                "postgres",
                "Mudar@123");

        return conexao;

    }

    private static Usuario setUserAndPassword (String login, String password) {
        Usuario user = new Usuario();

        user.setLogin(login);
        user.setSenha(HashUtils.criarMD5(password));

        return user;

    }

    private static Boolean  checkLoginAndPassword (Usuario usuarioLogin, UsuarioDAO usuarioDAO) throws SQLException {

        if(!usuarioDAO.selectByLoginESenha(usuarioLogin)) {
            return false;
        }
        return true;
    }

    private static Boolean checkIfBookExists (Long livroId, LivroDAO livroDAO) throws SQLException {

        if(!(livroDAO.selectById(livroId) == null)) {
            return false;
        }
        return true;

    }

    private static void checkoutBook (Usuario usuarioLogin, Long livroId, LivroDAO livroDAO, EmprestimoDAO emprestimoDAO) throws SQLException {

        Boolean bookExists =  checkIfBookExists(livroId, livroDAO);

        if (!bookExists){
            System.out.println("Id do livro não encontrado! ");
        }
        emprestimoDAO.insert(
                new Emprestimo(livroId, usuarioLogin.getId())
        );
        System.out.println("Emprestimo efetuado com sucesso! ");


    }

    private static Connection requestConnectionDataBase (Connection conexao)  {
        try {
                conexao = connectToDataBase(conexao);
                LOGGER.info("Conexão com o banco de dados PostgreSQL estabelecida com sucesso!");
                return conexao;

        } catch (SQLException ex) {
                String message = "Não foi possivel conectar com o banco de dados: ";
                logError(message, ex);
        }

        return null;
    }


    private static void ShowAvailableBooks (LivroDAO livroDAO, AutorDAO autorDAO) throws SQLException {
        System.out.println("| ID | TITULO | AUTOR |");
        for (Livro livro : livroDAO.selectAll()) {
            System.out.printf(
                    """
                    [ %d; '%s'; '%s']
                    """,
                    livro.getId(),
                    livro.getTitulo(),
                    autorDAO.selectById(livro.getAutorId()).getNome()
            );
        }

    }

    private static void logError(Exception e){
            LOGGER.severe(e.getMessage());
    }

    private static void logError(String message, Exception e){
        LOGGER.severe(message + e.getMessage());
    }
}
