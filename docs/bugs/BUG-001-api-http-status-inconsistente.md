# Relatório de Bug

## BUG-001: API retorna sempre HTTP 200, mesmo em cenários de erro de negócio

| Campo | Valor |
|---|---|
| **Severidade** | Major |
| **Prioridade** | P1 |
| **Ambiente** | dev / staging / produção (comportamento inerente à API pública, presente em todos os ambientes) |
| **Encontrado em** | 09/09/2026 |
| **Caso de teste relacionado** | RF-API-002, RF-API-006, RF-API-007 |
| **Status** | Aberto |

### Passos para reproduzir
1. Fazer uma requisição `POST` para `/api/productsList` (endpoint que só suporta `GET`).
2. Observar o status HTTP retornado.
3. Repetir o mesmo padrão com `POST /api/verifyLogin` enviando credenciais inexistentes, e com `POST /api/verifyLogin` sem o parâmetro obrigatório `email`.

### Resultado esperado
De acordo com a convenção HTTP (RFC 7231) e com a própria documentação pública da API (`/api_list`), essas respostas deveriam retornar o status HTTP correspondente ao erro:
- `POST /productsList` → `405 Method Not Allowed`
- `POST /verifyLogin` com credenciais inexistentes → `404 Not Found`
- `POST /verifyLogin` sem `email` → `400 Bad Request`

### Resultado obtido
Em todos os três cenários, a API retorna **HTTP 200 OK** no nível de protocolo. O código de erro real (`405`, `404`, `400`) só é informado dentro do corpo JSON da resposta, no campo `responseCode`:

```json
{"responseCode": 405, "message": "This request method is not supported."}
```

### Evidência
Comportamento confirmado e coberto por teste automatizado: `ProductsApiTest.postToProductsList_shouldReturnMethodNotSupported()` e `UserApiTest.verifyLogin_withInvalidCredentials_shouldReturnUserNotFound()` (ambos em `src/test/java/.../tests/api/`). Os testes verificam deliberadamente `response.jsonPath().getInt("responseCode")` em vez de `response.statusCode()` para esses cenários — o código-fonte do teste contém um comentário explicando esse motivo.

### Impacto
Qualquer sistema consumidor desta API que utilize bibliotecas HTTP client "convencionais" (que tratam qualquer resposta `2xx` como sucesso automaticamente, sem inspecionar o corpo) vai **silenciosamente tratar erros como sucesso**. Isso é particularmente arriscado para integrações que não têm testes automatizados tão detalhados quanto os deste projeto — o problema só apareceria em produção, de forma sutil (dados de erro sendo processados como se fossem válidos).

### Observações
Este comportamento não impede o uso da API (por isso a severidade é `Major`, não `Critical`/`Blocker`), mas é uma prática divergente das convenções REST/HTTP amplamente aceitas. Recomendação: alinhar o status HTTP retornado ao `responseCode` já presente no corpo, sem quebrar compatibilidade retroativa (poderia ser feito como uma mudança versionada da API, ex: `/api/v2/`).