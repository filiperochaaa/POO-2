package br.com.biblioteca.main;

import br.com.biblioteca.dao.LivroDAO;
import br.com.biblioteca.model.Livro;
import br.com.biblioteca.util.LoggerUtil;
import br.com.biblioteca.util.Validador;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private static final Scanner sc = new Scanner(System.in);
    private static final LivroDAO dao = new LivroDAO();

    public static void main(String[] args) {
        System.out.println("=== Sistema de Biblioteca (CRUD) ===");
        int opcao;
        do {
            menu();
            opcao = lerInteiro("Escolha uma opção: ");
            try {
                switch (opcao) {
                    case 1 -> cadastrarLivro();
                    case 2 -> listarLivros();
                    case 3 -> atualizarLivro();
                    case 4 -> removerLivro();
                    case 5 -> pesquisar();
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e.getMessage());
                LoggerUtil.error("Erro na operação do menu", e);
            }
        } while (opcao != 0);
    }

    private static void menu() {
        System.out.println();
        System.out.println("1 - Cadastrar livro");
        System.out.println("2 - Listar livros");
        System.out.println("3 - Atualizar livro");
        System.out.println("4 - Remover livro");
        System.out.println("5 - Pesquisar (título/autor)");
        System.out.println("0 - Sair");
    }

    private static void cadastrarLivro() throws SQLException {
        System.out.println("-- Cadastro de Livro --");
        String titulo = lerStringObrig("Título");
        String autor = lerStringObrig("Autor");
        Integer ano = lerInteiroOpc("Ano de Publicação (opcional, Enter para pular)");
        String editora = lerStringOpc("Editora (opcional)");
        String isbn = lerStringOpc("ISBN (opcional/único)");

        Livro l = new Livro(titulo, autor, ano, editora, isbn);
        dao.create(l);
        System.out.println("Livro cadastrado com ID: " + l.getId());
    }

    private static void listarLivros() throws SQLException {
        System.out.println("-- Lista de Livros --");
        List<Livro> lista = dao.findAll();
        if (lista.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private static void atualizarLivro() throws SQLException {
        System.out.println("-- Atualizar Livro --");
        int id = lerInteiro("Informe o ID do livro a atualizar: ");
        Livro existente = dao.findById(id);
        if (existente == null) {
            System.out.println("Livro não encontrado.");
            return;
        }
        System.out.println("Atual atual: " + existente);

        String titulo = lerStringObrig("Novo Título");
        String autor = lerStringObrig("Novo Autor");
        Integer ano = lerInteiroOpc("Novo Ano de Publicação (opcional)");
        String editora = lerStringOpc("Nova Editora (opcional)");
        String isbn = lerStringOpc("Novo ISBN (opcional/único)");

        existente.setTitulo(titulo);
        existente.setAutor(autor);
        existente.setAnoPublicacao(ano);
        existente.setEditora(editora);
        existente.setIsbn(isbn);

        if (dao.update(existente)) {
            System.out.println("Livro atualizado com sucesso.");
        } else {
            System.out.println("Falha ao atualizar livro.");
        }
    }

    private static void removerLivro() throws SQLException {
        System.out.println("-- Remover Livro --");
        int id = lerInteiro("Informe o ID do livro a remover: ");
        if (dao.delete(id)) {
            System.out.println("Livro removido com sucesso.");
        } else {
            System.out.println("Livro não encontrado.");
        }
    }

    private static void pesquisar() throws SQLException {
        System.out.println("-- Pesquisar --");
        System.out.println("1 - Por Título");
        System.out.println("2 - Por Autor");
        int op = lerInteiro("Escolha: ");
        String termo = lerStringObrig("Informe o termo de busca");
        List<Livro> resultado = switch (op) {
            case 1 -> dao.findByTitulo(termo);
            case 2 -> dao.findByAutor(termo);
            default -> List.of();
        };
        if (resultado.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
        } else {
            resultado.forEach(System.out::println);
        }
    }

    /* ===== Helpers ===== */

    private static int lerInteiro(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                String linha = sc.nextLine();
                return Integer.parseInt(linha.trim());
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }

    private static Integer lerInteiroOpc(String msg) {
        while (true) {
            try {
                System.out.print(msg + ": ");
                String linha = sc.nextLine();
                if (linha == null || linha.trim().isEmpty()) return null;
                return Integer.parseInt(linha.trim());
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro ou pressione Enter para pular.");
            }
        }
    }

    private static String lerStringObrig(String nomeCampo) {
        while (true) {
            System.out.print(nomeCampo + ": ");
            String v = sc.nextLine();
            try {
                Validador.exigirNaoVazio(v, nomeCampo);
                return v.trim();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static String lerStringOpc(String nomeCampo) {
        System.out.print(nomeCampo + ": ");
        String v = sc.nextLine();
        return (v == null) ? null : v.trim();
    }
}
