package com.qa.automationexercise.tests.api;

import com.qa.automationexercise.api.ProductsApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Feature("API de Produtos")
@DisplayName("API - Produtos (/productsList)")
class ProductsApiTest {

    private final ProductsApiClient productsApiClient = new ProductsApiClient();

    @Test
    @Tag("smoke")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Confirma que o endpoint principal de listagem de produtos está no ar e "
            + "retornando dados válidos - se isso quebrar, o catálogo inteiro do site é afetado.")
    @DisplayName("RF-API-001: GET /productsList deve retornar 200 e uma lista de produtos")
    void getProductsList_shouldReturnProductsSuccessfully() {
        Response response = productsApiClient.getProductsList();

        assertEquals(200, response.statusCode(), "Status code HTTP inesperado");

        int responseCode = response.jsonPath().getInt("responseCode");
        assertEquals(200, responseCode, "responseCode do corpo da API deveria ser 200");

        List<Object> products = response.jsonPath().getList("products");
        assertFalse(products.isEmpty(), "A lista de produtos não deveria vir vazia");
    }

    @Test
    @Tag("regression")
    @Severity(SeverityLevel.MINOR)
    @Description("Valida o comportamento da API quando um método HTTP não suportado é usado "
            + "no endpoint de produtos. Caso de borda, sem impacto direto na experiência do usuário.")
    @DisplayName("RF-API-002: POST /productsList deve indicar método não suportado (405)")
    void postToProductsList_shouldReturnMethodNotSupported() {
        Response response = productsApiClient.postToProductsList();

        // Particularidade desta API: o status HTTP retornado é sempre 200,
        // mesmo em cenário de erro. O código real vem dentro do corpo JSON.
        int responseCode = response.jsonPath().getInt("responseCode");
        assertEquals(405, responseCode, "A API deveria indicar método não suportado no corpo da resposta");

        String message = response.jsonPath().getString("message");
        assertEquals("This request method is not supported.", message);
    }
}