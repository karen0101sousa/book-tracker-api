# SLO — Book Tracker API

## 1. Objetivo

Este documento define um objetivo de nível de serviço (SLO) inicial para a API Book Tracker, com foco no tempo de resposta do endpoint de consulta de leituras.

O objetivo é estabelecer um critério simples de desempenho e permitir sua verificação por meio de testes automatizados.

## 2. SLO definido

**Endpoint monitorado:** `GET /readings`

**Indicador (SLI):** percentil 95 (p95) do tempo de resposta HTTP, medido pelo k6.

**Objetivo:** 95% das requisições devem apresentar tempo de resposta inferior a 500 ms.

**Critério de aprovação:**

```javascript
thresholds: {
    http_req_duration: ['p(95)<500'],
}
```

O limite é expresso em milissegundos.

## 3. Ferramenta de medição

Foi utilizado o k6 para executar um smoke test contra o endpoint `GET /readings`.

A configuração inicial do teste utiliza:

- 1 usuário virtual (VU);
- 10 iterações;
- verificação do status HTTP 200;
- limite de desempenho p95 inferior a 500 ms.

O teste pode ser executado com Docker, sem necessidade de instalar o k6 diretamente na máquina:

```bash
docker run --rm --network host \
  -v "$PWD/k6:/scripts:ro" \
  grafana/k6 run /scripts/smoke-test.js
```

## 4. Resultado do smoke test

**Data da execução:** 09/11/2026

| Indicador | Resultado |
|---|---:|
| Requisições realizadas | 10 |
| Checks aprovados | 10 (100%) |
| Falhas HTTP | 0% |
| Tempo médio | 7,5 ms |
| Tempo mínimo | 5,58 ms |
| Tempo máximo | 10,3 ms |
| p95 | 9,89 ms |
| Limite definido | 500 ms |

**Resultado:** o teste foi aprovado. O p95 observado, de 9,89 ms, ficou abaixo do limite estabelecido de 500 ms.

## 5. Limitações e considerações

O resultado representa uma execução pontual em ambiente local, com apenas um usuário virtual e dez requisições.

Portanto, ele demonstra que o endpoint atendeu ao objetivo de desempenho nessa execução, mas não comprova o comportamento da API em produção ou sob carga elevada.

O indicador `http_req_duration` do k6 representa a duração observada pelo cliente durante a requisição HTTP. Ele é diferente da métrica `http.server.requests` disponibilizada pelo Spring Boot Actuator, que registra informações sobre as requisições no lado do servidor.

Para uma avaliação mais abrangente, poderão ser realizados testes com maior volume de requisições e concorrência.

## 6. Monitoramento

O Spring Boot Actuator e o Micrometer também foram configurados para disponibilizar métricas HTTP por meio do endpoint:

```text
/actuator/metrics/http.server.requests
```

Essa métrica permite acompanhar informações como quantidade de requisições, tempo total e duração máxima observada pelo servidor.

O percentil 95 utilizado como critério deste SLO é medido pelo k6, conforme a configuração do smoke test.
