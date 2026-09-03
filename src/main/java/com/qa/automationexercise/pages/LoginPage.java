package com.qa.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Representa a página "/login" do Automation Exercise, que contém DOIS
 * formulários na mesma tela: "Login to your account" e "New User Signup!".
 *
 * Optamos por um único Page Object para essa URL (em vez de duas classes),
 * já que ambos os formulários compartilham o mesmo ciclo de vida de página.
 * A etapa de conclusão do cadastro (após o clique em "Signup") acontece em
 * uma página própria — o Page Object dela (AccountInfoPage) será criado na
 * Etapa 5, junto com a implementação do teste E2E de cadastro completo.
 */
public class LoginPage extends BasePage {

    // --- Formulário de login (RF-UI-002 / RF-UI-003) ---
    private final By loginEmailInput = By.cssSelector("input[data-qa='login-email']");
    private final By loginPasswordInput = By.cssSelector("input[data-qa='login-password']");
    private final By loginButton = By.cssSelector("button[data-qa='login-button']");
    private final By loginErrorMessage =
            By.xpath("//p[contains(text(),'Your email or password is incorrect!')]");

    // --- Formulário de cadastro (RF-UI-001 - implementação completa na Etapa 5) ---
    private final By signupNameInput = By.cssSelector("input[data-qa='signup-name']");
    private final By signupEmailInput = By.cssSelector("input[data-qa='signup-email']");
    private final By signupButton = By.cssSelector("button[data-qa='signup-button']");
    private final By signupErrorMessage =
            By.xpath("//p[contains(text(),'Email Address already exist!')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /** Login com sucesso: navega para a Home autenticada. */
    public HomePage login(String email, String password) {
        type(loginEmailInput, email);
        type(loginPasswordInput, password);
        click(loginButton);
        return new HomePage(driver);
    }

    /** Login esperando falha: permanece na própria página para validar a mensagem de erro. */
    public LoginPage loginExpectingError(String email, String password) {
        type(loginEmailInput, email);
        type(loginPasswordInput, password);
        click(loginButton);
        return this;
    }

    public boolean isLoginErrorDisplayed() {
        return isVisible(loginErrorMessage);
    }

    public String getLoginErrorMessage() {
        return getText(loginErrorMessage);
    }

    public boolean isSignupErrorDisplayed() {
        return isVisible(signupErrorMessage);
    }

    public boolean isLoaded() {
        return isVisible(loginEmailInput);
    }
}
