#!/bin/bash

# Script de teste para os endpoints de Ativos Completos
# Certifique-se de que a aplicação está rodando em http://localhost:8080

BASE_URL="http://localhost:8080/api/ativos"

echo "====== TESTES DE POST PARA ATIVOS ======"
echo ""

# 1. Teste: Conta Corrente
echo "1. Testando Conta Corrente..."
curl -X POST "$BASE_URL/completo" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Conta Corrente Principal",
    "tipo": "conta_corrente",
    "tipoFonteRenda": "salario",
    "valorAtual": 5000.00
  }' \
  -w "\nStatus: %{http_code}\n\n"

# 2. Teste: Investimento em Tesouro Direto
echo "2. Testando Tesouro Direto..."
curl -X POST "$BASE_URL/completo" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Tesouro IPCA 2035",
    "tipo": "investimentos",
    "tipoInvestimento": "tesouro_direto",
    "tesouroDireto": {
      "tipoTesouro": "tesouro_ipca",
      "valorInvestido": 10000.00,
      "valorAtual": 10500.00,
      "dataCompra": "2024-01-15",
      "dataVencimento": "2035-08-15",
      "corretora": "B3",
      "taxaRentabilidade": 5.25
    }
  }' \
  -w "\nStatus: %{http_code}\n\n"

# 3. Teste: Investimento em Renda Fixa (CDB)
echo "3. Testando Renda Fixa (CDB)..."
curl -X POST "$BASE_URL/completo" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "CDB Banco XYZ",
    "tipo": "investimentos",
    "tipoInvestimento": "renda_fixa",
    "rendaFixa": {
      "tipoAtivoRendaFixa": "cdb",
      "valorInvestido": 50000.00,
      "valorAtual": 52500.00,
      "corretora": "XP Investimentos",
      "dataCompra": "2024-06-01",
      "dataVencimento": "2026-06-01",
      "tipoTaxa": "prefixado",
      "taxaContratada": 10.5,
      "categoriaRiscoRendaFixa": "baixo",
      "irEstimado": 1500.00
    }
  }' \
  -w "\nStatus: %{http_code}\n\n"

# 4. Teste: Investimento em Renda Variável (Ações)
echo "4. Testando Renda Variável (Ações)..."
curl -X POST "$BASE_URL/completo" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "VALE3",
    "tipo": "investimentos",
    "tipoInvestimento": "renda_variavel",
    "rendaVariavel": {
      "tipoRendaVariavel": "acoes",
      "quantidade": 100,
      "precoMedio": 85.50,
      "valorAtual": 8750.00,
      "corretora": "B3",
      "categoriaRiscoRendaVariavel": "alto",
      "dataCompra": "2023-08-20",
      "dividendosRecebidos": 150.00,
      "irEstimadoAcoes": 20
    }
  }' \
  -w "\nStatus: %{http_code}\n\n"

echo "====== TESTES CONCLUÍDOS ======"

