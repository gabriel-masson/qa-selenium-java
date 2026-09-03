package com.qa.automationexercise.base;

import com.qa.automationexercise.config.ConfigManager;
import com.qa.automationexercise.config.DriverManager;
import com.qa.automationexercise.pages.HomePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

/**
 * Classe base para todos os testes de UI (E2E e Integração).
 *
 * O @BeforeEach/@AfterEach do JUnit 5 garante que CADA teste comece com um
 * browser novo e limpo, e termine encerrando o driver — isso é essencial
 * para isolamento e idempotência dos testes (evita que estado de um teste
 * vaze para o próximo, uma das causas comuns de flaky test).
 */
public abstract class BaseUiTest {

    protected WebDriver driver;
    protected HomePage homePage;

    @BeforeEach
    void setUpDriver() {
        driver = DriverManager.getDriver();
        driver.manage().window().maximize();
        driver.get(ConfigManager.getInstance().getUiBaseUrl());
        homePage = new HomePage(driver);
    }

    @AfterEach
    void tearDownDriver() {
        DriverManager.quitDriver();
    }
}
