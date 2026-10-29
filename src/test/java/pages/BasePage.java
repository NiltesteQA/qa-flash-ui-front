package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import support.DriverManager;

import java.time.Duration;
import java.util.List;

/**
 * QA Flash - UI | Base Page
 * Classe pai de todos os Page Objects
 * Contem metodos comuns reutilizaveis (DRY)
 * Usa esperas explicitas - PROIBIDO Thread.sleep (FIRST)
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;

    // Tempo padrao de espera explicita (em segundos)
    private static final int TIMEOUT_PADRAO = 15;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_PADRAO));
        this.actions = new Actions(driver);
    }

    /**
     * Navega para uma URL
     * @param url endereco de destino
     */
    public void navegarPara(String url) {
        driver.get(url);
    }

    /**
     * Aguarda elemento estar visivel (espera explicita)
     * @param by seletor do elemento
     * @return WebElement visivel
     */
    public WebElement aguardarVisivel(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    /**
     * Aguarda elemento estar clicavel (espera explicita)
     * @param by seletor do elemento
     * @return WebElement clicavel
     */
    public WebElement aguardarClicavel(By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    /**
     * Aguarda elemento estar presente no DOM
     * @param by seletor do elemento
     * @return WebElement presente
     */
    public WebElement aguardarPresente(By by) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    /**
     * Clica em um elemento apos aguardar estar clicavel
     * @param by seletor do elemento
     */
    public void clicar(By by) {
        aguardarClicavel(by).click();
    }

    /**
     * Digita texto em um campo apos aguardar estar visivel
     * @param by seletor do campo
     * @param texto texto a ser digitado
     */
    public void digitar(By by, String texto) {
        WebElement elemento = aguardarVisivel(by);
        elemento.clear();
        elemento.sendKeys(texto);
    }

    /**
     * Passa o mouse (hover) sobre um elemento
     * @param by seletor do elemento
     */
    public void hover(By by) {
        WebElement elemento = aguardarVisivel(by);
        actions.moveToElement(elemento).perform();
    }

    /**
     * Rola a barra de rolagem para baixo
     */
    public void scrollParaBaixo() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    /**
     * Rola a barra de rolagem ate um elemento especifico
     * @param by seletor do elemento
     */
    public void scrollAteElemento(By by) {
        WebElement elemento = aguardarPresente(by);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", elemento);
    }

    /**
     * Obtem o texto de um elemento
     * @param by seletor do elemento
     * @return texto do elemento
     */
    public String obterTexto(By by) {
        return aguardarVisivel(by).getText();
    }

    /**
     * Verifica se o elemento esta visivel
     * @param by seletor do elemento
     * @return true se visivel, false caso contrario
     */
    public boolean estaVisivel(By by) {
        try {
            aguardarVisivel(by);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Verifica se o elemento existe na pagina
     * @param by seletor do elemento
     * @return lista de elementos encontrados
     */
    public List<WebElement> buscarElementos(By by) {
        return driver.findElements(by);
    }

    /**
     * Aguarda um tempo determinado (espera explicita customizada)
     * @param segundos tempo de espera
     */
    public void aguardarSegundos(int segundos) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(segundos));
        customWait.until(driver -> false);
    }

    /**
     * Tira screenshot (evidencia)
     * @param nomeArquivo nome do arquivo de screenshot
     */
    public void tirarScreenshot(String nomeArquivo) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            // Salva evidencia - implementacao pode ser extendida para relatorio
            System.out.println("[Evidencia] Screenshot capturado: " + nomeArquivo);
        } catch (Exception e) {
            System.err.println("[Erro] Falha ao capturar screenshot: " + e.getMessage());
        }
    }

    /**
     * Obtem a URL atual da pagina
     * @return URL atual
     */
    public String obterUrlAtual() {
        return driver.getCurrentUrl();
    }

    /**
     * Obtem o titulo da pagina atual
     * @return titulo da pagina
     */
    public String obterTituloPagina() {
        return driver.getTitle();
    }
}
