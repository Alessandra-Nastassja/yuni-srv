-- Migrations para Ativos Completos

-- Tabela para Tesouro Direto
CREATE TABLE IF NOT EXISTS tesouro_direto (
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

-- Tabela para Renda Fixa
CREATE TABLE IF NOT EXISTS renda_fixa (
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

-- Tabela para Renda Variável
CREATE TABLE IF NOT EXISTS renda_variavel (
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

-- Tabela principal para Ativos Completos
CREATE TABLE IF NOT EXISTS ativos_completo (
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

-- Índices para melhor performance
CREATE INDEX idx_ativos_completo_tipo ON ativos_completo(tipo);
CREATE INDEX idx_ativos_completo_tipo_investimento ON ativos_completo(tipo_investimento);
CREATE INDEX idx_ativos_completo_risco ON ativos_completo(risco);
CREATE INDEX idx_ativos_completo_valor_atual ON ativos_completo(valor_atual DESC);

