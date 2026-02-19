# YUNI - Sistema de Gestão de Patrimônio e Metas Financeiras

## 📋 Visão Geral

O YUNI é uma aplicação Spring Boot que permite aos usuários gerenciar seu patrimônio e definir metas financeiras. A aplicação é construída com uma arquitetura moderna e containerizada com Docker para facilitar o deployment.

### Stack Tecnológico
- **Backend**: Spring Boot 4.0.2
- **Database**: PostgreSQL 16
- **Linguagem**: Java 21
- **Build**: Maven
- **Containerização**: Docker & Docker Compose
- **ORM**: Hibernate/JPA
- **Lombok**: Para reduzir boilerplate

---

## 🏗️ Arquitetura do Projeto

```
yuni-srv/
├── src/
│   ├── main/
│   │   ├── java/com/nast/yuni/
│   │   │   ├── YuniApplication.java          # Classe principal
│   │   │   ├── configuration/                 # Configurações (CORS, etc)
│   │   │   ├── controller/                    # Controllers REST
│   │   │   │   ├── MetasController.java
│   │   │   │   └── PatrimonioController.java
│   │   │   ├── domain/                        # Entidades JPA
│   │   │   │   ├── Metas.java
│   │   │   │   ├── PatrimonioAtivos.java
│   │   │   │   ├── PatrimonioNaoAtivos.java
│   │   │   │   ├── PatrimonioEvolucao.java
│   │   │   │   └── ItemFinanceiro.java
│   │   │   ├── repository/                    # Repositórios Spring Data JPA
│   │   │   │   └── MetasRepository.java
│   │   │   ├── service/                       # Lógica de negócio
│   │   │   │   └── MetasService.java
│   │   │   ├── request/                       # DTOs de entrada
│   │   │   │   └── MetasRequest.java
│   │   │   └── response/                      # DTOs de saída
│   │   │       ├── MetasResponse.java
│   │   │       └── PatrimonioResponse.java
│   │   └── resources/
│   │       └── application.properties         # Configuração da app
│   └── test/
│       └── java/com/nast/yuni/
│           └── YuniApplicationTests.java
├── docker/
│   └── docker-compose.yml                     # Configuração Docker
├── Dockerfile                                  # Imagem Docker (multi-stage)
├── pom.xml                                    # Dependências Maven
├── mvnw                                       # Maven Wrapper
├── init-db.sql                                # Script de inicialização DB
└── docs/                                      # Documentação
```

---

## 🎯 Conceitos Principais

### 1. **Entidades (Domain)**

#### Metas
Representa uma meta financeira que o usuário quer atingir.
```
- id: Long (PK)
- nome: String
- valorMeta: BigDecimal
- valorAtual: BigDecimal
- prazo: LocalDate
- dataCriacao: LocalDateTime
```

#### PatrimonioAtivos
Representa bens e investimentos do usuário (imóvel, carro, ações, etc).

#### PatrimonioNaoAtivos
Representa passivos (dívidas, empréstimos, etc).

#### PatrimonioEvolucao
Registra a evolução do patrimônio ao longo do tempo (histórico).

#### ItemFinanceiro
Itens financeiros genéricos para tracking detalhado.

### 2. **Padrão de Requisição/Resposta**

Usamos DTOs (Data Transfer Objects) para separar a representação dos dados:

- **MetasRequest**: Dados que o cliente envia para criar/atualizar metas
- **MetasResponse**: Dados que o servidor retorna ao cliente

### 3. **Repositórios (Spring Data JPA)**

Os repositórios herdam de `JpaRepository` e fornecem operações CRUD automáticas:

```java
public interface MetasRepository extends JpaRepository<Metas, Long> {
    List<Metas> findAllByOrderByPrazoAsc();
}
```

### 4. **Serviços (Lógica de Negócio)**

Os serviços contêm toda a lógica de negócio:

```java
@Service
public class MetasService {
    // Lida com operações de metas
    public MetasResponse listarMetas() { ... }
    public MetasResponse criarMeta(MetasRequest request) { ... }
}
```

### 5. **Controllers (REST API)**

Expõem endpoints HTTP:

```java
@RestController
@RequestMapping("/api/metas")
public class MetasController {
    @GetMapping
    public ResponseEntity<MetasResponse> listar() { ... }
    
    @PostMapping
    public ResponseEntity<MetasResponse> criar(@RequestBody MetasRequest request) { ... }
}
```

---

## 🚀 Getting Started

### Pré-requisitos
- Docker Desktop instalado e rodando
- Docker Compose
- Porta 5432 disponível (PostgreSQL)
- Porta 8080 disponível (Aplicação)

### Iniciar a Aplicação

```bash
# Navegar para o diretório do projeto
cd /Users/alessandranastassja/Desktop/yuni/yuni-srv

# Iniciar containers (build + run)
docker-compose up --build

# Para desligar
docker-compose down

# Para desligar e limpar volumes (apagar dados)
docker-compose down -v
```

### Acessar a Aplicação

- **URL**: http://localhost:8080
- **PostgreSQL**: localhost:5432
  - User: `yuni_user`
  - Password: `yuni_pass123`
  - Database: `yuni_db`

---

## 📡 Endpoints da API

### Metas

#### Listar Metas
```
GET /api/metas
Resposta: MetasResponse com lista de metas ordenadas por prazo
```

