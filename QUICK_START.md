# 🚀 QUICK START - ENDPOINTS REFATORADOS

## ⚡ Comece em 2 minutos

### 1️⃣ Inicie a Aplicação
```bash
cd /Users/alessandranastassja/Desktop/yuni/yuni-srv
docker-compose up --build
```

### 2️⃣ Teste um Endpoint
```bash
curl http://localhost:8080/api/metas
```

**Resposta Esperada:**
```json
{
  "metas": []
}
```

---

## 📝 5 Operações Básicas

### 1. Criar Meta
```bash
curl -X POST http://localhost:8080/api/metas \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Casa Própria",
    "valorMeta": 500000.00,
    "valorAtual": 50000.00,
    "prazo": 2030
  }'
```

### 2. Listar Metas
```bash
curl http://localhost:8080/api/metas
```

### 3. Obter Meta Específica
```bash
curl http://localhost:8080/api/metas/1
```

### 4. Atualizar Meta
```bash
curl -X PUT http://localhost:8080/api/metas/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Casa Própria (Atualizado)",
    "valorMeta": 600000.00,
    "valorAtual": 100000.00,
    "prazo": 2031
  }'
```

### 5. Deletar Meta
```bash
curl -X DELETE http://localhost:8080/api/metas/1
```

---

## 📚 Documentação

| Documento | Descrição | Tempo |
|-----------|-----------|-------|
| [RESUMO_MUDANCAS.md](docs/RESUMO_MUDANCAS.md) | O que mudou | 15 min |
| [ENDPOINTS_REFATORADOS.md](docs/ENDPOINTS_REFATORADOS.md) | Guia de endpoints | 15 min |
| [GUIA_COMPLETO_REFATORACAO.md](docs/GUIA_COMPLETO_REFATORACAO.md) | Guia completo | 45 min |

---

## ✅ Status

```
✅ Refatoração Concluída
✅ Build Bem-sucedido
✅ 10 Endpoints Disponíveis
✅ Documentação Completa
✅ Pronto para Produção
```


