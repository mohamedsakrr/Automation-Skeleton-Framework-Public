# Automation Skeleton Framework

Java 17 test automation skeleton with working samples for Web UI, API, DB, and Android Mobile.

## Stack

- Java 17
- Maven
- TestNG
- Selenium WebDriver
- Rest Assured
- PostgreSQL JDBC
- Appium
- Allure
- Log4j2

## Run Suites

Run all tests:

```powershell
mvn "-Dsurefire.suiteXmlFiles=src/test/resources/suites/all.xml" test
```

Run API only:

```powershell
mvn "-Dsurefire.suiteXmlFiles=src/test/resources/suites/api.xml" test
```

Run DB only:

```powershell
mvn "-Dsurefire.suiteXmlFiles=src/test/resources/suites/db.xml" test
```

Run Web UI only:

```powershell
mvn "-Dsurefire.suiteXmlFiles=src/test/resources/suites/ui.xml" test
```

Run Android Mobile only:

```powershell
mvn "-Dsurefire.suiteXmlFiles=src/test/resources/suites/mobile.xml" test
```

## Mobile Requirements

Before running mobile tests:

```powershell
appium --address 127.0.0.1 --port 4723
```

Make sure an Android emulator/device is visible:

```powershell
adb devices
```

The sample APK path is configured in:

`src/main/resources/mobile.properties`

## Local Secrets

Real credentials are intentionally ignored by Git. Before running the sample tests on a new machine, copy the example files and fill in local values:

```powershell
Copy-Item src/main/resources/db.example.properties src/main/resources/db.properties
Copy-Item src/test/resources/test-data/maintenxs-login-data.example.json src/test/resources/test-data/maintenxs-login-data.json
Copy-Item src/test/resources/test-data/maintenxs-api-login-data.example.json src/test/resources/test-data/maintenxs-api-login-data.json
```

## Project Samples

- API login positive and negative tests.
- API response schema validation.
- DB validation that checks `users.last_login_at` after API login.
- Web UI login test.
- Android Appium login test.

## Reports And Logs

- Allure report output: `test-output/reports`
- Raw Allure results: `test-output/allure-results`
- Logs: `test-output/Logs/logs.log`
- Screenshots and videos are retained for failed UI tests only.
