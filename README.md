# MovieFlix API

## Visão Geral

A **MovieFlix API** é uma aplicação REST desenvolvida em Spring Boot para o gerenciamento de um catálogo de filmes. A API permite o cadastro, consulta, atualização e remoção de filmes, categorias e plataformas de streaming, além de fornecer autenticação de usuários baseada em token JWT.

O projeto foi implementado seguindo uma arquitetura em camadas, com separação clara de responsabilidades e foco em simplicidade, segurança e manutenibilidade.

---

## Propósito da API

- Gerenciar um catálogo de filmes
- Associar filmes a categorias e plataformas de streaming
- Permitir cadastro e autenticação de usuários
- Proteger endpoints por meio de autenticação JWT

---

## Arquitetura da Aplicação

A aplicação segue uma arquitetura tradicional em camadas:

- **Controller**  
  Responsável por expor os endpoints REST e receber as requisições HTTP.

- **Service**  
  Contém as regras de negócio e a orquestração das operações.

- **Repository**  
  Camada de persistência baseada em Spring Data JPA.

- **Entity**  
  Representa o modelo de dados e o mapeamento JPA das tabelas do banco.

- **Mapper**  
  Conversão entre DTOs (Request/Response) e entidades.

- **Security**  
  Configuração de autenticação e autorização com Spring Security e JWT.

- **Exception**  
  Tratamento global de exceções da aplicação.

---

## Tecnologias Utilizadas

- **Java**: 17  
- **Spring Boot**: 4.0.1  
- **Spring Web MVC**  
- **Spring Data JPA**  
- **Spring Security**  
- **Spring Validation**  
- **Flyway**  
- **PostgreSQL**  
- **JWT (Auth0 java-jwt 4.4.0)**  
- **Lombok**  
- **Maven**

---

## Requisitos

- Java 17 ou superior  
- Maven  
- PostgreSQL

---

## Configuração e Execução Local

### 1. Clonar o repositório

```bash
git clone <url-do-repositorio>
cd movieflix
````

### 2. Configurar o banco de dados

Configurar o datasource no `application.properties` ou `application.yml`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/movieflix
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
```

As migrações de banco são gerenciadas pelo **Flyway**.

### 3. Configurar a chave JWT

Definir a chave secreta utilizada para assinar e validar os tokens JWT:

```properties
movieflix.security.secret=chave-secreta
```

### 4. Executar a aplicação

```bash
mvn spring-boot:run
```

A aplicação será iniciada em:

```
http://localhost:8080
```

---

## Autenticação e Segurança

A API utiliza autenticação **stateless** baseada em JWT.

### Endpoints Públicos

* `POST /movieflix/auth/register`
* `POST /movieflix/auth/login`

### Endpoints Protegidos

* Todos os demais endpoints exigem autenticação.

### Header de Autenticação

```http
Authorization: Bearer <token>
```

### Características do Token

* Algoritmo: HMAC256
* Expiração: 24 horas
* Claims:

  * `sub` (email do usuário)
  * `userId`
  * `name`

Não existe controle de perfis ou permissões. Todos os usuários autenticados possuem o mesmo nível de acesso.

---

## Endpoints

### Autenticação

#### Registrar Usuário

**POST** `/movieflix/auth/register`

Request:

```json
{
  "name": "Usuário Exemplo",
  "email": "usuario@email.com",
  "password": "123456"
}
```

Response:

```json
{
  "id": 1,
  "name": "Usuário Exemplo",
  "email": "usuario@email.com"
}
```

#### Login

**POST** `/movieflix/auth/login`

Request:

```json
{
  "email": "usuario@email.com",
  "password": "123456"
}
```

Response:

```json
{
  "token": "jwt-token"
}
```

---

### Categorias

#### Listar Categorias

**GET** `/movieflix/category`

#### Criar Categoria

**POST** `/movieflix/category`

Request:

```json
{
  "name": "Ação"
}
```

#### Buscar Categoria por ID

**GET** `/movieflix/category/{id}`

#### Remover Categoria

**DELETE** `/movieflix/category/{id}`

---

### Streamings

#### Listar Streamings

**GET** `/movieflix/streaming`

#### Criar Streaming

**POST** `/movieflix/streaming`

Request:

```json
{
  "name": "Netflix"
}
```

#### Buscar Streaming por ID

**GET** `/movieflix/streaming/{id}`

#### Remover Streaming

**DELETE** `/movieflix/streaming/{id}`

---

### Filmes

#### Listar Filmes

**GET** `/movieflix/movie`

#### Criar Filme

**POST** `/movieflix/movie`

Request:

```json
{
  "title": "Filme Exemplo",
  "description": "Descrição do filme",
  "releaseDate": "2024-01-01",
  "rating": 8.5,
  "categories": [1],
  "streamings": [1]
}
```

#### Buscar Filme por ID

**GET** `/movieflix/movie/{id}`

#### Atualizar Filme

**PUT** `/movieflix/movie/{id}`

#### Remover Filme

**DELETE** `/movieflix/movie/{id}`

#### Buscar Filmes por Categoria

**GET** `/movieflix/movie/search?category={categoryId}`

---

## Validação e Tratamento de Erros

* Validação de campos obrigatórios via `jakarta.validation`
* Erros de validação retornam `400 Bad Request` com lista de mensagens
* Recurso não encontrado retorna `404 Not Found`
* Credenciais inválidas retornam `400 Bad Request`
