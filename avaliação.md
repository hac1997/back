# Avaliação das Entregas

## Notas

| Entrega      | data      | nota         |
| ------------ | --------- | ------------ |
| 1            | 12/9/2025 | 5/5          |
| 2            |           | 7,9/10       |
| 3            |           | --/10        |
| 4            |           | --/10        |
| 5            |           | --/10        |
| 6            |           | --/10        |
| 7            |           | --/10        |
| 8            |           | --/10        |
| Final        |           | --/15        |
| Apresentação |           | --/10        |
| **Total**    |           | **12,9/100** |

## Entrega 1 (corrigida na Entrega 2)

- Equipe formada dentro do prazo: 1/1 pt
- Tema definido de forma clara e em conformidade com as restrições: 2/2 pts
- Arquivo README.md inicial preenchido 2/2 pts

## Entrega 2

### MVP: 1,6/2 pts

- Documento contempla as seções pedidas;

- Meta do produto não está focada em MVP: Reescreva em um parágrafo com foco no MVP. (ex.: "Permitir que um usuário autenticado crie posts de texto e que a comunidade visualize e pesquise posts por palavra-chave.").

### Backlog: 3,2/4 pts

- 8 histórias escritas em formato adequado, com prioridade e esforço;

- Há variação de prioridades (alto/médio) e estimativas presentes em todas as histórias.

- Critérios de aceitação estão genéricos em parte das histórias (ex., login, criação de post, busca); Detalhar casos de sucesso/erro (ex.: criar post -- sucesso: Dado usuário autenticado, quando preencher título (obrigatório) e corpo (≥ N caracteres), então o post é criado e listado no topo; erro: Dado título vazio, quando salvar, então o sistema exibe mensagem e bloqueia a ação.);

  - Por que usar inglês (_Acceptance Criteria_)?

- "Remover visibilidade de posts/respostas" do moderador reúne múltiplas regras de negócio e fluxos (denúncia, arquivamento, registro, compliance); quebrar em histórias menores (ex.: _denunciar_, _ocultar post_, _log de moderação_).

### Métricas: 1,5/2 pts

- Lista cobre _issues_ abertas/fechadas, tempo médio de resolução, cobertura de testes e velocidade do time.

- Tornem as métricas mensuráveis (meta, fonte de dados, frequência).

  - Exemplos:
    - Issues fechadas/semana ≥ 8 (fonte: GitHub Projects; coleta: sexta).
    - Lead time ≤ 5 dias por história (To do → Done).
    - Cobertura backend ≥ 60% na Sprint 1 (fonte: CI).
    - % builds verdes ≥ 90% na Sprint 1 (CI).

- A seção "Valor por etapa (%)" tem inconsistências (ex.: cobertura 60% no início e 30% no meio). Reavaliar ou remover até ter uma régua coerente com a maturidade do projeto.

### Estimativas: 1,6/2 pts

- Todas as histórias possuem SP e há uma tabela de faixas.

- A faixa "8+ pontos = período indefinido" é problemática: SP devem refletir complexidade relativa, não prazo indefinido. Limitem SP (p.ex., até 13) e quebrem histórias maiores.

- Rodem Planning Poker com história de referência (3 ou 5 SP) para calibrar variações (ex.: busca = 8 SP vs. edição = 2 SP).
