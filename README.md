# Quick Start - YUNI

## 🚀 Iniciar em 3 passos

```bash
# 1. Navegar para o projeto
cd /Users/alessandranastassja/Desktop/yuni/yuni-srv

# 2. Iniciar Docker
docker-compose up --build

# 3. Aguardar...
# ✓ PostgreSQL estará em localhost:5432
# ✓ Aplicação estará em http://localhost:8080
```

## 🔗 Endpoints Principais

### Metas
```
GET    /api/metas           - Listar todas as metas
POST   /api/metas           - Criar nova meta
GET    /api/metas/{id}      - Obter meta específica
PUT    /api/metas/{id}      - Atualizar meta
DELETE /api/metas/{id}      - Deletar meta
```

### Patrimônio
```
GET    /api/patrimonio      - Ver patrimônio total
GET    /api/patrimonio/ativos    - Listar ativos
GET    /api/patrimonio/inativos  - Listar inativos
POST   /api/patrimonio/ativo     - Adicionar ativo
```

## 💾 Banco de Dados

- **Host**: localhost
- **Port**: 5432
- **Database**: yuni_db
- **User**: yuni_user
- **Password**: yuni_pass123

## 🛑 Parar a Aplicação

```bash
# Parar containers
docker-compose stop

# Parar e remover containers
docker-compose down

# Remover tudo incluindo dados
docker-compose down -v
```

## 📖 Documentação Completa

Ver `PROJETO_COMPLETO.md` para detalhes sobre:
- Arquitetura
- Conceitos
- Configuração
- Troubleshooting
- Deploy

---

**Documentação**: `/docs/PROJETO_COMPLETO.md`

