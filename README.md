# BugBank Selenium Automation

Suíte de testes automatizados (Selenium + JUnit 5 + Spring Boot + Allure) para validação do fluxo de transferência entre contas na aplicação [BugBank](https://bugbank.netlify.app/).

## Tecnologias

- Java 17
- Spring Boot
- Selenium 
- WebDriverManager
- JUnit 5
- AssertJ
- Allure Report

## Pré-requisitos

- JDK 17

## Como executar os testes

```bash
./mvnw test
```


## Relatório Allure

Após a execução dos testes, gere e visualize o relatório:

```bash
./mvnw allure:report
./mvnw allure:serve
```

## Plano de Testes

### 1. Objetivo

Validar que o sistema permite realizar transferências entre contas corretamente, garantindo a consistência dos saldos e o sucesso da operação.

### 2. Escopo

**Em escopo:**
- Criação de contas com saldo inicial
- Autenticação de usuário
- Transferência de valores entre contas
- Validação de saldo após transferência nas contas
- Validação de mensagens de retorno

**Fora do escopo:**
- Teste de performance
- Teste de segurança
- Teste de carga

### 3. Estratégia de Teste

- Teste funcional
- Teste para validar regras de negócio

### 4. Cenários Cobertos

#### 4.1 Transferência entre Contas com Sucesso

**Pré-condições**
- Sistema disponível em:[BugBank](https://bugbank.netlify.app/).
- Possibilidade de criação de contas ativa
- Usuário não autenticado inicialmente

**Massa de Dados**

| Dado | Valor Exemplo |
|---|---|
| Conta de Origem | Gerada dinamicamente |
| Conta de Destino | Gerada dinamicamente |
| Saldo Conta Origem | R$ 100 |
| Saldo Conta Destino | R$ 50 |
| Valor da Transferência | R$ 50 |

**Fluxo de Teste**
1. Acessar o sistema
2. Criar Conta A com saldo inicial
3. Criar Conta B com saldo inicial
4. Realizar login com Conta A
5. Executar transferência para Conta B
6. Capturar saldo antes e depois da operação

**Validações**
- Transferência realizada com sucesso
- Sistema retorna confirmação da operação
- Saldo Conta A = saldo inicial - valor transferido
- Saldo Conta B = saldo inicial + valor recebido

**Critérios de Aceite**
- Operação deve retornar sucesso
- Valores devem ser atualizados corretamente
- Não deve haver inconsistência nos saldos
- Fluxo deve ser executado sem erros