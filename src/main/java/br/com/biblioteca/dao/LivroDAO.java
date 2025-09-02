package br.com.biblioteca.dao;

import br.com.biblioteca.model.Livro;
import br.com.biblioteca.util.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static br.com.biblioteca.util.ConexaoMySQL.getConnection;

public class LivroDAO {

    public void create(Livro livro) throws SQLException {
        String sql = "INSERT INTO livros (titulo, autor, anoPublicacao, editora, isbn) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getAutor());
            if (livro.getAnoPublicacao() == null) ps.setNull(3, Types.INTEGER);
            else ps.setInt(3, livro.getAnoPublicacao());
            ps.setString(4, livro.getEditora());
            ps.setString(5, livro.getIsbn());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    livro.setId(rs.getInt(1));
                }
            }
            LoggerUtil.info("Livro cadastrado: " + livro);
        }
    }

    public List<Livro> findAll() throws SQLException {
        String sql = "SELECT id, titulo, autor, anoPublicacao, editora, isbn FROM livros ORDER BY id";
        List<Livro> lista = new ArrayList<>(); //Aqui é usada a List<Livro> (interface) e a implementação ArrayList.
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(map(rs));
            }
        }
        return lista;
    }

    public Livro findById(int id) throws SQLException {
        String sql = "SELECT id, titulo, autor, anoPublicacao, editora, isbn FROM livros WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public List<Livro> findByTitulo(String termo) throws SQLException { //implementação de araylist
        String sql = "SELECT id, titulo, autor, anoPublicacao, editora, isbn FROM livros WHERE titulo LIKE ? ORDER BY titulo";
        List<Livro> lista = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + termo + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(map(rs));
            }
        }
        return lista;
    }

    public List<Livro> findByAutor(String termo) throws SQLException { //implementação de araylist
        String sql = "SELECT id, titulo, autor, anoPublicacao, editora, isbn FROM livros WHERE autor LIKE ? ORDER BY autor";
        List<Livro> lista = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + termo + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(map(rs));
            }
        }
        return lista;
    }

    public boolean update(Livro livro) throws SQLException {
        String sql = "UPDATE livros SET titulo=?, autor=?, anoPublicacao=?, editora=?, isbn=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getAutor());
            if (livro.getAnoPublicacao() == null) ps.setNull(3, Types.INTEGER);
            else ps.setInt(3, livro.getAnoPublicacao());
            ps.setString(4, livro.getEditora());
            ps.setString(5, livro.getIsbn());
            ps.setInt(6, livro.getId());
            int linhas = ps.executeUpdate();
            if (linhas > 0) {
                LoggerUtil.info("Livro atualizado: " + livro);
                return true;
            }
        }
        return false;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM livros WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int linhas = ps.executeUpdate();
            if (linhas > 0) {
                LoggerUtil.info("Livro removido. id=" + id);
                return true;
            }
        }
        return false;
    }

    private Livro map(ResultSet rs) throws SQLException {
        Livro l = new Livro();
        l.setId(rs.getInt("id"));
        l.setTitulo(rs.getString("titulo"));
        l.setAutor(rs.getString("autor"));
        int ano = rs.getInt("anoPublicacao");
        l.setAnoPublicacao(rs.wasNull() ? null : ano);
        l.setEditora(rs.getString("editora"));
        l.setIsbn(rs.getString("isbn"));
        return l;
    }
}
