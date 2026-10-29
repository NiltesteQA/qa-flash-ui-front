package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * QA Flash - UI | Categoria Page - Mercado Livre
 * Page Object da pagina de uma categoria principal
 * Mapeia subcategorias e elementos de navegacao
 */
public class CategoriaPage extends BasePage {

    // ================================================================
    // LOCATORS - Mapeamento de elementos da categoria
    // ================================================================

    // Subcategorias dentro do menu de uma categoria
    private static final By LISTA_SUBCATEGORIAS = By.cssSelector("[data-testid='categories-modal'] a[href*='sub'], .categories__list a, .nav-menu-list a");

    // Subcategorias especificas por nome
    private static final By SUBCATEGORIA_POR_NOME(String nome) {
        return By.xpath("//a[contains(text(),'" + nome + "')]");
    }

    // Titulo da categoria (breadcrumb ou header da pagina)
    private static final By TITULO_CATEGORIA = By.cssSelector(
        "h1, .ui-search-breadcrumb__title, .andes-breadcrumb, " +
        ".ui-search-search-key, .ui-search-result__title, " +
        "[data-testid='category-title'], .category-header, " +
        ".ui-search-rescue__title, .poly-component__title"
    );

    // Lista de anuncios exibidos
    private static final By LISTA_ANUNCIOS = By.cssSelector(
        "[data-testid='results-container'] li, .ui-search-layout__item, " +
        ".andes-card, .poly-card, .ui-search-result, " +
        ".ui-search-layout li, ol.ui-search-layout li"
    );

    // Botao de proxima pagina
    private static final By BOTAO_PROXIMA_PAGINA = By.cssSelector("a[title='Proxima'], li.andes-pagination__button--next a, a[rel='next']");

    // Botao de pagina anterior
    private static final By BOTAO_PAGINA_ANTERIOR = By.cssSelector("a[title='Anterior'], li.andes-pagination__button--prev a, a[rel='prev']");

    // Numero da pagina atual (indicador de paginacao)
    private static final By INDICADOR_PAGINA_ATUAL = By.cssSelector(".andes-pagination__page-current, [data-testid='pagination-current']");

    // Rodape da pagina
    private static final By RODAPE = By.cssSelector("footer, [data-testid='footer']");

    // ================================================================
    // METODOS - Acoes da pagina de categoria
    // ================================================================

    /**
     * Retorna a lista de subcategorias disponiveis
     * @return lista de WebElements
     */
    public List<WebElement> obterSubcategorias() {
        return buscarElementos(LISTA_SUBCATEGORIAS);
    }

    /**
     * Clica em uma subcategoria especifica por nome
     * @param nomeSubcategoria nome da subcategoria
     */
    public void clicarSubcategoria(String nomeSubcategoria) {
        clicar(SUBCATEGORIA_POR_NOME(nomeSubcategoria));
    }

    /**
     * Verifica se a pagina da categoria carregou
     * @return true se carregou
     */
    public boolean paginaCategoriaCarregou() {
        // Valida se a pagina carregou verificando titulo OU anuncios OU breadcrumb
        boolean temTitulo = estaVisivel(TITULO_CATEGORIA);
        boolean temAnuncios = buscarElementos(LISTA_ANUNCIOS).size() > 0;
        boolean temUrlValida = driver.getCurrentUrl().contains("mercadolivre");
        return temTitulo || (temAnuncios && temUrlValida);
    }

    /**
     * Obtem o titulo da categoria atual
     * @return titulo
     */
    public String obterTituloCategoria() {
        return obterTexto(TITULO_CATEGORIA);
    }

    /**
     * Obtem a quantidade de anuncios exibidos na pagina
     * @return quantidade de anuncios
     */
    public int quantidadeAnuncios() {
        return buscarElementos(LISTA_ANUNCIOS).size();
    }

    /**
     * Rola a barra de rolagem para baixo
     */
    public void rolarParaBaixo() {
        scrollParaBaixo();
    }

    /**
     * Verifica se o botao de proxima pagina esta disponivel
     * @return true se disponivel
     */
    public boolean botaoProximaPaginaDisponivel() {
        try {
            WebElement btn = aguardarVisivel(BOTAO_PROXIMA_PAGINA);
            return btn.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Clica no botao de proxima pagina
     */
    public void clicarProximaPagina() {
        clicar(BOTAO_PROXIMA_PAGINA);
    }

    /**
     * Verifica se o botao de proxima pagina esta desabilitado
     * @return true se desabilitado
     */
    public boolean botaoProximaPaginaDesabilitado() {
        try {
            WebElement btn = aguardarVisivel(BOTAO_PROXIMA_PAGINA);
            String ariaDisabled = btn.getAttribute("aria-disabled");
            String classAttr = btn.getAttribute("class");
            return "true".equals(ariaDisabled) || (classAttr != null && classAttr.contains("disabled"));
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Obtem o numero da pagina atual
     * @return numero da pagina
     */
    public String obterPaginaAtual() {
        try {
            return obterTexto(INDICADOR_PAGINA_ATUAL);
        } catch (Exception e) {
            return "1";
        }
    }

    /**
     * Verifica se o rodape esta visivel
     * @return true se visivel
     */
    public boolean rodapeVisivel() {
        return estaVisivel(RODAPE);
    }

    /**
     * Tira screenshot de evidencia
     * @param nome nome do screenshot
     */
    public void capturarEvidencia(String nome) {
        tirarScreenshot(nome);
    }
}
