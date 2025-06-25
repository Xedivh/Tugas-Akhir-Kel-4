package com.juaracoding.selenium.utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentTestManager {

        private static ExtentReports extent;
        private static ExtentTest test;

        public static void startReport() {
            ExtentSparkReporter htmlReporter = new ExtentSparkReporter("target/extent-report.html");
        extent = new ExtentReports();
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
        }

        public static void startTest(String testName) {
            test = extent.createTest(testName);
        }

        public static void logInfo(String message) {
            test.info(message);
        }

        public static void logPass(String message) {
            test.pass(message);
        }

        public static void logFail(String message) {
            test.fail(message);
        }

        public static void endTest() {
            extent.flush(); // Menulis semua log ke file
        }
}

