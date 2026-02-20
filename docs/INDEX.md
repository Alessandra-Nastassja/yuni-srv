# 📚 Documentação - YUNI

Bem-vindo à documentação do projeto YUNI! Aqui você encontrará guias completos para entender, usar e desenvolver a aplicação.

## 🚀 Começar Rápido

- **[Quick Start](README.md)** - Inicie em 3 passos
- **[Endpoints Refatorados](ENDPOINTS_REFATORADOS.md)** ✨ NOVO - API CRUD Completa
- **[Guia de Testes](GUIA_TESTES.md)** - Teste os endpoints

## 📖 Documentação Completa

### 📝 NOVO - Refatoração (Leia Primeiro!)
- **[RESUMO_MUDANCAS.md](RESUMO_MUDANCAS.md)** - O que foi refatorado
  - Comparativo antes/depois
  - Novos endpoints
  - Melhorias implementadas
  
- **[ENDPOINTS_REFATORADOS.md](ENDPOINTS_REFATORADOS.md)** - Documentação de Endpoints
  - Todos os 10 endpoints documentados
  - Exemplos de curl
  - Estrutura de request/response
  
- **[GUIA_COMPLETO_REFATORACAO.md](GUIA_COMPLETO_REFATORACAO.md)** - Guia Aprofundado
  - Arquitetura completa
  - Stack tecnológico
  - Modelo de dados
  - Deploy

### Para Aprender o Projeto
- **[PROJETO_COMPLETO.md](PROJETO_COMPLETO.md)** - Documentação abrangente
  - Arquitetura do projeto
  - Conceitos principais (Entidades, DTOs, Services, Controllers)
  - Stack tecnológico
  - Configuração detalhada
  - Troubleshooting

### Para Configurar Docker
- **[DOCKER_BUILD_FIX.md](DOCKER_BUILD_FIX.md)** - Solução do erro de build Docker
  - Explicação do problema
  - Como foi resolvido
  - Estrutura corrigida

### Para Testar a API
- **[GUIA_TESTES.md](GUIA_TESTES.md)** - Exemplos práticos de testes
  - Testes com cURL
  - Testes com Insomnia/Postman
  - Testes no PostgreSQL
  - Checklist de validação

### Outros Documentos
- **[API_TESTING.md](API_TESTING.md)** - Testes avançados da API
- **[CONCEITOS.md](CONCEITOS.md)** - Explicação dos conceitos
- **[HELP.md](HELP.md)** - Ajuda e FAQ
- **[INTERFACE_E_INJECAO.md](INTERFACE_E_INJECAO.md)** - Detalhes técnicos
- **[SETUP_COMPLETE.md](SETUP_COMPLETE.md)** - Histórico do setup
- **[README_DOCKER.md](README_DOCKER.md)** - Informações sobre Docker

---

## 🎯 Guia Rápido por Objetivo

### Quero Iniciar a Aplicação
1. Leia: **[README.md](README.md)**
2. Execute: `docker-compose up --build`
3. Acesse: http://localhost:8080

### Quero Entender a Arquitetura
1. Leia: **[PROJETO_COMPLETO.md](PROJETO_COMPLETO.md)** seção "🏗️ Arquitetura do Projeto"
2. Explore os arquivos em `src/main/java/com/nast/yuni/`

### Quero Testar os Endpoints
1. Leia: **[GUIA_TESTES.md](GUIA_TESTES.md)**
2. Use os exemplos de cURL fornecidos
3. Siga o teste completo passo a passo

### Quero Aprender os Conceitos
1. Leia: **[PROJETO_COMPLETO.md](PROJETO_COMPLETO.md)** seção "🎯 Conceitos Principais"
2. Leia: **[CONCEITOS.md](CONCEITOS.md)** para detalhes

### Tenho um Erro no Docker
1. Consulte: **[DOCKER_BUILD_FIX.md](DOCKER_BUILD_FIX.md)**
2. Se não resolver, leia troubleshooting em **[PROJETO_COMPLETO.md](PROJETO_COMPLETO.md)**

### Quero Deploy em Produção
1. Leia: **[PROJETO_COMPLETO.md](PROJETO_COMPLETO.md)** seção "🚢 Deploy em Produção"

---

## 📁 Estrutura de Documentos

```
docs/
├── INDEX.md                          ← Você está aqui
├── README.md                         ← Quick Start
├── PROJETO_COMPLETO.md               ← Documentação Principal
├── GUIA_TESTES.md                    ← Exemplos de Testes
├── DOCKER_BUILD_FIX.md               ← Solução Docker
├── CONCEITOS.md                      ← Explicação Conceitual
├── API_TESTING.md                    ← Testes Avançados
├── HELP.md                           ← FAQ
├── INTERFACE_E_INJECAO.md            ← Detalhes Técnicos
├── SETUP_COMPLETE.md                 ← Histórico de Setup
└── README_DOCKER.md                  ← Info Docker
```

---

## 🔗 Links Rápidos

| Recurso | Link |
|---------|------|
| Aplicação | http://localhost:8080 |
| PostgreSQL | localhost:5432 |
| Spring Boot Docs | https://spring.io/projects/spring-boot |
| Docker Docs | https://docs.docker.com |
| PostgreSQL Docs | https://www.postgresql.org/docs |

---

## 💡 Dicas

- Sempre comece pelo **[README.md](README.md)** se é sua primeira vez
- Use **[GUIA_TESTES.md](GUIA_TESTES.md)** para validar que tudo está funcionando
- Quando tiver dúvidas sobre conceitos, consulte **[PROJETO_COMPLETO.md](PROJETO_COMPLETO.md)**
- Se receber erros, vá para a seção de troubleshooting

---

## 🆘 Precisa de Ajuda?

1. **Erro técnico?** → Veja [PROJETO_COMPLETO.md](PROJETO_COMPLETO.md) - Troubleshooting
2. **Dúvida conceitual?** → Leia [CONCEITOS.md](CONCEITOS.md)
3. **Como testar?** → Use [GUIA_TESTES.md](GUIA_TESTES.md)
4. **FAQ?** → Consulte [HELP.md](HELP.md)

---

**Última atualização**: 19 de Fevereiro de 2026

Bom aprendizado! 🚀

