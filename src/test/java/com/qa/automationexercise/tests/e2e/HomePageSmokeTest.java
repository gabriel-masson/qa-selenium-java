package com.qa.automationexercise.tests.e2e;

import com.qa.automationexercise.base.BaseUiTest;
import com.qa.automationexercise.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Smoke test do framework: valida que ConfigManager, DriverManager e os
 * primeiros Page Objects estão corretamente integrados.
 * Não substitui os testes E2E completos, que virão na Etapa 5.
 */
@DisplayName("Smoke test - Home e navegação para Login")
class HomePageSmokeTest extends BaseUiTest {

    @Test
    @DisplayName("Home page deve carregar corretamente")
    void homePageShouldLoad() {
        assertTrue(homePage.isLoaded(), "A home page não carregou como esperado");
    }

    @Test
    @DisplayName("Deve navegar da Home para a página de Login")
    void shouldNavigateToLoginPage() {
        LoginPage loginPage = homePage.goToLoginPage();
        assertTrue(loginPage.isLoaded(), "A página de login não carregou como esperado");
    }
}
