# 📚 Guia Completo do Projeto Yuni - Conceitos e Arquitetura

## 📖 Índice
1. [Visão Geral](#visão-geral)
2. [Arquitetura do Projeto](#arquitetura-do-projeto)
3. [Conceitos Fundamentais](#conceitos-fundamentais)
4. [Stack Tecnológico](#stack-tecnológico)
5. [Estrutura de Pastas](#estrutura-de-pastas)
6. [Como Funciona](#como-funciona)
7. [Fluxo de Dados](#fluxo-de-dados)
8. [Banco de Dados](#banco-de-dados)
9. [Docker e Containerização](#docker-e-containerização)
10. [Glossário](#glossário)
11. [Documentos Relacionados](#documentos-relacionados)

---

## 🎯 Visão Geral

**Yuni** é uma aplicação de **gestão de metas financeiras** desenvolvida em Java com Spring Boot. Permite que os usuários:

- ✅ Criar e salvar metas financeiras (independência, casa, reforma, etc.)
- ✅ Acompanhar o progresso de cada meta
- ✅ Visualizar o patrimônio e sua evolução
- ✅ Armazenar dados persistentemente em PostgreSQL
- ✅ Acessar dados via API REST

**Objetivo:** Ajudar pessoas a organizar e acompanhar seus objetivos financeiros.

---

## 🏗️ Arquitetura do Projeto

O projeto segue o padrão **MVC (Model-View-Controller)** com camadas bem definidas:

```
┌─────────────────────────────────────────────┐
│          CLIENTE (Frontend/Postman)         │
├─────────────────────────────────────────────┤
│         CONTROLLER (MetasController)        │ ← Recebe requisições HTTP
├─────────────────────────────────────────────┤
│          SERVICE (MetasService)             │ ← Lógica de negócio
├─────────────────────────────────────────────┤
│        REPOSITORY (MetasRepository)         │ ← Acesso ao banco
├─────────────────────────────────────────────┤
│  DATABASE (PostgreSQL - Tabela 'metas')     │ ← Armazena dados
└─────────────────────────────────────────────┘
```

### Camadas Explicadas:

**1. Controller**
- Recebe requisições HTTP (GET, POST, PUT, DELETE)
- Valida dados de entrada
- Chama métodos do Service
- Retorna respostas HTTP (JSON)

**2. Service**
- Contém toda a lógica de negócio
- Realiza transformações de dados
- Coordena operações entre repositories
- Processa regras de negócio

**3. Repository**
- Faz a ponte entre a aplicação e o banco de dados
- Executa queries SQL
- Realiza operações CRUD (Create, Read, Update, Delete)

**4. Domain (Entidades)**
- Representa os dados que serão armazenados
- Mapeia tabelas do banco de dados

---

## 💡 Conceitos Fundamentais

### 1. **Entidades JPA**

Uma entidade é uma classe Java que representa uma tabela no banco de dados.

```java
@Entity  // Marca como entidade JPA
@Table(name = "metas")  // Nome da tabela no banco
public class Metas {
    @Id  // Chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremento
    private Long id;
    
    @Column(nullable = false)  // Campo obrigatório
    private String nome;
}
```

**O que significa:**
- `@Entity`: Diz ao Spring que essa classe é uma entidade de banco de dados
- `@Table`: Define o nome da tabela
- `@Id`: Define qual campo é a chave primária (identificador único)
- `@GeneratedValue`: Auto-incrementa o ID
- `@Column`: Define propriedades da coluna (obrigatória, tipo, tamanho, etc.)

### 2. **Repository Pattern (Padrão Repositório)**

O Repository é a camada de abstração que acessa o banco de dados.

```java
@Repository
public interface MetasRepository extends JpaRepository<Metas, Long> {
    List<Metas> findAllByOrderByPrazoAsc();
}
```

**Por que usar Repository?**
- ✅ Separa lógica de acesso a dados da lógica de negócio
- ✅ Facilita testes (pode mockar o repository)
- ✅ Centraliza queries em um único lugar
- ✅ Torna fácil trocar de banco de dados

**O que `JpaRepository<Metas, Long>` significa:**
- `Metas`: A entidade que será gerenciada
- `Long`: O tipo da chave primária

### 3. **Interface (Contrato de Implementação)**

Uma interface define **o que** uma classe deve fazer, sem definir **como** fazer.

**Exemplo no Yuni:**

```java
// Interface - define o contrato
@Repository
public interface MetasRepository extends JpaRepository<Metas, Long> {
    List<Metas> findAllByOrderByPrazoAsc();
    Optional<Metas> findByNome(String nome);
}

// Spring Data JPA cria a implementação automaticamente em tempo de execução
// Você não precisa criar uma classe MetasRepositoryImpl
```

**Por que usar Interface?**
- ✅ **Flexibilidade**: Trocar implementações facilmente
- ✅ **Testabilidade**: Criar mocks para testes
- ✅ **Baixo acoplamento**: Service não conhece detalhes de implementação
- ✅ **Múltiplas implementações**: Pode ter várias formas de fazer a mesma coisa

**Exemplo com múltiplas implementações:**

```java
// Interface
public interface NotificacaoService {
    void enviar(String mensagem);
}

// Implementação 1: Email
@Service
@Primary
public class EmailNotificacaoService implements NotificacaoService {
    public void enviar(String mensagem) {
        System.out.println("📧 Email enviado: " + mensagem);
    }
}

// Implementação 2: SMS
@Service
public class SmsNotificacaoService implements NotificacaoService {
    public void enviar(String mensagem) {
        System.out.println("📱 SMS enviado: " + mensagem);
    }
}

// Uso no Service - depende da interface, não da implementação
@Service
@RequiredArgsConstructor
public class MetasService {
    private final NotificacaoService notificacao;  // Spring injeta EmailNotificacaoService (@Primary)
    
    public void criarMeta(MetasRequest request) {
        // Não sabe se é Email ou SMS, apenas chama o método
        notificacao.enviar("Meta criada: " + request.getNome());
    }
}
```

**📚 Para mais detalhes:** Veja [INTERFACE_E_INJECAO.md](./INTERFACE_E_INJECAO.md)

---

### 4. **Dependency Injection (Injeção de Dependência)**

A injeção de dependência permite que o Spring "injete" automaticamente as dependências de uma classe, ao invés de você criar manualmente.

**Exemplo no Yuni:**

```java
@Service
@RequiredArgsConstructor  // Lombok cria construtor com as dependências
public class MetasService {
    // Dependências injetadas automaticamente pelo Spring
    private final MetasRepository metasRepository;
}
```

**Como funciona:**
1. Spring detecta que `MetasService` precisa de `MetasRepository`
2. Spring cria uma instância de `MetasRepository` (implementação JPA)
3. Spring passa essa instância para o construtor do `MetasService`
4. Você pode usar `metasRepository` sem criar manualmente

**Fluxo completo no Yuni:**

```java
// 1. Repository (Interface)
@Repository
public interface MetasRepository extends JpaRepository<Metas, Long> {
    List<Metas> findAllByOrderByPrazoAsc();
}

// 2. Service (recebe Repository injetado)
@Service
@RequiredArgsConstructor
public class MetasService {
    private final MetasRepository metasRepository;  // Injetado pelo Spring

    public MetasResponse listarMetas() {
        List<Metas> metas = metasRepository.findAllByOrderByPrazoAsc();
        return MetasResponse.builder().metas(metas).build();
    }
}

// 3. Controller (recebe Service injetado)
@RestController
@RequestMapping("/metas")
@RequiredArgsConstructor
public class MetasController {
    private final MetasService service;  // Injetado pelo Spring

    @GetMapping
    public MetasResponse metas() {
        return service.listarMetas();
    }
}
```

**Vantagens:**
- ✅ Código mais limpo e organizado
- ✅ Facilita testes (pode mockar dependências)
- ✅ Menos acoplamento entre classes
- ✅ Spring gerencia o ciclo de vida dos objetos

**📚 Para mais detalhes:** Veja [INTERFACE_E_INJECAO.md](./INTERFACE_E_INJECAO.md)

---

### 5. **RESTful API**

REST = Representational State Transfer. Uma forma padrão de criar APIs web.

**Princípios REST:**
- Usa URLs para representar recursos
- Usa verbos HTTP para ações (GET, POST, PUT, DELETE)
- Usa JSON para trocar dados

```java
@RestController
@RequestMapping("/metas")
public class MetasController {
    
    @GetMapping  // GET /metas
    public MetasResponse metas() { ... }
    
    @PostMapping  // POST /metas
    public MetasResponse criarMeta(@RequestBody MetasRequest request) { ... }
}
```

**Mapeamento REST:**
- `GET /metas` → Listar todas as metas
- `POST /metas` → Criar nova meta
- `GET /metas/{id}` → Obter meta específica
- `PUT /metas/{id}` → Atualizar meta
- `DELETE /metas/{id}` → Deletar meta

### 6. **ORM (Object-Relational Mapping)**

ORM mapeia objetos Java para tabelas SQL automaticamente.

```java
// Java
Metas meta = new Metas("Casa", 300000.0, null, 2028);
metasRepository.save(meta);

// SQL (gerado automaticamente)
INSERT INTO metas (nome, valor_meta, valor_atual, prazo)
VALUES ('Casa', 300000.0, NULL, 2028);
```

**Vantagens do ORM:**
- ✅ Escrever menos SQL
- ✅ Código mais seguro (proteção contra SQL Injection)
- ✅ Trocar de banco de dados facilmente

---

## 🛠️ Stack Tecnológico

### Backend
- **Java 21**: Linguagem de programação
- **Spring Boot 4.0.2**: Framework web
- **Spring Data JPA**: Abstração de acesso a dados
- **Hibernate**: Implementação de JPA (ORM)
- **Lombok**: Reduz boilerplate de código

### Banco de Dados
- **PostgreSQL 16**: Banco de dados relacional

### DevOps
- **Docker**: Containerização
- **Docker Compose**: Orquestração de containers

### Build e Gerenciamento
- **Maven**: Gerenciador de dependências e build
- **Spring Boot Maven Plugin**: Plugin para build de aplicações Spring Boot

---

## 📂 Estrutura de Pastas

```
yuni-srv/
├── src/
│   ├── main/
│   │   ├── java/com/nast/yuni/
│   │   │   ├── YuniApplication.java              # Classe principal (entry point)
│   │   │   ├── controller/
│   │   │   │   ├── MetasController.java          # Recebe requisições de metas
│   │   │   │   └── PatrimonioController.java     # Recebe requisições de patrimônio
│   │   │   ├── service/
│   │   │   │   ├── MetasService.java             # Lógica de metas
│   │   │   │   └── PatrimonioService.java        # Lógica de patrimônio
│   │   │   ├── repository/
│   │   │   │   └── MetasRepository.java          # Acesso a dados de metas
│   │   │   ├── domain/
│   │   │   │   ├── Metas.java                    # Entidade Metas
│   │   │   │   ├── PatrimonioAtivos.java         # Entidade Patrimônio Ativos
│   │   │   │   └── ... (outras entidades)
│   │   │   ├── request/
│   │   │   │   └── MetasRequest.java             # DTO para receber dados
│   │   │   ├── response/
│   │   │   │   └── MetasResponse.java            # DTO para enviar dados
│   │   │   └── configuration/
│   │   │       └── CorsConfig.java               # Configuração CORS
│   │   └── resources/
│   │       └── application.properties            # Configurações (banco, porta, etc)
│   └── test/
│       └── java/com/nast/yuni/                   # Testes unitários
├── docker/
├── Dockerfile                                    # Imagem Docker
├── docker-compose.yml                            # Orquestração
├── pom.xml                                       # Dependências Maven
└── docs/
    ├── API_TESTING.md                            # Como testar API
    ├── DOCKER_SETUP.md                           # Setup Docker
    ├── README_DOCKER.md                          # README do Docker
    └── CONCEITOS.md                              # Este arquivo
```

### Explicação das pastas:

**domain/** - Entidades (modelos de dados)
- Representam tabelas do banco de dados
- Contêm validações básicas

**repository/** - Acesso a dados
- Interfaces que extendem JpaRepository
- Métodos customizados para queries

**service/** - Lógica de negócio
- Regras de negócio
- Transformações de dados
- Orquestração entre repositories

**controller/** - API REST
- Endpoints HTTP
- Validação de entrada
- Formatação de resposta

**request/** - DTOs de entrada
- Classes que recebem dados do cliente
- Validações de entrada

**response/** - DTOs de saída
- Classes que enviam dados para o cliente
- Formatação de resposta

**configuration/** - Configurações
- CORS, segurança, etc.

---

## 🔄 Como Funciona

### Exemplo Prático: Criar uma Meta

**1. Cliente faz requisição HTTP**
```bash
curl -X POST http://localhost:8080/metas \
  -H "Content-Type: application/json" \
  -d '{"nome": "Casa", "valorMeta": 300000, "valorAtual": null, "prazo": 2028}'
```

**2. Controller recebe a requisição**
```java
@PostMapping
public MetasResponse criarMeta(@RequestBody MetasRequest request) {
    return service.criarMeta(request);
}
```
- Recebe o JSON
- Converte para objeto `MetasRequest`
- Chama o service

**3. Service processa**
```java
public MetasResponse criarMeta(MetasRequest request) {
    Metas metas = new Metas(
        request.getNome(),
        request.getValorMeta(),
        request.getValorAtual(),
        request.getPrazo()
    );
    
    Metas metasSalva = metasRepository.save(metas);  // Salva no banco
    
    return MetasResponse.builder()
        .metas(List.of(metasSalva))
        .build();
}
```
- Cria instância de `Metas`
- Chama repository para salvar
- Retorna resposta formatada

**4. Repository salva no banco**
```java
metasRepository.save(metas);
// Gera SQL automaticamente:
// INSERT INTO metas (nome, valor_meta, valor_atual, prazo) VALUES (...)
```

**5. Cliente recebe resposta**
```json
{
  "metas": [
    {
      "id": 1,
      "nome": "Casa",
      "valorMeta": 300000.0,
      "valorAtual": null,
      "prazo": 2028
    }
  ]
}
```

---

## 📊 Fluxo de Dados

```
┌──────────────────┐
│ Request JSON     │
│ (do cliente)     │
└────────┬─────────┘
         │
         ▼
┌──────────────────────┐
│ MetasController      │
│ (recebe e valida)    │
└────────┬─────────────┘
         │
         ▼
┌──────────────────────┐
│ MetasRequest         │
│ (DTO de entrada)     │
└────────┬─────────────┘
         │
         ▼
┌──────────────────────┐
│ MetasService         │
│ (lógica de negócio)  │
└────────┬─────────────┘
         │
         ▼
┌──────────────────────┐
│ Metas (Domain)       │
│ (entidade JPA)       │
└────────┬─────────────┘
         │
         ▼
┌──────────────────────────┐
│ MetasRepository          │
│ (acesso a dados)         │
└────────┬─────────────────┘
         │
         ▼
┌──────────────────────────┐
│ PostgreSQL               │
│ (persiste os dados)      │
└────────┬─────────────────┘
         │
         ▼ (query response)
┌──────────────────────────┐
│ MetasRepository (retorna)│
└────────┬─────────────────┘
         │
         ▼
┌──────────────────────┐
│ MetasService         │
│ (transforma em DTO)  │
└────────┬─────────────┘
         │
         ▼
┌──────────────────────────┐
│ MetasResponse            │
│ (DTO de saída)           │
└────────┬─────────────────┘
         │
         ▼
┌──────────────────────────┐
│ MetasController          │
│ (converte para JSON)     │
└────────┬─────────────────┘
         │
         ▼
┌──────────────────────┐
│ Response JSON        │
│ (retorna ao cliente) │
└──────────────────────┘
```

---

## 🗄️ Banco de Dados

### Tabela: metas

```sql
CREATE TABLE metas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    valor_meta DOUBLE NOT NULL,
    valor_atual DOUBLE,
    prazo INTEGER NOT NULL
);
```

**Campos:**
- `id`: Identificador único, auto-incrementado
- `nome`: Nome da meta (ex: "Casa", "Carro")
- `valor_meta`: Quanto quer economizar
- `valor_atual`: Quanto já economizou
- `prazo`: Ano alvo para atingir a meta

**Exemplo de dados:**
```
id | nome              | valor_meta | valor_atual | prazo
---|-------------------|------------|-------------|-------
1  | Independência     | 1000000.00 | 150000.00   | 2030
2  | Minha casa        | 300000.00  | 50000.00    | 2028
3  | Reforma da casa   | 15000.00   | NULL        | 2025
```

---

## 🐳 Docker e Containerização

### O que é Docker?

Docker é uma ferramenta que **empacota sua aplicação e todas as suas dependências em um container** (uma caixa isolada) que pode rodar em qualquer lugar.

**Analogia:**
- Sem Docker: Você precisa instalar Java, Maven, PostgreSQL em cada máquina
- Com Docker: Você empacota tudo em um container e roda em qualquer lugar

### Como funciona no nosso projeto

```
┌─────────────────────────────────┐
│      docker-compose.yml         │
│  (orquestração de containers)   │
├─────────────────────────────────┤
│  ┌────────────────────────────┐ │
│  │  Container 1: PostgreSQL   │ │
│  │  (banco de dados)          │ │
│  │  Porta: 5432              │ │
│  └────────────────────────────┘ │
│                                 │
│  ┌────────────────────────────┐ │
│  │  Container 2: Spring Boot  │ │
│  │  (aplicação Yuni)          │ │
│  │  Porta: 8080               │ │
│  └────────────────────────────┘ │
└─────────────────────────────────┘
```

### Componentes Docker

**Dockerfile**
```dockerfile
FROM eclipse-temurin:21-jdk-alpine AS builder
# Estágio 1: Compila o Maven

FROM eclipse-temurin:21-jre-alpine
# Estágio 2: Roda a aplicação
```

**docker-compose.yml**
```yaml
services:
  postgres:
    image: postgres:16-alpine
    environment:
      POSTGRES_DB: yuni_db
      POSTGRES_USER: yuni_user
      POSTGRES_PASSWORD: yuni_pass123
  
  app:
    build: .
    ports:
      - "8080:8080"
```

**Vantagens:**
- ✅ Ambiente consistente (dev, teste, prod)
- ✅ Fácil compartilhar com equipe
- ✅ Rápido começar a desenvolver
- ✅ Simula produção localmente

---

## 📚 Glossário

### A
- **API**: Application Programming Interface (interface para comunicação entre programas)
- **ACID**: Propriedades de banco de dados (Atomicidade, Consistência, Isolamento, Durabilidade)

### C
- **CORS**: Cross-Origin Resource Sharing (permite requisições de diferentes origens)
- **Controller**: Componente que recebe requisições HTTP

### D
- **DTO**: Data Transfer Object (objeto para transferir dados)
- **DDL**: Data Definition Language (criação de tabelas)
- **Domain**: Entidades do sistema

### E
- **Entity**: Entidade mapeada para o banco de dados
- **Endpoint**: URL da API

### H
- **HTTP**: Protocolo de comunicação web (GET, POST, PUT, DELETE)
- **Hibernate**: Framework ORM para Java

### J
- **JPA**: Java Persistence API (padrão de ORM)
- **JSON**: Formato de dados (JavaScript Object Notation)

### M
- **Maven**: Gerenciador de dependências e build
- **MVC**: Model-View-Controller (padrão de arquitetura)

### O
- **ORM**: Object-Relational Mapping (mapeia objetos para banco de dados)

### P
- **PostgreSQL**: Banco de dados relacional
- **Port**: Porta (número para acessar um serviço)

### R
- **Repository**: Padrão para acesso a dados
- **REST**: Representational State Transfer (arquitetura web)
- **Response**: Resposta HTTP

### S
- **Service**: Componente com lógica de negócio
- **SQL**: Structured Query Language (linguagem de banco de dados)
- **Spring Boot**: Framework Java para web

### T
- **Table**: Tabela do banco de dados

---

## 🚀 Próximos Passos para Aprender

1. **Testes Unitários**
   - Mockar dependências
   - Testar cada camada isoladamente

2. **Segurança**
   - Autenticação (JWT, OAuth)
   - Autorização (roles, permissions)
   - HTTPS

3. **Performance**
   - Cache (Redis)
   - Índices no banco
   - N+1 query problem

4. **Documentação**
   - Swagger/OpenAPI
   - Javadoc

5. **Mais Endpoints**
   - Atualizar meta (PUT)
   - Deletar meta (DELETE)
   - Filtrar metas

---

## 📖 Documentos Relacionados

- **[INTERFACE_E_INJECAO.md](./INTERFACE_E_INJECAO.md)** - Guia completo sobre Interface e Injeção de Dependência
- **[API_TESTING.md](./API_TESTING.md)** - Como testar a API do Yuni
- **[DOCKER_SETUP.md](./DOCKER_SETUP.md)** - Setup e configuração do Docker
- **[README_DOCKER.md](./README_DOCKER.md)** - Informações sobre containerização

---

## 📖 Recursos Úteis

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Guide](https://spring.io/projects/spring-data-jpa)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Docker Documentation](https://docs.docker.com/)
- [REST API Best Practices](https://restfulapi.net/)

---

**Criado com ❤️ para aprendizado**

Última atualização: Fevereiro 2026

