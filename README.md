# Library Manager

![Java](https://img.shields.io/badge/Java-21-orange?logo=java)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?logo=apachemaven)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-316192?logo=postgresql)
![Status](https://img.shields.io/badge/Status-Em%20desenvolvimento-yellow)

Sistema de gerenciamento de biblioteca desenvolvido em **Java 21**, com **Maven**, **JDBC** e **PostgreSQL**.

O projeto tem como objetivo permitir o controle de **usuários**, **livros**, **autores** e **empréstimos**, seguindo uma organização por camadas e com foco em boas práticas de estruturação para aplicações Java.

---

## Sumário

- [Visão geral](#visão-geral)
- [Funcionalidades implementadas](#funcionalidades-implementadas)
- [Funcionalidades pendentes](#funcionalidades-pendentes)
- [Tecnologias utilizadas](#tecnologias-utilizadas)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Pré-requisitos](#pré-requisitos)
- [Como executar](#como-executar)
- [Banco de dados](#banco-de-dados)
- [Boas práticas aplicadas](#boas-práticas-aplicadas)
- [Melhorias futuras](#melhorias-futuras)
- [Contribuição](#contribuição)
- [Licença](#licença)

---

## Visão geral

O **Library Manager** é uma aplicação de gerenciamento de biblioteca que já possui uma base funcional para:

- autenticação de usuário;
- consulta de livros disponíveis;
- registro de empréstimos;
- persistência em banco de dados.

A aplicação está em evolução e ainda precisa implementar parte dos requisitos comportamentais para se tornar uma solução completa.

---

## Funcionalidades implementadas

Com base no estado atual do projeto, as seguintes funcionalidades já estão presentes:

### Autenticação
- Login com usuário e senha
- Validação de credenciais no banco de dados
- Criação de hash da senha antes da validação

### Livros
- Listagem de livros disponíveis
- Exibição do título do livro
- Associação do livro ao nome do autor

### Empréstimos
- Seleção de livro por ID
- Registro de empréstimo no banco de dados

### Arquitetura
- Separação entre:
    - `model`
    - `dao`
    - `view`
    - `util`
- Uso de classes de domínio para as entidades principais
- Camada de persistência com DAOs
- Conexão com PostgreSQL via `ConnectionFactory`

---

## Funcionalidades pendentes

Abaixo está uma visão organizada do que ainda falta evoluir no sistema.

### Requisitos comportamentais pendentes
- Cadastro de usuário
- Edição de usuário
- Inativação/ativação de usuário
- Cadastro de autor
- Edição de autor
- Remoção de autor
- Cadastro de livro
- Edição de livro
- Remoção de livro
- Listagem avançada com filtros
- Devolução de livros
- Histórico de empréstimos por usuário
- Regras de negócio para empréstimo/devolução
- Controle de disponibilidade de livros
- Regras por perfil de acesso

### Melhorias técnicas pendentes
- Externalizar configurações do banco de dados
- Remover credenciais sensíveis do código
- Aprimorar tratamento de exceções
- Corrigir inconsistências de modelagem, se necessário
- Adicionar testes automatizados
- Melhorar logs e rastreabilidade
- Revisar fluxo de interação com o usuário
- Substituir MD5 por um algoritmo mais seguro para senha

---

## Tecnologias utilizadas

- **Java 21**
- **Maven**
- **JDBC**
- **PostgreSQL**
- **Java SE**

---

## Estrutura do projeto

```
src/main/java/com/app/
├── database/
│   ├── ConnectionFactory.java       # Factory de conexão com o PostgreSQL via JDBC
│   ├── dao/
│   │   ├── HelperDAO.java           # Interface genérica com operações CRUD padrão
│   │   ├── AutorDAO.java            # DAO para a entidade Autor
│   │   ├── LivroDAO.java            # DAO para a entidade Livro
│   │   ├── EmprestimoDAO.java       # DAO para a entidade Emprestimo
│   │   └── UsuarioDAO.java          # DAO para a entidade Usuario (inclui autenticação)
│   └── model/
│       ├── Autor.java               # Entidade: autor do livro
│       ├── Emprestimo.java          # Entidade: registro de empréstimo
│       ├── Livro.java               # Entidade: livro do acervo
│       └── Usuario.java             # Entidade: usuário do sistema
├── util/
│   └── HashUtils.java               # Utilitário de hash MD5 para senhas
└── view/
    └── Main.java                    # Ponto de entrada e interface CLI da aplicação
```

### Descrição das camadas

| Camada      | Pacote                    | Responsabilidade                                              |
|-------------|---------------------------|---------------------------------------------------------------|
| `model`     | `com.app.database.model`  | Classes de domínio que representam as entidades do sistema    |
| `dao`       | `com.app.database.dao`    | Acesso ao banco de dados; implementam a interface `HelperDAO` |
| `database`  | `com.app.database`        | Fábrica de conexão com o PostgreSQL                          |
| `util`      | `com.app.util`            | Utilitários gerais (ex.: geração de hash para senhas)         |
| `view`      | `com.app.view`            | Interface de linha de comando e fluxo principal da aplicação  |

---

## Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- [Java 21](https://adoptium.net/) ou superior
- [Maven 3.8+](https://maven.apache.org/)
- [PostgreSQL 14+](https://www.postgresql.org/)
- Um cliente SQL (ex.: pgAdmin, DBeaver ou psql)

---

## Como executar

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/library-manager.git
   cd library-manager
   ```

2. **Configure o banco de dados** (veja a seção [Banco de dados](#banco-de-dados)).

3. **Ajuste as credenciais de conexão** em `Main.java`, método `connectToDataBase()`:
   ```java
   conexao = ConnectionFactory.getConnection(
       "SEU_IP",   // endereço do servidor PostgreSQL
       5432,       // porta
       "livraria", // nome do banco
       "usuario",  // usuário do banco
       "senha"     // senha do banco
   );
   ```

4. **Compile e execute com Maven:**
   ```bash
   mvn compile
   mvn exec:java -Dexec.mainClass="com.app.view.Main"
   ```

   Ou gere o JAR e rode:
   ```bash
   mvn package
   java -cp target/library-manager-1.0-SNAPSHOT.jar com.app.view.Main
   ```

5. **Interação via terminal:**
   - Informe seu login e senha cadastrados no banco.
   - Visualize os livros disponíveis.
   - Informe o ID do livro para realizar o empréstimo.

---

## Banco de dados

O projeto utiliza **PostgreSQL** como banco de dados relacional. A conexão é feita via JDBC através da classe `ConnectionFactory`.

### Tabelas esperadas

```sql
CREATE TABLE usuarios (
    id          SERIAL PRIMARY KEY,
    nome        VARCHAR(100),
    login       VARCHAR(50) UNIQUE NOT NULL,
    senha       VARCHAR(32) NOT NULL,  -- hash MD5
    perfil      VARCHAR(20),
    email       VARCHAR(100),
    telefone    VARCHAR(20),
    status      VARCHAR(10),
    data_nascimento DATE
);

CREATE TABLE autores (
    id              SERIAL PRIMARY KEY,
    nome            VARCHAR(100) NOT NULL,
    data_nascimento DATE
);

CREATE TABLE livros (
    id               SERIAL PRIMARY KEY,
    titulo           VARCHAR(200) NOT NULL,
    autor_id         INTEGER REFERENCES autores(id),
    disponivel       BOOLEAN DEFAULT TRUE,
    data_cadastro    TIMESTAMP DEFAULT NOW(),
    data_atualizacao TIMESTAMP DEFAULT NOW()
);

CREATE TABLE emprestimos (
    id               SERIAL PRIMARY KEY,
    livro_id         INTEGER REFERENCES livros(id),
    usuario_id       INTEGER REFERENCES usuarios(id),
    data_emprestimo  TIMESTAMP DEFAULT NOW(),
    data_devolucao   TIMESTAMP
);
```

> **Atenção:** as credenciais de conexão estão atualmente embutidas no código (`Main.java`). Externalizar essas configurações é uma das [melhorias técnicas pendentes](#melhorias-técnicas-pendentes).

---

## Boas práticas aplicadas

- **Padrão DAO** — separação entre lógica de negócio e acesso a dados.
- **Interface genérica `HelperDAO<T>`** — contrato único de CRUD para todos os DAOs.
- **Factory pattern** — `ConnectionFactory` centraliza a criação de conexões JDBC.
- **Arquitetura em camadas** — `model`, `dao`, `util` e `view` com responsabilidades bem definidas.
- **Hash de senha** — a senha nunca é comparada em texto puro; é convertida para MD5 antes da validação.
- **Logger nativo Java** — uso de `java.util.logging.Logger` para registro de erros e eventos.

---

## Melhorias futuras

- Externalizar configurações do banco de dados para arquivo `.properties` ou variáveis de ambiente.
- Substituir MD5 por **BCrypt** ou outro algoritmo seguro para hash de senhas.
- Implementar controle de perfis de acesso (administrador, bibliotecário, leitor).
- Adicionar fluxo completo de devolução de livros com atualização de disponibilidade.
- Criar histórico de empréstimos por usuário.
- Adicionar testes unitários e de integração.
- Evoluir a interface para uma aplicação web ou desktop.
- Aprimorar o tratamento de exceções com mensagens mais informativas.

---

## Contribuição

Contribuições são bem-vindas! Para contribuir:

1. Faça um fork do repositório.
2. Crie uma branch para sua feature ou correção:
   ```bash
   git checkout -b feature/minha-feature
   ```
3. Realize as alterações e faça o commit:
   ```bash
   git commit -m "feat: descrição da minha feature"
   ```
4. Envie para o seu fork:
   ```bash
   git push origin feature/minha-feature
   ```
5. Abra um **Pull Request** descrevendo as mudanças realizadas.

---

## Licença

Este projeto está sob a licença **MIT**. Consulte o arquivo `LICENSE` para mais detalhes.
