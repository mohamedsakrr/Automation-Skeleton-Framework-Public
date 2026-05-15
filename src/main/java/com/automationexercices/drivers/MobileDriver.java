package com.automationexercices.drivers;

import com.automationexercices.utils.dataReader.PropertyReader;
import com.automationexercices.utils.logs.LogsManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URI;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class MobileDriver {
    private final ThreadLocal<AndroidDriver> driverThreadLocal = new ThreadLocal<>();

    public MobileDriver() {
        try {
            LogsManager.info("Starting Android driver for device: " + PropertyReader.getProperty("androidDeviceName"));
            preLaunchApplicationWithAdb();
            AndroidDriver driver = new AndroidDriver(
                    new URI(PropertyReader.getProperty("appiumServerUrl")).toURL(),
                    getAndroidOptions()
            );
            activateApplication(driver);
            driverThreadLocal.set(driver);
        } catch (Exception e) {
            LogsManager.error("Failed to start Android driver: " + e.getMessage(), e);
            throw new RuntimeException("Failed to start Android driver", e);
        }
    }

    private UiAutomator2Options getAndroidOptions() {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(PropertyReader.getProperty("androidPlatformName"))
                .setAutomationName(PropertyReader.getProperty("androidAutomationName"))
                .setDeviceName(PropertyReader.getProperty("androidDeviceName"))
                .setAppPackage(PropertyReader.getProperty("androidAppPackage"))
                .setAppActivity(PropertyReader.getProperty("androidAppActivity"))
                .setNoReset(Boolean.parseBoolean(PropertyReader.getProperty("androidNoReset")))
                .setNewCommandTimeout(Duration.ofSeconds(120));
        options.setCapability("appWaitPackage", PropertyReader.getProperty("androidAppPackage"));
        options.setCapability("appWaitActivity", "*");
        options.setCapability("autoLaunch", Boolean.parseBoolean(PropertyReader.getProperty("androidAutoLaunch")));
        options.setCapability("dontStopAppOnReset", Boolean.parseBoolean(PropertyReader.getProperty("androidDontStopAppOnReset")));
        options.setCapability("skipDeviceInitialization", Boolean.parseBoolean(PropertyReader.getProperty("androidSkipDeviceInitialization")));
        options.setCapability("disableWindowAnimation", Boolean.parseBoolean(PropertyReader.getProperty("androidDisableWindowAnimation")));

        if (Boolean.parseBoolean(PropertyReader.getProperty("androidInstallApp"))) {
            File appFile = new File(PropertyReader.getProperty("androidAppPath"));
            options.setApp(appFile.getAbsolutePath());
        }

        return options;
    }

    private void preLaunchApplicationWithAdb() {
        if (!Boolean.parseBoolean(PropertyReader.getProperty("androidPreLaunchWithAdb"))) {
            return;
        }

        String appPackage = PropertyReader.getProperty("androidAppPackage");
        String appActivity = PropertyReader.getProperty("androidAppActivity");
        String launchActivity = appActivity.startsWith(".") ? appPackage + appActivity : appActivity;

        try {
            clearApplicationDataIfConfigured(appPackage);
            Process process = new ProcessBuilder(
                    "adb",
                    "-s",
                    PropertyReader.getProperty("androidDeviceName"),
                    "shell",
                    "am",
                    "start",
                    "-n",
                    appPackage + "/" + launchActivity
            ).redirectErrorStream(true).start();

            boolean completed = process.waitFor(10, TimeUnit.SECONDS);
            if (completed && process.exitValue() == 0) {
                LogsManager.info("Android app pre-launched with ADB: " + appPackage + "/" + launchActivity);
            } else {
                LogsManager.warn("ADB pre-launch did not complete successfully. Appium will launch the app.");
            }
        } catch (Exception e) {
            LogsManager.warn("ADB pre-launch failed. Appium will launch the app.", e.getMessage());
        }
    }

    private void clearApplicationDataIfConfigured(String appPackage) throws Exception {
        if (!Boolean.parseBoolean(PropertyReader.getProperty("androidClearAppDataBeforeLaunch"))) {
            return;
        }

        Process process = new ProcessBuilder(
                "adb",
                "-s",
                PropertyReader.getProperty("androidDeviceName"),
                "shell",
                "pm",
                "clear",
                appPackage
        ).redirectErrorStream(true).start();

        boolean completed = process.waitFor(10, TimeUnit.SECONDS);
        if (completed && process.exitValue() == 0) {
            LogsManager.info("Android app data cleared before launch: " + appPackage);
        } else {
            LogsManager.warn("Android app data clear did not complete successfully: " + appPackage);
        }
    }

    private void activateApplication(AndroidDriver driver) {
        String appPackage = PropertyReader.getProperty("androidAppPackage");
        driver.activateApp(appPackage);
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(appDriver -> appPackage.equals(driver.getCurrentPackage()));
        LogsManager.info("Android app is active: " + driver.getCurrentPackage() + "/" + driver.currentActivity());
    }

    public AndroidDriver get() {
        return driverThreadLocal.get();
    }

    public void quitDriver() {
        AndroidDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}
