package com.automationexercices.tests.ui;

import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.drivers.UITest;
import com.automationexercices.pages.MaintenxsLoginPage;
import com.automationexercices.tests.BaseTest;
import com.automationexercices.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Maintenxs")
@Feature("UI User Management")
@Story("User Login")
@Severity(SeverityLevel.CRITICAL)
@Owner("Sakr")
@UITest
public class MaintenxsLoginTest extends BaseTest {

    @Description("Verify Maintenxs user can login with valid credentials")
    @Test
    public void validMaintenxsLoginTC() {
        new MaintenxsLoginPage(driver).navigate()
                .enterEmail(testData.getJsonData("email"))
                .enterPassword(testData.getJsonData("password"))
                .clickSignInButton()
                .verifyLoginSucceeded();
    }

    @BeforeClass
    protected void preCondition() {
        testData = new JsonReader("maintenxs-login-data");
    }

    @BeforeMethod
    public void setUp() {
        driver = new GUIDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws InterruptedException {
        if (driver != null) {
            Thread.sleep(10000);
            driver.quitDriver();
        }
    }
}
