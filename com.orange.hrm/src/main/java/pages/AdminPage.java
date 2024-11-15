package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AdminPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By menuOptions = By.xpath("//ul[@class='oxd-main-menu']//li//span");
    private By usernameField = By.xpath("//input[@placeholder='Type for hints...']");
    private By searchButton = By.xpath("//button[@type='submit']");
    private By recordsMessage = By.xpath("//div[contains(@class,'orangehrm-horizontal-padding')]//span");
    private By userRoleDropdown = By.xpath("//div[@class='oxd-select-text-input'][1]");
    private By userStatusDropdown = By.xpath("//div[@class='oxd-select-text-input'][2]");
    private By dropdownOptions = By.xpath("//div[@role='listbox']//div[@role='option']//span");

    // Constructor
    public AdminPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Count and return total number of menu options
    public int getMenuOptionsCount() {
        List<WebElement> options = driver.findElements(menuOptions);
        return options.size();
    }

    // Search for an employee by username
    public void searchEmpByUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(searchButton).click();
    }

    // Search for an employee by user role with explicit wait
    public void searchEmpByUserRole(String role) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(userRoleDropdown)).click();
        selectFromDropdown(role);
        driver.findElement(searchButton).click();
    }

    // Search for an employee by user status with explicit wait
    public void searchEmpByUserStatus(String status) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(userStatusDropdown)).click();
        selectFromDropdown(status);
        driver.findElement(searchButton).click();
    }

    // Helper method to select from dropdown based on text
    private void selectFromDropdown(String text) {
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dropdownOptions));
        for (WebElement option : options) {
            if (option.getText().equalsIgnoreCase(text)) {
                option.click();
                break;
            }
        }
    }

    // Retrieve message displayed in records area
    public String getRecordsMessage() {
        return driver.findElement(recordsMessage).getText();
    }
}
