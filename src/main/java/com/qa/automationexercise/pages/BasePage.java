package com.qa.automationexercise.pages;

import com.qa.automationexercise.config.ConfigManager;
import org.openqa.selenium.By;
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
        waitForClickable(locator).click();
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
