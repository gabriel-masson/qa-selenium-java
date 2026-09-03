package com.qa.automationexercise.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Cliente de API para o recurso de produtos.
 * Cobre RF-API-001 (GET /productsList) e RF-API-002 (POST /productsList).
 */
public class ProductsApiClient extends BaseApiClient {

    private static final String PRODUCTS_LIST_ENDPOINT = "/productsList";

    public Response getProductsList() {
        return given()
                .spec(requestSpec)
            .when()
                .get(PRODUCTS_LIST_ENDPOINT)
            .then()
                .extract().response();
    }

    public Response postToProductsList() {
        return given()
                .spec(requestSpec)
            .when()
                .post(PRODUCTS_LIST_ENDPOINT)
            .then()
                .extract().response();
    }
}