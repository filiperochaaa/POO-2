-- Criação do schema e tabela de livros
CREATE DATABASE IF NOT EXISTS biblioteca CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE biblioteca;

CREATE TABLE IF NOT EXISTS livros (
  id INT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(255) NOT NULL,
  autor VARCHAR(255) NOT NULL,
  anoPublicacao INT,
  editora VARCHAR(255),
  isbn VARCHAR(32) UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
