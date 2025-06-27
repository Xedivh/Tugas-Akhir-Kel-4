package com.juaracoding.selenium.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import com.juaracoding.selenium.DriverSingleton;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class TestBase {

    protected WebDriver driver;
    protected static ExtentReports extent;
    protected static ExtentTest test;

    @BeforeTest
    public void startReport() {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("utils/extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Tester", "Qonita Lutfia");
    }

    @BeforeClass
    public void setUp() {
        driver = DriverSingleton.createOrGetDriver();
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get("https://magang.dikahadir.com/authentication/login");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        String methodName = result.getMethod().getMethodName();

        if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test PASSED: " + methodName);
        } else if (result.getStatus() == ITestResult.FAILURE) {
            test.fail("Test FAILED: " + methodName);
            test.fail(result.getThrowable()); // log error
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("Test SKIPPED: " + methodName);
        }

        System.out.println("===> @AfterMethod: Finished test case");
    }

    @AfterClass
    public void tearDown() {
        DriverSingleton.quitDriver();
        System.out.println("===> @AfterClass: Driver quit");
    }

    @AfterTest
    public void tearDownReport() {
        extent.flush();
        System.out.println("===> @AfterTest: Report flushed");
    }
}
