# Banco de Dados - Schema e UML

Documentação do banco de dados PostgreSQL do projeto YUNI.

## 🗄️ Conexão

```
Host: localhost
Port: 5432
Database: yuni_db
Username: yuni_user
Password: yuni_pass123
```

**Connection String:**
```
jdbc:postgresql://localhost:5432/yuni_db
```

## 📊 Diagrama UML

```
┌─────────────────────────────────────────────────────────────────────┐
│                          ativos_completo                            │
├─────────────────────────────────────────────────────────────────────┤
│ PK  id                    SERIAL                                    │
│     nome                  VARCHAR(30)     NOT NULL                  │
│     tipo                  VARCHAR(50)     NOT NULL                  │
│     tipo_fonte_renda      VARCHAR(100)                              │
│     valor_atual           DECIMAL(15,2)                             │
│     tipo_investimento     VARCHAR(50)                               │
│ FK  tesouro_direto_id     INTEGER         UNIQUE                    │
│ FK  renda_fixa_id         INTEGER         UNIQUE                    │
│ FK  renda_variavel_id     INTEGER         UNIQUE                    │
│     categorizacao         VARCHAR(100)                              │
│     risco                 VARCHAR(20)     NOT NULL DEFAULT 'indefinido' │
│     data_criacao          TIMESTAMP       NOT NULL                  │
│     data_atualizacao      TIMESTAMP       NOT NULL                  │
└─────────────────────────────────────────────────────────────────────┘
                 │                  │                  │
                 │ 1:1              │ 1:1              │ 1:1
                 ▼                  ▼                  ▼
     ┌──────────────────┐  ┌──────────────────┐  ┌──────────────────┐
     │ tesouro_direto   │  │   renda_fixa     │  │ renda_variavel   │
     ├──────────────────┤  ├──────────────────┤  ├──────────────────┤
     │ PK id            │  │ PK id            │  │ PK id            │
     │ tipo_tesouro     │  │ tipo_ativo_rf    │  │ tipo_renda_var   │
     │ valor_investido  │  │ valor_investido  │  │ quantidade       │
     │ valor_atual      │  │ valor_atual      │  │ preco_medio      │
     │ data_compra      │  │ corretora        │  │ valor_atual      │
     │ data_vencimento  │  │ data_compra      │  │ corretora        │
     │ corretora        │  │ data_vencimento  │  │ categoria_risco  │
     │ taxa_rentab      │  │ tipo_taxa        │  │ data_compra      │
     │ risco            │  │ taxa_contratada  │  │ dividendos_rec   │
     │ created_at       │  │ percentual_cdi   │  │ ir_est_acoes     │
     │ updated_at       │  │ cdi_atual        │  │ dividend_yield   │
     └──────────────────┘  │ ipca_taxa        │  │ ir_est_fii       │
                           │ tipo_debenture   │  │ ir_est_etf       │
┌──────────────────┐      │ categoria_risco  │  │ created_at       │
│     ativos       │      │ isento           │  │ updated_at       │
├──────────────────┤      │ ir_estimado      │  └──────────────────┘
│ PK id            │      │ valor_final_est  │
│ nome             │      │ created_at       │
│ tipo             │      │ updated_at       │
│ valor_atual      │      └──────────────────┘
└──────────────────┘

┌──────────────────┐
│      metas       │
├──────────────────┤
│ PK id            │
│ descricao        │
│ valor_objetivo   │
│ valor_atual      │
│ prazo            │
│ percentual_prog  │
└──────────────────┘
```

## 📋 Tabelas

### 1. ativos (Ativos Simples)

Tabela para ativos básicos.

```sql
CREATE TABLE ativos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    valor_atual DECIMAL(15, 2)
);
```

**Campos:**
- `id`: Identificador único (chave primária)
- `nome`: Nome do ativo
- `tipo`: Tipo do ativo (conta_corrente, meu_negocio, etc.)
- `valor_atual`: Valor atual do ativo

**Exemplo de dados:**
```sql
INSERT INTO ativos (nome, tipo, valor_atual) VALUES
('Conta Corrente', 'conta_corrente', 5000.00),
('Poupança', 'reserva_emergencia', 15000.00);
```

---

### 2. ativos_completo (Ativos Completos)

Tabela principal para ativos com investimentos detalhados.

```sql
CREATE TABLE ativos_completo (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(30) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    tipo_fonte_renda VARCHAR(100),
    valor_atual DECIMAL(15, 2),
    tipo_investimento VARCHAR(50),
    tesouro_direto_id INTEGER UNIQUE,
    renda_fixa_id INTEGER UNIQUE,
    renda_variavel_id INTEGER UNIQUE,
    categorizacao VARCHAR(100),
    risco VARCHAR(20) NOT NULL DEFAULT 'indefinido',
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (tesouro_direto_id) REFERENCES tesouro_direto(id) ON DELETE SET NULL,
    FOREIGN KEY (renda_fixa_id) REFERENCES renda_fixa(id) ON DELETE SET NULL,
    FOREIGN KEY (renda_variavel_id) REFERENCES renda_variavel(id) ON DELETE SET NULL
);
```

