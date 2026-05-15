package com.automationexercices.tests.mobile;

import com.automationexercices.drivers.MobileDriver;
import com.automationexercices.drivers.MobileTest;
import com.automationexercices.screens.MaintenxsMobileLoginScreen;
import com.automationexercices.tests.BaseTest;
import com.automationexercices.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Maintenxs")
@Feature("Mobile")
@Story("Android App Launch")
@Severity(SeverityLevel.CRITICAL)
@Owner("Sakr")
@MobileTest
public class MaintenxsMobileLaunchTest extends BaseTest {
    private MobileDriver driver;

    @Description("Verify Maintenxs user can login from Android app with valid credentials")
    @Test
    public void validMaintenxsMobileLoginTC() {
        new MaintenxsMobileLoginScreen(driver.get())
                .enterEmail(testData.getJsonData("email"))
                .enterPassword(testData.getJsonData("password"))
                .tapLoginButton()
                .verifyLoginSucceeded();
    }

    @BeforeClass
    protected void preCondition() {
        testData = new JsonReader("maintenxs-login-data");
    }

    @BeforeMethod
    public void setUp() {
        driver = new MobileDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws InterruptedException {
        if (driver != null) {
            Thread.sleep(10000);
            driver.quitDriver();
        }
    }
}
