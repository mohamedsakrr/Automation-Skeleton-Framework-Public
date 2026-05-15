package com.automationexercices.tests.api;

import com.automationexercices.apis.MaintenxsAuthApi;
import com.automationexercices.tests.BaseTest;
import com.automationexercices.utils.dataReader.JsonReader;
import com.automationexercices.validations.ApiValidation;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Maintenxs")
@Feature("API Authentication")
@Story("User Login")
@Severity(SeverityLevel.CRITICAL)
@Owner("Sakr")
public class MaintenxsAuthApiTest extends BaseTest {
    private static final String LOGIN_SCHEMA = "schemas/maintenxs-login-schema.json";

    @Description("Verify Maintenxs API login returns a valid bearer token")
    @Test
    public void validMaintenxsApiLoginTC() {
        Response response = new MaintenxsAuthApi().login(
                testData.getJsonData("username"),
                testData.getJsonData("password")
        );

        Assert.assertEquals(response.statusCode(), 200, "Login status code is not correct");
        ApiValidation.validateSchema(response, LOGIN_SCHEMA);
        Assert.assertNotNull(response.jsonPath().getString("accessToken"), "Access token should not be null");
        Assert.assertEquals(response.jsonPath().getString("tokenType"), "Bearer", "Token type is not correct");
        Assert.assertTrue(response.jsonPath().getInt("expiresIn") > 0, "Token expiry should be greater than zero");
    }

    @Description("Verify Maintenxs API login rejects invalid credentials")
    @Test
    public void invalidMaintenxsApiLoginTC() {
        Response response = new MaintenxsAuthApi().login(
                testData.getJsonData("username"),
                testData.getJsonData("password") + "_invalid"
        );

        Assert.assertEquals(response.statusCode(), 401, "Invalid login status code is not correct");
    }

    @BeforeClass
    protected void preCondition() {
        testData = new JsonReader("maintenxs-api-login-data");
    }
}
