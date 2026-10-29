package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import support.DriverManager;

/**
 * QA Flash - UI | Hooks de Teste
 * Executado antes e depois de cada cenario
 * Garante independencia total entre testes (principio FIRST)
 */
public class Hooks {

    /**
     * Executado antes de cada cenario
     * Inicializa o navegador e configura o ambiente
     */
    @Before
    public void antesDoCenario(Scenario scenario) {
        System.out.println("[QA Flash - UI] Iniciando cenario: " + scenario.getName());
        DriverManager.iniciarDriver();
    }

    /**
     * Executado apos cada cenario
     * Tira screenshot em caso de falha e encerra o navegador
     */
    @After
    public void depoisDoCenario(Scenario scenario) {
        // Captura screenshot automatico se o cenario falhou
        if (scenario.isFailed()) {
            System.out.println("[QA Flash - UI] CENARIO FALHOU: " + scenario.getName());
            try {
                byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "evidencia-falha");
            } catch (Exception e) {
                System.err.println("[Erro] Falha ao capturar screenshot: " + e.getMessage());
            }
        } else {
            System.out.println("[QA Flash - UI] Cenario concluido com sucesso: " + scenario.getName());
        }

        // Encerra o navegador - garante independencia entre testes
        DriverManager.encerrarDriver();
    }
}
