# language: pt
# QA Flash - UI | FASE 1: Cenários BDD
# História: Validação de Navegação por Categorias e Subcategorias no Mercado Livre
# URL base: https://www.mercadolivre.com.br
# Autor: Nilson Correa Souza - Analista de Teste - QA Engineer
# Data: 29/10/2026

Feature: Navegação por Categorias e Subcategorias do Mercado Livre
  Como um engenheiro de automação de testes / analista de QA
  Quero automatizar a navegação pelas categorias e subcategorias do menu principal
  Para garantir que as listagens de anúncios carregam corretamente
  E que a paginação funciona por pelo menos três páginas em cada subcategoria

  # ============================================================
  # CAMINHO FELIZ (POSITIVO) - 3 cenários
  # ============================================================

  # @smoke @caminho-feliz
  Cenário: 001 - Acesso à página inicial sem exigência de login
    Dado que o navegador foi aberto
    Quando eu acessar o site oficial do Mercado Livre "https://www.mercadolivre.com.br"
    Então a página inicial deve carregar completamente
    E não deve exigir nenhum tipo de autenticação ou login
    E o menu superior deve estar visível

  # @regressao @caminho-feliz
  Cenário: 002 - Exibição do menu flutuante ao passar o mouse sobre "Categorias"
    Dado que estou na página inicial do Mercado Livre
    Quando eu passar o mouse (hover) sobre a opção "Categorias" no menu superior
    Então o menu flutuante de categorias deve ser exibido corretamente
    E as categorias principais devem estar listadas e visíveis

  # @regressao @caminho-feliz
  Cenário: 003 - Navegação por subcategoria com paginação mínima de 3 páginas
    Dado que uma categoria selecionada possui subcategorias
    Quando o submenu de subcategorias for aberto
    E eu selecionar e clicar em uma subcategoria obrigatória
    Então a página da subcategoria deve carregar corretamente
    Quando eu rolar a barra de rolagem (scroll) para baixo
    Então a paginação deve estar visível e funcional
    E devo conseguir navegar por no mínimo três páginas de anúncios
    E os anúncios de cada página devem carregar corretamente

  # ============================================================
  # CAMINHO ALTERNATIVO (POSITIVO VARIANTE) - 3 cenários
  # ============================================================

  # @regressao @alternativo
  Cenário: 004 - Navegação direta por link de categoria na URL
    Dado que estou na página inicial do Mercado Livre
    Quando eu acessar diretamente a URL de uma categoria específica
    Então a página da categoria deve carregar corretamente
    E os anúncios da categoria devem ser exibidos

  # @alternativo
  Cenário: 005 - Uso da barra de busca como alternativa ao menu de categorias
    Dado que estou na página inicial do Mercado Livre
    Quando eu digitar o nome de uma categoria na barra de busca
    E pressionar Enter
    Então os resultados devem corresponder à categoria pesquisada
    E a listagem de anúncios deve carregar corretamente

  # @alternativo
  Cenário: 006 - Navegação por breadcrumb ao voltar de uma subcategoria
    Dado que estou na página de uma subcategoria específica
    Quando eu clicar no breadcrumb da categoria pai
    Então devo ser redirecionado para a página da categoria principal
    E a listagem de subcategorias deve estar acessível

  # ============================================================
  # VALIDAÇÕES DE BORDA (NEGATIVO EXTREMO) - 4 cenários
  # ============================================================

  # @borda
  Cenário: 007 - Acesso à página inicial em janela anônima (incognito)
    Dado que o navegador foi aberto em modo anônimo
    Quando eu acessar o site oficial do Mercado Livre "https://www.mercadolivre.com.br"
    Então a página inicial deve carregar completamente sem exigir login
    E o menu de categorias deve funcionar normalmente

  # @borda
  Cenário: 008 - Paginação além da última página disponível
    Dado que estou navegando na páginação de uma subcategoria
    Quando eu chegar à última página de anúncios disponível
    Então o botão de "Próxima página" deve ser desabilitado ou oculto
    E o sistema não deve tentar carregar uma página inexistente

  # @borda
  Cenário: 009 - Carregamento de subcategoria com poucos anúncios (menos de 3 páginas)
    Dado que estou em uma subcategoria com poucos anúncios cadastrados
    Quando a listagem carregar
    Então o sistema deve exibir os anúncios disponíveis
    E se houver menos de três páginas, a paginação deve indicar o fim corretamente
    E o teste não deve falhar por ausência de páginas adicionais

  # @borda
  Cenário: 010 - Scroll até o rodapé da página de subcategoria
    Dado que estou na página de uma subcategoria
    Quando eu rolar a barra de rolagem até o final da página
    Então o rodapé do Mercado Livre deve ser exibido
    E a paginação deve permanecer acessível

  # ============================================================
  # TRATAMENTO DE ERROS DE TELA (NEGATIVO DIRETO) - 4 cenários
  # ============================================================

  # @erro @tratamento
  Cenário: 011 - Menu flutuante não exibido ao passar o mouse rapidamente
    Dado que estou na página inicial do Mercado Livre
    Quando eu passar o mouse sobre "Categorias" e remover rapidamente
    Então o menu flutuante pode recolher
    E ao passar o mouse novamente, o menu deve reexibir corretamente

  # @erro @tratamento
  Cenário: 012 - Tentativa de navegação em subcategoria com erro de carregamento
    Dado que estou na página de uma categoria principal
    Quando eu clicar em uma subcategoria e a página falhar ao carregar
    Então o sistema deve exibir uma mensagem de erro ou tela de fallback
    E o teste deve registrar a falha com screenshot automático

  # @erro @tratamento
  Cenário: 013 - Paginação com botão "Próxima" clicado antes do carregamento completo
    Dado que estou na primeira página de anúncios de uma subcategoria
    Quando eu clicar em "Próxima página" antes da página carregar completamente
    Então o sistema deve aguardar o carregamento ou bloquear o clique duplicado
    E não deve exibir conteúdo quebrado ou duplicado

  # @erro @tratamento
  Cenário: 014 - Verificação de anúncios duplicados entre páginas consecutivas
    Dado que estou navegando entre páginas de uma subcategoria
    Quando eu avançar da página 1 para a página 2
    Então os anúncios da página 2 não devem ser idênticos aos da página 1
    E cada página deve exibir uma listagem distinta de produtos

  # ============================================================
  # CENÁRIO ADICIONAL - INTEGRIDADE COMPLETA - 1 cenário
  # ============================================================

  # @regressao @integridade
  Cenário: 015 - Validação completa de todas as categorias e subcategorias sequencialmente
    Dado que estou na página inicial do Mercado Livre
    Quando eu passar o mouse sobre "Categorias"
    E iterar e clicar em cada categoria principal listada
    E para cada categoria, abrir e clicar em cada subcategoria obrigatória
    E rolar a barra de rolagem para baixo em cada subcategoria
    E navegar por no mínimo três páginas de anúncios em cada subcategoria
    Então todas as categorias devem ter sido visitadas com sucesso
    E todas as subcategorias devem ter carregado corretamente
    E a paginação de três páginas deve ter sido validada em cada subcategoria
    E um relatório de evidências com screenshots deve ser gerado ao final
