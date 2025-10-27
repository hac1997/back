## MODERAÇÃO

A moderação da plataforma deve ser implementada levando em consideração dois aspectos:
- Políticas de Moderação
- Ferramentas de Moderação

* Políticas de moderação (regras e diretrizes de uso) → definem o que deve ser moderado e como agir diante das violações;

* Ferramentas de moderação (mecanismos técnicos) → permitem executar essas políticas de forma prática.

As políticas de moderação ficarão documentadas posteriormente nos "Termos de Uso".




## Entidades

**Report**
```java
public class Report {
    long reportId; 
    User reportUser;
    Post reportedPost;
    Comment reported;
    boolean accepted;
    LocalDate createAt;
    List<String> reasons;}
```

**Discussion**
```java
@Entity
public class Discussion {
    @Id @GeneratedValue
    private Long discussionId;

    private Long reportId;
    private Long userId;

    @ManyToMany
    private List<User> moderators;

    @OneToMany(mappedBy = "discussion")
    private List<Vote> votes;

    @Enumerated(EnumType.STRING)
    private DiscussionStatus status; // OPEN, CLOSED, APPROVED, REJECTED
}

```


**Vote**
```java
@Entity
public class Vote {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Discussion discussion;

    @ManyToOne
    private User moderator;

    private boolean inFavor;
    private LocalDateTime votedAt;
}
```


```java
public enum CommentStatus(
    PENDING,
    APPROVED, //vai ser default
    REMOVED,
    FLAGED
)
```
```java
public enum UserStatus(
    ACTIVE, //vai ser default
    SUSPEND, 
    BANNED
)

```

```java
public enum DiscussionStatus(
    OPEN, 
    CLOSED, 
    APPROVED, 
    REJECTED
)
```

```java
public enum PostStatus(
    APPROVED, //DEFAULT
    LOCKED, 
    REMOVED,
)
```

## Entidades que depois iremos alterar

```java
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(columnDefinition = "TEXT")
    private String body;

    //futuramente pra ferramentas de moderação
    private boolean visible;rator -> u

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    ///Acrescentaremos depois
    private CommentStatus status;
}
```

```java
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    //futuramente pra ferramentas de moderação
    private boolean visible;
    private Long answerId;

    @ElementCollection
    private List<String> tags;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    ///Acrescentaremos depois
    private PostStatus status;
}
```

```java
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    //futuramente pra ferramentas de moderação
    private boolean visible;
    private Long answerId;

    @ElementCollection
    private List<String> tags;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    ///Acrescentaremos depois
    private UserStatus status;
}
```



#### LÓGICA DE NEGÓCIOS - MODERAÇÃO


**Alterar visibilidade de post/comentário** |

**Trancar post** 
 Impede novos comentários em um post  
  - Usuário com o login moderador pode alterar o status de um post. Posts com o status trancado não podem receber comentários. 
  - Lógica de negócios -> commentService deve avaliar se o post está trancado 

**Fixar post (pinned)** 
Destaca posts importantes (anúncios, avisos) 
 - Moderator: o moderator pode deixar um comment destacado
 - Lógica de negócios: Os commentários devem ter um status pinned. O frontend deve renderizar os comentários pinnados primeiro


**Excluir definitivamente post/comentário** 
Remove do sistema permanentemente. 
- O admin deve poder deletar permanentemente?


**Restaurar conteúdo removido** 
Reverte exclusões injustas. 

- O Admin pode reverter o status de um user, commnet ou post;
- Moderators podem reverter o status de um User, Comment ou post através de uma discussion

**Iniciar Discussion de banimento** 
Moderador abre uma *discussion* vinculada a um `reportId` e `userId` para votação. 
- implementar no dashboard?

**Votar em Discussion** 
Moderadores votam (favorável ou contra banimento). 
- criada entidade vote e Discussion

**Banir usuário (execução)**
- Aplicado automaticamente se a votação for aprovada. 
- criar um duscussionService para atualizar o status do usuário

**Suspender temporariamente**
Bloqueia login por X dias (mantém a conta).
- Após discussion, atualiza-se o status da conta do User para blocked

**Advertência formal (Warning)**
Sistema envia notificação formal com motivo e histórico de infrações.
- Automaticamente notifica após um report

**Visualizar histórico de reports**
Acesso a todos os *reports* em que o usuário foi mencionado. 
- criar um dashboard? 

----------------------
#### INTERFACE DOS ADMINS E MODERATORS
**Promover/Demitir Moderadores** 
Admin define ou remove os privilégios de moderação de outros usuários. 
- Criar uma interface para o Admin

**Registro de Discussões**
 Histórico completo de votações, resultados e justificativas de banimento/moderação.

**Canal interno de moderação (*chat* interno)**
Espaço privado e dedicado para debate e coordenação entre moderadores. 


**Agenda de revisões** 
Lista e acompanha moderações pendentes (ex: discussões ainda sem quórum para votação).

**Sistema de tags de moderação**
Ferramenta para categorizar o tipo de infração (*"Spam"*, *"Ofensa"*, *"Conteúdo Sensível"*). 

**Dashboard de reports** 
Lista de posts/comentários denunciados com filtros (motivo, data, reincidência). 

**Histórico de moderação (log)** 
Registra detalhadamente toda ação de moderação (quem, quando, o que fez). 
- Acesso de consulta somente ao Admin

**Painel de estatísticas de moderação** 
Mostra métricas como número de *reports*, discussões abertas, banimentos e reversões.
- Acesso de consulta somente ao Admin

**Sistema de apelação** 
Permite ao usuário recorrer de um banimento, abrindo uma `Appeal entity` ou um outro tipo de discussion para revisão.
- Como quem bane é o Admin, então só ele pode desbanir
