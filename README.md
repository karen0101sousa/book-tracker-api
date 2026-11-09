# Book Tracker API

API REST desenvolvida em Java com Spring Boot para gerenciamento de livros e registros de leitura.

O projeto foi desenvolvido como parte do Projeto B do curso Full Cycle, com foco em organização de código, princípios SOLID, testes automatizados, qualidade e observabilidade.

## 1. Tecnologias utilizadas

- **Java 21** — linguagem de programação.
- **Spring Boot** — desenvolvimento da API REST.
- **Spring Data JPA** — persistência e acesso aos dados.
- **PostgreSQL 17** — banco de dados relacional.
- **Docker e Docker Compose** — ambiente de execução.
- **Maven** — gerenciamento de dependências e build.
- **JUnit e Mockito** — testes automatizados.
- **JaCoCo** — análise da cobertura de testes.
- **Checkstyle** — análise de padrões de código.
- **GitHub Actions** — integração contínua (CI).
- **Spring Boot Actuator e Micrometer** — métricas e monitoramento.
- **k6** — teste de desempenho.

## 2. Funcionalidades

A API permite:

- Cadastrar livros com título e autor.
- Registrar leituras de livros.
- Consultar os registros de leitura.
- Identificar livros inexistentes.
- Impedir registros duplicados para o mesmo livro, mês e ano.
- Validar os dados dos registros de leitura.

Os formatos de leitura disponíveis são:

- `PHYSICAL`
- `DIGITAL`

## 3. Arquitetura e organização

O projeto utiliza uma arquitetura em camadas organizada por funcionalidades, separando responsabilidades entre controladores, serviços e repositórios.

```text
src/
├── main/
│   ├── java/com/booktracker/
│   │   ├── BookTrackerApiApplication.java
│   │   ├── common/
│   │   │   └── exception/
│   │   │       ├── BookNotFoundException.java
│   │   │       ├── DuplicateReadingException.java
│   │   │       └── GlobalExceptionHandler.java
│   │   ├── book/
│   │   │   ├── controller/
│   │   │   │   └── BookController.java
│   │   │   ├── dto/
│   │   │   │   └── BookRequest.java
│   │   │   ├── repository/
│   │   │   │   └── BookRepository.java
│   │   │   ├── service/
│   │   │   │   └── BookService.java
│   │   │   └── Book.java
│   │   └── reading/
│   │       ├── controller/
│   │       │   └── ReadingController.java
│   │       ├── dto/
│   │       │   └── ReadingRequest.java
│   │       ├── repository/
│   │       │   └── ReadingRepository.java
│   │       ├── service/
│   │       │   └── ReadingService.java
│   │       ├── Reading.java
│   │       └── ReadingFormat.java
│   └── resources/
└── test/
```

O padrão Repository é utilizado para organizar o acesso aos dados por meio das interfaces `BookRepository` e `ReadingRepository`. A implementação utiliza Spring Data JPA, disponibilizando operações comuns de persistência sem exigir sua implementação manual.

As decisões arquiteturais, alternativas consideradas e justificativas estão registradas em [`docs/adr/001-arquitetura.md`](docs/adr/001-arquitetura.md).

## 4. Pré-requisitos

Para executar a aplicação, é necessário ter:

- Docker.
- Docker Compose.

Para executar os testes e as verificações de qualidade localmente, também são necessários:

- Java 21.
- Maven 3.9 ou superior.

## 5. Configuração do ambiente

Clone o repositório:

```bash
git clone git@github.com:karen0101sousa/book-tracker-api.git
cd book-tracker-api
```

Crie o arquivo `.env` a partir do exemplo disponibilizado:

```bash
cp .env.example .env
```

O arquivo `.env.example` contém as seguintes variáveis:

| Variável | Descrição | Valor de exemplo |
|---|---|---|
| `POSTGRES_DB` | Nome do banco de dados | `book_tracker` |
| `POSTGRES_USER` | Usuário do PostgreSQL | `booktracker` |
| `POSTGRES_PASSWORD` | Senha do PostgreSQL | `change_me` |

Se necessário, altere os valores no arquivo `.env` conforme seu ambiente.

**Importante:** o arquivo `.env` contém configurações locais e não deve ser versionado no Git. O repositório disponibiliza apenas o `.env.example` como modelo.

O Docker Compose utiliza essas variáveis para configurar o PostgreSQL e a conexão da API com o banco de dados.

O serviço da API aguarda a verificação de saúde do PostgreSQL antes de iniciar.

## 6. Como executar a aplicação

Com o Docker em execução e o arquivo `.env` configurado, utilize:

```bash
docker compose up --build
```

Esse comando constrói a imagem da API, inicia o banco PostgreSQL e executa os serviços definidos no Docker Compose.

A API estará disponível em:

```text
http://localhost:8080
```

Para verificar a saúde da aplicação:

```bash
curl http://localhost:8080/actuator/health
```

