package adminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class AdminPage {
    WebDriver driver;

    // Locator for Admin page elements
    By leftMenuOptions = By.xpath("//ul[@class='oxd-main-menu']//li");
    By adminMenuOption = By.xpath("//span[text()='Admin']");
    By usernameField = By.name("searchSystemUser[userName]");
    By searchButton = By.xpath("//button[normalize-space(text())='Search']");
    By roleDropdown = By.name("searchSystemUser[role]");
    By statusDropdown = By.name("searchSystemUser[status]");

    // Constructor
    public AdminPage(WebDriver driver) {
        this.driver = driver;
    }

    // Method to get the count of left menu options
    public int getLeftMenuOptionsCount() {
        List<WebElement> options = driver.findElements(leftMenuOptions);
        return options.size();
    }

    // Method to click on Admin menu option
    public void clickAdminMenu() {
        driver.findElement(adminMenuOption).click();
    }

    // Method to search by username
    public void searchByUserName(String username) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(searchButton).click();
    }

    // Method to search by user role
    public void searchByUserRole(String role) {
        Select roleSelect = new Select(driver.findElement(roleDropdown));
        roleSelect.selectByVisibleText(role);
        driver.findElement(searchButton).click();
    }

    // Method to search by user status
    public void searchByUserStatus(String status) {
        Select statusSelect = new Select(driver.findElement(statusDropdown));
        statusSelect.selectByVisibleText(status);
        driver.findElement(searchButton).click();
    }

    // Method to get total records count after search
    public int getTotalRecordsFound() {
        WebElement totalRecordsElement = driver.findElement(By.xpath("//div[@class='panel-body']//div[@class='ui-helper-hidden']/.."));
        return Integer.parseInt(totalRecordsElement.getText());
    }

    // Method to refresh the page
    public void refreshPage() {
        driver.navigate().refresh();
    }
}