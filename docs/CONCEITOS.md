# Conceitos de Java e Spring Boot

Este documento explica os principais conceitos utilizados no projeto YUNI.

## 📚 Anotações Spring

### Controller Layer

#### `@RestController`
Combina `@Controller` + `@ResponseBody`. Indica que a classe é um controller REST e todas as respostas são automaticamente serializadas para JSON.

```java
@RestController
@RequestMapping("/api/ativos")
public class AtivosController {
    // métodos retornam objetos convertidos para JSON
}
```

#### `@RequestMapping`
Define o path base para todos os endpoints do controller.

```java
@RequestMapping("/api/metas")  // Todos os métodos terão /api/metas como prefixo
```

#### `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`
Mapeiam métodos HTTP específicos.

```java
@GetMapping           // GET /api/ativos
@GetMapping("/{id}")  // GET /api/ativos/123
@PostMapping          // POST /api/ativos
@PutMapping("/{id}")  // PUT /api/ativos/123
@DeleteMapping("/{id}") // DELETE /api/ativos/123
```

#### `@PathVariable`
Extrai variáveis do path da URL.

```java
@GetMapping("/{id}")
public ResponseEntity<AtivosResponse> obterAtivoPorId(@PathVariable Long id) {
    // id vem da URL: /api/ativos/123 -> id = 123
}
```

#### `@RequestBody`
Converte o JSON do body da requisição para um objeto Java.

```java
@PostMapping
public ResponseEntity<AtivosResponse> criarAtivo(@RequestBody AtivosRequest request) {
    // request é criado automaticamente a partir do JSON
}
```

#### `@Valid`
Ativa validações automáticas baseadas em anotações do Bean Validation.

```java
@PostMapping
public ResponseEntity<AtivoCompletoResponse> criarAtivo(
    @Valid @RequestBody AtivosCompletoRequest request) {
    // Se validação falhar, retorna 400 Bad Request automaticamente
}
```

### Service Layer

#### `@Service`
Marca a classe como um componente de serviço (lógica de negócio). Permite que seja injetada via `@Autowired` ou construtor.

```java
@Service
public class AtivosService {
    // lógica de negócio aqui
}
```

#### `@Transactional`
Gerencia transações de banco de dados automaticamente. Se ocorrer exceção, faz rollback.

```java
@Service
@Transactional  // Todas as operações são transacionais
public class AtivosCompletoService {
    public AtivosCompleto criarAtivo(AtivosCompletoRequest request) {
        // Se falhar aqui, faz rollback automático
    }
}
```

### Repository Layer

#### `@Repository` (implícito em interfaces JPA)
Marca a interface como um repositório. Com Spring Data JPA, não precisa implementação.

```java
public interface AtivosRepository extends JpaRepository<Ativos, Long> {
    // Spring gera implementação automaticamente
    List<Ativos> findAllByOrderByValorAtualDesc();
}
```

### Domain Layer (Entidades)

#### `@Entity`
Marca a classe como uma entidade JPA (tabela no banco).

```java
@Entity
@Table(name = "ativos")
public class Ativos {
    // campos = colunas da tabela
}
```

#### `@Table`
Define o nome da tabela no banco.

```java
@Table(name = "ativos_completo")
```

#### `@Id` e `@GeneratedValue`
Define a chave primária e como ela é gerada.

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;  // PostgreSQL gera automaticamente (SERIAL)
```

#### `@Column`
Customiza a coluna no banco.

```java
@Column(name = "valor_atual", precision = 15, scale = 2)
private BigDecimal valorAtual;  // DECIMAL(15,2) no banco
```

#### `@OneToOne`, `@ManyToOne`, `@OneToMany`
Define relacionamentos entre entidades.

```java
@OneToOne
@JoinColumn(name = "tesouro_direto_id")
private TesouroDireto tesouroDireto;  // FK para tesouro_direto
```

### Lombok

#### `@Data`
Gera getters, setters, toString, equals, hashCode automaticamente.

```java
@Data
public class AtivosRequest {
    private String nome;  // getLome(), setNome() gerados automaticamente
}
```

#### `@Builder`
Implementa o padrão Builder para construção de objetos.

```java
@Builder
public class Ativos {
    private Long id;
    private String nome;
}

// Uso:
Ativos ativo = Ativos.builder()
    .id(1L)
    .nome("Conta Corrente")
    .build();
