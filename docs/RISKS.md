# Registro de Riscos - TPJ (TriggerPixel Jogos)


____
## Falha de estimativa

### Descrição do risco.
As estimativas de tempo e/ou custo para o desenvolvimento do projeto estão incorretas, levando a atrasos na entrega.

### Categoria (de projeto, de produto, técnico, de negócio).

De projeto
### Probabilidade (alta, média, baixa).
média

### Impacto (alta, média, baixa).
alta

### Estratégia de mitigação ou resposta.
Reavaliação contínua das estimativas a cada interação/sprint.

____
## Gargalo técnico (feature de pesquisa)

### Descrição do risco.
A feature de pesquisa apresenta desafios técnicos complexos e requer maior planejamento ou baixo desempenho, dificultando a implementação ou exigindo um retrabalho significativo.

### Categoria (de projeto, de produto, técnico, de negócio).
Técnico

### Probabilidade (alta, média, baixa).
Média

### Impacto (alta, média, baixa).
Média

### Estratégia de mitigação ou resposta.
Tentar implementar a funcionalidade logo no começo para avaliar melhor o tempo e complexidade de se implementar a feature.

____
## Problema de integração

### Descrição do risco.
Módulos ou componentes do sistema não funcionam corretamente quando combinados, ou há falhas na comunicação com sistemas externos (APIs, serviços de terceiros).

### Categoria (de projeto, de produto, técnico, de negócio).
De produto

### Probabilidade (alta, média, baixa).
média

### Impacto (alta, média, baixa).
alto

### Estratégia de mitigação ou resposta.
Integração contínua (CI) e Testes de Integração: Estabelecer um processo de Integração Contínua que execute testes de integração automatizados sempre que houver uma mudança no código. Definir contratos de interface (APIs) claros e bem documentados entre os componentes.

____
## Limitação de hardware (servidor/database)

### Descrição do risco.
O hardware (servidores, banco de dados, rede) não é capaz de suportar a carga de usuários ou a performance exigida pelo sistema, resultando em lentidão ou falhas.

### Categoria (de projeto, de produto, técnico, de negócio).
de produto

### Probabilidade (alta, média, baixa).
média

### Impacto (alta, média, baixa).
alto

### Estratégia de mitigação ou resposta.
Testes de carga e escalabilidade: Realizar Testes de Performance e Carga antes do lançamento para simular o uso esperado. Utilizar uma arquitetura de cloud (nuvem) que permita escalabilidade horizontal (aumentar recursos sob demanda) de forma rápida e eficiente.

____
## Bugs

### Descrição do risco.
O software contém defeitos ou erros de programação que causam comportamento incorreto, falhas ou vulnerabilidades de segurança.

### Categoria (de projeto, de produto, técnico, de negócio).
de Produto

### Probabilidade (alta, média, baixa).
Alta

### Impacto (alta, média, baixa).
Médio

### Estratégia de mitigação ou resposta.
Testes e revisões contínuas de código. 

