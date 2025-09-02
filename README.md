# Biblioteca CRUD (Java + MySQL)

Aplicativo de console para gerenciar livros de uma biblioteca usando JDBC.

## Requisitos
- Java 17+
- Maven 3.8+
- MySQL 8+
- Driver será baixado via Maven (mysql-connector-java)

## Como configurar
1. **Crie o banco e tabela** no MySQL executando o script em `sql/create_database.sql`.
2. **Atualize as credenciais** em `src/main/resources/db.properties`.
3. **Rodar pelo Maven**:
   ```bash
   mvn clean compile
   mvn exec:java
   ```

## Estrutura dos pacotes
- `model` → `Livro`
- `dao` → `LivroDAO` (CRUD + buscas)
- `util` → `ConexaoMySQL`, `LoggerUtil`, `Validador`
- `main` → `Principal` (menu CLI)

## Logs
As operações são registradas em `logs/app.log`.

## Observações
- Pesquisas por título/autor são *contains* (LIKE).
- Valida entrada de dados (campos obrigatórios não vazios).

