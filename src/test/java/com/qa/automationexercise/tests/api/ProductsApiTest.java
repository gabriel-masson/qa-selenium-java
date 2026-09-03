package com.qa.automationexercise.tests.api;

import com.qa.automationexercise.api.ProductsApiClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("API - Produtos (/productsList)")
class ProductsApiTest {

    private final ProductsApiClient productsApiClient = new ProductsApiClient();

    @Test
    @DisplayName("RF-API-001: GET /productsList deve retornar 200 e uma lista de produtos")
    void getProductsList_shouldReturnProductsSuccessfully() {
        Response response = productsApiClient.getProductsList();

        assertEquals(200, response.statusCode(), "Status code HTTP inesperado");

        int responseCode = response.jsonPath().getInt("responseCode");
        assertEquals(200, responseCode, "responseCode do corpo da API deveria ser 200");

        List<Object> products = response.jsonPath().getList("products");
        
        // System.out.println("Produtos retornados: " + products);
        assertFalse(products.isEmpty(), "A lista de produtos não deveria vir vazia");
    }

    @Test
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