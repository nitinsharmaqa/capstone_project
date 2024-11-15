package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportUtil {
    private static ExtentReports extent;

    public static ExtentReports getReportInstance() {
        if (extent == null) {
            // Using ExtentSparkReporter instead of ExtentHtmlReporter
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/ExtentReport.html");
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
        }
        return extent;
    }
}
