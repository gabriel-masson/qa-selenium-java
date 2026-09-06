package com.qa.automationexercise.tests.e2e;

import com.qa.automationexercise.api.UserApiClient;
import com.qa.automationexercise.base.BaseUiTest;
import com.qa.automationexercise.models.AccountInfo;
import com.qa.automationexercise.pages.AccountCreatedPage;
import com.qa.automationexercise.pages.AccountInfoPage;
import com.qa.automationexercise.pages.HomePage;
import com.qa.automationexercise.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("E2E - Cadastro completo de usuário")
class SignupE2ETest extends BaseUiTest {

    private static final String PASSWORD = "Senha@123";
    private static final String NAME = "QA Portfolio";

    private final UserApiClient userApiClient = new UserApiClient();
    private String createdAccountEmail;

    @AfterEach
    void cleanUpCreatedAccount() {
        if (createdAccountEmail != null) {
            userApiClient.deleteAccount(createdAccountEmail, PASSWORD);
            createdAccountEmail = null;
        }
    }

    @Test
    @DisplayName("RF-UI-001: usuário deve conseguir se cadastrar via UI, do início ao fim")
    void shouldSignUpSuccessfullyThroughUi() {
        String uniqueEmail = "qa.portfolio." + System.currentTimeMillis() + "@teste.com";
        createdAccountEmail = uniqueEmail;

        LoginPage loginPage = homePage.goToLoginPage();
        AccountInfoPage accountInfoPage = loginPage.startSignup(NAME, uniqueEmail);

        AccountInfo accountInfo = AccountInfo.builder()
                .password(PASSWORD)
                .build();

        AccountCreatedPage accountCreatedPage = accountInfoPage.fillForm(accountInfo);

        // DEBUG TEMPORÁRIO - remover depois de diagnosticar a falha
        // System.out.println("DEBUG - URL atual: " + accountCreatedPage.getCurrentUrl());
        // System.out.println("DEBUG - Título atual: " + accountCreatedPage.getPageTitle());

        assertTrue(accountCreatedPage.isAccountCreatedMessageDisplayed(),
                "A mensagem de conta criada deveria aparecer");



        HomePage loggedHomePage = accountCreatedPage.continueToHomePage();

        // DEBUG TEMPORÁRIO - estado da página logo após clicar em "Continue"
        // System.out.println("DEBUG - URL pós-continue: " + loggedHomePage.getCurrentUrl());
        // System.out.println("DEBUG - Título pós-continue: " + loggedHomePage.getPageTitle());
        // boolean signupLoginAindaVisivel =
        //         !driver.findElements(org.openqa.selenium.By.linkText("Signup / Login")).isEmpty();
        // System.out.println("DEBUG - Link 'Signup / Login' ainda visível (ou seja, NÃO logado)? " + signupLoginAindaVisivel);

        // java.util.List<org.openqa.selenium.WebElement> loggedInElements =
        //         driver.findElements(org.openqa.selenium.By.xpath("//a[contains(.,'Logged in')]"));
        // loggedInElements.forEach(el ->
        //         System.out.println("DEBUG - texto encontrado: '" + el.getText() + "'"));

        assertTrue(loggedHomePage.isUserLoggedIn(NAME),
                "O usuário deveria estar logado automaticamente após o cadastro");
    }
}