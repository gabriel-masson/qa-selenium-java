package com.qa.automationexercise.tests.api;

import com.qa.automationexercise.api.UserApiClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("API - Usuário (login, criação e exclusão de conta)")
class UserApiTest {

    private static final String PASSWORD = "Senha@123";

    private final UserApiClient userApiClient = new UserApiClient();

    // Guarda o e-mail de uma conta criada durante o teste, para que o
    // @AfterEach saiba se precisa limpar algo.
    private String createdAccountEmail;

    @AfterEach
    void cleanUpCreatedAccount() {
        if (createdAccountEmail != null) {
            userApiClient.deleteAccount(createdAccountEmail, PASSWORD);
            createdAccountEmail = null;
        }
    }

    @Test
    @DisplayName("RF-API-006: login com credenciais inválidas deve retornar 'User not found!'")
    void verifyLogin_withInvalidCredentials_shouldReturnUserNotFound() {
        Response response = userApiClient.verifyLogin("usuario.inexistente@teste.com", "senhaErrada123");

        assertEquals(404, response.jsonPath().getInt("responseCode"));
        assertEquals("User not found!", response.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("RF-API-007: login sem o parâmetro email deve retornar 400")
    void verifyLogin_withoutEmail_shouldReturnBadRequest() {
        Response response = userApiClient.verifyLoginWithoutEmail(PASSWORD);

        assertEquals(400, response.jsonPath().getInt("responseCode"));
    }

    @Test
    @DisplayName("RF-API-008 + RF-API-005: criar conta deve permitir login imediato com as mesmas credenciais")
    void createAccount_thenVerifyLogin_shouldSucceed() {
        String uniqueEmail = "qa.portfolio." + System.currentTimeMillis() + "@teste.com";
        createdAccountEmail = uniqueEmail; // registrado ANTES da chamada, para garantir limpeza mesmo se o teste falhar depois

        Map<String, String> accountData = new HashMap<>();
        accountData.put("name", "QA Portfolio");
        accountData.put("email", uniqueEmail);
        accountData.put("password", PASSWORD);
        accountData.put("title", "Mr");
        accountData.put("birth_date", "10");
        accountData.put("birth_month", "5");
        accountData.put("birth_year", "1995");
        accountData.put("firstname", "QA");
        accountData.put("lastname", "Portfolio");
        accountData.put("company", "Automation Exercise Study");
        accountData.put("address1", "Rua dos Testes, 123");
        accountData.put("address2", "");
        accountData.put("country", "Brazil");
        accountData.put("zipcode", "55636000");
        accountData.put("state", "Pernambuco");
        accountData.put("city", "xxxxxx");
        accountData.put("mobile_number", "99999999999");

        Response createResponse = userApiClient.createAccount(accountData);
        assertEquals(201, createResponse.jsonPath().getInt("responseCode"), "A conta deveria ter sido criada");

        Response loginResponse = userApiClient.verifyLogin(uniqueEmail, PASSWORD);
        assertEquals(200, loginResponse.jsonPath().getInt("responseCode"), "Login deveria funcionar com a conta recém-criada");
        assertEquals("User exists!", loginResponse.jsonPath().getString("message"));
    }
}