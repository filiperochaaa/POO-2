package br.com.biblioteca.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoMySQL {

    private static Properties carregarProps() {
        try (InputStream in = ConexaoMySQL.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) throw new RuntimeException("Arquivo db.properties não encontrado em resources.");
            Properties p = new Properties();
            p.load(in);
            return p;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar db.properties: " + e.getMessage(), e); //Captura problemas de arquivo e configuração e retorna mensagens.
        }
    }

    public static Connection getConnection() throws SQLException {
        Properties p = carregarProps();
        String url = p.getProperty("db.url");
        String user = p.getProperty("db.user");
        String pass = p.getProperty("db.password");
        return DriverManager.getConnection(url, user, pass);
    }
}
