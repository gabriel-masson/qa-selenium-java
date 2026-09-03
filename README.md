# QA Automation Suite — Automation Exercise

Projeto de portfólio de automação de testes (API, Integração e E2E) construído com **Java 17 + Selenium 4 + JUnit 5 + Maven**, contra a aplicação pública [automationexercise.com](https://automationexercise.com).

> Este projeto está sendo construído por etapas. A documentação completa de processo (Test Plan, matriz de rastreabilidade, template de bugs) está em [`docs/`](./docs).

## Stack técnica

- Java 17
- Maven
- JUnit 5 (Jupiter)
- Selenium WebDriver 4
- RestAssured (testes de API)
- WebDriverManager (gerenciamento automático de driver de browser)
- GitHub Actions (CI/CD — dev / staging / produção)

## Estrutura do projeto

```
src
├── main/java/com/qa/automationexercise
│   ├── config    → gerenciamento de ambiente e driver
│   ├── api       → clientes de API (RestAssured)
│   ├── pages     → Page Objects (UI)
│   ├── models    → POJOs de request/response da API
│   └── utils     → utilitários (waits, geração de dados, listeners)
│
└── test/java/com/qa/automationexercise
    ├── base            → classes base de teste
    └── tests
        ├── api         → testes de API (isolados, camada base da pirâmide)
        ├── integration → testes combinando API + UI
        └── e2e         → testes de fluxo completo via browser

docs/
├── test-plan.md    → plano de testes completo
└── bugs/           → relatórios de bugs encontrados durante a execução
```

## Como executar

```bash
# Ambiente de desenvolvimento (padrão)
mvn test

# Ambiente específico
mvn test -Pdev
mvn test -Pstaging
mvn test -Pprod
```

## Status do projeto

- [x] Etapa 1 — Test Plan
- [x] Etapa 2 — Setup do projeto
- [x] Etapa 3 — Page Object Model
- [ ] Etapa 4 — Testes de Integração e API
- [ ] Etapa 5 — Testes E2E
- [ ] Etapa 6 — Gestão de ambientes
- [ ] Etapa 7 — Tratamento de Flaky Tests
- [ ] Etapa 8 — Relatórios (Allure)
- [ ] Etapa 9 — Documentação de Bugs
- [ ] Etapa 10 — Pipeline CI/CD (GitHub Actions)
