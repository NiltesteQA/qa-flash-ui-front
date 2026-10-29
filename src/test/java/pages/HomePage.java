package pages;

import org.openqa.selenium.By;

/**
 * QA Flash - UI | Home Page - Mercado Livre
 * Page Object da pagina inicial
 * Mapeia elementos do menu superior e categorias
 */
public class HomePage extends BasePage {

    // ================================================================
    // LOCATORS - Mapeamento de elementos da pagina inicial
    // Prioriza seletores estaveis (data-testid, aria-label, IDs)
    // ================================================================

    // Menu "Categorias" no topo - usa seletor por texto e aria-label
    private static final By MENU_CATEGORIAS = By.xpath("//a[contains(text(),'Categorias') or @aria-label='Categorias']");
    private static final By MENU_CATEGORIAS_ALT = By.cssSelector("a[href*='categorias']");

    // Container do menu flutuante de categorias
    private static final By MENU_FLUTUANTE = By.cssSelector("[data-testid='categories-modal'] aside, .categories__container, .nav-menu");

    // Lista de categorias principais no menu flutuante
    private static final By LISTA_CATEGORIAS = By.cssSelector("[data-testid='categories-modal'] a, .categories__list a, .nav-menu-list a");

    // Barra de busca
    private static final By CAMPO_BUSCA = By.cssSelector("input[name='as_word'], input[placeholder*='Buscar'], #cb1-edit");
    private static final By BOTAO_BUSCAR = By.cssSelector("button[type='submit'], button[aria-label*='Buscar']");

    // Breadcrumb (navegacao estrutural)
    private static final By BREADCRUMB = By.cssSelector(".breadcrumb, [data-testid='breadcrumb']");
    private static final By BREADCRUMB_CATEGORIA_PAI = By.cssSelector(".breadcrumb a:first-child, [data-testid='breadcrumb'] a:first-child");

    // ================================================================
    // METODOS - Acoes da pagina inicial
    // ================================================================

    /**
     * Acessa a pagina inicial do Mercado Livre
     */
    public void acessarPaginaInicial(String url) {
        navegarPara(url);
        aguardarVisivel(MENU_CATEGORIAS);
    }

    /**
     * Passa o mouse (hover) sobre o menu "Categorias"
     */
    public void hoverCategorias() {
        try {
            hover(MENU_CATEGORIAS);
        } catch (Exception e) {
            hover(MENU_CATEGORIAS_ALT);
        }
    }

    /**
     * Verifica se o menu flutuante de categorias esta visivel
     * @return true se visivel
     */
    public boolean menuFlutuanteVisivel() {
        return estaVisivel(MENU_FLUTUANTE);
    }

    /**
     * Obtem a lista de categorias principais do menu
     * @return lista de WebElements das categorias
     */
    public int quantidadeCategorias() {
        return buscarElementos(LISTA_CATEGORIAS).size();
    }

    /**
     * Clica em uma categoria especifica pelo nome
     * @param nomeCategoria nome da categoria (ex: "Tecnologia")
     */
    public void clicarCategoriaPorNome(String nomeCategoria) {
        By categoria = By.xpath("//a[contains(text(),'" + nomeCategoria + "')]");
        clicar(categoria);
    }

    /**
     * Digita na barra de busca e pressiona Enter
     * @param termo termo de busca
     */
    public void buscarTermo(String termo) {
        digitar(CAMPO_BUSCA, termo);
        aguardarVisivel(CAMPO_BUSCA).sendKeys(org.openqa.selenium.Keys.ENTER);
    }

    /**
     * Clica no breadcrumb para voltar a categoria pai
     */
    public void clicarBreadcrumbCategoriaPai() {
        clicar(BREADCRUMB_CATEGORIA_PAI);
    }

    /**
     * Verifica se a barra de busca esta visivel
     * @return true se visivel
     */
    public boolean barraBuscaVisivel() {
        return estaVisivel(CAMPO_BUSCA);
    }

    /**
     * Verifica se a pagina inicial carregou sem exigir login
     * @return true se carregou sem tela de login
     */
    public boolean paginaInicialCarregou() {
        return estaVisivel(MENU_CATEGORIAS) && !obterUrlAtual().contains("login");
    }
}
