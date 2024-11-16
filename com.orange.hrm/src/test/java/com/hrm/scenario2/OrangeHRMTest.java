package com.hrm.scenario2;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.AdminPage;
import pages.LoginPage;

public class OrangeHRMTest {
    WebDriver driver;
    LoginPage loginPage;
    AdminPage adminPage;

    @BeforeMethod
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "driver\\chromedriver_130.exe"); // Change path to your chromedriver
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30)); // Page load timeout
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        adminPage.searchByUserStatus("Enabled");
        int totalRecords = adminPage.getTotalRecordsFound();
        System.out.println("Total records found: " + totalRecords);
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}