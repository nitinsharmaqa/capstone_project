package utility;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {

    public static Object[][] getTestData(String excelPath, String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(new File(excelPath));
        Workbook workbook = WorkbookFactory.create(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        // Get the number of rows and columns
        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

        Object[][] data = new Object[rowCount - 1][colCount];

        // Reading data from the sheet
        for (int i = 1; i < rowCount; i++) {  // Start from row 1 to skip header row
            for (int j = 0; j < colCount; j++) {
                data[i - 1][j] = sheet.getRow(i).getCell(j).toString();
            }
        }

        workbook.close();
        fis.close();
        return data;
    }

    // Excel Data Provider
    @org.testng.annotations.DataProvider(name = "loginData")
    public static Object[][] loginData() throws IOException {
        return getTestData("C:\\Users\\ADMIN\\Dropbox\\Developer\\SeleniumWD5\\com.orange.hrm\\src\\test\\resources\\Data.xlsx", "userdata");
    }
}