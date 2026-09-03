package com.qa.automationexercise.api;

import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Cliente de API para o recurso de usuário: verifyLogin, createAccount, deleteAccount.
 * Cobre RF-API-005, RF-API-006, RF-API-007, RF-API-008 e RF-API-009 do Test Plan.
 */
public class UserApiClient extends BaseApiClient {

    private static final String VERIFY_LOGIN_ENDPOINT = "/verifyLogin";
    private static final String CREATE_ACCOUNT_ENDPOINT = "/createAccount";
    private static final String DELETE_ACCOUNT_ENDPOINT = "/deleteAccount";

    /** RF-API-005 / RF-API-006: login com credenciais válidas ou inválidas. */
    public Response verifyLogin(String email, String password) {
        return given()
                .spec(requestSpec)
                .formParam("email", email)
                .formParam("password", password)
            .when()
                .post(VERIFY_LOGIN_ENDPOINT)
            .then()
                .extract().response();
    }

    /** RF-API-007: login sem o parâmetro obrigatório "email". */
    public Response verifyLoginWithoutEmail(String password) {
        return given()
                .spec(requestSpec)
                .formParam("password", password)
            .when()
                .post(VERIFY_LOGIN_ENDPOINT)
            .then()
                .extract().response();
    }

    /** RF-API-005 (variação DELETE): confirma que o método não é suportado. */
    public Response deleteVerifyLogin() {
        return given()
                .spec(requestSpec)
            .when()
                .delete(VERIFY_LOGIN_ENDPOINT)
            .then()
                .extract().response();
    }

    /** RF-API-008: cria uma conta a partir de um conjunto de campos do formulário. */
    public Response createAccount(Map<String, String> accountData) {
        return given()
                .spec(requestSpec)
                .formParams(accountData)
            .when()
                .post(CREATE_ACCOUNT_ENDPOINT)
            .then()
                .extract().response();
    }

    /** RF-API-009: remove a conta criada, usada para limpeza de dados de teste. */
    public Response deleteAccount(String email, String password) {
        return given()
                .spec(requestSpec)
                .formParam("email", email)
                .formParam("password", password)
            .when()
                .delete(DELETE_ACCOUNT_ENDPOINT)
            .then()
                .extract().response();
    }
}