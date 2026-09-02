# Test Plan — Automação de Testes Automation Exercise

**Projeto:** QA Automation Framework (Java + Selenium + JUnit 5 + Maven)
**Aplicação sob teste (AUT):** [automationexercise.com](https://automationexercise.com) (UI) + [API pública](https://automationexercise.com/api_list)
**Autor:** [seu nome]
**Versão do documento:** 1.1
**Data:** Setembro/2026

---

## 1. Introdução

Este documento define a estratégia, o escopo e os critérios de teste para o projeto de automação da aplicação Automation Exercise, um site de e-commerce com **UI e API pública** dedicados à prática de testes automatizados, utilizado como ambiente de referência para este portfólio. O objetivo é demonstrar um processo de QA completo: da documentação à automação (UI, API e E2E), passando por integração contínua e gestão de defeitos.

## 2. Objetivo

Garantir, por meio de testes automatizados de **API**, **integração** e **ponta a ponta (E2E)**, que os fluxos críticos de negócio da aplicação funcionem corretamente — tanto na camada de back-end (API) quanto na camada de UI —, com execução confiável (baixo índice de flaky tests) e integrada a um pipeline de CI/CD multi-ambiente.

## 3. Escopo

### 3.1 Em escopo

**Camada UI (E2E / Integração):**

| Módulo | Funcionalidades |
|---|---|
| Autenticação | Cadastro, login com credenciais válidas/inválidas, logout |
| Catálogo de produtos | Listagem, busca, visualização de detalhes |
| Carrinho | Adicionar/remover produtos, persistência do carrinho entre páginas |
| Checkout | Preenchimento de dados, revisão do pedido, finalização de compra |

**Camada API (testes de contrato/integração de back-end):**

| Endpoint | Método | Cenário |
|---|---|---|
| `/api/productsList` | GET | Listagem de todos os produtos |
| `/api/productsList` | POST | Método não permitido (405) |
| `/api/brandsList` | GET | Listagem de marcas |
| `/api/searchProduct` | POST | Busca de produto (com e sem parâmetro obrigatório) |
| `/api/verifyLogin` | POST | Login válido, inválido, sem parâmetro, método não permitido (DELETE) |
| `/api/createAccount` | POST | Criação de conta |
| `/api/deleteAccount` | DELETE | Exclusão de conta |
| `/api/updateAccount` | PUT | Atualização de conta |
| `/api/getUserDetailByEmail` | GET | Consulta de dados de usuário por e-mail |

### 3.2 Fora de escopo

- Testes de performance/carga (fora do propósito deste projeto)
- Testes de segurança (pentest)
- Testes de acessibilidade (poderá ser um projeto futuro)
- Testes visuais/pixel-perfect (screenshot testing)

## 4. Níveis e tipos de teste

| Tipo | O que valida | Ferramenta |
|---|---|---|
| Teste de API | Contrato, status code, payload e regras de negócio dos endpoints, isoladamente da UI | JUnit 5 + RestAssured |
| Teste de Integração | Fluxos que combinam API + UI (ex: criar usuário via API e validar login na UI), ou integração entre componentes de página | JUnit 5 + Selenium + RestAssured |
| Teste E2E | Fluxo completo do ponto de vista do usuário, 100% via UI (cadastro → login → compra → checkout) | JUnit 5 + Selenium WebDriver |

> **Nota conceitual:** agora que temos API real, a pirâmide de testes fica mais próxima do mundo real: testes de API (mais rápidos e estáveis, base da pirâmide), testes de integração (API + UI combinados) e testes E2E (mais lentos, topo da pirâmide, cobrindo os fluxos mais críticos). Isso será detalhado tecnicamente na Etapa 4.

## 5. Ambientes

| Ambiente | Propósito | URL/Config |
|---|---|---|
| **dev** | Execução a cada push/PR — feedback rápido para o desenvolvedor | Config apontando para saucedemo.com (simulado como "dev" via profile Maven) |
| **staging** | Execução completa antes de merge na branch principal — suíte completa + regressão | Config equivalente, profile `staging` |
| **produção** | Execução pós-merge/deploy — smoke tests críticos (API + E2E essenciais) | Config equivalente, profile `prod` |

*(Como estamos usando uma aplicação pública única, os "ambientes" serão simulados via Maven Profiles + variáveis do GitHub Actions — prática comum quando não há múltiplos ambientes reais disponíveis, e isso será explicado tecnicamente na Etapa 6. Agora com API real, isso também nos permite diferenciar: dev roda API + integração rápida; staging roda a suíte completa incluindo E2E; produção roda apenas smoke tests críticos de API + UI.)*

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

**Camada API:**

| ID Requisito | Descrição | ID Caso de Teste | Tipo | Prioridade |
|---|---|---|---|---|
| RF-API-001 | GET `/productsList` deve retornar lista de produtos (200) | TC-API-001 | API | P0 |
| RF-API-002 | POST `/productsList` deve retornar 405 (método não suportado) | TC-API-002 | API | P2 |
| RF-API-003 | POST `/searchProduct` deve retornar produtos filtrados (200) | TC-API-003 | API | P0 |
| RF-API-004 | POST `/searchProduct` sem parâmetro deve retornar 400 | TC-API-004 | API | P1 |
| RF-API-005 | POST `/verifyLogin` com credenciais válidas deve retornar 200 | TC-API-005 | API | P0 |
| RF-API-006 | POST `/verifyLogin` com credenciais inválidas deve retornar 404 | TC-API-006 | API | P0 |
| RF-API-007 | POST `/verifyLogin` sem e-mail deve retornar 400 | TC-API-007 | API | P1 |
| RF-API-008 | POST `/createAccount` deve criar usuário (201) | TC-API-008 | API | P0 |
| RF-API-009 | DELETE `/deleteAccount` deve remover usuário (200) | TC-API-009 | API | P1 |
| RF-API-010 | PUT `/updateAccount` deve atualizar dados do usuário (200) | TC-API-010 | API | P1 |
| RF-API-011 | GET `/getUserDetailByEmail` deve retornar dados do usuário | TC-API-011 | API | P1 |

**Camada UI / E2E / Integração:**

| ID Requisito | Descrição | ID Caso de Teste | Tipo | Prioridade |
|---|---|---|---|---|
| RF-UI-001 | Usuário deve se cadastrar via formulário de signup | TC-UI-001 | E2E | P0 |
| RF-UI-002 | Usuário deve logar com credenciais válidas | TC-UI-002 | E2E | P0 |
| RF-UI-003 | Sistema deve exibir erro em login inválido | TC-UI-003 | Integração | P1 |
| RF-UI-004 | Usuário deve adicionar produto ao carrinho | TC-UI-004 | Integração | P0 |
| RF-UI-005 | Carrinho deve persistir entre páginas | TC-UI-005 | Integração | P1 |
| RF-UI-006 | Usuário deve concluir checkout com dados válidos | TC-UI-006 | E2E | P0 |
| RF-UI-007 | Usuário deve deslogar e ser redirecionado ao login | TC-UI-007 | Integração | P2 |

**Fluxo combinado (Integração API + UI):**

| ID Requisito | Descrição | ID Caso de Teste | Tipo | Prioridade |
|---|---|---|---|---|
| RF-INT-001 | Conta criada via API deve permitir login com sucesso na UI | TC-INT-001 | Integração | P0 |

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
| Mudanças na aplicação alvo (site de terceiros) | Testes podem quebrar sem aviso | Page Object Model isola mudanças de UI; testes de API isolados por endpoint |
| Dependência de estado entre testes | Testes não idempotentes | Cada teste deve configurar seu próprio estado (ex: criar/excluir conta via API a cada execução) |
| Dados de teste "sujando" a base pública (ex: contas criadas via API) | Poluição de dados, testes não repetíveis | Toda conta criada em teste de API deve ser excluída ao final (`@AfterEach`) via `deleteAccount` |

## 12. Ferramentas do projeto

- **Linguagem:** Java 17+
- **Build:** Maven
- **Framework de testes:** JUnit 5
- **Automação de browser:** Selenium WebDriver 4
- **Testes de API:** RestAssured
- **Gerenciamento de driver:** WebDriverManager
- **CI/CD:** GitHub Actions
- **Relatórios:** Allure Report (a definir/confirmar na Etapa 8)
- **Gestão de bugs:** Template Markdown versionado (simulando ferramenta tipo Jira)

---

**Próxima etapa:** Setup do projeto (estrutura Maven, dependências, `pom.xml`, convenções de pacotes).