#### Criar Meta
```
POST /api/metas
Body: {
  "nome": "Comprar casa",
  "valorMeta": 500000.00,
  "valorAtual": 100000.00,
  "prazo": "2026-12-31"
}
Resposta: MetasResponse com a meta criada
```

---

## 🗄️ Banco de Dados

### Inicialização Automática

O arquivo `init-db.sql` é executado automaticamente ao iniciar o PostgreSQL. Ele cria as tabelas e popula dados iniciais.

### Configuração do Spring Boot

No `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://postgres:5432/yuni_db
spring.datasource.username=yuni_user
spring.datasource.password=yuni_pass123
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

**Modos DDL disponíveis**:
- `validate`: Apenas valida o schema (usar em produção)
- `update`: Atualiza o schema com base nas entidades (desenvolvimento)
- `create`: Cria novo schema (teste)
- `create-drop`: Cria e destrói ao finalizar (teste)

---

## 🐳 Docker & Docker Compose

### Dockerfile

Usa **multi-stage build** para otimizar a imagem final:

**Stage 1 (Builder)**: 
- Usa `eclipse-temurin:21-jdk-alpine`
- Compila o projeto com Maven
- Gera o JAR

**Stage 2 (Runtime)**:
- Usa `eclipse-temurin:21-jre-alpine` (menor)
- Copia o JAR do stage 1
- Executa a aplicação

**Benefício**: Imagem final muito menor (sem Maven/JDK)

### docker-compose.yml

Define dois serviços:

**PostgreSQL**:
- Imagem: `postgres:16-alpine`
- Porta: 5432
- Volume: Persiste dados em `postgres_data`
- Healthcheck: Verifica se DB está pronto

**App (Spring Boot)**:
- Build a partir do Dockerfile
- Porta: 8080
- Variáveis de ambiente: Configuração do Spring Boot
- Depends_on: Aguarda PostgreSQL estar saudável
- Restart: Reinicia automaticamente se cair

---

## 🔧 Configuração & Troubleshooting

### Problema: "Porta 8080 já em uso"

```bash
# Encontrar e matar processo
lsof -ti:8080 | xargs kill -9

# Ou especificar porta diferente no docker-compose.yml
ports:
  - "8081:8080"
```

### Problema: "Database does not exist"

```bash
# Verificar logs do PostgreSQL
docker-compose logs postgres

# Se necessário, recriartudo
docker-compose down -v
docker-compose up --build
```

### Problema: "Cannot connect to PostgreSQL"

```bash
# Verificar se containers estão rodando
docker-compose ps

# Verificar logs
docker-compose logs app
docker-compose logs postgres

# Reiniciar
docker-compose restart
```

### Executar Comandos no Container

```bash
# Acessar PostgreSQL
docker-compose exec postgres psql -U yuni_user -d yuni_db

# Ver logs em tempo real
docker-compose logs -f app

# Parar container específico
docker-compose stop postgres
```

---

## 📝 Exemplos de Uso

### Criar uma Meta com cURL

```bash
curl -X POST http://localhost:8080/api/metas \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Viagem para Europa",
    "valorMeta": 15000.00,
    "valorAtual": 5000.00,
    "prazo": "2026-07-15"
  }'
```

### Listar Metas com cURL

```bash
curl -X GET http://localhost:8080/api/metas
```

---

## 🧪 Testes

Executar testes localmente:

```bash
./mvnw test
```

Executar testes dentro do container:

```bash
docker-compose run app ./mvnw test
```

---

## 📚 Dependências Principais

| Dependência | Versão | Uso |
|---|---|---|
| spring-boot-starter-web | 4.0.2 | API REST |
| spring-boot-starter-data-jpa | 4.0.2 | Persistência |
| spring-boot-starter-devtools | 4.0.2 | Hot reload |
| postgresql | 42.7.9 | Driver JDBC |
| lombok | 1.18.42 | Reduzir boilerplate |

---

## 🔒 Segurança

Recomendações futuras:
- Implementar Spring Security
- Usar JWT para autenticação
- HTTPS em produção
- Validação de entrada robusta
- Rate limiting

---

## 📈 Performance & Escalabilidade

### Otimizações Implementadas
- Alpine Linux no Docker (imagem menor)
- Multi-stage build no Dockerfile
- Lazy loading de entidades JPA
- Índices no banco de dados (prazo)

### Melhorias Futuras
- Cache com Redis
- Paginação nos endpoints
- Índices adicionais
- Query optimization

---

## 🚢 Deploy em Produção

### Pre-requisitos
- Docker & Docker Compose no servidor
- Certificado SSL/TLS
- Backup automatizado do PostgreSQL
- Monitoramento

### Checklist
- [ ] Alterar `spring.jpa.hibernate.ddl-auto=validate`
- [ ] Usar passwords fortes
- [ ] Configurar environment variables em produção
- [ ] Adicionar health checks
- [ ] Configurar logs centralizados
- [ ] Fazer backup regular do banco

---

## 📞 Suporte

Para dúvidas ou problemas:
1. Verificar logs: `docker-compose logs`
2. Consultar documentação Spring Boot
3. Verificar status dos containers: `docker-compose ps`

---

## 📄 Licença

Projeto pessoal para aprendizado.

---

**Última atualização**: 19 de Fevereiro de 2026

