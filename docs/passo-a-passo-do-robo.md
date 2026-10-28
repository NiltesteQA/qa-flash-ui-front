# ⚡ Passo a Passo do Robô — QA Flash - UI

---

## O que o QA Flash - UI faz exatamente?

Pensa assim: você é o QA humano. O robô é seu assistente técnico de automação.
Ele transforma o texto da sua História de Usuário (aquela que vem do Jira) em código de teste automatizado pronto para rodar.
Mas ele **nunca avança sozinho** — em cada fase, ele para e espera você decidir.

---

## O fluxo na prática, passo a passo

### 📋 Você cola a História de Usuário

Exemplo:
> "Como usuário, quero fazer login com e-mail e senha, para acessar minha conta."

---

### ⚡ FASE 1 — O robô lê e gera os cenários de teste (Gherkin/BDD)

Ele analisa a história e escreve entre **10 a 15 cenários de teste** cobrindo:

| Tipo | Exemplo |
|------|---------|
| ✅ Caminho Feliz | Login com e-mail e senha válidos |
| 🔀 Caminho Alternativo | Login com "lembrar senha" marcado |
| 🔲 Borda | Campo senha com 1 caractere, e-mail sem @ |
| ❌ Erros de Tela | Mensagem de erro ao errar a senha 3x |

No formato Gherkin:
```gherkin
Cenário: Login com credenciais válidas
  Dado que o usuário está na tela de login
  Quando ele preenche o e-mail "user@email.com" e a senha correta
  Então deve ser redirecionado para o dashboard
```

🛑 **Para aqui e mostra tudo para você revisar.**

---

### ⚡ FASE 2 — Você aprova e escolhe a tecnologia

Você diz: *"Aprovado, quero em Cypress com TypeScript"* (ou Java, Python, Ruby — Web ou Mobile).

O robô monta a estrutura completa do projeto:

```
projeto/
├── features/           ← Arquivos .feature (Gherkin)
├── pages/              ← Page Objects (mapeamento de tela)
├── steps/              ← Implementação dos passos
├── support/            ← Configurações
└── package.json        ← Dependências
```

🛑 **Para aqui e mostra o código para você revisar.**

---

### ⚡ FASE 3 — Você manda rodar os testes

O robô executa o comando de teste (`npx cypress run`, `mvn test`, etc.), monitora os resultados e:
- Se falhar → mostra o erro exato, propõe correção e **aguarda sua aprovação**
- Se passar → confirma 100% verde

🛑 **Para aqui e aguarda sua autorização para fazer o commit.**

---

### ⚡ FASE 4 — Você autoriza subir para o repositório

O robô faz o commit, cria a branch no padrão (`feature/JIRA-xxx`) e faz o push. Tarefa concluída.

---

## O que ele garante em todo o código

- ✅ **Sem senhas no código** — credenciais sempre em variáveis de ambiente
- ✅ **Sem `cy.wait(3000)`** — esperas inteligentes pelos elementos
- ✅ **Sem seletores frágeis** — usa `data-testid`, `aria-label`, IDs estáveis
- ✅ **Cada teste independente** — pode rodar em qualquer ordem ou em paralelo
- ✅ **Screenshot automático** em caso de falha

---

## Cenários Positivos e Negativos

### Os 4 tipos de cenários que ele gera

#### ✅ Caminho Feliz (Positivo)
Tudo funciona como o esperado. O usuário faz tudo certo.
```gherkin
Cenário: Login com credenciais válidas
  Dado que o usuário está na tela de login
  Quando preenche e-mail e senha corretos
  Então é redirecionado para o dashboard
```

#### 🔀 Caminho Alternativo (Positivo variante)
Fluxos válidos mas fora do padrão principal.
```gherkin
Cenário: Login via Google SSO
  Dado que o usuário está na tela de login
  Quando clica em "Entrar com Google"
  Então é autenticado via OAuth e acessa o sistema
```

#### ❌ Tratamento de Erros (Negativo direto)
O usuário erra dados, o sistema deve responder corretamente.
```gherkin
Cenário: Login com senha incorreta
  Dado que o usuário está na tela de login
  Quando preenche a senha errada 3 vezes
  Então a conta deve ser bloqueada temporariamente
  E uma mensagem de aviso deve ser exibida na tela
```

