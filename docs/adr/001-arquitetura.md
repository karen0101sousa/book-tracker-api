# ADR 001 — Arquitetura da aplicação

- **Status:** Aceita
- **Data:** 09/11/2026
- **Projeto:** Book Tracker API

## 1. Contexto

O Book Tracker API é uma aplicação REST desenvolvida com Java e Spring Boot, 
responsável pelo cadastro de livros e pelo gerenciamento do histórico de leituras.

Durante o desenvolvimento, surgiu a necessidade de organizar o código de forma 
que as responsabilidades da aplicação fossem separadas, facilitando a manutenção, 
os testes automatizados e a evolução do sistema.

A aplicação possui duas funcionalidades principais: gerenciamento de livros 
e registro de leituras. Por esse motivo, sua estrutura foi organizada em módulos 
separados por funcionalidade, mantendo também uma divisão de responsabilidades 
entre controllers, services e repositories.

## 2. Decisão

Foi adotada uma arquitetura em camadas, organizada por funcionalidades.

Cada funcionalidade possui seus próprios componentes, separados de acordo 
com suas responsabilidades:

- **Controller:** responsável por receber as requisições HTTP e retornar 
as respostas da API.
- **Service:** responsável por coordenar as operações e executar as regras 
de negócio.
- **Repository:** responsável pelo acesso e pela persistência dos dados.

As funcionalidades principais foram organizadas nos módulos `book` e `reading`, 
enquanto componentes compartilhados, como o tratamento global de exceções, 
foram agrupados no pacote `common`.

## 3. Alternativas consideradas

### 3.1. Arquitetura em camadas tradicional

Organiza a aplicação em camadas técnicas, como controllers, services 
e repositories, agrupando os componentes por responsabilidade.

### 3.2. Clean Architecture

Organiza o sistema em camadas com regras explícitas de dependência, 
priorizando o domínio e mantendo detalhes de infraestrutura externos 
às regras de negócio.

### 3.3. Arquitetura em camadas organizada por funcionalidades

Agrupa os componentes por funcionalidade, como livros e leituras, 
mantendo a separação entre controllers, services e repositories 
dentro de cada módulo. **Alternativa adotada neste projeto.**

## 4. Consequências

### Benefícios

- Organização do código por funcionalidade.
- Separação de responsabilidades entre as camadas.
- Facilidade para localizar e manter componentes.
- Possibilidade de testar os serviços de forma isolada.
- Estrutura simples, adequada ao escopo atual da aplicação.

### Limitações

- As entidades `Book` e `Reading` acumulam responsabilidades de domínio e persistência, pois utilizam anotações do Jakarta Persistence.
- Os serviços utilizam interfaces do Spring Data JPA, mantendo 
acoplamento com o mecanismo de persistência.
- Os controllers retornam entidades diretamente, vinculando a representação 
das respostas HTTP ao modelo de persistência.

## 5. Justificativa

A arquitetura em camadas organizada por funcionalidades foi escolhida 
por oferecer uma estrutura simples, organizada e adequada às necessidades 
atuais do Book Tracker API.

A separação entre controllers, services e repositories permite distribuir 
as responsabilidades da aplicação, enquanto a organização por funcionalidades 
facilita a manutenção e a evolução dos módulos de livros e leituras.

Embora a solução não implemente todos os princípios de uma Clean Architecture 
rigorosa, ela oferece uma base organizada para o projeto, sem introduzir 
complexidade arquitetural desnecessária ao seu escopo atual.

## 6. Padrão de projeto

O projeto utiliza o padrão Repository para organizar o acesso
aos dados e separar as operações de persistência da lógica
de negócio.

As interfaces `BookRepository` e `ReadingRepository` estendem
`JpaRepository`, do Spring Data JPA, disponibilizando operações
de persistência sem exigir que os serviços implementem diretamente
as consultas ao banco de dados.