package com.automationexercices.screens;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MaintenxsMobileLoginScreen {
    private final AndroidDriver driver;

    private final By textInputs = AppiumBy.className("android.widget.EditText");
    private final By loginButton = AppiumBy.accessibilityId("Log In");

    public MaintenxsMobileLoginScreen(AndroidDriver driver) {
        this.driver = driver;
    }

    @Step("Enter mobile email")
    public MaintenxsMobileLoginScreen enterEmail(String email) {
        WebElement emailInput = getTextInput(0);
        emailInput.click();
        emailInput.clear();
        emailInput.sendKeys(email);
        return this;
    }

    @Step("Enter mobile password")
    public MaintenxsMobileLoginScreen enterPassword(String password) {
        WebElement passwordInput = getTextInput(1);
        passwordInput.click();
        passwordInput.clear();
        passwordInput.sendKeys(password);
        return this;
    }

    @Step("Tap mobile login button")
    public MaintenxsMobileLoginScreen tapLoginButton() {
        driver.findElement(loginButton).click();
        return this;
    }

    @Step("Verify mobile login succeeded")
    public MaintenxsMobileLoginScreen verifyLoginSucceeded() {
        new WebDriverWait(driver, Duration.ofSeconds(45))
                .until(d -> !d.getPageSource().contains("Welcome back!")
                        && !d.getPageSource().contains("Log In"));
        return this;
    }

    private WebElement getTextInput(int index) {
        return new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(d -> {
                    List<WebElement> inputs = d.findElements(textInputs);
                    return inputs.size() > index ? inputs.get(index) : null;
                });
    }
}
