package com.qa.automationexercise.tests.integration;

import com.qa.automationexercise.api.UserApiClient;
import com.qa.automationexercise.base.BaseUiTest;
import com.qa.automationexercise.pages.HomePage;
import com.qa.automationexercise.pages.LoginPage;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Integração - Conta criada via API deve logar na UI")
class UserAccountIntegrationTest extends BaseUiTest {

    private static final String PASSWORD = "Senha@123";
    private static final String USER_NAME = "QA Portfolio";

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
    @DisplayName("RF-INT-001: conta criada via API deve permitir login com sucesso na UI")
    void accountCreatedViaApi_shouldLoginSuccessfullyOnUi() {
        String uniqueEmail = "qa.portfolio." + System.currentTimeMillis() + "@teste.com";
        createdAccountEmail = uniqueEmail;

        Response createResponse = userApiClient.createAccount(buildAccountData(uniqueEmail));
        assertEquals(201, createResponse.jsonPath().getInt("responseCode"),
                "A conta deveria ter sido criada via API antes do teste de UI");

        LoginPage loginPage = homePage.goToLoginPage();
        HomePage loggedHomePage = loginPage.login(uniqueEmail, PASSWORD);

        assertTrue(loggedHomePage.isUserLoggedIn(USER_NAME),
                "O usuário deveria aparecer como logado após o login na UI");
    }

    private Map<String, String> buildAccountData(String email) {
        Map<String, String> data = new HashMap<>();
        data.put("name", USER_NAME);
        data.put("email", email);
        data.put("password", PASSWORD);
        data.put("title", "Mr");
        data.put("birth_date", "10");
        data.put("birth_month", "5");
        data.put("birth_year", "1995");
        data.put("firstname", "QA");
        data.put("lastname", "Portfolio");
        data.put("company", "Automation Exercise Study");
        data.put("address1", "Rua dos Testes, 123");
        data.put("address2", "");
        data.put("country", "Brazil");
        data.put("zipcode", "55636000");
        data.put("state", "xxxx");
        data.put("city", "xxxxxx");
        data.put("mobile_number", "11999999999");
        return data;
    }
}