package test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import utility.ExcelUtils; // Import the ExcelUtils class

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
        ExtentSparkReporter spark = new ExtentSparkReporter("ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @BeforeMethod
    public void initializeDriver() {
        // Setup WebDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ADMIN\\Dropbox\\Developer\\SeleniumWD5\\com.orange.hrm\\driver\\chromedriver_130.exe"); // Change path to your chromedriver
        driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/"); // OrangeHRM URL
    }

    // No need to specify dataProviderClass if you're directly using @DataProvider method from ExcelUtils
    @Test(dataProvider = "loginData", dataProviderClass = ExcelUtils.class)
    public void testLogin(String username, String password, String expectedResult) throws IOException {
        test = extent.createTest("Test Login for " + username);
        
        // Find the elements for login
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.findElement(By.xpath("//button[normalize-space(text()='Login')]")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        
        // Capture screenshot
        takeScreenshot("LoginAttempt_" + username);
        
        // Check for valid login (Dashboard appears for valid login)
        boolean isLoginSuccessful = driver.getTitle().contains("OrangeHRM");

        // Assertion based on expected result
        if (expectedResult.equals("Pass")) {
            Assert.assertTrue(isLoginSuccessful, "Login should be successful for valid credentials");
        } else {
            Assert.assertFalse(isLoginSuccessful, "Login should fail for invalid credentials");
        }
        
        // Logout if login was successful
        if (isLoginSuccessful) {
            WebElement logoutButton = driver.findElement(By.id("welcome"));
            logoutButton.click();
            driver.findElement(By.linkText("Logout")).click();
        }
    }

    // Method to capture screenshot
    public void takeScreenshot(String testName) throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File("screenshots/" + testName + ".png");
        org.apache.commons.io.FileUtils.copyFile(screenshot, destFile);
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }

    @AfterClass
    public void generateReport() {
        extent.flush(); // Generate and save the report after all tests have completed
    }
}
