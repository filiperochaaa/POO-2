package br.com.biblioteca.model;

import java.util.Objects;

public class Livro {
    private Integer id;
    private String titulo;
    private String autor;
    private Integer anoPublicacao;
    private String editora;
    private String isbn;

    public Livro() {}

    public Livro(Integer id, String titulo, String autor, Integer anoPublicacao, String editora, String isbn) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.editora = editora;
        this.isbn = isbn;
    }

    public Livro(String titulo, String autor, Integer anoPublicacao, String editora, String isbn) {
        this(null, titulo, autor, anoPublicacao, editora, isbn);
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public Integer getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(Integer anoPublicacao) { this.anoPublicacao = anoPublicacao; }
    public String getEditora() { return editora; }
    public void setEditora(String editora) { this.editora = editora; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", anoPublicacao=" + anoPublicacao +
                ", editora='" + editora + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(id, livro.id) && Objects.equals(isbn, livro.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn);
    }
}
