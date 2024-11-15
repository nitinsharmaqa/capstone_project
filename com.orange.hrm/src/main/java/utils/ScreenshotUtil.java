package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtil {
    public static void captureScreenshot(WebDriver driver, String fileName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir") + "//src//test//resources//Screenshots//" + fileName + ".png");
        FileHandler.copy(src, dest);
        System.out.println("Screenshot captured for " + fileName);
    }
}