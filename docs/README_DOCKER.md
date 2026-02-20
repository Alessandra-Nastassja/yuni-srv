# Yuni Service - Docker Setup

## 📋 Requisitos

- Docker instalado e rodando
- Docker Compose instalado
- Java 21+ (para build local)
- Maven 3.6+ (para build local)

## 🚀 Como Executar

### 1. Build do Projeto

Primeiro, compile o projeto com Maven:

```bash
mvn clean package
```

### 2. Iniciar os Containers

Execute o Docker Compose para iniciar a aplicação e o PostgreSQL:

```bash
docker-compose up
```

Para rodar em background:

```bash
docker-compose up -d
```

### 3. Acessar a Aplicação

- **API**: http://localhost:8080
- **Banco de Dados**: localhost:5432

## 📦 Serviços

### PostgreSQL
- **Imagem**: postgres:16-alpine
- **Banco**: yuni_db
- **Usuário**: yuni_user
- **Senha**: yuni_pass123
- **Porta**: 5432

### Aplicação Spring Boot
- **Porta**: 8080
- **Variáveis de Ambiente**:
  - `SPRING_DATASOURCE_URL`: jdbc:postgresql://postgres:5432/yuni_db
  - `SPRING_DATASOURCE_USERNAME`: yuni_user
  - `SPRING_DATASOURCE_PASSWORD`: yuni_pass123
  - `SPRING_JPA_HIBERNATE_DDL_AUTO`: update

## 🛑 Parar os Containers

```bash
docker-compose down
```

Para remover também os volumes (dados do PostgreSQL):

```bash
docker-compose down -v
```

## 📝 Logs

Ver logs de todos os serviços:

```bash
docker-compose logs -f
```

Ver logs da aplicação apenas:

```bash
docker-compose logs -f app
```

Ver logs do PostgreSQL apenas:

```bash
docker-compose logs -f postgres
```

## 🔧 Personalizar Credenciais

Para mudar as credenciais do banco de dados, edite o arquivo `docker-compose.yml`:

```yaml
environment:
  POSTGRES_DB: seu_banco
  POSTGRES_USER: seu_usuario
  POSTGRES_PASSWORD: sua_senha
```

E atualize também as variáveis da aplicação:

```yaml
environment:
  SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/seu_banco
  SPRING_DATASOURCE_USERNAME: seu_usuario
  SPRING_DATASOURCE_PASSWORD: sua_senha
```

## 📂 Estrutura de Arquivos

```
yuni-srv/
├── Dockerfile              # Configuração da imagem Docker
├── docker-compose.yml      # Orquestração dos serviços
├── pom.xml                 # Dependências Maven
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── target/                 # Build compilado
```

## 🐛 Troubleshooting

### Porta 5432 já está em uso
```bash
# Encontrar processo usando a porta
lsof -i :5432

# Ou mudar a porta no docker-compose.yml
ports:
  - "5433:5432"
```

### Erro de conexão com PostgreSQL
Aguarde o healthcheck passar (até 30 segundos). O Docker Compose só inicia a aplicação quando o PostgreSQL está pronto.

### Reconstruir imagem
```bash
docker-compose up --build
```

## 📚 Mais Informações

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [PostgreSQL Docker Hub](https://hub.docker.com/_/postgres)
- [Docker Compose Documentation](https://docs.docker.com/compose/)

