package support;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * QA Flash - UI | Gerenciador de WebDriver (padrao Singleton)
 * Responsavel por inicializar e encerrar o navegador
 * Usa WebDriverManager para download automatico do driver
 */
public class DriverManager {

    private static WebDriver driver;

    /**
     * Inicializa o WebDriver (Chrome)
     * Configura opcoes de navegacao segura
     */
    public static void iniciarDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // Modo anonimo (usado pelo Cenario 007)
        if (Boolean.parseBoolean(ConfigReader.getProp("modo.anonimo"))) {
            options.addArguments("--incognito");
        }

        driver = new ChromeDriver(options);
    }

    /**
     * Retorna a instancia ativa do WebDriver
     * @return WebDriver
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            iniciarDriver();
        }
        return driver;
    }

    /**
     * Encerra o WebDriver e libera recursos
     */
    public static void encerrarDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