```

#### `@RequiredArgsConstructor`
Gera construtor com campos `final` ou `@NonNull`.

```java
@Service
@RequiredArgsConstructor
public class AtivosService {
    private final AtivosRepository repository;  // Injetado via construtor
}
```

#### `@NoArgsConstructor` e `@AllArgsConstructor`
Geram construtor sem argumentos e com todos os argumentos, respectivamente.

## 🏗️ Princípios SOLID

### S - Single Responsibility Principle (Responsabilidade Única)
Cada classe tem uma única responsabilidade.

```java
// ✅ BOM: Cada camada tem sua responsabilidade
AtivosController    -> Recebe requisições HTTP
AtivosService       -> Lógica de negócio
AtivosRepository    -> Acesso ao banco
```

### O - Open/Closed Principle (Aberto/Fechado)
Aberto para extensão, fechado para modificação.

```java
// ✅ BOM: Podemos adicionar novos tipos de investimento sem modificar código existente
private void processarInvestimento(AtivosCompleto ativo, AtivosCompletoRequest request) {
    switch (request.getTipoInvestimento()) {
        case "tesouro_direto":
            processarTesouroDireto(ativo, request.getTesouroDireto());
            break;
        case "renda_fixa":
            processarRendaFixa(ativo, request.getRendaFixa());
            break;
        // Adicionar novo tipo aqui não modifica métodos existentes
    }
}
```

### L - Liskov Substitution Principle (Substituição de Liskov)
Subclasses podem substituir suas superclasses.

```java
// JpaRepository é substituível por qualquer implementação
JpaRepository<Ativos, Long> repository = new SimpleJpaRepository<>(...);
```

### I - Interface Segregation Principle (Segregação de Interface)
Interfaces específicas são melhores que uma interface geral.

```java
// ✅ BOM: Repositórios específicos para cada entidade
AtivosRepository
TesouroDiretoRepository
RendaFixaRepository
```

### D - Dependency Inversion Principle (Inversão de Dependência)
Depender de abstrações, não de implementações concretas.

```java
@Service
@RequiredArgsConstructor
public class AtivosService {
    // ✅ BOM: Depende da interface JpaRepository, não da implementação
    private final AtivosRepository repository;
}
```

## 🔄 Padrões de Projeto

### DTO (Data Transfer Object)
Objetos para transferir dados entre camadas.

```java
// Request DTO (entrada)
public class AtivosRequest {
    private String nome;
    private String tipo;
    private BigDecimal valorAtual;
}

// Response DTO (saída)
public class AtivosResponse {
    private List<Ativos> ativos;
}
```

### Builder Pattern
Construção fluente de objetos complexos.

```java
Ativos ativo = Ativos.builder()
    .nome("Conta Corrente")
    .tipo("conta_corrente")
    .valorAtual(new BigDecimal("5000.00"))
    .build();
```

### Repository Pattern
Abstração do acesso a dados.

```java
public interface AtivosRepository extends JpaRepository<Ativos, Long> {
    List<Ativos> findAllByOrderByValorAtualDesc();
    // Spring Data JPA implementa automaticamente
}
```

### Dependency Injection
Inversão de controle para injetar dependências.

```java
@RestController
@RequiredArgsConstructor  // Lombok gera construtor
public class AtivosController {
    private final AtivosService service;  // Spring injeta automaticamente
}
```

## 📦 Convenções de Nomenclatura

### Camadas
- **Controller**: `*Controller.java` - Endpoints REST
- **Service**: `*Service.java` - Lógica de negócio
- **Repository**: `*Repository.java` - Acesso a dados
- **Domain**: `*.java` - Entidades JPA
- **Request**: `*Request.java` - DTOs de entrada
- **Response**: `*Response.java` - DTOs de saída

### Métodos
- `listar*()` - Retorna lista
- `obter*PorId()` - Busca por ID
- `criar*()` - Cria novo registro
- `atualizar*()` - Atualiza registro existente
- `deletar*()` - Remove registro

### Entidades
- Singular para classes: `Ativos`, `Metas`
- Plural para tabelas: `ativos`, `metas`
- Snake_case para colunas: `valor_atual`, `tipo_investimento`

## 🔍 Boas Práticas Aplicadas

1. **Separação de Camadas**: Controller → Service → Repository
2. **Injeção de Dependência**: Via construtor com `@RequiredArgsConstructor`
3. **Tratamento de Erros**: `IllegalArgumentException` para validações
4. **Validações**: No Service Layer antes de salvar
5. **DTOs**: Request/Response separados das entidades
6. **Transações**: `@Transactional` para operações que modificam dados
7. **Imutabilidade**: Uso de `final` em campos injetados
8. **Builder Pattern**: Para construção de objetos complexos
9. **Repository Pattern**: Abstração de acesso a dados
10. **RESTful**: Endpoints seguem convenções REST

## 📖 Referências

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Lombok](https://projectlombok.org/features/all)
- [SOLID Principles](https://www.baeldung.com/solid-principles)

---

Voltar para: [README](../README.md)

