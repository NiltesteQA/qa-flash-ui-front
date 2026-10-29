package support;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * QA Flash - UI | Leitor de Configuracoes
 * Le URLs e configuracoes de variaveis de ambiente (.env)
 * Segue compliance OWASP/LGPD: zero credenciais em texto puro
 */
public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try {
            // Tenta carregar .env (nao sobe para o GitHub - esta no .gitignore)
            FileInputStream fis = new FileInputStream(".env");
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            // Se .env nao existir, usa valores padrao (apenas para testes)
            System.out.println("[ConfigReader] Arquivo .env nao encontrado. Usando valores padrao.");
        }
    }

    /**
     * Retorna o valor de uma propriedade do .env
     * @param chave nome da propriedade
     * @return valor da propriedade
     */
    public static String getProp(String chave) {
        // Prioriza variavel de ambiente do sistema
        String envValue = System.getenv(chave.replace(".", "_").toUpperCase());
        if (envValue != null && !envValue.isEmpty()) {
            return envValue;
        }
        // Se nao encontrar, busca no .env
        return properties.getProperty(chave, "");
    }

    /**
     * Retorna a URL base do Mercado Livre
     * @return URL base
     */
    public static String getUrlBase() {
        String url = getProp("url.mercadolivre");
        if (url == null || url.isEmpty()) {
            url = "https://www.mercadolivre.com.br";
        }
        return url;
    }
}
