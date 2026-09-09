# Relatório de Bug

## BUG-002: Anúncio de terceiro (Google Ads "vignette") pode interceptar o clique no botão "Continue" após criação de conta

| Campo | Valor |
|---|---|
| **Severidade** | Major |
| **Prioridade** | P2 |
| **Ambiente** | staging / produção (reproduzido em execução não-headless local; depende do carregamento do anúncio, que é intermitente por natureza) |
| **Encontrado em** | 09/09/2026 |
| **Caso de teste relacionado** | RF-UI-001 |
| **Status** | Aberto |

### Passos para reproduzir
1. Completar o fluxo de cadastro até a página "ACCOUNT CREATED!" (`/account_created`).
2. Clicar no botão "Continue".
3. Observar a URL resultante.

### Resultado esperado
O clique deveria navegar imediatamente para a Home (`/`), com o usuário já autenticado (mensagem "Logged in as [nome]" visível no cabeçalho).

### Resultado obtido
Em parte das execuções, a navegação não ocorre. A URL permanece em `/account_created`, apenas acrescida de um fragmento `#google_vignette` (ex: `https://automationexercise.com/account_created#google_vignette`) — assinatura de um anúncio intersticial de terceiro interceptando o evento de clique antes que ele complete a navegação esperada. Nenhum erro é exibido ao usuário; a página simplesmente não avança.

### Evidência
Capturado via log de depuração durante a execução de `SignupE2ETest` (Etapa 5 do projeto): URL e título da página coletados imediatamente após o clique confirmaram que a navegação não avançou, e a busca por qualquer elemento contendo "Logged in" retornou lista vazia — indicando que a sessão autenticada não chegou a ser exibida.

### Impacto
Se o mesmo comportamento ocorrer com usuários reais (ver ressalva abaixo), o efeito seria: o usuário conclui o cadastro, clica em "Continue", e a tela aparenta não responder — sem nenhuma mensagem de erro. Um usuário menos técnico pode interpretar isso como o site estar com problema e abandonar o fluxo logo após criar a conta, mesmo com o cadastro já tendo sido concluído com sucesso no backend.

### Observações
- **Ressalva importante**: o clique capturado neste teste foi disparado via Selenium WebDriver (evento sintético). Navegadores por vezes tratam eventos sintéticos de forma diferente de um clique humano real ("trusted event"), então não há confirmação de que usuários reais são afetados na mesma proporção. Recomenda-se validação manual/exploratória adicional antes de tratar isso como confirmado para tráfego real.
- Mitigação aplicada no nosso framework de automação (não corrige o problema na aplicação, apenas evita que ele quebre os testes): após o clique, verificamos ativamente se a URL mudou dentro de um tempo curto; se não mudou, navegamos diretamente por código como contingência (`AccountCreatedPage.waitForRealNavigationAwayFromThisPage()`).
- Sugestão para o time de produto/desenvolvimento: considerar suprimir anúncios intersticiais em páginas de conclusão de fluxos críticos de conversão (checkout, criação de conta), prática comum para não arriscar a taxa de conclusão desses funis.