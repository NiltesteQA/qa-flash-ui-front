package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * QA Flash - UI | Test Runner
 * Configura e executa os testes BDD com Cucumber
 * Gera relatorio HTML automaticamente apos a execucao
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",           // Caminho dos arquivos .feature
    glue = "steps",                                       // Pacote dos step definitions
    plugin = {
        "pretty",                                         // Saida no console
        "html:target/cucumber-report/report.html",        // Relatorio HTML
        "json:target/cucumber-report/report.json",        // Relatorio JSON (para CI/CD)
        "timeline:target/cucumber-report/timeline"        // Timeline visual
    },
    monochrome = true,                                    // Saida colorida no console
    publish = false                                       // Nao publica online (privacidade)
)
public class RunCucumberTest {
    // Classe de configuracao - o Cucumber executa os testes automaticamente
}
