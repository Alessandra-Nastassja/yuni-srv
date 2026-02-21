# Troubleshooting - Erros Comuns e Soluções

Guia para resolver problemas comuns no desenvolvimento e execução do projeto YUNI.

## 🐳 Docker

### Erro: "Cannot connect to Docker daemon"

**Problema:** Docker não está rodando.

**Solução (macOS):**
```bash
# Abrir Docker Desktop
open -a Docker

# Aguardar alguns segundos até o Docker iniciar
# Verificar status
docker ps
```

**Solução (Linux):**
```bash
# Iniciar serviço Docker
sudo systemctl start docker

# Habilitar para iniciar automaticamente
sudo systemctl enable docker
```

---

### Erro: "Port 8080 already in use"

**Problema:** Outra aplicação está usando a porta 8080.

**Solução:**
```bash
# Encontrar processo usando a porta
lsof -i :8080

# Ou no Linux
netstat -tulpn | grep 8080

# Matar o processo (substituir <PID> pelo número do processo)
kill -9 <PID>

# Alternativa: Mudar a porta no docker-compose.yml
# Alterar de "8080:8080" para "8081:8080"
```

---

### Erro: "Port 5432 already in use"

**Problema:** PostgreSQL já está rodando localmente.

**Solução:**
```bash
# Parar PostgreSQL local (macOS)
brew services stop postgresql

# Ou no Linux
sudo systemctl stop postgresql

# Alternativa: Mudar porta no docker-compose.yml
# Alterar de "5432:5432" para "5433:5432"
# E atualizar SPRING_DATASOURCE_URL para jdbc:postgresql://postgres:5432/yuni_db
```

---

### Erro: "Dockerfile not found"

**Problema:** Docker não encontra o Dockerfile.

**Solução:**
```bash
# Verificar se Dockerfile está na raiz do projeto
ls -la | grep Dockerfile

# Se estiver em docker/, mover para raiz
mv docker/Dockerfile .

# Garantir que docker-compose.yml aponta para raiz
# build: . (e não build: ./docker)
```

---

### Erro: "Database connection failed"

**Problema:** Aplicação não consegue conectar ao PostgreSQL.

**Solução:**
```bash
# 1. Verificar se PostgreSQL está rodando
docker ps | grep postgres

# 2. Verificar logs do PostgreSQL
docker logs yuni-postgres

# 3. Testar conexão manual
docker exec -it yuni-postgres psql -U yuni_user -d yuni_db

# 4. Reiniciar container do banco
docker-compose restart postgres

# 5. Recriar container do banco (apaga dados!)
docker-compose down -v
docker-compose up -d postgres
```

---

### Erro: "Service 'app' failed to build"

**Problema:** Erro na compilação da imagem Docker.

**Solução:**
```bash
# Ver logs completos
docker-compose build --no-cache

# Se erro for de compilação Java, compilar localmente primeiro
./mvnw clean package -DskipTests

# Limpar cache do Docker
docker system prune -a

# Reconstruir
docker-compose up --build
```

---

## ☕ Maven

### Erro: "mvnw: Permission denied"

**Problema:** Script Maven não tem permissão de execução.

**Solução:**
```bash
# Dar permissão de execução
chmod +x mvnw

# Executar
./mvnw clean install
```

---

### Erro: "JAVA_HOME not set"

**Problema:** Variável de ambiente JAVA_HOME não está configurada.

**Solução (macOS):**
```bash
# Verificar Java instalado
/usr/libexec/java_home -V

# Adicionar ao ~/.zshrc ou ~/.bash_profile
export JAVA_HOME=$(/usr/libexec/java_home -v 21)

# Recarregar
source ~/.zshrc
```

**Solução (Linux):**
```bash
# Encontrar Java
update-alternatives --config java

# Adicionar ao ~/.bashrc
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64

# Recarregar
source ~/.bashrc
```

---

### Erro: "Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin"

**Problema:** Erro de compilação Java.

**Solução:**
```bash
# Limpar cache do Maven
./mvnw clean

# Compilar novamente
./mvnw compile

# Se persistir, deletar cache local
rm -rf ~/.m2/repository

# Baixar dependências novamente
./mvnw clean install
```

---

### Erro: "Lombok not working / getters and setters not found"

**Problema:** Lombok não está configurado na IDE.

**Solução IntelliJ IDEA:**
1. Instalar plugin Lombok:
   - Settings → Plugins → Search "Lombok" → Install
2. Habilitar annotation processing:
   - Settings → Build, Execution, Deployment → Compiler → Annotation Processors
   - Check "Enable annotation processing"
3. Restart IDE

**Solução VS Code:**
```bash
# Instalar extensão Java Lombok
code --install-extension GabrielBB.vscode-lombok
```

---

## 🗄️ PostgreSQL

### Erro: "password authentication failed for user"

**Problema:** Senha incorreta.

**Solução:**
```bash
# Verificar credenciais no docker-compose.yml
# Devem ser:
# POSTGRES_USER: yuni_user
# POSTGRES_PASSWORD: yuni_pass123
# POSTGRES_DB: yuni_db

# Se mudou, atualizar também no SPRING_DATASOURCE_URL e _USERNAME e _PASSWORD
```

---

### Erro: "relation does not exist"

