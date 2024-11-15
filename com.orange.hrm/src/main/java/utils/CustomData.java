package utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CustomData {
    public XSSFWorkbook wb;

    @DataProvider(name = "Excel")
    public Object[][] excelData() {
        File file = new File(System.getProperty("user.dir") + "//src//main//Data.xlsx");
        FileInputStream fis;
        Object[][] arr = null;

        try {
            fis = new FileInputStream(file);
            wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheet("userdata");

            // Get number of rows and cells
            int row = sheet.getPhysicalNumberOfRows();
            int cell = sheet.getRow(0).getPhysicalNumberOfCells();

            // Initialize 2D array with row and cell count
            arr = new Object[row - 1][cell]; // -1 for skipping the header row

            // Loop through rows and cells to get data
            for (int i = 1; i < row; i++) { // Start from row 1 to skip headers
                for (int j = 0; j < cell; j++) {
                    arr[i - 1][j] = sheet.getRow(i).getCell(j).getStringCellValue();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arr;
    }
}
