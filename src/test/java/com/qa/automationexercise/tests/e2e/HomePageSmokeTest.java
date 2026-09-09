package com.qa.automationexercise.tests.e2e;

import com.qa.automationexercise.base.BaseUiTest;
import com.qa.automationexercise.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Smoke test do framework: valida que ConfigManager, DriverManager e os
 * primeiros Page Objects estão corretamente integrados.
 * Não substitui os testes E2E completos, que virão na Etapa 5.
 */
@Tag("smoke")
@Feature("Navegação e Smoke Test")
@DisplayName("Smoke test - Home e navegação para Login")
class HomePageSmokeTest extends BaseUiTest {

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verificação mais básica possível do sistema: a página inicial carrega. "
            + "Se este teste falhar, o site inteiro está inacessível para qualquer usuário.")
    @DisplayName("Home page deve carregar corretamente")
    void homePageShouldLoad() {
        assertTrue(homePage.isLoaded(), "A home page não carregou como esperado");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Confirma que a navegação básica entre Home e a página de Login/Cadastro "
            + "funciona - pré-requisito para qualquer fluxo de autenticação do site.")
    @DisplayName("Deve navegar da Home para a página de Login")
    void shouldNavigateToLoginPage() {
        LoginPage loginPage = homePage.goToLoginPage();
        assertTrue(loginPage.isLoaded(), "A página de login não carregou como esperado");
    }
}