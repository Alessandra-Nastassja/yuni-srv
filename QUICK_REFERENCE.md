# 🚀 YUNI - Quick Reference

Referência rápida para comandos e informações essenciais.

## ⚡ Start Rápido

```bash
# Iniciar tudo
./start-docker.sh

# Ou manualmente
docker-compose up --build

# Parar
docker-compose down
```

## 🔗 URLs

- **API Base:** http://localhost:8080
- **PostgreSQL:** localhost:5432
- **Health Check:** http://localhost:8080/api/ativos

## 🗄️ Banco de Dados

```
Host: localhost
Port: 5432
Database: yuni_db
User: yuni_user
Password: yuni_pass123
```

## 📋 Endpoints Principais

```bash
# Listar ativos
curl http://localhost:8080/api/ativos

# Criar ativo simples
curl -X POST http://localhost:8080/api/ativos \
  -H "Content-Type: application/json" \
  -d '{"nome":"Teste","tipo":"conta_corrente","valorAtual":1000.00}'

# Criar investimento (Tesouro Direto)
curl -X POST http://localhost:8080/api/ativos/completo \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Tesouro Selic",
    "tipo": "investimentos",
    "tipoInvestimento": "tesouro_direto",
    "tesouroDireto": {
      "tipoTesouro": "selic",
      "valorInvestido": 10000.00,
      "valorAtual": 10500.00,
      "dataCompra": "2025-01-15",
      "dataVencimento": "2027-12-31",
      "corretora": "XP",
      "taxaRentabilidade": 5.5
    }
  }'

# Listar metas
curl http://localhost:8080/api/metas
```

## 🛠️ Comandos Docker

```bash
# Ver logs
docker-compose logs -f app

# Reiniciar app
docker-compose restart app

# Acessar container
docker exec -it yuni-app bash

# Acessar PostgreSQL
docker exec -it yuni-postgres psql -U yuni_user -d yuni_db

# Limpar tudo
docker-compose down -v
docker system prune -a
```

## ☕ Comandos Maven

```bash
# Compilar
./mvnw clean compile

# Rodar local
./mvnw spring-boot:run

# Empacotar
./mvnw clean package

# Pular testes
./mvnw clean package -DskipTests
```

## 🔍 Diagnóstico

```bash
# Status containers
docker-compose ps

# Porta ocupada?
lsof -i :8080

# Testar banco
docker exec -it yuni-postgres psql -U yuni_user -d yuni_db -c "SELECT 1;"

# Ver todas as tabelas
docker exec -it yuni-postgres psql -U yuni_user -d yuni_db -c "\dt"
```

## 🐛 Erros Comuns

| Erro | Solução |
|------|---------|
| Port 8080 in use | `lsof -i :8080` → `kill -9 <PID>` |
| Docker not running | `open -a Docker` (macOS) |
| Connection refused | `docker-compose restart postgres` |
| Permission denied | `chmod +x mvnw start-docker.sh` |

## 📚 Documentação

| Doc | Link | Quando Usar |
|-----|------|-------------|
| README | [README.md](README.md) | Início |
| Índice | [DOCS_INDEX.md](DOCS_INDEX.md) | Navegação |
| Conceitos | [docs/CONCEITOS.md](docs/CONCEITOS.md) | Aprender |
| API | [docs/API.md](docs/API.md) | Endpoints |
| Database | [docs/DATABASE.md](docs/DATABASE.md) | SQL/UML |
| Erros | [docs/TROUBLESHOOTING.md](docs/TROUBLESHOOTING.md) | Debug |

## 🎯 Tipos Válidos

### Tipos de Ativo
```
conta_corrente
meu_negocio
investimentos
contas_a_receber
reserva_emergencia
previdencia_privada
outros
```

### Tipos de Investimento
```
tesouro_direto
renda_fixa
renda_variavel
outros
```

### Tipos de Renda Fixa
```
cdb, lc, debenture, lci, lca, cri, cra, outros
```

### Tipos de Renda Variável
```
acoes, fii, etf
```

## 📝 Queries SQL Úteis

```sql
-- Ver todos os ativos
SELECT * FROM ativos_completo ORDER BY valor_atual DESC;

-- Total por tipo
SELECT tipo, COUNT(*), SUM(valor_atual) FROM ativos_completo GROUP BY tipo;

-- Investimentos de baixo risco
SELECT nome, tipo_investimento, valor_atual FROM ativos_completo WHERE risco = 'baixo';

-- Limpar tabelas
TRUNCATE TABLE ativos_completo CASCADE;
```

## 🆘 Socorro?

1. Consultar [TROUBLESHOOTING.md](docs/TROUBLESHOOTING.md)
2. Ver logs: `docker logs yuni-app --tail 100`
3. Rebuild: `docker-compose down -v && docker-compose up --build`

---

**Dica:** Salve este arquivo nos favoritos para acesso rápido!

Voltar para: [README](README.md) | [Índice](DOCS_INDEX.md)

