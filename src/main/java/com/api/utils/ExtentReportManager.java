package com.api.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ExtentReportManager
 * 
 * TestNG ITestListener implementation for generating rich, executive-ready HTML reports.
 * Uses ThreadLocal to ensure 100% thread safety when running tests in parallel.
 */
public class ExtentReportManager implements ITestListener {

    private static ExtentReports extent;
    // ThreadLocal ensures that tests running in parallel do not overwrite each other's test instances
    private static final ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportDir = System.getProperty("user.dir") + File.separator + "reports";
        new File(reportDir).mkdirs(); // Ensure directory exists

        String reportPath = reportDir + File.separator + "API_Automation_Report_" + timestamp + ".html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("REST API Automation Test Report");
        sparkReporter.config().setReportName("CRUD & Negative API Test Execution");
        sparkReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // System / Environment Information
        extent.setSystemInfo("Environment", "QA / Staging");
        extent.setSystemInfo("Base URL", ConfigReader.getBaseUrl());
        extent.setSystemInfo("Framework", "REST Assured + TestNG");
        extent.setSystemInfo("Tester", "QA Automation Engineer");
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();

        ExtentTest test = extent.createTest(testName, description != null ? description : testName);
        test.assignCategory(result.getTestClass().getRealClass().getSimpleName());
        testThreadLocal.set(test);

        testThreadLocal.get().log(Status.INFO, "Started test: " + testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testThreadLocal.get().log(Status.PASS, "Test PASSED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        testThreadLocal.get().log(Status.FAIL, "Test FAILED: " + result.getMethod().getMethodName());
        testThreadLocal.get().log(Status.FAIL, "Failure Reason: " + result.getThrowable().getMessage());
        testThreadLocal.get().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testThreadLocal.get().log(Status.SKIP, "Test SKIPPED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush(); // Writes all logs to the HTML report file
        }
    }

    /**
     * Utility method to log custom steps from within test methods
     */
    public static void log(Status status, String message) {
        if (testThreadLocal.get() != null) {
            testThreadLocal.get().log(status, message);
        }
    }
}
