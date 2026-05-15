package com.automationexercices.apis;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

public class MaintenxsAuthApi {
    private static final String LOGIN_ENDPOINT = "/api/auth/login";

    @SuppressWarnings("unchecked")
    @Step("Login to Maintenxs API")
    public Response login(String username, String password) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("username", username);
        requestBody.put("password", password);

        return given()
                .spec(Builder.getJsonRequestSpecification())
                .body(requestBody.toJSONString())
                .when()
                .post(LOGIN_ENDPOINT);
    }
}
