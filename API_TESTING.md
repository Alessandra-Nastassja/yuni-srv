# 📋 Testando Metas API com PostgreSQL

## ✅ Endpoints Disponíveis

### 1. Listar Todas as Metas
```
GET /metas
```

**cURL:**
```bash
curl -X GET http://localhost:8080/metas
```

**Resposta esperada:**
```json
{
  "metas": [
    {
      "id": 1,
      "nome": "Independência",
      "valorMeta": 1000000.00,
      "valorAtual": null,
      "prazo": 2030
    }
  ]
}
```

---

### 2. Criar Uma Nova Meta
```
POST /metas
Content-Type: application/json
```

**cURL:**
```bash
curl -X POST http://localhost:8080/metas \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Independência",
    "valorMeta": 1000000.00,
    "valorAtual": 150000.00,
    "prazo": 2030
  }'
```

**Corpo da requisição (JSON):**
```json
{
  "nome": "Independência",
  "valorMeta": 1000000.00,
  "valorAtual": 150000.00,
  "prazo": 2030
}
```

**Resposta esperada:**
```json
{
  "metas": [
    {
      "id": 1,
      "nome": "Independência",
      "valorMeta": 1000000.00,
      "valorAtual": 150000.00,
      "prazo": 2030
    }
  ]
}
```

---

## 🧪 Testando com Postman ou Insomnia

### Criar Meta (POST)
1. **Método**: POST
2. **URL**: `http://localhost:8080/metas`
3. **Headers**: 
   - `Content-Type: application/json`
4. **Body (JSON)**:
```json
{
  "nome": "Minha casa",
  "valorMeta": 300000.00,
  "valorAtual": 50000.00,
  "prazo": 2028
}
```

### Listar Metas (GET)
1. **Método**: GET
2. **URL**: `http://localhost:8080/metas`
3. **Headers**: Nenhum necessário
4. **Body**: Vazio

---

## 📊 Dados Salvos no PostgreSQL

Todos os dados estão sendo salvos em tempo real no banco `yuni_db` na tabela `metas`.

### Conectar ao PostgreSQL direto (opcional):
```bash
psql -h localhost -p 5432 -U yuni_user -d yuni_db

# Depois executar:
SELECT * FROM metas;
```

---

## 🐛 Troubleshooting

### Erro 404 - Endpoint não encontrado
- Verifique se a aplicação está rodando: `docker ps`
- Verifique se a porta 8080 está correta

### Erro 500 - Erro no servidor
- Verifique os logs: `docker-compose logs -f app`
- Pode ser erro de conexão ao banco

### Banco de dados não conecta
- Verifique se PostgreSQL está rodando: `docker-compose logs postgres`
- Verifique credenciais no `docker-compose.yml`

---

## 💾 Estrutura de Dados

### Tabela: metas
```sql
CREATE TABLE metas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    valor_meta DOUBLE NOT NULL,
    valor_atual DOUBLE,
    prazo INTEGER NOT NULL
);
```

---

## 🚀 Próximas Melhorias Possíveis

- [ ] Adicionar endpoint DELETE para remover metas
- [ ] Adicionar endpoint PUT para atualizar metas
- [ ] Adicionar validações de negócio
- [ ] Adicionar tratamento de exceções
- [ ] Adicionar testes unitários
- [ ] Adicionar documentação Swagger/OpenAPI

---

