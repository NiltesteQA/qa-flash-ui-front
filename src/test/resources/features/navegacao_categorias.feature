# language: pt
Funcionalidade: Navegacao por Categorias e Subcategorias do Mercado Livre
  Como um engenheiro de automacao de testes / analista de QA
  Quero automatizar a navegacao pelas categorias e subcategorias do menu principal
  Para garantir que as listagens de anuncios carregam corretamente
  E que a paginacao funciona por pelo menos tres paginas em cada subcategoria

  # CAMINHO FELIZ (POSITIVO) - 3 cenarios

  @smoke
  Cenario: 001 - Acesso a pagina inicial sem exigencia de login
    Dado que o navegador foi aberto
    Quando eu acessar o site oficial do Mercado Livre "https://www.mercadolivre.com.br"
    Entao a pagina inicial deve carregar completamente
    E nao deve exigir nenhum tipo de autenticacao ou login
    E o menu superior deve estar visivel

  @smoke
  Cenario: 002 - Exibicao do menu flutuante ao passar o mouse sobre Categorias
    Dado que estou na pagina inicial do Mercado Livre
    Quando eu passar o mouse (hover) sobre a opcao "Categorias" no menu superior
    Entao o menu flutuante de categorias deve ser exibido corretamente
    E as categorias principais devem estar listadas e visiveis

  Cenario: 003 - Navegacao por subcategoria com paginacao minima de 3 paginas
    Dado que uma categoria selecionada possui subcategorias
    Quando o submenu de subcategorias for aberto
    E eu selecionar e clicar em uma subcategoria obrigatoria
    Entao a pagina da subcategoria deve carregar corretamente
    Quando eu rolar a barra de rolagem (scroll) para baixo
    Entao a paginacao deve estar visivel e funcional
    E devo conseguir navegar por no minimo tres paginas de anuncios
    E os anuncios de cada pagina devem carregar corretamente

  # CAMINHO ALTERNATIVO (POSITIVO VARIANTE) - 3 cenarios

  @smoke
  Cenario: 004 - Navegacao direta por link de categoria na URL
    Dado que estou na pagina inicial do Mercado Livre
    Quando eu acessar diretamente a URL de uma categoria especifica
    Entao a pagina da categoria deve carregar corretamente
    E os anuncios da categoria devem ser exibidos

  Cenario: 005 - Uso da barra de busca como alternativa ao menu de categorias
    Dado que estou na pagina inicial do Mercado Livre
    Quando eu digitar o nome de uma categoria na barra de busca
    E pressionar Enter
    Entao os resultados devem corresponder a categoria pesquisada
    E a listagem de anuncios deve carregar corretamente

  Cenario: 006 - Navegacao por breadcrumb ao voltar de uma subcategoria
    Dado que estou na pagina de uma subcategoria especifica
    Quando eu clicar no breadcrumb da categoria pai
    Entao devo ser redirecionado para a pagina da categoria principal
    E a listagem de subcategorias deve estar acessivel

  # VALIDACOES DE BORDA (NEGATIVO EXTREMO) - 4 cenarios

  Cenario: 007 - Acesso a pagina inicial em janela anonima (incognito)
    Dado que o navegador foi aberto em modo anonimo
    Quando eu acessar o site oficial do Mercado Livre "https://www.mercadolivre.com.br"
    Entao a pagina inicial deve carregar completamente sem exigir login
    E o menu de categorias deve funcionar normalmente

  Cenario: 008 - Paginacao alem da ultima pagina disponivel
    Dado que estou navegando na paginacao de uma subcategoria
    Quando eu chegar a ultima pagina de anuncios disponivel
    Entao o botao de "Proxima pagina" deve ser desabilitado ou oculto
    E o sistema nao deve tentar carregar uma pagina inexistente

  Cenario: 009 - Carregamento de subcategoria com poucos anuncios (menos de 3 paginas)
    Dado que estou em uma subcategoria com poucos anuncios cadastrados
    Quando a listagem carregar
    Entao o sistema deve exibir os anuncios disponiveis
    E se houver menos de tres paginas, a paginacao deve indicar o fim corretamente
    E o teste nao deve falhar por ausencia de paginas adicionais

  Cenario: 010 - Scroll ate o rodape da pagina de subcategoria
    Dado que estou na pagina de uma subcategoria
    Quando eu rolar a barra de rolagem ate o final da pagina
    Entao o rodape do Mercado Livre deve ser exibido
    E a paginacao deve permanecer acessivel

  # TRATAMENTO DE ERROS DE TELA (NEGATIVO DIRETO) - 4 cenarios

  Cenario: 011 - Menu flutuante nao exibido ao passar o mouse rapidamente
    Dado que estou na pagina inicial do Mercado Livre
    Quando eu passar o mouse sobre "Categorias" e remover rapidamente
    Entao o menu flutuante pode recolher
    E ao passar o mouse novamente, o menu deve reexibir corretamente

  Cenario: 012 - Tentativa de navegacao em subcategoria com erro de carregamento
    Dado que estou na pagina de uma categoria principal
    Quando eu clicar em uma subcategoria e a pagina falhar ao carregar
    Entao o sistema deve exibir uma mensagem de erro ou tela de fallback
    E o teste deve registrar a falha com screenshot automatico

  Cenario: 013 - Paginacao com botao Proxima clicado antes do carregamento completo
    Dado que estou na primeira pagina de anuncios de uma subcategoria
    Quando eu clicar em "Proxima pagina" antes da pagina carregar completamente
    Entao o sistema deve aguardar o carregamento ou bloquear o clique duplicado
    E nao deve exibir conteudo quebrado ou duplicado

  Cenario: 014 - Verificacao de anuncios duplicados entre paginas consecutivas
    Dado que estou navegando entre paginas de uma subcategoria
    Quando eu avancar da pagina 1 para a pagina 2
    Entao os anuncios da pagina 2 nao devem ser identicos aos da pagina 1
    E cada pagina deve exibir uma listagem distinta de produtos

  # CENARIO ADICIONAL - INTEGRIDADE COMPLETA - 1 cenario

  Cenario: 015 - Validacao completa de todas as categorias e subcategorias sequencialmente
    Dado que estou na pagina inicial do Mercado Livre
    Quando eu passar o mouse sobre "Categorias"
    E iterar e clicar em cada categoria principal listada
    E para cada categoria, abrir e clicar em cada subcategoria obrigatoria
    E rolar a barra de rolagem para baixo em cada subcategoria
    E navegar por no minimo tres paginas de anuncios em cada subcategoria
    Entao todas as categorias devem ter sido visitadas com sucesso
    E todas as subcategorias devem ter carregado corretamente
    E a paginacao de tres paginas deve ter sido validada em cada subcategoria
    E um relatorio de evidencias com screenshots deve ser gerado ao final
