package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import utils.CustomData; 

import java.io.IOException;
import java.time.Duration;

public class DataDrivenTest {
    public static WebDriver driver;
    public static ExtentReports extent;
    public static ExtentTest test;

    @BeforeClass
    public void setupReport() {
        // Setting up ExtentReports
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Tester", "Nitin"); 
    }

    @BeforeMethod	
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test(dataProvider = "Excel", dataProviderClass = CustomData.class)
    public void testLoginLogout(String username, String password) throws InterruptedException {
        // Start logging this test in Extent Report
        test = extent.createTest("Login Test with username: " + username);

        try {
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
            test.log(Status.INFO, "Navigated to login page");

            driver.findElement(By.name("username")).sendKeys(username);
            test.log(Status.INFO, "Entered username");

            driver.findElement(By.name("password")).sendKeys(password);
            test.log(Status.INFO, "Entered password");

            driver.findElement(By.xpath("//button[@type='submit']")).click();
            test.log(Status.INFO, "Clicked Login button");

            // Wait for the page to load
            Thread.sleep(2000);

            // Check if login was successful or not
            if (username.equals(username) && password.equals(password)) {
                Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Login Failed");
                test.log(Status.PASS, "Login Successful");
            } else {
                Assert.assertFalse(driver.getCurrentUrl().contains("dashboard"), "Login should have failed");
                test.log(Status.PASS, "Invalid login attempt failed as expected");
            }
        } catch (Exception e) {
            test.log(Status.FAIL, "Test failed due to exception: " + e.getMessage());
            captureScreenshot("LoginTest_" + username);
        }
    }

    public void captureScreenshot(String fileName) {
        try {
            // Updated to save the screenshot in the correct folder
            utils.ScreenshotUtil.captureScreenshot(driver, "src//test//resources//Screenshots//" + fileName);  
            test.addScreenCaptureFromPath("Screenshots/" + fileName + ".png");
            test.log(Status.INFO, "Screenshot taken: " + fileName);
        } catch (IOException e) {
            test.log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterClass
    public void tearDownReport() {
        extent.flush(); // Writes all test information to the report file
    }
}
