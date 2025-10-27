TPJ (TriggerPixel Jogos) Backlog
User Stories:

Os casos foram ordenados da seguinte maneira: prioridade alta e por estimativa de esforço:


### Caso 2 - seção de comentários
Como desenvolvedor, quero responder a um post para compartilhar meu conhecimento para ajudar outros e contribuir com a comunidade.

Acceptance Criteria:
- O usuário deve estar logado para responder a um post
- A resposta deve estar vinculada ao post original ou a outra resposta
- O usuário pode formatar sua resposta usando rich text (negrito, itálico, etc.)
- Prioridade: alta
- Medida de esforço: 2

### Caso 1 - criação de posts
Como jogador, eu quero criar um post para fazer perguntas ou iniciar discussões de tópicos para compartilhar ideias ou dúvidas.

Acceptance Criteria:
- O usuário deve estar logado
- O post deve ter um titulo e um corpo de texto
- O post deve ter tags associadas
- Prioridade: alta 
- Medida de esforço: 3

### Caso 5 - criação de contas
Como novo usuário, quero criar uma conta para participar da comunidade.

Acceptance Criteria:
- O usuário pode se registrar com um e-mail único e senha
- A senha deve atender a requisitos de segurança (tamanho mínimo, caracteres especiais)
- Um e-mail de confirmação deve ser enviado para verificar a conta
- Prioridade: alta
- Medida de esforço: 3

### Caso 4 - busca de posts
Como gamer, quero buscar posts sobre jogos ou tópicos específicos para encontrar informações relevantes rapidamente.

Acceptance Criteria:
- A barra de pesquisa deve ser de fácil acesso
- A busca deve filtrar posts por palavras-chave nos títulos e corpos
- Os resultados devem ser exibidos em uma lista clara e organizada
- Prioridade: alta
- Medida de esforço: 8

### Caso 6 - ferramentas de moderação
Como moderador, quero remover a visibilidade de posts ou respostas inapropriados para usuários padrão para proteger a comunidade de conteúdo prejudicial.

Acceptance Criteria:
- Apenas usuários com privilégios de moderador podem realizar esta ação
- O conteúdo não deve ser visível para usuários padrão, mas permanecer arquivado para revisão
- A remoção deve seguir regras de compliance da plataforma e deve ser avaliada por outros moderadores
- O usuário deve ter o direito a saber o motivo de ter seu conteúdo tornado invisível
- O sistema deve registrar todas as ações de moderação
- O usuário deve poder denunciar um post para os moderadores
- Prioridade: alta
- Medida de esforço: 8

-------------

### Caso 3
Como usuário, quero marcar uma resposta como solução para minha pergunta para que outros encontrem a resposta rapidamente.

Acceptance Criteria:
- Somente o autor do post pode marcar uma resposta como solução
- A solução marcada deve estar visualmente destacada no post
- Um post pode ter apenas uma resposta marcada como solução
- Prioridade: média
- Medida de esforço: 2

### Caso 7
Como usuário, quero editar meus próprios posts e respostas para corrigir erros ou adicionar novas informações.

Acceptance Criteria:
- Somente o autor pode editar o conteúdo
- O histórico de edições deve estar visível para os moderadores
- O usuário não pode editar posts após um período definido (ex.: 24 horas)
- Prioridade: média
- Medida de esforço: 2

### Caso 8 
Como membro da comunidade, quero votar em tópicos e respostas, para dar visibilidade a conteúdos úteis.

Acceptance Criteria:
- Somente membros da comunidade podem votar
- Tópicos com alta aceitação devem ser destacados de alguma maneira (talvez com o post com uma cor diferente)
- O usuário pode escolher retirar seu voto
- Os posts mais curtidos devem ser divulgados
- Prioridade: média
- Medida de esforço: 5

  
## Métricas
* **Issues abertas x issues fechadas**
* **Tempo médio de resolução de issues (dias)**
* **Cobertura de testes (% do código)**
* **Velocidade da equipe (story points/sprint)**

### Consideracoes
* **Utilizacao de Sprints**
* **Tempo por sprint: `10 dias`**

### Story Points

* **1-2 ponto:** tarefa pequena (até 12h).
* **3-4 pontos:** tarefa média (até 2 dia).
* **5-7 pontos:** tarefa complexa (3–4 dias).
* **8+ pontos:** tarefa complexa + extras (periodo indefinido, depende de outras tarefas e/ou implementacoes de codigo).

### Valor por etapa (%)

| Fase do Projeto           | Cobertura de Testes                   | Bugs Corrigidos | Issues/Story Points | Eficiência/Velocidade        |
| ------------------------- | ------------------------------------- | --------------- | ------------------- | ---------------------------- |
| Início (Sprints 1-2)      | **60%**                               | 10%             | 20%                 | 10%                          |
| Meio (Sprints 3-4)        | **30%**                               | 25%             | 30%                 | 15%                          |
| Futuro (Indefinido)       | **Indefinido**                        | 50%-25%         | 30%                 | 20%                          |
---
<br>

<div align="center">

### Explicacao

**Início (Sprints 1-2):**
- Base principal do projeto - garantimento de testes & funcionamento pra nao retroceder.

**Crescimento (Sprints 3-4):**
- Incremento de features "extras" ou "nao preferencias", com foco muito maior em produtividade/entrega.

**Futuro (Indefinido):**
- Aplicação finalizada, testes apenas para novas features & manutenção, foco maior em correção de bugs e eficiência nas entregas - features extras.
</div>
