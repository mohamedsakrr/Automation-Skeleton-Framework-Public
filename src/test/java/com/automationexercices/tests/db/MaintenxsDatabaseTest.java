package com.automationexercices.tests.db;

import com.automationexercices.apis.MaintenxsAuthApi;
import com.automationexercices.db.DatabaseManager;
import com.automationexercices.tests.BaseTest;
import com.automationexercices.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Maintenxs")
@Feature("Database")
@Story("User Last Login")
@Severity(SeverityLevel.CRITICAL)
@Owner("Sakr")
public class MaintenxsDatabaseTest extends BaseTest {
    private static final String LAST_LOGIN_TEXT_QUERY = "SELECT last_login_at::text FROM users WHERE email = ?";
    private static final String RECENT_LAST_LOGIN_QUERY = """
            SELECT COUNT(*)
            FROM users
            WHERE email = ?
              AND last_login_at >= (CURRENT_TIMESTAMP AT TIME ZONE 'UTC') - INTERVAL '1 minute'
            """;

    @Description("Verify API login updates the user's last_login_at in UTC within the last minute")
    @Test
    public void validMaintenxsLastLoginUpdatedAfterApiLoginTC() throws Exception {
        Response response = new MaintenxsAuthApi().login(
                testData.getJsonData("username"),
                testData.getJsonData("password")
        );
        Assert.assertEquals(response.statusCode(), 200, "API login status code is not correct");

        DatabaseManager databaseManager = new DatabaseManager();
        String username = testData.getJsonData("username");
        String lastLoginAt = databaseManager.getSingleStringValue(LAST_LOGIN_TEXT_QUERY, username);
        int recentLoginRecords = databaseManager.getSingleIntValue(RECENT_LAST_LOGIN_QUERY, username);

        Assert.assertNotNull(lastLoginAt, "User last_login_at should not be null");
        Assert.assertEquals(
                recentLoginRecords,
                1,
                "User last_login_at was not updated within the last minute. Actual last_login_at: " + lastLoginAt
        );
    }

    @BeforeClass
    protected void preCondition() {
        testData = new JsonReader("maintenxs-api-login-data");
    }
}
