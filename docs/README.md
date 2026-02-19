# 📚 Documentação - Projeto Yuni

Bem-vindo à documentação do projeto Yuni! Esta pasta contém toda a documentação sobre o projeto.

## 📖 Documentos Disponíveis

### 1. **CONCEITOS.md** ⭐ **COMECE AQUI**
Guia completo sobre conceitos e arquitetura do projeto.

**Tópicos:**
- Visão geral do projeto
- Arquitetura em camadas
- Conceitos fundamentais (Entidades, Repository, Dependency Injection, etc.)
- Stack tecnológico
- Estrutura de pastas
- Fluxo de dados
- Banco de dados
- Docker
- Glossário

**👉 Ideal para:** Entender como o projeto funciona

---

### 2. **API_TESTING.md**
Como testar os endpoints da API.

**Tópicos:**
- Endpoints disponíveis (GET, POST)
- Exemplos com cURL
- Como usar Postman/Insomnia
- Estrutura de dados
- Troubleshooting

**👉 Ideal para:** Testar a API na prática

---

### 3. **DOCKER_SETUP.md**
Instruções detalhadas para usar Docker.

**Tópicos:**
- Como executar com Docker
- Variáveis de ambiente
- Personalizar credenciais
- Troubleshooting
- Comandos úteis

**👉 Ideal para:** Rodar a aplicação com Docker

---

### 4. **README_DOCKER.md**
Documentação rápida do Docker.

**Tópicos:**
- Requisitos
- Como executar
- Acessar a aplicação
- Serviços disponíveis
- Parar containers
- Logs

**👉 Ideal para:** Referência rápida

---

### 5. **SETUP_COMPLETE.md**
Resumo do que foi configurado no projeto.

**Tópicos:**
- Problema identificado e corrigido
- Ajustes realizados
- Arquivos criados
- Melhorias implementadas

**👉 Ideal para:** Ver o que foi feito

---

### 6. **HELP.md**
Ajuda geral do projeto Spring Boot.

**👉 Ideal para:** Informações do projeto Maven

---

## 🚀 Como Começar

1. **Quer entender o projeto?**
   → Leia: `CONCEITOS.md`

2. **Quer rodar localmente?**
   → Leia: `DOCKER_SETUP.md`

3. **Quer testar a API?**
   → Leia: `API_TESTING.md`

4. **Precisa de referência rápida?**
   → Leia: `README_DOCKER.md`

---

## 📊 Mapa Mental do Projeto

```
Yuni (Gestão de Metas Financeiras)
├── Frontend (não implementado ainda)
└── Backend (Spring Boot)
    ├── Controller (recebe requisições)
    ├── Service (lógica de negócio)
    ├── Repository (acesso a dados)
    ├── Domain (entidades)
    └── PostgreSQL (banco de dados)
```

---

## 🛠️ Stack Tecnológico

- **Linguagem**: Java 21
- **Framework**: Spring Boot 4.0.2
- **Banco de Dados**: PostgreSQL 16
- **ORM**: Hibernate (via Spring Data JPA)
- **Containerização**: Docker + Docker Compose
- **Build**: Maven

---

## 📞 Estrutura de Pastas

```
yuni-srv/
├── docs/                    # 📚 Documentação (você está aqui)
│   ├── CONCEITOS.md        # Guia completo
│   ├── API_TESTING.md      # Como testar
│   ├── DOCKER_SETUP.md     # Setup do Docker
│   ├── README_DOCKER.md    # Referência rápida
│   └── ...
├── src/
│   ├── main/java/          # Código-fonte
│   │   └── com/nast/yuni/
│   │       ├── controller/ # API REST
│   │       ├── service/    # Lógica de negócio
│   │       ├── repository/ # Acesso a dados
│   │       └── domain/     # Entidades
│   └── test/java/          # Testes
├── Dockerfile              # Imagem Docker
├── docker-compose.yml      # Orquestração
├── pom.xml                 # Dependências Maven
└── README.md              # README principal
```

---

## 💻 Comandos Rápidos

### Docker
```bash
# Iniciar
docker-compose up

# Iniciar em background
docker-compose up -d

# Ver logs
docker-compose logs -f

# Parar
docker-compose down

# Parar e limpar dados
docker-compose down -v
```

### API
```bash
# Listar metas
curl http://localhost:8080/metas

# Criar meta
curl -X POST http://localhost:8080/metas \
  -H "Content-Type: application/json" \
  -d '{"nome": "Casa", "valorMeta": 300000, "prazo": 2028}'
```

---

## ❓ FAQ

### P: Por que Docker?
**R:** Para garantir que a aplicação rode igual em qualquer computador (dev, teste, produção).

### P: Por que Spring Boot?
**R:** Framework Java completo e maduro para criar APIs web rapidamente.

### P: Por que PostgreSQL?
**R:** Banco de dados relacional robusto, confiável e com muitos recursos.

### P: Como funciona o ORM?
**R:** Mapeia objetos Java para tabelas do banco automaticamente, sem precisar escrever SQL.

### P: O que é um Repository?
**R:** Interface que abstrai o acesso ao banco de dados, facilitando testes e trocas de banco.

---

## 🎓 Conceitos Principais

### Entity (Entidade)
Classe Java que representa uma tabela do banco de dados.

### DTO (Data Transfer Object)
Objeto usado para transferir dados entre camadas.

### Repository
Padrão que abstrai o acesso a dados.

### Service
Camada com lógica de negócio.

### Controller
Recebe requisições HTTP e coordena a resposta.

### REST
Arquitetura para criar APIs web usando HTTP.

### ORM
Mapeia objetos Java para tabelas do banco automaticamente.

---

## 📈 Próximos Passos

- [ ] Adicionar autenticação
- [ ] Criar frontend (React, Vue, etc)
- [ ] Adicionar mais endpoints (PUT, DELETE)
- [ ] Testes unitários
- [ ] Documentação Swagger/OpenAPI
- [ ] CI/CD pipeline

---

## 🆘 Precisa de Ajuda?

1. Consulte a documentação relevante acima
2. Verifique a seção "Troubleshooting" do documento
3. Veja os logs: `docker-compose logs -f`
4. Teste a API com os exemplos em `API_TESTING.md`

---

**Última atualização:** Fevereiro 2026

Aproveite! 🎉

