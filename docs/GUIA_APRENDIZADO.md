# 🎓 Guia de Aprendizado - YUNI

Um caminho estruturado para aprender sobre o projeto YUNI de forma progressiva.

---

## 📍 Mapa de Documentação

```
┌─────────────────────────────────────────────────────┐
│  VOCÊ ESTÁ AQUI: Começando                          │
│  (Dia 1: Primeiras 30 minutos)                      │
└─────────────────────────────────────────────────────┘
              ↓
    Leia: README.md
    Faça: docker-compose up --build
    Teste: Acesse http://localhost:8080
    
┌─────────────────────────────────────────────────────┐
│  NÍVEL 1: Iniciante                                 │
│  (Dia 1: 1-2 horas)                                 │
└─────────────────────────────────────────────────────┘
              ↓
    Leia: GUIA_TESTES.md
    Teste: Endpoints com cURL
    Entenda: Como testar a API
    Crie: Sua primeira meta
    
┌─────────────────────────────────────────────────────┐
│  NÍVEL 2: Intermediário                             │
│  (Dia 2-3: 2-4 horas)                               │
└─────────────────────────────────────────────────────┘
              ↓
    Leia: PROJETO_COMPLETO.md - Arquitetura
    Explore: Código em src/main/java
    Entenda: Padrão MVC
    Modifique: Crie um novo endpoint
    
┌─────────────────────────────────────────────────────┐
│  NÍVEL 3: Avançado                                  │
│  (Dia 4+: 4+ horas)                                 │
└─────────────────────────────────────────────────────┘
              ↓
    Leia: Todas as seções de PROJETO_COMPLETO.md
    Implemente: Novos controllers
    Configure: Banco de dados avançado
    Deploy: Em ambiente de produção
```

---

## 🎯 Trilhas de Aprendizado

### 🔵 Trilha: Usuário Iniciante
**Objetivo**: Usar a aplicação

1. **README.md** (5 min)
   - Como iniciar
   - Onde acessar

2. **GUIA_TESTES.md** (20 min)
   - Como testar com cURL
   - Exemplos práticos

3. **Prática** (30 min)
   - Criar metas
   - Listar metas
   - Verificar no banco

---

### 🟢 Trilha: Desenvolvedor Java
**Objetivo**: Entender o código

1. **README.md** (5 min)
   - Setup inicial

2. **ESTRUTURA_PROJETO.md** (15 min)
   - Organização de arquivos
   - Componentes principais

3. **PROJETO_COMPLETO.md** (60 min)
   - Controllers
   - Services
   - Repositories
   - Entidades

4. **Prática** (30 min)
   - Explore o código
   - Crie um novo endpoint
   - Teste com cURL

---

### 🟡 Trilha: DevOps/Docker
**Objetivo**: Entender containerização

1. **README.md** (5 min)
   - Como rodar

2. **DOCKER_BUILD_FIX.md** (20 min)
   - Entender o erro que foi corrigido
   - Solução implementada

3. **PROJETO_COMPLETO.md** (30 min)
   - Seção Docker & Docker Compose
   - Multi-stage builds

4. **Prática** (30 min)
   - Modifique o Dockerfile
   - Crie uma imagem customizada
   - Push para registry

---

### 🟠 Trilha: Banco de Dados
**Objetivo**: SQL e PostgreSQL

1. **PROJETO_COMPLETO.md** (30 min)
   - Seção Banco de Dados
   - Entidades JPA

2. **GUIA_TESTES.md** (20 min)
   - Comandos SQL
   - Verificar dados

3. **Prática** (30 min)
   - Crie tabelas
   - Execute queries
   - Analise dados

---

## 📚 Documentos Agrupados por Tópico

### Começando
- [ ] README.md
- [ ] INDEX.md

### Entendendo o Projeto
- [ ] ESTRUTURA_PROJETO.md
- [ ] PROJETO_COMPLETO.md
- [ ] CONCEITOS.md

### Testando
- [ ] GUIA_TESTES.md
- [ ] API_TESTING.md

### Tecnologia
- [ ] DOCKER_BUILD_FIX.md
- [ ] README_DOCKER.md
- [ ] INTERFACE_E_INJECAO.md

### Referência
- [ ] RESUMO_TRABALHO.md
- [ ] SETUP_COMPLETE.md
- [ ] HELP.md

---

## 🕐 Cronograma Recomendado

### Semana 1: Fundamentals
```
Dia 1 (30 min):    README.md + Setup Docker
Dia 2 (1h):        GUIA_TESTES.md + Testar API
Dia 3 (2h):        ESTRUTURA_PROJETO.md + Explorar Código
Dia 4 (1h):        PROJETO_COMPLETO.md - Arquitetura
Dia 5 (2h):        Praticar - Crie novo endpoint
```

### Semana 2: Aprofundamento
```
Dia 6 (2h):        PROJETO_COMPLETO.md - Conceitos
Dia 7 (1h):        DOCKER_BUILD_FIX.md
Dia 8 (2h):        Praticar - Modifique código
Dia 9 (1h):        CONCEITOS.md
Dia 10 (2h):       Praticar - Adicione features
```

### Semana 3+: Especialização
```
Deploy em Produção
Otimizações de Performance
Testes Automatizados
Monitoramento
```

---

## 🧠 Conceitos-Chave para Aprender

