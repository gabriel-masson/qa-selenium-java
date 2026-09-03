package com.qa.automationexercise.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Responsável por criar, entregar e encerrar instâncias do WebDriver.
 *
 * Usa ThreadLocal para isolar o driver por thread de execução — pré-requisito
 * para rodar testes em paralelo com segurança (JUnit 5 parallel execution,
 * abordado na Etapa 7 junto com o tratamento de flaky tests).
 */
public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER_THREAD = new ThreadLocal<>();

    private DriverManager() {
    }

    public static WebDriver getDriver() {
        if (DRIVER_THREAD.get() == null) {
            initDriver();
        }
        return DRIVER_THREAD.get();
    }

    private static void initDriver() {
        ConfigManager config = ConfigManager.getInstance();
        String browser = config.getBrowser().toLowerCase();
        WebDriver driver;

        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (config.isHeadless()) {
                    firefoxOptions.addArguments("-headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (config.isHeadless()) {
                    // "--headless=new" usa o motor headless mais recente do Chrome,
                    // com renderização mais fiel ao modo com interface (menos flaky).
                    chromeOptions.addArguments("--headless=new");
                }
                chromeOptions.addArguments("--window-size=1920,1080");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitTimeoutSeconds()));
        DRIVER_THREAD.set(driver);
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER_THREAD.get();
        if (driver != null) {
            driver.quit();
            DRIVER_THREAD.remove();
        }
    }
}