Para encerrar os serviços:

```bash
docker compose down
```

## 7. Endpoints disponíveis

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/books` | Cadastra um livro |
| POST | `/readings` | Registra uma leitura |
| GET | `/readings` | Consulta os registros de leitura |

### Cadastrar livro

**POST** `/books`

Exemplo de requisição:

```json
{
  "title": "O Hobbit",
  "author": "J. R. R. Tolkien"
}
```

Retorno esperado: `201 Created`.

### Registrar leitura

**POST** `/readings`

Exemplo de requisição:

```json
{
  "bookId": 1,
  "month": 11,
  "year": 2026,
  "format": "PHYSICAL"
}
```

O campo `format` aceita os valores `PHYSICAL` e `DIGITAL`.

Retorno esperado: `201 Created`.

### Consultar leituras

**GET** `/readings`

Retorna a lista de leituras cadastradas.

Retorno esperado: `200 OK`.

### Códigos HTTP

A API utiliza códigos HTTP para indicar o resultado das operações:

- `200 OK` — consulta realizada com sucesso.
- `201 Created` — recurso criado.
- `400 Bad Request` — dados inválidos ou requisição com parâmetros não aceitos.
- `404 Not Found` — livro não encontrado.
- `409 Conflict` — tentativa de registro duplicado.

## 8. Testes automatizados

O projeto possui testes unitários e de integração, utilizando JUnit e Mockito.

Os testes de integração utilizam um banco PostgreSQL separado, denominado `book_tracker_test`, para evitar interferência nos dados da aplicação.

### Preparação do banco de testes

Antes de executar os testes localmente pela primeira vez, inicie o serviço PostgreSQL:

```bash
docker compose up -d postgres
```

O Docker Compose cria automaticamente o banco principal, definido em `.env`. É necessário criar também o banco de testes:

```bash
docker compose exec postgres sh -c 'psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -c "CREATE DATABASE book_tracker_test;"'
```

Esse procedimento precisa ser realizado apenas uma vez por ambiente. Se o banco de testes já existir, não é necessário criá-lo novamente.

### Execução dos testes

Com o PostgreSQL em execução, execute:

```bash
mvn clean verify
```

O processo executa os testes automatizados, verifica os padrões de código com Checkstyle e gera o relatório de cobertura com JaCoCo.

O projeto estabelece cobertura mínima de 60% para linhas de código.

O relatório pode ser consultado em:

```text
target/site/jacoco/index.html
```

## 9. Integração contínua (CI)

O projeto utiliza GitHub Actions para executar automaticamente as verificações de qualidade a cada push na branch `main` e em pull requests direcionados a essa branch.

O pipeline executa:

- Configuração do ambiente Java 21.
- Inicialização de um serviço PostgreSQL para os testes.
- Build e testes automatizados.
- Verificação dos padrões de código com Checkstyle.
- Verificação da cobertura mínima com JaCoCo.

## 10. Logs, métricas e SLO

A aplicação utiliza logs estruturados em formato JSON, facilitando a leitura e o processamento dos registros.

O Spring Boot Actuator disponibiliza endpoints de monitoramento:

**Saúde da aplicação:**

```text
http://localhost:8080/actuator/health
```

**Métricas HTTP:**

```text
http://localhost:8080/actuator/metrics/http.server.requests
```

Foi definido um objetivo inicial de desempenho para o endpoint `GET /readings`:

- **Indicador:** percentil 95 (p95) do tempo de resposta.
- **Objetivo:** p95 inferior a 500 ms.

A definição do SLO e os resultados da medição estão documentados em [`docs/slo.md`](docs/slo.md).

## 11. Teste de desempenho com k6

O projeto possui um smoke test implementado com k6 para verificar o comportamento do endpoint `GET /readings`.

Com a API em funcionamento, execute o teste utilizando Docker:

```bash
docker run --rm --network host \
  -v "$PWD/k6:/scripts:ro" \
  grafana/k6 run /scripts/smoke-test.js
```

O teste verifica o status HTTP 200 e utiliza o seguinte critério de desempenho:

```javascript
http_req_duration: ['p(95)<500']
```

Na execução documentada em `docs/slo.md`, foram realizadas 10 requisições, com 100% dos checks aprovados e p95 de 9,89 ms.

Esse resultado representa uma medição pontual em ambiente local, não uma garantia de desempenho em produção.

**Observação:** o comando utiliza `--network host`, opção adequada ao ambiente Linux. A API precisa estar em execução antes de iniciar o teste.

## 12. Documentação arquitetural

As decisões relacionadas à arquitetura e às escolhas técnicas do projeto estão registradas em documentos específicos:

- [`docs/adr/001-arquitetura.md`](docs/adr/001-arquitetura.md) — decisão arquitetural, alternativas consideradas e justificativa da escolha.
- [`docs/slo.md`](docs/slo.md) — objetivo de nível de serviço e resultados do teste de desempenho.