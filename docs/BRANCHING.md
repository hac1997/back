### Estratégia de Branching

---
## Nome dos Principais Branches

| Branch       | Descrição                                                                                            |
| ------------ | ---------------------------------------------------------------------------------------------------- |
| `main`       | Contém a versão estável da aplicação. Apenas código testado e aprovado é mesclado. |
| `features/*` | Utilizada para desenvolvimento de novas funcionalidades. Ex: `features/authentication`.              |

---

### Fluxo de Integração

### Criação de Branches

* Qualquer desenvolvedor pode criar uma branch a partir da branch `features`.
* A nomenclatura deve seguir o padrão: `features/<nome-da-funcionalidade>`.

### Pull Requests
* Todo código novo ou modificado deve ser submetido via Pull Request (PR).
* E testado pelo .github/workflow

### Mesclagens

* A branch `main` só pode receber alterações após:
  * Execução e aprovação nos testes automatizados e/ou manuais.

---

## Frequência de Integração

* A cada **ciclo de desenvolvimento (Sprint)**, devem ocorrer múltiplas integrações das branches `features/*` para a branch `main`.
* O objetivo é manter a `main` sempre com a versão mais estável e pronta para produção.
---
