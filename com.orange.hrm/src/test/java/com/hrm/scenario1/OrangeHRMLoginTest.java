package com.hrm.scenario1;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import utility.ExcelUtils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class OrangeHRMLoginTest {
    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        // Setup Extent Report
        ExtentSparkReporter spark = new ExtentSparkReporter("extentReports/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @BeforeMethod
    public void initializeDriver() {
        // Setup WebDriver
        System.setProperty("webdriver.chrome.driver", "driver\\chromedriver_130.exe"); 
        driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/"); 
    }

    @Test(dataProvider = "loginData", dataProviderClass = ExcelUtils.class)
    public void testLogin(String username, String password, String expectedResult) throws IOException {
        // Start the Extent Test
        test = extent.createTest("Test Login for " + username);

        // Wait for the username field to be visible before interacting
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space(text()='Login')]")));

        // Find the elements for login
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();

        // Explicit wait until the Dashboard is visible (check for a unique element on the dashboard)
        boolean isLoginSuccessful = false;
        try {
            // Wait for the dashboard to load by waiting for an element that appears only after a successful login
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Dashboard']"))); 
            isLoginSuccessful = true;
            test.info("Dashboard is visible, login successful.");
        } catch (TimeoutException e) {
            test.fail("Dashboard did not appear. Login failed for user: " + username);
        }

        // Capture screenshot after login attempt
        takeScreenshot("LoginAttempt_" + username);

        // Check for valid login (Dashboard appears for valid login)
        try {
            // Friendly assertion failure messages
            if (expectedResult.equals("Pass")) {
                Assert.assertTrue(isLoginSuccessful, "Login should be successful for valid credentials. However, the login was invalid for user: " + username);
            } else {
                Assert.assertFalse(isLoginSuccessful, "Login should fail for invalid credentials. However, the login was unexpectedly successful for user: " + username);
            }
        } catch (AssertionError e) {
            // Catch assertion errors and log them with a custom message for cleaner output
            test.fail("Assertion failed: " + e.getMessage());
        }

        // Logout if login was successful
        if (isLoginSuccessful) {
            WebElement logoutButton = driver.findElement(By.xpath("//h6[text()='Dashboard']"));
            takeScreenshot("LoginAttempt_" + username);
            test.pass("Valid Credentials - Login Successful for user: " + username);
            logout();
        }
    }

    // Logout function
    public void logout() {
        driver.findElement(By.xpath("//span[@class='oxd-userdropdown-tab']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.xpath("//li//a[text()='Logout']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    // Method to capture screenshot
    public void takeScreenshot(String testName) throws IOException {
        File screenshotsDir = new File("screenshots");
        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdirs(); 
        }

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File("screenshots/" + testName + ".png");
        org.apache.commons.io.FileUtils.copyFile(screenshot, destFile);

        // Add the screenshot to ExtentReports
        test.addScreenCaptureFromPath(destFile.getAbsolutePath());
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }

    @AfterClass
    public void generateReport() {
        extent.flush(); 
    }
}
