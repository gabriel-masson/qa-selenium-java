package com.qa.automationexercise.pages;

import com.qa.automationexercise.config.ConfigManager;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Representa a página "ACCOUNT CREATED!" — a tela de confirmação exibida
 * logo após o preenchimento bem-sucedido do formulário em AccountInfoPage.
 * Seu único papel é confirmar a mensagem de sucesso e levar de volta à Home.
 */
public class AccountCreatedPage extends BasePage {

    private final By accountCreatedMessage = By.xpath("//b[contains(text(),'Account Created!')]");
    private final By continueButton = By.cssSelector("a[data-qa='continue-button']");

    public AccountCreatedPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAccountCreatedMessageDisplayed() {
        return isVisible(accountCreatedMessage);
    }

    public HomePage continueToHomePage() {
        click(continueButton);
        waitForRealNavigationAwayFromThisPage();
        return new HomePage(driver);
    }

    /**
     * O site exibe anúncios de terceiros (Google Ads "vignette") que podem
     * interceptar o evento de clique via JavaScript e desviar a navegação,
     * sem que o Selenium detecte nenhuma exceção (o clique "funciona" do
     * ponto de vista do navegador). Como salvaguarda, confirmamos que a URL
     * de fato mudou; se não mudou dentro de um tempo curto, navegamos
     * diretamente por código, ignorando a interferência externa.
     */
    private void waitForRealNavigationAwayFromThisPage() {
        try {
            wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("account_created")));
        } catch (TimeoutException e) {
            driver.navigate().to(ConfigManager.getInstance().getUiBaseUrl());
        }
    }
}