package tests;

import config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AdminPage;
import pages.LoginPage;

import java.time.Duration;

public class AdminTest {
    WebDriver driver;
    LoginPage loginPage;
    AdminPage adminPage;

    @BeforeClass
    public void setUp() {
        System.out.println("Setting up WebDriver and navigating to admin page...");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Config.IMPLICIT_WAIT));
        driver.get(Config.BASE_URL);

        // Login before performing admin actions
        loginPage = new LoginPage(driver);
        loginPage.doLogin("Admin", "admin123");

        // Navigate to the AdminPage after logging in
        adminPage = new AdminPage(driver);
    }

    @Test(priority = 1)
    public void testMenuOptionsCount() {
        System.out.println("Testing the count of menu options...");
        int menuCount = adminPage.getMenuOptionsCount();
        System.out.println("Total menu options: " + menuCount);
        assert menuCount == 12 : "Menu options count is not as expected!";
    }

    @Test(priority = 2)
    public void testSearchByUsername() {
        System.out.println("Testing search by username...");
        adminPage.searchEmpByUsername("Admin");
        String recordsMessage = adminPage.getRecordsMessage();
        System.out.println("Search results: " + recordsMessage);
    }

    @Test(priority = 3)
    public void testSearchByUserRole() {
        System.out.println("Testing search by user role...");
        adminPage.searchEmpByUserRole("Admin");
        String recordsMessage = adminPage.getRecordsMessage();
        System.out.println("Search results: " + recordsMessage);
    }

    @Test(priority = 4)
    public void testSearchByUserStatus() {
        System.out.println("Testing search by user status...");
        adminPage.searchEmpByUserStatus("Enabled");
        String recordsMessage = adminPage.getRecordsMessage();
        System.out.println("Search results: " + recordsMessage);
    }

    @AfterClass
    public void tearDown() {
        System.out.println("Closing WebDriver...");
        if (driver != null) {
            driver.quit();
        }
    }
}
