package com.qa.automationexercise.pages;

import com.qa.automationexercise.config.ConfigManager;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Classe base para todos os Page Objects.
 *
 * Centraliza as estratégias de espera (waits explícitos). Nenhuma classe filha
 * deve usar Thread.sleep() ou interagir com WebElement diretamente sem passar
 * por aqui — este é o ponto único que vamos reforçar na Etapa 7 para reduzir
 * flaky tests.
 *
 * Importante: esta classe NÃO faz asserções. Ela só executa ações e devolve
 * estado. Quem decide se o estado está correto é o teste, não o Page Object.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        int explicitTimeout = ConfigManager.getInstance().getExplicitTimeoutSeconds();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitTimeout));
    }

    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        WebElement element = waitForClickable(locator);
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            // Alguns elementos podem ser encobertos por overlays de terceiros
            // (ex: anúncios "vignette" do Google Ads no automationexercise.com),
            // que interceptam o clique nativo do navegador antes que ele
            // alcance o elemento certo. Como fallback, disparamos o clique
            // via JavaScript diretamente no elemento — isso não depende do
            // "hit-testing" visual do navegador, então ignora o que estiver
            // sobreposto na tela.
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    protected void type(By locator, String text) {
        WebElement element = waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitForVisible(locator).getText();
    }

    protected boolean isVisible(By locator) {
        try {
            return waitForVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}