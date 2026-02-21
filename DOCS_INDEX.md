# 📚 Índice da Documentação

Guia rápido para navegar pela documentação do YUNI.

## 📁 Arquivos

| Arquivo | Descrição | Quando Usar |
|---------|-----------|-------------|
| [README.md](README.md) | Visão geral, como rodar, comandos básicos | Primeira vez ou para rodar o projeto |
| [CONCEITOS.md](docs/CONCEITOS.md) | Java, Spring, Annotations, SOLID, Padrões | Aprender conceitos ou revisar boas práticas |
| [API.md](docs/API.md) | Endpoints, cURLs, contratos JSON | Testar API ou integrar com frontend |
| [DATABASE.md](docs/DATABASE.md) | Schema SQL, UML, queries úteis | Entender banco de dados ou criar queries |
| [TROUBLESHOOTING.md](docs/TROUBLESHOOTING.md) | Erros comuns e soluções | Quando algo der errado |

## 🎯 Por Categoria

### 🚀 Começando
1. [Como rodar o projeto](README.md#-como-rodar)
2. [Pré-requisitos](README.md#-pré-requisitos)
3. [Comandos Docker](README.md#️-comandos-úteis)

### 💻 Desenvolvimento
1. [Conceitos Java/Spring](docs/CONCEITOS.md)
2. [Estrutura do projeto](README.md#-estrutura-do-projeto)
3. [Padrões de código](docs/CONCEITOS.md#-padrões-de-projeto)

### 🔗 API
1. [Todos os endpoints](docs/API.md)
2. [Ativos Simples](docs/API.md#ativos-simples)
3. [Ativos Completos (Investimentos)](docs/API.md#ativos-completos)
4. [Metas](docs/API.md#metas)

### 🗄️ Banco de Dados
1. [Schema completo](docs/DATABASE.md#-tabelas)
2. [Diagrama UML](docs/DATABASE.md#-diagrama-uml)
3. [Queries úteis](docs/DATABASE.md#-comandos-sql-úteis)

### 🐛 Problemas
1. [Erros Docker](docs/TROUBLESHOOTING.md#-docker)
2. [Erros Maven](docs/TROUBLESHOOTING.md#-maven)
3. [Erros PostgreSQL](docs/TROUBLESHOOTING.md#️-postgresql)
4. [Erros Spring Boot](docs/TROUBLESHOOTING.md#-aplicação-spring-boot)

## 🔍 Busca Rápida

### Como fazer um POST de ativos?
→ [API.md - Criar Ativo](docs/API.md#criar-ativo)

### Como criar um investimento em Tesouro Direto?
→ [API.md - Tesouro Direto](docs/API.md#criar-ativo-com-tesouro-direto)

### O que é @RestController?
→ [CONCEITOS.md - Anotações](docs/CONCEITOS.md#restcontroller)

### Como ver as tabelas do banco?
→ [DATABASE.md - Schema](docs/DATABASE.md#-tabelas)

### Porta 8080 já está em uso?
→ [TROUBLESHOOTING.md - Port in use](docs/TROUBLESHOOTING.md#erro-port-8080-already-in-use)

### Docker não conecta?
→ [TROUBLESHOOTING.md - Docker daemon](docs/TROUBLESHOOTING.md#erro-cannot-connect-to-docker-daemon)

### Como limpar o banco?
→ [DATABASE.md - Limpar dados](docs/DATABASE.md#limpar-dados-de-teste)

## 📊 Organização

```
yuni-srv/
├── README.md                    ⭐ Início aqui
├── DOCS_INDEX.md               📚 Este arquivo
└── docs/
    ├── CONCEITOS.md            💡 Java/Spring
    ├── API.md                  🔗 Endpoints
    ├── DATABASE.md             🗄️ SQL/UML
    └── TROUBLESHOOTING.md      🐛 Erros
```

---

✅ **Navegue pela documentação usando os links acima!**

Voltar para: [README](README.md)