**Problema:** Tabela não foi criada.

**Solução:**
```bash
# 1. Verificar logs da aplicação
docker logs yuni-app

# 2. Executar migrations manualmente
docker exec -i yuni-postgres psql -U yuni_user -d yuni_db < sql/migrations-ativos-completo.sql

# 3. Verificar configuração JPA
# Em application.properties deve ter:
# spring.jpa.hibernate.ddl-auto=update
```

---

### Erro: "too many connections"

**Problema:** Limite de conexões do PostgreSQL excedido.

**Solução:**
```bash
# Reiniciar PostgreSQL
docker-compose restart postgres

# Ou aumentar limite no docker-compose.yml
# Adicionar em environment:
# POSTGRES_MAX_CONNECTIONS: 200
```

---

## 🚀 Aplicação Spring Boot

### Erro: "org.springframework.beans.factory.BeanCreationException"

**Problema:** Spring não conseguiu criar um bean.

**Solução:**
```bash
# Ver erro completo nos logs
docker logs yuni-app --tail 100

# Causas comuns:
# 1. Dependência não injetada corretamente
# 2. Circular dependency
# 3. Repository não está como interface

# Verificar:
# - @Service nas classes de serviço
# - @Repository ou extends JpaRepository nos repositórios
# - @RequiredArgsConstructor + campos final para injeção
```

---

### Erro: "Whitelabel Error Page"

**Problema:** Endpoint não encontrado ou erro na aplicação.

**Solução:**
```bash
# Verificar se aplicação está rodando
curl http://localhost:8080/api/ativos

# Ver logs
docker logs yuni-app

# Verificar CORS
# CorsConfig deve permitir sua origem

# Verificar se controller tem @RestController e @RequestMapping
```

---

### Erro: "400 Bad Request - Validation failed"

**Problema:** Dados enviados não passaram na validação.

**Solução:**
```json
// Verificar campos obrigatórios:
// Para ativos simples:
{
  "nome": "obrigatório, max 30 chars",
  "tipo": "obrigatório, valores válidos: conta_corrente, meu_negocio, etc.",
  "valorAtual": "obrigatório (exceto para investimentos)"
}

// Para investimentos, verificar campos específicos de cada tipo
```

---

### Erro: "IllegalArgumentException"

**Problema:** Validação de negócio falhou.

**Solução:**
```bash
# Ver mensagem de erro específica
# Exemplos comuns:
# - "Nome é obrigatório"
# - "Tipo inválido"
# - "tipoFonteRenda é obrigatório para tipo: conta_corrente"
# - "Dados de tesouro direto são obrigatórios"

# Ajustar payload conforme a mensagem
```

---

## 🔧 Comandos de Diagnóstico

### Verificar status dos containers

```bash
docker-compose ps
```

### Ver logs em tempo real

```bash
# Todos os serviços
docker-compose logs -f

# Apenas app
docker-compose logs -f app

# Apenas postgres
docker-compose logs -f postgres

# Últimas 100 linhas
docker logs yuni-app --tail 100
```

### Acessar container

```bash
# Acessar bash do app
docker exec -it yuni-app bash

# Acessar PostgreSQL
docker exec -it yuni-postgres psql -U yuni_user -d yuni_db
```

### Verificar conectividade

```bash
# App consegue acessar banco?
docker exec yuni-app ping postgres

# Testar endpoint
curl -X GET http://localhost:8080/api/ativos

# Ver headers
curl -v http://localhost:8080/api/ativos
```

### Limpar tudo e recomeçar

```bash
# Parar e remover containers, volumes e redes
docker-compose down -v

# Remover imagens
docker rmi yuni-srv-app
docker rmi postgres:16-alpine

# Limpar cache do Docker
docker system prune -a

# Reconstruir tudo
docker-compose up --build
```

---

## 📝 Logs Importantes

### Onde encontrar logs

```bash
# Logs da aplicação
docker logs yuni-app > app.log

# Logs do PostgreSQL
docker logs yuni-postgres > postgres.log

# Logs do build Docker
# Estão em: docker/docker-build.log (se configurado)
```

### Aumentar verbosidade dos logs

Em `application.properties`:
```properties
# Habilitar SQL logs
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Log level
logging.level.root=INFO
logging.level.com.nast.yuni=DEBUG
logging.level.org.springframework.web=DEBUG
```

---

## 🆘 Ainda com problemas?

1. **Verificar versões:**
   ```bash
   docker --version        # >= 20.0
   docker-compose --version # >= 1.29
   java --version          # 21
   ./mvnw --version        # Maven 3.8+
   ```

2. **Limpar cache Maven:**
   ```bash
   ./mvnw dependency:purge-local-repository
   ```

3. **Rebuild completo:**
   ```bash
   # Limpar Maven
   ./mvnw clean
   
   # Limpar Docker
   docker-compose down -v
   docker system prune -a
   
   # Reconstruir
   ./mvnw clean package -DskipTests
   docker-compose up --build
   ```

4. **Verificar documentação:**
   - [README.md](../README.md)
   - [API.md](API.md)
   - [DATABASE.md](DATABASE.md)
   - [CONCEITOS.md](CONCEITOS.md)

---

Voltar para: [README](../README.md)

