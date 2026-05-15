package com.automationexercices.utils.report;

import com.automationexercices.media.ScreenRecordManager;
import com.automationexercices.utils.logs.LogsManager;
import io.qameta.allure.Allure;

import java.io.File;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.automationexercices.utils.dataReader.PropertyReader.getProperty;

public class AllureAttachmentManager {
    // attachScreenshot, attachLogs, attachRecords methods would go here
    public static void attachScreenshot(String name, String path) {
        try {
            Path screenshot = Path.of(path);
            if (Files.exists(screenshot)) {
                Allure.addAttachment(name, Files.newInputStream(screenshot));
            } else {
                LogsManager.error("Screenshot not found: " + path);
            }
        } catch (Exception e) {
            LogsManager.error("Error attaching screenshot", e.getMessage());
        }
    }

    public static void attachScreenshot(String name, byte[] screenshot) {
        try {
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), ".png");
        } catch (Exception e) {
            LogsManager.error("Error attaching screenshot", e.getMessage());
        }
    }

    public static void attachLogs() {
        try {
            File logFile = new File(LogsManager.LOGS_PATH +"logs.log");
            if (logFile.exists()) {
                Allure.attachment("logs.log", Files.readString(logFile.toPath()));
            }
        } catch (Exception e) {
            LogsManager.error("Error attaching logs", e.getMessage());
        }
    }

    public static void attachRecords(String testMethodName) {
        if (getProperty("recordTests").equalsIgnoreCase("true")) {
            try {
                File record = new File(ScreenRecordManager.RECORDINGS_PATH + testMethodName);
                if (record != null && record.getName().endsWith(".mp4")) {
                    Allure.addAttachment(testMethodName, "video/mp4", Files.newInputStream(record.toPath()), ".mp4");
                }
            } catch (Exception e) {
                LogsManager.error("Error attaching records", e.getMessage());
            }
        }
    }

    public static void attachRecord(File record) {
        try {
            if (record != null && record.exists()) {
                Allure.addAttachment(record.getName(), "video/mp4", Files.newInputStream(record.toPath()), ".mp4");
            }
        } catch (Exception e) {
            LogsManager.error("Error attaching record", e.getMessage());
        }
    }

}
