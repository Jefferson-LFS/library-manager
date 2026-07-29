package com.app.view;

import com.app.database.ConnectionFactory;
import com.app.database.dao.AutorDAO;
import com.app.database.dao.EmprestimoDAO;
import com.app.database.dao.LivroDAO;
import com.app.database.dao.UsuarioDAO;
import com.app.database.model.Livro;
import com.app.database.model.Usuario;
import com.app.exceptions.BookNotFoundException;
import com.app.exceptions.BookUnavailableException;
import com.app.service.LibraryService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            Connection conexao = ConnectionFactory.getConnection();
            LOGGER.info("Conexão com o banco de dados PostgreSQL estabelecida com sucesso!");

            LibraryService service = new LibraryService(
                    new UsuarioDAO(conexao),
                    new LivroDAO(conexao),
                    new AutorDAO(conexao),
                    new EmprestimoDAO(conexao)
            );

            System.out.println("Informe seu login e senha: ");

            System.out.print("login:");
            String login = scanner.nextLine();

            System.out.print("senha:");
            String password = scanner.nextLine();

            Usuario usuarioLogin = service.autenticar(login, password);
            while (usuarioLogin == null) {
                System.out.println("Usuário ou senha inválidos, preencha novamente!");

                System.out.print("login:");
                login = scanner.nextLine();

                System.out.print("senha:");
                password = scanner.nextLine();

                usuarioLogin = service.autenticar(login, password);
            }

            System.out.println("##### Bem-vindo Library Manager! ####");
            System.out.println("Gostaria de ver os livros disponíveis? Digite 'SIM', ou 'NÃO' para sair do programa: ");

            Boolean solicitouEncerramento = false;

            while (!solicitouEncerramento) {
                String input = scanner.nextLine();

                if (input.equals("NÃO")) {
                    solicitouEncerramento = true;
                    break;
                }

                System.out.println("| ID | TITULO | AUTOR |");
                for (Livro livro : service.listarLivrosDisponiveis()) {
                    System.out.printf("[ %d; '%s'; '%s']%n",
                            livro.getId(),
                            livro.getTitulo(),
                            service.buscarNomeAutor(livro.getAutorId()));
                }

                System.out.println("Digite o id do livro que deseja realizar o empréstimo: ");
                // TODO: Validar o valor do id do livro
                Long inputIdBook = scanner.nextLong();

                try {
                    service.realizarEmprestimo(usuarioLogin, inputIdBook);
                    System.out.println("Emprestimo efetuado com sucesso!");
                } catch (BookNotFoundException | BookUnavailableException ex) {
                    System.out.println(ex.getMessage());
                }

                break;
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao acessar o banco de dados. Tente novamente.");
            LOGGER.severe("Erro de banco de dados: " + ex.getMessage());
        } catch (RuntimeException ex) {
            System.out.println("Erro inesperado. Tente novamente.");
            LOGGER.severe("Erro inesperado: " + ex.getMessage());
        }
    }
}
