package com.automationexercices.media;

import com.automationexercices.utils.TimeManager;
import com.automationexercices.utils.logs.LogsManager;
import com.automationexercices.utils.report.AllureAttachmentManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;

public class ScreenshotsManager {

    public static final String SCREENSHOTS_PATH = "test-output/screenshots/";

    //take full page screenshot
    public static void takeFullPageScreenshot(WebDriver driver, String screenshotName) {
        try {
            // Capture screenshot using TakesScreenshot
            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            // Save screenshot to a file if needed
            File screenshotFile = new File(SCREENSHOTS_PATH + screenshotName + "-" + TimeManager.getTimestamp() + ".png");
            Files.write(screenshotFile.toPath(), screenshotBytes);


            AllureAttachmentManager.attachScreenshot(screenshotName, screenshotBytes);

            LogsManager.info("Capturing Screenshot Succeeded");
        } catch (Exception e) {
            LogsManager.error("Failed to Capture Screenshot " + e.getMessage());
        }
    }

    //take screenshot of a specific element
    public static void takeElementScreenshot(WebDriver driver, By elementSelector) {
        try {
            // Highlight the element if needed (not implemented here)
            // Capture screenshot using TakesScreenshot
            String ariaName = driver.findElement(elementSelector).getAccessibleName();
            File screenshotSrc = driver.findElement(elementSelector).getScreenshotAs(OutputType.FILE);

            // Save screenshot to a file if needed
            File screenshotFile = new File(SCREENSHOTS_PATH + ariaName + "-" + TimeManager.getTimestamp() + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);
            // TODO: Attach the screenshot to Allure if needed

            LogsManager.info("Capturing Screenshot Succeeded");
        } catch (Exception e) {
            LogsManager.error("Failed to Capture Element Screenshot" , e.getMessage());
        }
    }
}
