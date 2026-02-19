# Solução do Erro de Build Docker

## Problema
```
failed to solve: failed to compute cache key: failed to resolve source metadata for docker.io/library/openjdk:21-slim: not found
failed to compute cache key: "/src": not found
```

## Causa
O erro ocorria porque:
1. O `docker-compose.yml` estava na pasta `docker/` apontando para `build: .`
2. Isso fazia o Docker procurar pelo `Dockerfile` na pasta `docker/`
3. Quando o Dockerfile tentava fazer `COPY src src`, não conseguia encontrar porque `src` está no nível raiz, não dentro de `docker/`

## Solução Implementada

### 1. Dockerfile Movido para Raiz
Movemos o `Dockerfile` para o diretório raiz do projeto (`/Users/alessandranastassja/Desktop/yuni/yuni-srv/Dockerfile`)

### 2. docker-compose.yml Atualizado
Alteramos a referência de build de:
```yaml
app:
  build: .
```

Para:
```yaml
app:
  build: ..
```

Agora o Docker consegue encontrar todos os arquivos necessários:
- `pom.xml` ✓
- `mvnw` ✓
- `.mvn` ✓
- `src` ✓

### 3. .dockerignore Criado
Adicionamos um arquivo `.dockerignore` para melhorar o desempenho do build excluindo arquivos desnecessários:
- `target/` - diretório de build anterior
- `.git/` - dados de versionamento
- `.idea/` - configuração do IDE
- `*.log` - arquivos de log

## Como Usar

### Build e Start
```bash
docker-compose -f docker/docker-compose.yml up --build
```

Ou se estiver na raiz do projeto:
```bash
cd /Users/alessandranastassja/Desktop/yuni/yuni-srv
docker-compose -f docker/docker-compose.yml up --build
```

### Stop
```bash
docker-compose -f docker/docker-compose.yml down
```

### Limpar Volumes (apagar dados do PostgreSQL)
```bash
docker-compose -f docker/docker-compose.yml down -v
```

## Estrutura Corrigida
```
yuni-srv/
├── Dockerfile                 ← Movido para raiz
├── docker-compose.yml         (na pasta docker/)
├── .dockerignore              ← Novo arquivo
├── pom.xml
├── mvnw
├── .mvn/
├── src/
└── docker/
    └── docker-compose.yml
```

## Configuração do PostgreSQL

O `docker-compose.yml` já está configurado com:
- **Database**: yuni_db
- **Username**: yuni_user
- **Password**: yuni_pass123
- **Port**: 5432

A aplicação Spring Boot automaticamente se conecta ao PostgreSQL através das variáveis de ambiente definidas no `docker-compose.yml`.

## Próximos Passos

1. Certifique-se de que a porta 8080 está disponível
2. Certifique-se de que a porta 5432 está disponível
3. Execute: `docker-compose -f docker/docker-compose.yml up --build`
4. A aplicação estará disponível em: `http://localhost:8080`

## Troubleshooting

### Porta 8080 já em uso
```bash
# Matar processo na porta 8080
lsof -ti:8080 | xargs kill -9
```

### Porta 5432 já em uso
```bash
# Matar processo na porta 5432
lsof -ti:5432 | xargs kill -9
```

### Erro de conexão com PostgreSQL
Verificar se o banco de dados foi criado:
```bash
docker-compose -f docker/docker-compose.yml exec postgres psql -U yuni_user -d yuni_db -l
```

