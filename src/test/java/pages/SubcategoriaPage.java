package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * QA Flash - UI | Subcategoria Page - Mercado Livre
 * Page Object da pagina de uma subcategoria
 * Mapeia anuncios, paginacao e validacoes de conteudo
 */
public class SubcategoriaPage extends BasePage {

    // ================================================================
    // LOCATORS - Mapeamento de elementos da subcategoria
    // ================================================================

    // Titulo da subcategoria
    private static final By TITULO_SUBCATEGORIA = By.cssSelector("h1, .ui-search-breadcrumb__title, [data-testid='subcategory-title']");

    // Lista de anuncios da subcategoria
    private static final By LISTA_ANUNCIOS = By.cssSelector("[data-testid='results-container'] li, .ui-search-layout__item, .andes-card");

    // Titulos dos anuncios (para verificar duplicidade entre paginas)
    private static final By TITULOS_ANUNCIOS = By.cssSelector(".ui-search-item__title, [data-testid='item-title'], .poly-component__title");

    // Botao de proxima pagina
    private static final By BOTAO_PROXIMA_PAGINA = By.cssSelector("a[title='Proxima'], li.andes-pagination__button--next a, a[rel='next']");

    // Indicador de pagina atual
    private static final By INDICADOR_PAGINA = By.cssSelector(".andes-pagination__page-current, [data-testid='pagination-current']");

    // Rodape da pagina
    private static final By RODAPE = By.cssSelector("footer, [data-testid='footer']");

    // Mensagem de erro / fallback
    private static final By MENSAGEM_ERRO = By.cssSelector(".ui-search-empty-state, [data-testid='error-message'], .error-page");

    // ================================================================
    // METODOS - Acoes da pagina de subcategoria
    // ================================================================

    /**
     * Verifica se a pagina da subcategoria carregou
     * @return true se carregou
     */
    public boolean subcategoriaCarregou() {
        return estaVisivel(TITULO_SUBCATEGORIA);
    }

    /**
     * Obtem o titulo da subcategoria atual
     * @return titulo da subcategoria
     */
    public String obterTituloSubcategoria() {
        return obterTexto(TITULO_SUBCATEGORIA);
    }

    /**
     * Rola a barra de rolagem para baixo
     */
    public void rolarParaBaixo() {
        scrollParaBaixo();
    }

    /**
     * Retorna a quantidade de anuncios na pagina atual
     * @return numero de anuncios
     */
    public int quantidadeAnuncios() {
        return buscarElementos(LISTA_ANUNCIOS).size();
    }

    /**
     * Obtem a lista de titulos dos anuncios da pagina atual
     * Usado para verificar se ha duplicidade entre paginas
     * @return lista de titulos
     */
    public List<String> obterTitulosAnuncios() {
        List<WebElement> elementos = buscarElementos(TITULOS_ANUNCIOS);
        List<String> titulos = new ArrayList<>();
        for (WebElement el : elementos) {
            titulos.add(el.getText());
        }
        return titulos;
    }

    /**
     * Verifica se o botao de proxima pagina esta disponivel
     * @return true se disponivel
     */
    public boolean proximaPaginaDisponivel() {
        try {
            WebElement btn = aguardarVisivel(BOTAO_PROXIMA_PAGINA);
            return btn.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica se o botao de proxima pagina esta desabilitado
     * @return true se desabilitado ou oculto
     */
    public boolean proximaPaginaDesabilitada() {
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
     * Clica no botao de proxima pagina
     */
    public void avancarPagina() {
        clicar(BOTAO_PROXIMA_PAGINA);
    }

    /**
     * Obtem o numero da pagina atual
     * @return numero da pagina como String
     */
    public String obterNumeroPaginaAtual() {
        try {
            return obterTexto(INDICADOR_PAGINA);
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
     * Verifica se a pagina exibiu mensagem de erro
     * @return true se houver erro
     */
    public boolean exibiuErro() {
        return estaVisivel(MENSAGEM_ERRO);
    }

    /**
     * Navega por N paginas e valida o carregamento de cada uma
     * @param quantidadePaginas numero minimo de paginas a navegar
     * @return true se todas as paginas carregaram corretamente
     */
    public boolean navegarPaginas(int quantidadePaginas) {
        for (int i = 1; i < quantidadePaginas; i++) {
            rolarParaBaixo();

            if (!proximaPaginaDisponivel()) {
                // Se nao ha mais paginas, valida se e a ultima
                System.out.println("[Paginacao] Pagina " + i + " - nao ha proxima pagina disponivel");
                return i >= quantidadePaginas;
            }

            // Captura titulos antes de avancar (para checar duplicidade)
            List<String> titulosAntes = obterTitulosAnuncios();

            avancarPagina();

            // Aguarda carregamento da nova pagina
            aguardarVisivel(LISTA_ANUNCIOS);

            // Captura titulos apos avancar
            List<String> titulosDepois = obterTitulosAnuncios();

            // Verifica se os anuncios sao diferentes (nao duplicados)
            if (titulosAntes.equals(titulosDepois)) {
                System.out.println("[Paginacao] AVISO: Anuncios iguais entre paginas " + i + " e " + (i + 1));
            }

            System.out.println("[Paginacao] Pagina " + (i + 1) + " carregada com " + titulosDepois.size() + " anuncios");
        }
        return true;
    }

    /**
     * Captura screenshot de evidencia
     * @param nome nome do arquivo
     */
    public void capturarEvidencia(String nome) {
        tirarScreenshot(nome);
    }
}
