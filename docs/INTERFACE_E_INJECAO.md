# 🔌 Interface e Injeção de Dependência no Yuni

## 📖 Índice
1. [O que é uma Interface?](#o-que-é-uma-interface)
2. [Interface no Java](#interface-no-java)
3. [O que é Injeção de Dependência?](#o-que-é-injeção-de-dependência)
4. [Como o Spring Faz a Injeção?](#como-o-spring-faz-a-injeção)
5. [Tipos de Injeção de Dependência](#tipos-de-injeção-de-dependência)
6. [Múltiplas Implementações](#múltiplas-implementações)
7. [Testando com Injeção de Dependência](#testando-com-injeção-de-dependência)
8. [Exemplos Práticos no Yuni](#exemplos-práticos-no-yuni)

---

## 🎯 O que é uma Interface?

Uma **interface** é um "contrato" que define **o que** uma classe deve fazer, mas não **como** fazer.

### 📱 Analogia do Mundo Real

Pense em uma **tomada elétrica**:

```
┌──────────────────────────┐
│   TOMADA (Interface)     │
│   - Fornece energia      │
│   - Tem 2 ou 3 pinos    │
└────────────▲─────────────┘
             │ implementam
        ┌────┴────┐
        │         │
    ┌───────┐ ┌────────┐
    │ Usina │ │ Painel │
    │ Solar │ │ Solar  │
    └───────┘ └────────┘
```

Você não se importa **de onde** vem a energia, só precisa de uma tomada que **forneça energia**. A interface é a tomada, as implementações são as fontes de energia.

### 💡 Por que usar Interfaces?

- ✅ **Flexibilidade**: Trocar implementações facilmente
- ✅ **Testabilidade**: Criar mocks para testes
- ✅ **Baixo acoplamento**: Classes não dependem de detalhes de implementação
- ✅ **Múltiplas implementações**: Uma interface, várias formas de fazer

---

## 💻 Interface no Java

### Declaração da Interface

```java
// Interface = contrato
public interface NotificacaoService {
    void enviar(String mensagem);  // O QUE fazer (sem implementação)
    boolean validarDestinatario(String destinatario);
}
```

**Características:**
- Apenas assinaturas de métodos (sem corpo)
- Não tem atributos (apenas constantes)
- Define **o que** deve ser feito

### Implementações

```java
// Implementação 1: Enviar por Email
@Service
public class EmailNotificacaoService implements NotificacaoService {
    
    @Override
    public void enviar(String mensagem) {
        // COMO fazer: implementação específica para email
        System.out.println("📧 Email enviado: " + mensagem);
    }
    
    @Override
    public boolean validarDestinatario(String destinatario) {
        return destinatario.contains("@");
    }
}

// Implementação 2: Enviar por SMS
@Service
public class SmsNotificacaoService implements NotificacaoService {
    
    @Override
    public void enviar(String mensagem) {
        // COMO fazer: implementação específica para SMS
        System.out.println("📱 SMS enviado: " + mensagem);
    }
    
    @Override
    public boolean validarDestinatario(String destinatario) {
        return destinatario.matches("\\d{11}");
    }
}
```

### Usando a Interface

```java
@Service
@RequiredArgsConstructor
public class MetasService {
    // Depende da INTERFACE, não da implementação
    private final NotificacaoService notificacao;

    public void criarMeta(MetasRequest request) {
        // Salva meta...
        
        // Não sabe se é Email ou SMS, só chama enviar()
        notificacao.enviar("Meta criada: " + request.getNome());
    }
}
```

**Vantagem:** `MetasService` não precisa saber como a notificação é enviada. Pode ser email, SMS, push notification, etc.

---

## 💉 O que é Injeção de Dependência?

**Injeção de Dependência (DI)** é quando você **não cria** as dependências manualmente, mas **recebe** elas prontas de um container (no caso, o Spring).

### ❌ Sem Injeção de Dependência (Acoplado)

```java
@Service
public class MetasService {
    // Criando a dependência manualmente
    private MetasRepository repository = new MetasRepositoryImpl();
    private NotificacaoService notificacao = new EmailNotificacaoService();

    public void salvar(Metas meta) {
        repository.save(meta);
        notificacao.enviar("Meta salva!");
    }
}
```

**Problemas:**
- ❌ `MetasService` está **acoplado** às implementações concretas
- ❌ Difícil **trocar** de implementação (precisa alterar código)
- ❌ Impossível **testar** (não consegue mockar as dependências)
- ❌ Se `MetasRepositoryImpl` mudar o construtor, precisa alterar aqui

### ✅ Com Injeção de Dependência (Desacoplado)

```java
@Service
@RequiredArgsConstructor  // Lombok gera o construtor
public class MetasService {
    // Recebe as dependências prontas (injetadas)
    private final MetasRepository repository;
    private final NotificacaoService notificacao;

    public void salvar(Metas meta) {
        repository.save(meta);
        notificacao.enviar("Meta salva!");
    }
}
```

**Vantagens:**
- ✅ `MetasService` **não conhece** as implementações concretas
- ✅ Spring **decide** qual implementação usar
- ✅ Fácil **trocar** de implementação (configuração)
- ✅ Fácil **testar** (pode mockar as dependências)

---

## 🔄 Como o Spring Faz a Injeção?

### 1. Spring Escaneia as Classes

No startup, o Spring escaneia todas as classes com anotações especiais:

```java
@Repository  // Spring detecta e registra
public interface MetasRepository extends JpaRepository<Metas, Long> {}

@Service  // Spring detecta e registra
public class MetasService {
    private final MetasRepository repository;
}

@RestController  // Spring detecta e registra
public class MetasController {
    private final MetasService service;
}
```

**Anotações que o Spring detecta:**
- `@Component` - Componente genérico
- `@Service` - Camada de serviço
- `@Repository` - Camada de acesso a dados
- `@Controller` / `@RestController` - Camada de controle

### 2. Spring Cria as Instâncias

```java
// Spring faz internamente algo similar a:
MetasRepository repository = criarImplementacaoJpa();
MetasService service = new MetasService(repository);
MetasController controller = new MetasController(service);
```

### 3. Spring Mantém no Container

Todas as instâncias ficam no **Application Context** (container do Spring):

```
┌─────────────────────────────────────┐
│    Spring Application Context       │
├─────────────────────────────────────┤
│  [MetasRepository] → JpaImpl        │
│  [MetasService] → MetasService      │
│  [MetasController] → Controller     │
│  [NotificacaoService] → EmailImpl   │
└─────────────────────────────────────┘
```

### 4. Você Usa Sem Se Preocupar

```java
@RestController
@RequestMapping("/metas")
@RequiredArgsConstructor
public class MetasController {
    private final MetasService service;  // Já vem injetado automaticamente!

    @GetMapping
    public List<Metas> listar() {
        return service.listar();  // Simplesmente usa
    }
}
```

---

## 🧩 Tipos de Injeção de Dependência

### 1. Injeção por Construtor (✅ Recomendado)

```java
@Service
public class MetasService {
    private final MetasRepository repository;
    private final NotificacaoService notificacao;

    // Construtor (pode ser gerado pelo Lombok)
    public MetasService(MetasRepository repository, 
                       NotificacaoService notificacao) {
        this.repository = repository;
        this.notificacao = notificacao;
    }
}

// Com Lombok fica mais limpo:
@Service
@RequiredArgsConstructor  // Gera o construtor automaticamente
public class MetasService {
    private final MetasRepository repository;
    private final NotificacaoService notificacao;
}
```

**Vantagens:**
- ✅ Dependências são **obrigatórias**
- ✅ Objetos são **imutáveis** (`final`)
- ✅ Fácil de **testar** (passa mocks no construtor)
- ✅ Não precisa de anotações do Spring

### 2. Injeção por Field (⚠️ Não Recomendado)

```java
@Service
public class MetasService {
    @Autowired  // Spring injeta diretamente no campo
    private MetasRepository repository;
    
    @Autowired
    private NotificacaoService notificacao;
}
```

**Desvantagens:**
- ❌ Dificulta **testes** (precisa de reflection)
- ❌ Não é `final` (pode ser alterado)
- ❌ Dependências podem ser **nulas**
- ❌ Viola princípios de **imutabilidade**

### 3. Injeção por Setter (⚠️ Raramente Usado)

```java
@Service
public class MetasService {
    private MetasRepository repository;
    private NotificacaoService notificacao;

    @Autowired
    public void setRepository(MetasRepository repository) {
        this.repository = repository;
    }
    
    @Autowired
    public void setNotificacao(NotificacaoService notificacao) {
        this.notificacao = notificacao;
    }
}
```

**Quando usar:** Apenas se a dependência for **opcional** ou **mutável**.

---

## 🎭 Múltiplas Implementações

### Problema: 2 Classes Implementam a Mesma Interface

```java
public interface NotificacaoService {
    void enviar(String mensagem);
}

@Service
public class EmailNotificacaoService implements NotificacaoService {
    public void enviar(String mensagem) {
        System.out.println("📧 Email: " + mensagem);
    }
}

@Service
public class SmsNotificacaoService implements NotificacaoService {
    public void enviar(String mensagem) {
        System.out.println("📱 SMS: " + mensagem);
    }
}
```

**Problema:** Qual implementação o Spring deve injetar?

### Solução 1: `@Primary` (Implementação Padrão)

```java
@Service
@Primary  // Esta será usada por padrão
public class EmailNotificacaoService implements NotificacaoService {
    // ...
}

@Service
public class SmsNotificacaoService implements NotificacaoService {
    // ...
}

// Quando injetado, EmailNotificacaoService será usado
@Service
@RequiredArgsConstructor
public class MetasService {
    private final NotificacaoService notificacao;  // Recebe EmailNotificacaoService
}
```

### Solução 2: `@Qualifier` (Escolher Manualmente)

```java
@Service
@RequiredArgsConstructor
public class MetasService {
    @Qualifier("smsNotificacaoService")  // Especifica qual usar
    private final NotificacaoService notificacao;
}
```

### Solução 3: Nome da Variável

```java
@Service
@RequiredArgsConstructor
public class MetasService {
    // Nome da variável = nome do bean (camelCase)
    private final NotificacaoService emailNotificacaoService;  // Usa EmailNotificacaoService
    private final NotificacaoService smsNotificacaoService;    // Usa SmsNotificacaoService
}
```

### Solução 4: Usar Múltiplas Implementações

```java
@Service
@RequiredArgsConstructor
public class MetasService {
    private final List<NotificacaoService> notificacoes;  // Lista com TODAS as implementações

    public void criarMeta(MetasRequest request) {
        // Envia por todos os canais
        notificacoes.forEach(n -> n.enviar("Meta criada!"));
    }
}
```

---

## 🧪 Testando com Injeção de Dependência

### ✅ Com Injeção (Fácil de Testar)

```java
@ExtendWith(MockitoExtension.class)
class MetasServiceTest {
    
    @Mock  // Cria um mock da interface
    private MetasRepository repository;
    
    @Mock
    private NotificacaoService notificacao;

    @InjectMocks  // Injeta os mocks no service
    private MetasService service;

    @Test
    void deveCriarMeta() {
        // Arrange
        MetasRequest request = new MetasRequest("Casa", 300000.0, null, 2028);
        Metas meta = new Metas("Casa", 300000.0, null, 2028);
        
        when(repository.save(any(Metas.class))).thenReturn(meta);

        // Act
        MetasResponse response = service.criarMeta(request);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getMetas().size());
        verify(repository).save(any(Metas.class));  // Verifica se foi chamado
        verify(notificacao).enviar(anyString());     // Verifica notificação
    }
}
```

### ❌ Sem Injeção (Difícil de Testar)

```java
@Service
public class MetasService {
    private MetasRepository repository = new MetasRepositoryImpl();  // ❌ Hardcoded

    // Como testar? Não dá pra mockar repository!
    // Precisa de banco de dados real!
}
```

---

## 📊 Exemplos Práticos no Yuni

### Exemplo 1: Repository (Interface JPA)

```java
@Repository
public interface MetasRepository extends JpaRepository<Metas, Long> {
    // JpaRepository já é uma interface!
    // Spring cria a implementação automaticamente em tempo de execução
    
    List<Metas> findAllByOrderByPrazoAsc();
    Optional<Metas> findByNome(String nome);
}
```

**O que acontece:**
- `JpaRepository` é uma **interface**
- Spring Data JPA cria a **implementação** automaticamente
- Você usa como se fosse uma classe normal

### Exemplo 2: Service com Injeção

```java
@Service
@RequiredArgsConstructor
public class MetasService {
    // Depende da INTERFACE, não da implementação
    private final MetasRepository metasRepository;

    public MetasResponse listarMetas() {
        // Chama método da interface
        List<Metas> metas = metasRepository.findAllByOrderByPrazoAsc();
        
        return MetasResponse.builder()
            .metas(metas)
            .build();
    }

    public MetasResponse criarMeta(MetasRequest request) {
        Metas metas = new Metas(
            request.getNome(),
            request.getValorMeta(),
            request.getValorAtual(),
            request.getPrazo()
        );
        
        // Spring injeta a implementação correta
        Metas metasSalva = metasRepository.save(metas);
        
        return MetasResponse.builder()
            .metas(List.of(metasSalva))
            .build();
    }
}
```

### Exemplo 3: Controller com Injeção

```java
@RestController
@RequestMapping("/metas")
@RequiredArgsConstructor
public class MetasController {
    // Recebe service injetado
    private final MetasService service;

    @GetMapping
    public MetasResponse metas() {
        // Apenas delega para o service
        return service.listarMetas();
    }

    @PostMapping
    public MetasResponse criarMeta(@RequestBody MetasRequest request) {
        return service.criarMeta(request);
    }
}
```

### Exemplo 4: Múltiplas Camadas com Injeção

```
┌─────────────────────────────────────┐
│   MetasController                   │
│   @RequiredArgsConstructor          │
│   private final MetasService        │ ← Recebe service injetado
└──────────────┬──────────────────────┘
               │ injeta
               ▼
┌─────────────────────────────────────┐
│   MetasService                      │
│   @RequiredArgsConstructor          │
│   private final MetasRepository     │ ← Recebe repository injetado
└──────────────┬──────────────────────┘
               │ injeta
               ▼
┌─────────────────────────────────────┐
│   MetasRepository (interface)       │
│   extends JpaRepository             │
└──────────────┬──────────────────────┘
               │ Spring cria
               ▼
┌─────────────────────────────────────┐
│   JpaRepositoryImpl (gerado)        │
│   Implementação automática          │
└─────────────────────────────────────┘
```

### Exemplo 5: Criando uma Interface Customizada

Vamos criar um exemplo de notificação para o Yuni:

```java
// 1. Interface
public interface MetasNotificacaoService {
    void notificarMetaCriada(Metas meta);
    void notificarMetaAtingida(Metas meta);
}

// 2. Implementação
@Service
public class EmailMetasNotificacaoService implements MetasNotificacaoService {
    
    @Override
    public void notificarMetaCriada(Metas meta) {
        System.out.println("📧 Meta criada: " + meta.getNome());
    }
    
    @Override
    public void notificarMetaAtingida(Metas meta) {
        System.out.println("🎉 Parabéns! Meta atingida: " + meta.getNome());
    }
}

// 3. Usar no Service
@Service
@RequiredArgsConstructor
public class MetasService {
    private final MetasRepository metasRepository;
    private final MetasNotificacaoService notificacao;

    public MetasResponse criarMeta(MetasRequest request) {
        Metas metas = new Metas(
            request.getNome(),
            request.getValorMeta(),
            request.getValorAtual(),
            request.getPrazo()
        );
        
        Metas metasSalva = metasRepository.save(metas);
        
        // Envia notificação
        notificacao.notificarMetaCriada(metasSalva);
        
        return MetasResponse.builder()
            .metas(List.of(metasSalva))
            .build();
    }
}
```

---

## 🔧 Como Spring Injeta a Dependência (Passo a Passo)

### No Startup da Aplicação

```java
// 1. YuniApplication inicia
@SpringBootApplication
public class YuniApplication {
    public static void main(String[] args) {
        SpringApplication.run(YuniApplication.class, args);
    }
}

// 2. Spring escaneia pacotes e encontra:
@Repository
interface MetasRepository extends JpaRepository<Metas, Long> {}

@Service
@RequiredArgsConstructor
class MetasService {
    private final MetasRepository metasRepository;
}

@RestController
@RequiredArgsConstructor
class MetasController {
    private final MetasService service;
}

// 3. Spring cria instâncias (simplificado):
// - Cria implementação do MetasRepository
MetasRepository repoBean = springDataJpa.createProxy(MetasRepository.class);

// - Cria MetasService e injeta repository
MetasService serviceBean = new MetasService(repoBean);

// - Cria MetasController e injeta service
MetasController controllerBean = new MetasController(serviceBean);

// 4. Armazena no Application Context
applicationContext.put("metasRepository", repoBean);
applicationContext.put("metasService", serviceBean);
applicationContext.put("metasController", controllerBean);

// 5. Pronto! Aplicação funcionando
```

### Quando uma Requisição Chega

```java
// Request: GET http://localhost:8080/metas

// 1. Spring recebe a requisição
// 2. Spring encontra o método no MetasController
// 3. MetasController já tem MetasService injetado
// 4. MetasService já tem MetasRepository injetado
// 5. Executa normalmente

@RestController
@RequestMapping("/metas")
@RequiredArgsConstructor
public class MetasController {
    private final MetasService service;  // Já injetado no startup

    @GetMapping
    public MetasResponse metas() {
        return service.listarMetas();  // Usa o service injetado
    }
}
```

---

## 💡 Resumo

### Interface
- **Definição**: Contrato que define **o que** fazer
- **Vantagem**: Permite múltiplas implementações
- **Uso no Yuni**: `JpaRepository`, `MetasRepository`

### Injeção de Dependência
- **Definição**: Spring **fornece** as dependências automaticamente
- **Vantagem**: Baixo acoplamento, fácil testar
- **Como**: Através de construtores com `@RequiredArgsConstructor`

### Fluxo no Yuni
1. Define interface (`MetasRepository`)
2. Spring cria implementação automaticamente
3. Spring injeta no construtor do `MetasService`
4. `MetasController` recebe `MetasService` injetado
5. Você usa sem se preocupar com instanciação

### Melhores Práticas
- ✅ Use injeção por **construtor**
- ✅ Use `@RequiredArgsConstructor` do Lombok
- ✅ Declare dependências como `final`
- ✅ Dependa de **interfaces**, não de implementações
- ✅ Use `@Primary` ou `@Qualifier` para múltiplas implementações

---

## 🎯 Próximos Passos

1. **Criar suas próprias interfaces**
   - Experimente criar uma interface de notificação
   - Implemente múltiplas formas (email, SMS, push)

2. **Testar com Mocks**
   - Pratique criar testes unitários
   - Use `@Mock` e `@InjectMocks`

3. **Explorar Spring Data JPA**
   - Entenda como Spring cria implementações
   - Crie queries customizadas

4. **Aplicar SOLID**
   - Dependency Inversion Principle (DIP)
   - Interface Segregation Principle (ISP)

---

**Criado com ❤️ para aprendizado**

Última atualização: Fevereiro 2026

