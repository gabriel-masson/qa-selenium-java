# Test Plan — Automação de Testes SauceDemo

**Projeto:** QA Automation Framework (Java + Selenium + JUnit 5 + Maven)
**Aplicação sob teste (AUT):** [saucedemo.com](https://www.saucedemo.com)
**Autor:** [seu nome]
**Versão do documento:** 1.0
**Data:** Setembro/2026

---

## 1. Introdução

Este documento define a estratégia, o escopo e os critérios de teste para o projeto de automação da aplicação SauceDemo, uma aplicação de e-commerce utilizada como ambiente de referência para este portfólio. O objetivo é demonstrar um processo de QA completo: da documentação à automação, passando por integração contínua e gestão de defeitos.

## 2. Objetivo

Garantir, por meio de testes automatizados de integração e ponta a ponta (E2E), que os fluxos críticos de negócio da aplicação funcionem corretamente, com execução confiável (baixo índice de flaky tests) e integrada a um pipeline de CI/CD multi-ambiente.

## 3. Escopo

### 3.1 Em escopo

| Módulo | Funcionalidades |
|---|---|
| Autenticação | Login com credenciais válidas, inválidas, usuário bloqueado (`locked_out_user`), usuário com performance degradada (`performance_glitch_user`) |
| Catálogo de produtos | Listagem, ordenação (preço, nome), visualização de detalhes |
| Carrinho | Adicionar/remover produtos, persistência do carrinho entre páginas |
| Checkout | Preenchimento de dados, validação de campos obrigatórios, resumo do pedido, finalização de compra |
| Sessão | Logout, comportamento ao acessar páginas protegidas sem login |

### 3.2 Fora de escopo

- Testes de performance/carga (fora do propósito deste projeto)
- Testes de segurança (pentest)
- Testes de acessibilidade (poderá ser um projeto futuro)
- Testes visuais/pixel-perfect (screenshot testing)

## 4. Níveis e tipos de teste

| Tipo | O que valida | Ferramenta |
|---|---|---|
| Teste de Integração | Comunicação entre camadas da automação e componentes da página (ex: se um Page Object interage corretamente com múltiplos elementos e reflete estado esperado) | JUnit 5 + Selenium (WebDriver isolado por componente) |
| Teste E2E | Fluxo completo do ponto de vista do usuário (login → compra → checkout) | JUnit 5 + Selenium WebDriver |

> **Nota conceitual:** como o SauceDemo não expõe uma API pública para testarmos separadamente, o "teste de integração" aqui será tratado como *teste de integração entre componentes de UI/fluxos parciais*, diferente do E2E que cobre a jornada completa. Isso será detalhado tecnicamente na Etapa 4.

## 5. Ambientes

| Ambiente | Propósito | URL/Config |
|---|---|---|
| **dev** | Execução a cada push/PR — feedback rápido para o desenvolvedor | Config apontando para saucedemo.com (simulado como "dev" via profile Maven) |
| **staging** | Execução completa antes de merge na branch principal — suíte completa + regressão | Config equivalente, profile `staging` |
| **produção** | Execução pós-merge/deploy — smoke tests críticos | Config equivalente, profile `prod` |

*(Como estamos usando uma aplicação pública única, os "ambientes" serão simulados via Maven Profiles + variáveis do GitHub Actions — prática comum quando não há múltiplos ambientes reais disponíveis, e isso será explicado tecnicamente na Etapa 6.)*

## 6. Critérios de entrada

- Código da automação revisado (Pull Request aberto)
- Ambiente acessível e estável
- Casos de teste mapeados na matriz de rastreabilidade (seção 9)

## 7. Critérios de saída

- 100% dos testes críticos (P0) passando
- Nenhum bug com severidade **Blocker** ou **Critical** em aberto
- Taxa de flaky test abaixo de 2% nas últimas 20 execuções (métrica definida na Etapa 7)

## 8. Estratégia de execução por gatilho (CI/CD)

| Gatilho | Ambiente | Suíte executada |
|---|---|---|
| `push` em branch de feature | dev | Testes unitários/integração rápidos |
| `pull_request` para `develop`/`main` | staging | Suíte completa (integração + E2E) |
| `merge` em `main` | produção | Smoke tests (fluxos críticos apenas) |

*(Detalhamento técnico completo do workflow do GitHub Actions será entregue na Etapa 10.)*

## 9. Matriz de rastreabilidade (amostra inicial)

| ID Requisito | Descrição | ID Caso de Teste | Tipo | Prioridade |
|---|---|---|---|---|
| RF-001 | Usuário deve logar com credenciais válidas | TC-001 | E2E | P0 |
| RF-002 | Sistema deve bloquear usuário `locked_out_user` | TC-002 | Integração | P0 |
| RF-003 | Usuário deve adicionar produto ao carrinho | TC-003 | Integração | P0 |
| RF-004 | Carrinho deve persistir entre páginas | TC-004 | Integração | P1 |
| RF-005 | Usuário deve concluir checkout com dados válidos | TC-005 | E2E | P0 |
| RF-006 | Sistema deve validar campos obrigatórios no checkout | TC-006 | E2E | P1 |
| RF-007 | Usuário deve deslogar e ser redirecionado ao login | TC-007 | Integração | P2 |

*(Esta matriz será expandida e vinculada aos casos de teste reais nas próximas etapas.)*

## 10. Classificação de severidade e prioridade de bugs

| Severidade | Definição |
|---|---|
| **Blocker** | Impede o uso da aplicação ou execução dos testes |
| **Critical** | Funcionalidade principal quebrada, sem workaround |
| **Major** | Funcionalidade afetada, mas com workaround |
| **Minor** | Problema cosmético ou de baixo impacto |

| Prioridade | Definição |
|---|---|
| **P0** | Deve ser corrigido imediatamente |
| **P1** | Corrigir no próximo ciclo |
| **P2** | Backlog, sem urgência |

*(O template completo de Bug Report será entregue na Etapa 9.)*

## 11. Riscos e mitigação

| Risco | Impacto | Mitigação |
|---|---|---|
| Instabilidade de rede/CI (flaky tests) | Falsos negativos, perda de confiança na suíte | Waits explícitos, retries controlados, tagueamento de testes instáveis (Etapa 7) |
| Mudanças na aplicação alvo (SauceDemo é site de terceiros) | Testes podem quebrar sem aviso | Page Object Model isola mudanças de UI em um único ponto |
| Dependência de estado entre testes | Testes não idempotentes | Cada teste deve configurar seu próprio estado (login independente por teste) |

## 12. Ferramentas do projeto

- **Linguagem:** Java 17+
- **Build:** Maven
- **Framework de testes:** JUnit 5
- **Automação de browser:** Selenium WebDriver 4
- **Gerenciamento de driver:** WebDriverManager
- **CI/CD:** GitHub Actions
- **Relatórios:** Allure Report (a definir/confirmar na Etapa 8)
- **Gestão de bugs:** Template Markdown versionado (simulando ferramenta tipo Jira)

---

**Próxima etapa:** Setup do projeto (estrutura Maven, dependências, `pom.xml`, convenções de pacotes).
