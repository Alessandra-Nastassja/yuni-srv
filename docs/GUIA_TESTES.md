# Guia de Testes da API - YUNI

## 📱 Testando com cURL

### 1. Listar Todas as Metas

```bash
curl -X GET http://localhost:8080/api/metas \
  -H "Content-Type: application/json"
```

**Resposta esperada:**
```json
{
  "metas": []
}
```

### 2. Criar uma Nova Meta

```bash
curl -X POST http://localhost:8080/api/metas \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Comprar Casa",
    "valorMeta": 500000.00,
    "valorAtual": 100000.00,
    "prazo": "2026-12-31"
  }'
```

**Resposta esperada:**
```json
{
  "metas": [
    {
      "id": 1,
      "nome": "Comprar Casa",
      "valorMeta": 500000.00,
      "valorAtual": 100000.00,
      "prazo": "2026-12-31",
      "dataCriacao": "2026-02-19T18:25:00"
    }
  ]
}
```

### 3. Criar Mais Metas

```bash
curl -X POST http://localhost:8080/api/metas \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Viagem Europa",
    "valorMeta": 15000.00,
    "valorAtual": 5000.00,
    "prazo": "2026-07-15"
  }'

curl -X POST http://localhost:8080/api/metas \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Fundo de Emergência",
    "valorMeta": 50000.00,
    "valorAtual": 25000.00,
    "prazo": "2026-06-30"
  }'
```

### 4. Listar Metas (Verificar Ordem)

As metas devem aparecer ordenadas por prazo ascendente:

```bash
curl -X GET http://localhost:8080/api/metas \
  -H "Content-Type: application/json"
```

**Resposta (ordenadas por prazo):**
```json
{
  "metas": [
    {
      "id": 2,
      "nome": "Viagem Europa",
      "valorMeta": 15000.00,
      "valorAtual": 5000.00,
      "prazo": "2026-07-15",
      ...
    },
    {
      "id": 3,
      "nome": "Fundo de Emergência",
      "valorMeta": 50000.00,
      "valorAtual": 25000.00,
      "prazo": "2026-06-30",
      ...
    },
    ...
  ]
}
```

---

## 🔍 Testando com Insomnia/Postman

### Setup

1. **Criar Nova Collection**: "YUNI API"
2. **Criar Ambiente**:
   ```
   base_url: http://localhost:8080
   ```

### Requisições

#### GET - Listar Metas
```
Method: GET
URL: {{base_url}}/api/metas
Headers: Content-Type: application/json
```

#### POST - Criar Meta
```
Method: POST
URL: {{base_url}}/api/metas
Headers: Content-Type: application/json
Body (JSON):
{
  "nome": "Comprar Carro",
  "valorMeta": 80000.00,
  "valorAtual": 20000.00,
  "prazo": "2026-09-30"
}
```

---

## 🗄️ Testando Banco de Dados

### Conectar ao PostgreSQL

```bash
# Usando docker-compose
docker-compose exec postgres psql -U yuni_user -d yuni_db

# Ou direto se tiver psql instalado
psql -h localhost -U yuni_user -d yuni_db
# Senha: yuni_pass123
```

### Comandos SQL Úteis

```sql
-- Ver todas as metas
SELECT * FROM metas ORDER BY prazo ASC;

-- Ver metas criadas hoje
SELECT * FROM metas WHERE DATE(data_criacao) = CURRENT_DATE;

-- Ver status de preenchimento de meta
SELECT 
  nome,
  valor_meta,
  valor_atual,
  ROUND((valor_atual::numeric / valor_meta::numeric * 100), 2) as percentual
FROM metas;

-- Deletar todas as metas
DELETE FROM metas;

-- Ver estrutura da tabela
\d metas
```

---

## 🧪 Teste Completo (Passo a Passo)

### 1. Verificar Saúde da API

```bash
curl -v http://localhost:8080/api/metas
```

Deve retornar status 200 com `metas: []`

### 2. Criar 3 Metas

**Meta 1 - Curto prazo (3 meses)**
```bash
curl -X POST http://localhost:8080/api/metas \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Férias em Maio",
    "valorMeta": 10000.00,
    "valorAtual": 3000.00,
    "prazo": "2026-05-31"
  }'
```

**Meta 2 - Médio prazo (6 meses)**
```bash
curl -X POST http://localhost:8080/api/metas \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Reforma do Apartamento",
    "valorMeta": 40000.00,
    "valorAtual": 8000.00,
    "prazo": "2026-08-31"
  }'
```

**Meta 3 - Longo prazo (1 ano)**
```bash
curl -X POST http://localhost:8080/api/metas \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Carro Zero Km",
    "valorMeta": 150000.00,
    "valorAtual": 30000.00,
    "prazo": "2027-02-28"
  }'
```

### 3. Listar e Verificar Ordem

```bash
curl -X GET http://localhost:8080/api/metas
```

Deve retornar as 3 metas **ordenadas por prazo ascendente**.

### 4. Verificar no Banco

```bash
docker-compose exec postgres psql -U yuni_user -d yuni_db -c "SELECT id, nome, prazo FROM metas ORDER BY prazo;"
```

### 5. Fazer Cálculos

```bash
docker-compose exec postgres psql -U yuni_user -d yuni_db << EOF
SELECT 
  nome,
  valor_meta,
  valor_atual,
  ROUND((valor_atual::numeric / valor_meta::numeric * 100), 1) as percentual_atingido
FROM metas
ORDER BY prazo;
EOF
```

---

## ✅ Checklist de Testes

- [ ] API respondendo em `http://localhost:8080`
- [ ] Endpoint GET `/api/metas` retorna 200
- [ ] Endpoint POST `/api/metas` cria meta com sucesso
- [ ] Meta criada é retornada no POST
- [ ] Metas são listadas em ordem de prazo
- [ ] Dados aparecem no PostgreSQL
- [ ] Novo container inicia sem erros

---

## 🐛 Troubleshooting

### "Connection refused"

```bash
# Verificar se containers estão rodando
docker-compose ps

# Se não, iniciar
docker-compose up -d
```

### "Database does not exist"

```bash
# Ver logs do PostgreSQL
docker-compose logs postgres

# Recria tudo
docker-compose down -v
docker-compose up --build
```

### "Cannot create bean 'AtivosRepository'"

Significa que a entidade `Ativos.java` não tem as anotações JPA. Verificar se tem `@Entity`.

### Aplicação demora para subir

A compilação Maven pode levar 2-3 minutos. Aguarde. Você pode monitorar:

```bash
docker-compose logs -f app
```

---

## 📊 Exemplos de Respostas

### Sucesso - 200 OK
```json
{
  "metas": [
    {
      "id": 1,
      "nome": "Meta Exemplo",
      "valorMeta": 100000.00,
      "valorAtual": 50000.00,
      "prazo": "2026-12-31",
      "dataCriacao": "2026-02-19T18:20:00"
    }
  ]
}
```

### Erro - 400 Bad Request
```json
{
  "timestamp": "2026-02-19T18:20:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/metas"
}
```

### Erro - 500 Internal Server Error
```json
{
  "timestamp": "2026-02-19T18:20:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An error occurred",
  "path": "/api/metas"
}
```

---

## 📝 Notas

- Todas as datas devem estar no formato ISO: `YYYY-MM-DD`
- Valores monetários são `Double` (podem ter 2 casas decimais)
- A ordenação é **sempre** por prazo ascendente (metas mais próximas primeiro)
- `dataCriacao` é preenchida automaticamente pelo servidor


