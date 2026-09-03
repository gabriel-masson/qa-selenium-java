package com.qa.automationexercise.api;

import com.qa.automationexercise.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

/**
 * Classe base para todos os clientes de API (ex: UserApiClient, ProductsApiClient).
 *
 * Centraliza a montagem da RequestSpecification (URL base + configuração de log),
 * lida a partir do ConfigManager — assim como o BasePage centraliza as esperas
 * para os Page Objects, esta classe centraliza a configuração de requisição
 * para os clientes de API.
 */
public abstract class BaseApiClient {

    protected final RequestSpecification requestSpec;

    protected BaseApiClient() {
        String baseUrl = ConfigManager.getInstance().getApiBaseUrl();

        this.requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .log(LogDetail.URI)
                .build();
    }
}