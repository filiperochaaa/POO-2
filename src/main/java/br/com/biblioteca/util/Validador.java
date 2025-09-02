package br.com.biblioteca.util;

public class Validador {

    public static void exigirNaoVazio(String valor, String nomeCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo '" + nomeCampo + "' é obrigatório e não pode ser vazio.");
        }
    }

    public static void exigirTamanhoMax(String valor, int max, String nomeCampo) {
        if (valor != null && valor.length() > max) {
            throw new IllegalArgumentException("O campo '" + nomeCampo + "' excede o tamanho máximo de " + max + " caracteres.");
        }
    }
}
