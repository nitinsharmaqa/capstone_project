package adminTests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import adminPages.AdminPage;
import adminPages.LoginPage;

public class OrangeHRMTest {
    WebDriver driver;
    LoginPage loginPage;
    AdminPage adminPage;

    @BeforeMethod
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "driver\\chromedriver_130.exe"); // Change path to your chromedriver
        driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/"); // OrangeHRM URL
        loginPage = new LoginPage(driver);
        adminPage = new AdminPage(driver);
    }

    // Test case for Valid Login
    @Test
    public void testLogin() {
        loginPage.login("Admin", "admin123");
        String pageTitle = driver.getTitle();
        Assert.assertTrue(pageTitle.contains("OrangeHRM"));
    }

    // Test case for getting all 12 menu options from left side menu
    @Test
    public void testLeftMenuOptionsCount() {
        loginPage.login("Admin", "admin123");
        adminPage.clickAdminMenu();
        int menuCount = adminPage.getLeftMenuOptionsCount();
        Assert.assertEquals(menuCount, 12, "The number of menu options should be 12.");
    }

    // Test case for searching employee by Username
    @Test
    public void testSearchByUserName() {
        loginPage.login("Admin", "admin123");
        adminPage.clickAdminMenu();
        adminPage.searchByUserName("Admin");
        int totalRecords = adminPage.getTotalRecordsFound();
        System.out.println("Total records found: " + totalRecords);
        adminPage.refreshPage();
    }

    // Test case for searching employee by Role
    @Test
    public void testSearchByUserRole() {
        loginPage.login("Admin", "admin123");
        adminPage.clickAdminMenu();
        adminPage.searchByUserRole("Admin");
        int totalRecords = adminPage.getTotalRecordsFound();
        System.out.println("Total records found: " + totalRecords);
        adminPage.refreshPage();
    }

    // Test case for searching employee by Status
    @Test
    public void testSearchByUserStatus() {
        loginPage.login("Admin", "admin123");
        adminPage.clickAdminMenu();
        adminPage.searchByUserStatus("Enabled");
        int totalRecords = adminPage.getTotalRecordsFound();
        System.out.println("Total records found: " + totalRecords);
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}