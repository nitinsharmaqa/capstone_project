package tests;

import config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class LoginTest {
    WebDriver driver;
    LoginPage loginPage;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Config.IMPLICIT_WAIT));
        driver.get(Config.BASE_URL);
        loginPage = new LoginPage(driver);
    }

    @Test(dataProvider = "excelData")
    public void testLogin(String username, String password) {
        loginPage.doLogin(username, password);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Config.IMPLICIT_WAIT));
        wait.until(ExpectedConditions.urlContains("/dashboard"));
    }

     
    @DataProvider(name = "excelData")
    public Object[][] excelData() {
        // Implement your DataProvider to read from Data.xlsx
        return new Object[][] { {"Admin", "admin123"} };
    }
    
    
    
}