**Índices:**
```sql
CREATE INDEX idx_ativos_completo_tipo ON ativos_completo(tipo);
CREATE INDEX idx_ativos_completo_tipo_investimento ON ativos_completo(tipo_investimento);
CREATE INDEX idx_ativos_completo_risco ON ativos_completo(risco);
CREATE INDEX idx_ativos_completo_valor_atual ON ativos_completo(valor_atual DESC);
```

---

### 3. tesouro_direto

Tabela para investimentos em Tesouro Direto.

```sql
CREATE TABLE tesouro_direto (
    id SERIAL PRIMARY KEY,
    tipo_tesouro VARCHAR(50) NOT NULL,
    valor_investido DECIMAL(15, 2) NOT NULL,
    valor_atual DECIMAL(15, 2) NOT NULL,
    data_compra DATE NOT NULL,
    data_vencimento DATE NOT NULL,
    corretora VARCHAR(100),
    taxa_rentabilidade DECIMAL(5, 2) NOT NULL,
    risco VARCHAR(20) NOT NULL DEFAULT 'baixo',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**Tipos válidos:**
- `selic` - Tesouro Selic
- `prefixado` - Tesouro Prefixado
- `ipca` - Tesouro IPCA+

**Risco:** Sempre `baixo`

---

### 4. renda_fixa

Tabela para investimentos em Renda Fixa.

```sql
CREATE TABLE renda_fixa (
    id SERIAL PRIMARY KEY,
    tipo_ativo_renda_fixa VARCHAR(50) NOT NULL,
    valor_investido DECIMAL(15, 2) NOT NULL,
    valor_atual DECIMAL(15, 2) NOT NULL,
    corretora VARCHAR(100),
    data_compra DATE NOT NULL,
    data_vencimento DATE NOT NULL,
    tipo_taxa VARCHAR(50) NOT NULL,
    taxa_contratada DECIMAL(5, 2),
    percentual_cdi DECIMAL(5, 2),
    cdi_atual DECIMAL(5, 2),
    ipca_taxa DECIMAL(5, 2),
    tipo_debenture VARCHAR(50),
    categoria_risco_renda_fixa VARCHAR(20) NOT NULL,
    isento BOOLEAN NOT NULL DEFAULT false,
    ir_estimado DECIMAL(15, 2),
    valor_final_estimado DECIMAL(15, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**Tipos de Ativo:**
- `cdb` - Certificado de Depósito Bancário
- `lc` - Letra de Câmbio
- `debenture` - Debênture
- `lci` - Letra de Crédito Imobiliário (isento)
- `lca` - Letra de Crédito do Agronegócio (isento)
- `cri` - Certificado de Recebíveis Imobiliários (isento)
- `cra` - Certificado de Recebíveis do Agronegócio (isento)
- `outros`

**Tipos de Taxa:**
- `prefixado` - Taxa fixa (usar `taxa_contratada`)
- `pos_fixado_cdi` - % do CDI (usar `percentual_cdi` e `cdi_atual`)
- `ipca` - IPCA + taxa (usar `ipca_taxa`)

**Tipos de Debênture:**
- `incentivada` - Isenta de IR
- `comum` - Com IR

**Categoria de Risco:**
- `baixo`, `medio`, `alto`

---

### 5. renda_variavel

Tabela para investimentos em Renda Variável.

```sql
CREATE TABLE renda_variavel (
    id SERIAL PRIMARY KEY,
    tipo_renda_variavel VARCHAR(50) NOT NULL,
    quantidade INTEGER NOT NULL,
    preco_medio DECIMAL(15, 2) NOT NULL,
    valor_atual DECIMAL(15, 2) NOT NULL,
    corretora VARCHAR(100),
    categoria_risco_renda_variavel VARCHAR(20) NOT NULL,
    data_compra DATE,
    dividendos_recebidos DECIMAL(15, 2),
    ir_estimado_acoes INTEGER,
    dividend_yield DECIMAL(5, 3),
    ir_estimado_fii VARCHAR(50),
    ir_estimado_etf INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**Tipos de Renda Variável:**
- `acoes` - Ações
  - Campos: `data_compra`, `dividendos_recebidos`, `ir_estimado_acoes` (15 ou 20)
- `fii` - Fundos Imobiliários
  - Campos: `dividend_yield`, `ir_estimado_fii` (texto descritivo)
- `etf` - Exchange Traded Funds
  - Campos: `ir_estimado_etf` (15 ou 20)

**Categoria de Risco:**
- `baixo`, `medio`, `alto`

---

### 6. metas

Tabela para metas financeiras.

```sql
CREATE TABLE metas (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    valor_objetivo DECIMAL(15, 2) NOT NULL,
    valor_atual DECIMAL(15, 2) NOT NULL,
    prazo DATE NOT NULL,
    percentual_progresso DECIMAL(5, 2)
);
```

**Campos:**
- `id`: Identificador único
- `descricao`: Descrição da meta
- `valor_objetivo`: Valor que se deseja alcançar
- `valor_atual`: Valor já alcançado
- `prazo`: Data limite para alcançar a meta
- `percentual_progresso`: Calculado automaticamente (valor_atual / valor_objetivo * 100)

---

## 🔑 Relacionamentos

### ativos_completo → tesouro_direto
- Relacionamento: **1:1** (One-to-One)
- FK: `ativos_completo.tesouro_direto_id`
- Restrição: `UNIQUE` (um ativo completo tem no máximo um tesouro direto)
- Delete: `ON DELETE SET NULL`

### ativos_completo → renda_fixa
- Relacionamento: **1:1** (One-to-One)
- FK: `ativos_completo.renda_fixa_id`
- Restrição: `UNIQUE`
- Delete: `ON DELETE SET NULL`

### ativos_completo → renda_variavel
- Relacionamento: **1:1** (One-to-One)
- FK: `ativos_completo.renda_variavel_id`
- Restrição: `UNIQUE`
- Delete: `ON DELETE SET NULL`

---

## 📝 Comandos SQL Úteis

### Consultar todos os ativos completos com seus investimentos

```sql
SELECT 
    ac.id,
    ac.nome,
    ac.tipo,
    ac.valor_atual,
    ac.risco,
    ac.tipo_investimento,
    td.tipo_tesouro,
    td.valor_investido as td_valor_investido,
    rf.tipo_ativo_renda_fixa,
    rf.valor_investido as rf_valor_investido,
    rv.tipo_renda_variavel,
    rv.quantidade as rv_quantidade
FROM ativos_completo ac
LEFT JOIN tesouro_direto td ON ac.tesouro_direto_id = td.id
LEFT JOIN renda_fixa rf ON ac.renda_fixa_id = rf.id
LEFT JOIN renda_variavel rv ON ac.renda_variavel_id = rv.id
ORDER BY ac.valor_atual DESC;
```

### Listar ativos por tipo de investimento

```sql
-- Tesouro Direto
SELECT ac.nome, ac.valor_atual, td.*
FROM ativos_completo ac
JOIN tesouro_direto td ON ac.tesouro_direto_id = td.id
WHERE ac.tipo_investimento = 'tesouro_direto';

-- Renda Fixa
SELECT ac.nome, ac.valor_atual, rf.*
FROM ativos_completo ac
JOIN renda_fixa rf ON ac.renda_fixa_id = rf.id
WHERE ac.tipo_investimento = 'renda_fixa';

-- Renda Variável
SELECT ac.nome, ac.valor_atual, rv.*
FROM ativos_completo ac
JOIN renda_variavel rv ON ac.renda_variavel_id = rv.id
WHERE ac.tipo_investimento = 'renda_variavel';
```

### Listar ativos por categoria de risco

```sql
SELECT nome, tipo_investimento, valor_atual, risco
FROM ativos_completo
WHERE risco = 'baixo'
ORDER BY valor_atual DESC;
```

### Calcular total investido por tipo

```sql
SELECT 
    tipo,
    COUNT(*) as quantidade,
    SUM(valor_atual) as total
FROM ativos_completo
GROUP BY tipo
ORDER BY total DESC;
```

### Verificar metas e progresso

```sql
SELECT 
    descricao,
    valor_objetivo,
    valor_atual,
    percentual_progresso,
    prazo,
    CASE 
        WHEN percentual_progresso >= 100 THEN 'Concluída'
        WHEN prazo < CURRENT_DATE THEN 'Atrasada'
        ELSE 'Em andamento'
    END as status
FROM metas
ORDER BY prazo;
```

### Limpar dados de teste

```sql
-- Limpar na ordem correta (respeitar FKs)
TRUNCATE TABLE ativos_completo CASCADE;
TRUNCATE TABLE tesouro_direto RESTART IDENTITY CASCADE;
TRUNCATE TABLE renda_fixa RESTART IDENTITY CASCADE;
TRUNCATE TABLE renda_variavel RESTART IDENTITY CASCADE;
TRUNCATE TABLE ativos RESTART IDENTITY CASCADE;
TRUNCATE TABLE metas RESTART IDENTITY CASCADE;
```

---

## 🛠️ Migrações

Os scripts de migração estão em: `sql/migrations-ativos-completo.sql`

Para executar manualmente:

```bash
# Via psql
psql -h localhost -U yuni_user -d yuni_db -f sql/migrations-ativos-completo.sql

# Via Docker
docker exec -i yuni-postgres psql -U yuni_user -d yuni_db < sql/migrations-ativos-completo.sql
```

**Nota:** As tabelas são criadas automaticamente pelo Hibernate/JPA na primeira execução da aplicação.

---

## 📊 Tipos de Dados

| Tipo PostgreSQL | Java Type | Descrição |
|----------------|-----------|-----------|
| SERIAL | Long | Auto-incremento (PK) |
| VARCHAR(n) | String | Texto variável |
| DECIMAL(15,2) | BigDecimal | Valores monetários |
| DATE | LocalDate | Data (YYYY-MM-DD) |
| TIMESTAMP | LocalDateTime | Data e hora |
| BOOLEAN | Boolean | Verdadeiro/Falso |
| INTEGER | Integer | Número inteiro |

---

Voltar para: [README](../README.md)