### 1️⃣ Arquitetura MVC
```
Model (Entidade)     ← O que armazenar
View (Response DTO)  ← O que retornar
Controller           ← Como acessar
```

### 2️⃣ Injeção de Dependência
```
@Service             ← Spring gerencia
@Autowired           ← Injeta dependência
Construtor           ← Recebe dependências
```

### 3️⃣ Padrão Repository
```
JpaRepository        ← Operações CRUD
List<T> findAll()    ← Get todos
T save(T)            ← Create/Update
void delete(T)       ← Delete
```

### 4️⃣ DTOs vs Entidades
```
Entidade   → Mapeada para tabela, use @Entity
Request DTO → Dados que cliente envia
Response DTO → Dados que servidor retorna
```

### 5️⃣ Docker Compose
```
services:     ← Contêineres a rodar
volumes:      ← Dados persistentes
networks:     ← Comunicação entre containers
depends_on:   ← Ordem de inicialização
```

---

## ✅ Verificação de Progresso

### Nível 1 - Iniciante
- [ ] Consegue iniciar aplicação com `docker-compose up`
- [ ] Sabe acessar http://localhost:8080
- [ ] Consegue criar meta com cURL
- [ ] Consegue listar metas
- [ ] Entende o banco de dados

### Nível 2 - Intermediário
- [ ] Entende a estrutura de arquivos
- [ ] Sabe o que faz cada camada (Controller, Service, Repository)
- [ ] Consegue ler e entender o código
- [ ] Consegue criar um novo endpoint
- [ ] Entende injeção de dependência

### Nível 3 - Avançado
- [ ] Consegue adicionar nova entidade JPA
- [ ] Consegue implementar novo serviço
- [ ] Consegue criar novo repositório
- [ ] Consegue criar novo controller
- [ ] Entende todo o pipeline de dados

---

## 🎓 Exercícios Progressivos

### Exercício 1 (Iniciante)
**Objetivo**: Testar que tudo funciona

```bash
# Inicie Docker
docker-compose up --build

# Crie uma meta
curl -X POST http://localhost:8080/api/metas \
  -H "Content-Type: application/json" \
  -d '{"nome":"Test","valorMeta":1000,"valorAtual":100,"prazo":"2026-12-31"}'

# Liste metas
curl http://localhost:8080/api/metas
```

### Exercício 2 (Intermediário)
**Objetivo**: Entender o código

1. Abra: `src/main/java/com/nast/yuni/controller/MetasController.java`
2. Identifique: `@PostMapping`, `@GetMapping`
3. Rastreie: Para qual Service vai
4. Rastreie: Para qual Repository vai
5. Entenda: Como os dados fluem

### Exercício 3 (Intermediário)
**Objetivo**: Criar um novo endpoint

```java
@GetMapping("/{id}")
public ResponseEntity<MetasResponse> obterMeta(@PathVariable Long id) {
    // TODO: Implementar busca por ID
    // 1. Chamar metasRepository.findById(id)
    // 2. Se existe, retornar
    // 3. Se não existe, retornar 404
}
```

### Exercício 4 (Avançado)
**Objetivo**: Adicionar nova entidade

1. Crie classe em `domain/NovaEntidade.java`
2. Adicione anotações: `@Entity`, `@Table`, `@Id`
3. Crie Repository: `NovaEntidadeRepository.java`
4. Crie Service: `NovaEntidadeService.java`
5. Crie Controller: `NovaEntidadeController.java`

---

## 📖 Leitura Recomendada (Fora)

### Spring Boot
- Spring Boot Official Guide
- Spring Data JPA Documentation
- Spring Web MVC Documentation

### Docker
- Docker Official Documentation
- Docker Compose User Guide

### PostgreSQL
- PostgreSQL Official Manual
- SQL Tutorial (W3Schools)

### Java
- Java 21 Documentation
- Effective Java (Joshua Bloch)

---

## 🤔 Perguntas Frequentes do Aprendizado

**P: Por onde começo?**
R: Leia README.md e inicie docker-compose up

**P: Como entendo o código?**
R: Siga: Controller → Service → Repository

**P: Por que existem DTOs?**
R: Para separar dados internos dos dados públicos

**P: Como adiciono um novo endpoint?**
R: Controller → Service → Repository → Entidade

**P: Como testo?**
R: Use os exemplos em GUIA_TESTES.md com cURL

**P: Onde coloco minhas modificações?**
R: No código em src/main/java, depois `mvn clean package`

---

## 🎯 Metas de Aprendizado

### Meta 1: Compreensão
- [ ] Entendo como a app é estruturada
- [ ] Entendo o fluxo de dados
- [ ] Entendo o papel de cada camada

### Meta 2: Competência
- [ ] Consigo navegar o código
- [ ] Consigo fazer pequenas mudanças
- [ ] Consigo testar minhas mudanças

### Meta 3: Expertise
- [ ] Consigo adicionar novas features
- [ ] Consigo debugar problemas
- [ ] Consigo otimizar código

---

## 🚀 Próximos Passos Após Completar

1. **Implemente novos controllers** para Patrimônio
2. **Adicione validações** robustas
3. **Implemente testes unitários**
4. **Configure CI/CD**
5. **Faça deploy** em produção

---

**Criado**: 19 de Fevereiro de 2026

Bom aprendizado! 🎓


