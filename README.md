# YUNI - Sistema de Gestão de Ativos Financeiros

Sistema backend para gestão de ativos financeiros (conta corrente, investimentos, metas, etc.) desenvolvido com Spring Boot e PostgreSQL.

> 📚 **[Índice da Documentação](DOCS_INDEX.md)** | ⚡ **[Quick Reference](QUICK_REFERENCE.md)**

## 🚀 Tecnologias

- **Java 21**
- **Spring Boot 4.0.2**
- **PostgreSQL 16**
- **Docker & Docker Compose**
- **Maven**
- **Lombok**

## 📋 Pré-requisitos

- Docker e Docker Compose instalados
- Java 21 (se for rodar localmente sem Docker)
- Maven 3.8+ (se for rodar localmente sem Docker)

## 🏃 Como Rodar

### Opção 1: Docker (Recomendado)

```bash
# 1. Dar permissão ao script
chmod +x start-docker.sh

# 2. Iniciar aplicação e banco de dados
./start-docker.sh

# Ou manualmente:
docker-compose up --build
```

A aplicação estará disponível em: **http://localhost:8080**

### Opção 2: Local (sem Docker)

```bash
# 1. Certifique-se de ter PostgreSQL rodando localmente
# Configurar banco: yuni_db, usuário: yuni_user, senha: yuni_pass123

# 2. Compilar o projeto
./mvnw clean install

# 3. Executar a aplicação
./mvnw spring-boot:run
```

## 📦 Estrutura do Projeto

```
yuni-srv/
├── src/main/java/com/nast/yuni/
│   ├── controller/        # Endpoints REST
│   ├── service/           # Lógica de negócio
│   ├── repository/        # Acesso ao banco de dados
│   ├── domain/            # Entidades JPA
│   ├── request/           # DTOs de entrada
│   ├── response/          # DTOs de saída
│   └── configuration/     # Configurações (CORS, etc.)
├── docs/                  # Documentação
├── sql/                   # Scripts SQL
├── docker-compose.yml     # Orquestração Docker
└── pom.xml               # Dependências Maven
```

## 🔗 Endpoints Principais

### Ativos Simples
- `GET /api/ativos` - Listar todos os ativos
- `POST /api/ativos` - Criar um ativo
- `POST /api/ativos/lote` - Criar múltiplos ativos
- `PUT /api/ativos/{id}` - Atualizar ativo
- `DELETE /api/ativos/{id}` - Deletar ativo

### Ativos Completos (Investimentos)
- `POST /api/ativos/completo` - Criar ativo com investimentos detalhados

### Metas
- `GET /api/metas` - Listar todas as metas
- `POST /api/metas` - Criar meta
- `PUT /api/metas/{id}` - Atualizar meta
- `DELETE /api/metas/{id}` - Deletar meta

Ver documentação completa em: [docs/API.md](docs/API.md)

## 🛠️ Comandos Úteis

### Docker

```bash
# Parar containers
docker-compose down

# Parar e remover volumes (limpa banco de dados)
docker-compose down -v

# Ver logs
docker-compose logs -f

# Ver logs apenas do app
docker-compose logs -f app

# Recriar apenas o app
docker-compose up -d --build app
```

### Maven

```bash
# Compilar
./mvnw clean compile

# Rodar testes
./mvnw test

# Empacotar (gera JAR)
./mvnw clean package

# Pular testes
./mvnw clean package -DskipTests
```

## 🗄️ Banco de Dados

**Conexão:**
- Host: localhost
- Porta: 5432
- Database: yuni_db
- Usuário: yuni_user
- Senha: yuni_pass123

**Tabelas principais:**
- `ativos` - Ativos simples
- `ativos_completo` - Ativos com investimentos
- `tesouro_direto` - Investimentos em Tesouro Direto
- `renda_fixa` - Investimentos em Renda Fixa
- `renda_variavel` - Investimentos em Renda Variável
- `metas` - Metas financeiras

Ver schema completo em: [docs/DATABASE.md](docs/DATABASE.md)

## 🐛 Troubleshooting

### Erro: "Port 8080 already in use"
```bash
# Encontrar processo usando a porta
lsof -i :8080

# Matar processo
kill -9 <PID>
```

### Erro: "Cannot connect to Docker daemon"
```bash
# Iniciar Docker Desktop (macOS)
open -a Docker
```

### Erro: "Database connection failed"
```bash
# Verificar se PostgreSQL está rodando
docker ps | grep postgres

# Reiniciar container do banco
docker-compose restart postgres
```

Ver mais soluções em: [docs/TROUBLESHOOTING.md](docs/TROUBLESHOOTING.md)

## 📚 Documentação Adicional

- [Conceitos Java/Spring](docs/CONCEITOS.md)
- [API e Exemplos](docs/API.md)
- [Banco de Dados e UML](docs/DATABASE.md)
- [Troubleshooting](docs/TROUBLESHOOTING.md)

## 👨‍💻 Desenvolvimento

```bash
# Habilitar live reload (já configurado com devtools)
./mvnw spring-boot:run

# Format code
./mvnw spring-javaformat:apply
```

## 📝 Notas

- O banco é criado automaticamente na primeira execução
- As tabelas são geradas pelo Hibernate (JPA) automaticamente
- CORS está configurado para aceitar requisições de qualquer origem
- Logs estão disponíveis em `docker/docker-output.log`

## 🎯 Status do Projeto

✅ **COMPLETO E FUNCIONAL** (última atualização: 20/02/2026)

---

Desenvolvido com ☕ e 💙

