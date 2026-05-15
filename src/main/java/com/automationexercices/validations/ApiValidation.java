package com.automationexercices.validations;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ApiValidation {
    private ApiValidation() {
    }

    @Step("Validate API response schema")
    public static void validateSchema(Response response, String schemaPath) {
        response.then().body(matchesJsonSchemaInClasspath(schemaPath));
    }
}