#### 🔲 Validações de Borda (Negativo extremo)
Situações limite que normalmente quebram sistemas mal feitos.
```gherkin
Cenário: Campo e-mail com formato inválido
  Dado que o usuário está na tela de login
  Quando digita "usuariosemarroba.com" no campo e-mail
  Então o botão de entrar deve permanecer desabilitado
  E uma mensagem "E-mail inválido" deve aparecer
```

### Distribuição típica dos cenários

| Tipo | Quantidade |
|------|------------|
| ✅ Caminho Feliz | 2 a 3 |
| 🔀 Alternativo | 2 a 3 |
| ❌ Erros de Tela | 3 a 4 |
| 🔲 Borda | 3 a 5 |

---

## Validações de Campo que o Robô Cobre

| Categoria | O que valida |
|-----------|-------------|
| 🔴 Obrigatoriedade | Campo vazio → mensagem de erro + bloqueio de submit |
| 📏 Tamanho | Mínimo e máximo de caracteres |
| 📧 Formato | E-mail, CPF, CNPJ, telefone, data |
| 🎭 Máscara | Aplicação automática de máscara ao digitar |
| 🔢 Tipo de dado | Letras em campo numérico, números em campo texto |
| 🔗 Cross-field | Senha ≠ Confirmar senha, Data fim < Data início |
| 🔒 Segurança | XSS, SQL Injection, campo senha mascarado |
| 🎨 Feedback visual | Borda vermelha/verde, ícone de erro, tooltip |
| ♿ Acessibilidade | aria-describedby, anúncio em leitor de tela |

---

## O QA escolhe a Linguagem, Biblioteca e Tipo de Teste

### Plataforma
| Opção | Quando usar |
|-------|-------------|
| **Web** | Testes em navegador (Chrome, Firefox, Edge) |
| **Mobile Android** | App nativo ou híbrido Android |
| **Mobile iOS** | App nativo ou híbrido iPhone/iPad |

### Linguagem
| Linguagem | Perfil do time |
|-----------|---------------|
| **Java** | Times enterprise, squads com background Java/Spring |
| **Python** | Times de dados, automação leve, scripts rápidos |
| **Cypress/TypeScript** | Times frontend, squads React/Angular/Vue |
| **Ruby** | Times com legado Rails ou preferência BDD puro |

### Tipo de Teste
| Tipo | O que é | Quando pedir |
|------|---------|--------------|
| **BDD (Gherkin)** | Cenários em linguagem humana | Quando negócio precisa entender os testes |
| **E2E (End-to-End)** | Fluxo completo da tela | Validar jornada inteira do usuário |
| **Smoke** | Só os fluxos críticos | Validação rápida após deploy |
| **Regressão** | Todos os cenários históricos | Garantir que nada quebrou |

---

## Resumo em uma linha

> O QA Flash - UI é um robô que escreve, testa e versiona automações de UI para você —
> mas **nunca toma nenhuma decisão sem a sua aprovação**. Você está sempre no controle.

---

## Resumo das Paradas Obrigatórias

| Fase | O robô entrega | Você decide |
|------|---------------|------------|
| FASE 1 | 10-15 cenários Gherkin | Aprovar + escolher plataforma e linguagem |
| FASE 2 | Código completo com POM | Revisar código + autorizar execução |
| FASE 3 | Resultado dos testes (Verde/Vermelho) | Autorizar commit e push |
| FASE 4 | Branch criada e enviada | Tarefa concluída ✅ |

---

*Documento gerado em 19/10/2026 | QA Flash - UI | qa-flash-ui-front*

---

## 📍 Ponto de Retomada — Próximos Passos

### ✅ O que já está pronto

| Item | Status | Localização |
|------|--------|-------------|
| Steering file do agente | ✅ Feito | `.kiro/steering/qa-flash-ui.md` |
| Hook de sessão | ✅ Feito | `.kiro/hooks/qa-flash-ui-session-start.json` |
| Passo a passo documentado | ✅ Feito | `docs/passo-a-passo-do-robo.md` |
| Repositório GitHub | ✅ Feito | `https://github.com/NiltesteQA/qa-flash-ui-front` |

### ⏳ Próximo passo ao retornar

**Configurar o MCP do Jira** para o robô buscar histórias diretamente pelo número do ticket.

O que será necessário:
1. URL do Jira (ex: `https://seutime.atlassian.net`)
2. E-mail da conta Jira
3. Token de API (gerar em `https://id.atlassian.com/manage-profile/security/api-tokens`)

Após configurar, o fluxo será:
```
Você digita: "busca a história QA-123 e executa a FASE 1"
Robô busca no Jira automaticamente e gera os cenários BDD
```
