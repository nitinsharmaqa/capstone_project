package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class AdminPage {
    WebDriver driver;
    WebDriverWait wait;

    public AdminPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Explicit wait for elements
    }

    public void clickAdminMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Admin']\r\n"))).click();
    }
    
    public int getLeftMenuOptionsCount() {
        List<WebElement> menuOptions = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ul[@class='oxd-main-menu']//li//span")));
        return menuOptions.size();
    }

    public void searchByUserName(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='oxd-input oxd-input--active']"))).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Search ']"))).click();
    }

    public void searchByUserRole(String role) {
        // Click to expand the User Role dropdown
        WebElement userRoleDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'oxd-select-text-input')][1]")));
        userRoleDropdown.click();

        // Locate all dropdown options
        List<WebElement> UseroleDD = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@role,'listbox')]//div[@role='option']")));

        // Iterate through dropdown options and select the one matching the role
        for (WebElement i : UseroleDD) {
            if (i.getText().contains(role)) {
                i.click();
                break;
            }
        }

        // Click the Search button
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Search ']")));
        searchButton.click();
    }


    

    public void searchByUserStatus(String status) {
        // Wait for the dropdown to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement statusDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[contains(@class,'oxd-select-text-input')])[2]")));
         
        statusDropdown.click();

        // Wait for the dropdown options to appear and select the desired status
        WebElement statusOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'oxd-select-dropdown')]//span[text()='Enabled']")));
        statusOption.click();

        // Click the Search button
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Search ']")));
        searchButton.click();
    }



    public int getTotalRecordsFound() {
        List<WebElement> records = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[@class='oxd-text oxd-text--span']")));
        return records.size() - 1; // Exclude header row
    }

    public void refreshPage() {
        driver.navigate().refresh();
      //  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("menu_admin_viewAdminModule")));
    }
}