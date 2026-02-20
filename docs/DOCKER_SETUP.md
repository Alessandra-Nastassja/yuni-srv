# 🚀 Guia Completo - Yuni Service com Docker

## ✅ O que foi configurado:

1. **Dockerfile** - Multi-stage build que:
   - Compila o projeto Maven automaticamente no primeiro estágio
   - Cria uma imagem runtime leve no segundo estágio
   - Exponhe a porta 8080

2. **docker-compose.yml** - Orquestra dois serviços:
   - **PostgreSQL 16** na porta 5432
   - **Spring Boot Application** na porta 8080

3. **application.properties** - Configurações do Spring:
   - Usa variáveis de ambiente do Docker Compose
   - Configurado para PostgreSQL
   - DDL auto configurado como "update"

4. **pom.xml** - Dependências adicionadas:
   - `spring-boot-starter-data-jpa`
   - `org.postgresql:postgresql`

## 🎯 Como Executar:

### Opção 1: Simplificada (recomendado)
```bash
cd /Users/alessandranastassja/Desktop/yuni/yuni-srv
docker-compose up --build
```

### Opção 2: Em background
```bash
cd /Users/alessandranastassja/Desktop/yuni/yuni-srv
docker-compose up -d --build
```

### Opção 3: Com rebuild completo
```bash
cd /Users/alessandranastassja/Desktop/yuni/yuni-srv
docker-compose down -v
docker-compose up --build
```

## 📊 Verificar Status:

```bash
# Ver containers rodando
docker ps

# Ver logs da aplicação
docker-compose logs -f app

# Ver logs do PostgreSQL
docker-compose logs -f postgres

# Ver todos os logs
docker-compose logs -f
```

## 🔌 Acessar os Serviços:

- **API Spring Boot**: http://localhost:8080
- **PostgreSQL**: localhost:5432
  - Banco: `yuni_db`
  - Usuário: `yuni_user`
  - Senha: `yuni_pass123`

## 🛑 Parar tudo:

```bash
# Parar mantendo volumes (dados persistem)
docker-compose down

# Parar e remover volumes (limpar dados)
docker-compose down -v
```

## 📝 Variáveis de Ambiente do Docker:

No `docker-compose.yml`, você pode customizar:

```yaml
environment:
  POSTGRES_DB: yuni_db           # Nome do banco
  POSTGRES_USER: yuni_user       # Usuário PostgreSQL
  POSTGRES_PASSWORD: yuni_pass123 # Senha PostgreSQL
  SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/yuni_db
  SPRING_DATASOURCE_USERNAME: yuni_user
  SPRING_DATASOURCE_PASSWORD: yuni_pass123
  SPRING_JPA_HIBERNATE_DDL_AUTO: update
```

## ⚙️ Arquivos Criados:

- ✅ `Dockerfile` - Multi-stage build
- ✅ `docker-compose.yml` - Orquestração
- ✅ `.dockerignore` - Otimização de build
- ✅ `README_DOCKER.md` - Documentação
- ✅ `application.properties` - Config Spring

## 🐛 Troubleshooting:

### Porta 8080 já em uso
```bash
lsof -i :8080
# Mude a porta no docker-compose.yml
```

### Porta 5432 já em uso
```bash
lsof -i :5432
# Mude a porta no docker-compose.yml
```

### Build muito lento
- Verifique sua conexão de internet
- O Docker está baixando a imagem eclipse-temurin:21-jdk-alpine (≈600MB)

### Erro de conexão ao banco
- Espere o PostgreSQL ficar pronto (healthcheck verifica isso)
- Verifique as credenciais no docker-compose.yml

### Limpar tudo e recomeçar
```bash
docker-compose down -v
docker system prune -a
docker-compose up --build
```

## 📚 Próximos Passos:

1. Execute: `docker-compose up --build`
2. Aguarde o build do Maven (primeira vez é mais lenta)
3. Quando ver "database system is ready to accept connections", aguarde mais um pouco
4. Acesse http://localhost:8080

Boa sorte! 🎉

