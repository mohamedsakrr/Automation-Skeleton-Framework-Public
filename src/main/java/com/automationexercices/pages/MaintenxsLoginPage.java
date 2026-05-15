package com.automationexercices.pages;

import com.automationexercices.drivers.GUIDriver;
import com.automationexercices.utils.WaitManager;
import com.automationexercices.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class MaintenxsLoginPage {
    private final GUIDriver driver;
    private final String loginUrl = PropertyReader.getProperty("maintenxsLoginUrl");

    public MaintenxsLoginPage(GUIDriver driver) {
        this.driver = driver;
    }

    private final By emailInput = By.name("email");
    private final By passwordInput = By.name("password");
    private final By signInButton = By.xpath("//button[@type='submit']//span[text()='Sign In']/parent::button");
    private final By userMenuButton = By.xpath("//button[@type='button' and @aria-haspopup='menu' and .//*[name()='svg' and contains(@class,'lucide-chevron-down')]]");

    @Step("Navigate to Maintenxs Login Page")
    public MaintenxsLoginPage navigate() {
        driver.browser().navigateTo(loginUrl);
        return this;
    }

    @Step("Enter Maintenxs email {email}")
    public MaintenxsLoginPage enterEmail(String email) {
        driver.element().type(emailInput, email);
        return this;
    }

    @Step("Enter Maintenxs password {password}")
    public MaintenxsLoginPage enterPassword(String password) {
        driver.element().type(passwordInput, password);
        return this;
    }

    @Step("Click Maintenxs Sign In button")
    public MaintenxsLoginPage clickSignInButton() {
        driver.element().click(signInButton);
        return this;
    }

    @Step("Verify Maintenxs user logged in successfully")
    public MaintenxsLoginPage verifyLoginSucceeded() {
        new WaitManager(driver.get()).fluentWait()
                .until(d -> d.findElement(userMenuButton).isDisplayed());
        driver.verification().isElementVisible(userMenuButton);
        return this;
    }
}
