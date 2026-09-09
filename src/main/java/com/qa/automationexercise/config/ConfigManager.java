package com.qa.automationexercise.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Carrega a configuração do ambiente ativo (dev/staging/prod).
 * O ambiente é definido pela propriedade de sistema "env", injetada
 * pelo Maven Profile ativo (veja pom.xml -> <profiles>).
 *
 * Uso: mvn test -Pdev | -Pstaging | -Pprod
 * Fallback: se nenhum profile for informado, assume "dev".
 */
public final class ConfigManager {

    private static ConfigManager instance;
    private final Properties properties = new Properties();

    private ConfigManager() {
        String env = System.getProperty("env", "dev");
        String fileName = "config/" + env + ".properties";

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new IllegalStateException(
                        "Arquivo de configuração não encontrado no classpath: " + fileName);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao carregar configuração do ambiente: " + fileName, e);
        }
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    public String getUiBaseUrl() {
        return properties.getProperty("base.ui.url");
    }

    public String getApiBaseUrl() {
        return properties.getProperty("base.api.url");
    }

    public String getBrowser() {
        return properties.getProperty("browser", "chrome");
    }

    public boolean isHeadless() {
        // Permite forçar headless via linha de comando (ex: -Dheadless=true),
        // sem precisar alterar o .properties do ambiente. Essencial para CI:
        // runners do GitHub Actions não têm display, então mesmo o profile
        // "dev" (headless=false, pensado para debug local) precisa rodar
        // headless quando executado lá. Se a system property não for
        // informada, cai no valor padrão do .properties do ambiente.
        String override = System.getProperty("headless");
        if (override != null) {
            return Boolean.parseBoolean(override);
        }
        return Boolean.parseBoolean(properties.getProperty("headless", "false"));
    }

    public int getImplicitTimeoutSeconds() {
        return Integer.parseInt(properties.getProperty("timeout.implicit.seconds", "5"));
    }

    public int getExplicitTimeoutSeconds() {
        return Integer.parseInt(properties.getProperty("timeout.explicit.seconds", "10"));
    }
}