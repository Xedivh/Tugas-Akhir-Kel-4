package com.juaracoding.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ImportCutiNeg {
    WebDriver driver;
    List<String> missingElements = new ArrayList<>();
    boolean skipTest = false;

    private WebElement checkAndGet(WebDriverWait wait, String name, By locator) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            missingElements.add(name);
            return null;
        }
    }

    @BeforeClass
    public void setup() {
        driver = DriverSingleton.createOrGetDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.contains("dashboard") && !currentUrl.contains("home")) {
            skipTest = true;
            System.out.println("User  is not logged in. Skipping Import Cuti Test.");
        }
    }

    @Test
    public void testImportAbsenFunctionality() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement importButton1 = checkAndGet(wait, "importButton", By.xpath("//button[@type='submit']"));
        Assert.assertNotNull(importButton1, "Import button element not found on Import Absen page.");
        importButton1.click();
        Thread.sleep(5000);

        WebElement inputFile = checkAndGet(wait, "inputFile", By.xpath("//input[@id='file_excel']"));
        Assert.assertNotNull(inputFile, "File input element not found on Import Cuti page.");

        String projectDir = System.getProperty("user.dir");
        String filePath = projectDir + "/src/test/resources/sample_cuti.docx";
        System.out.println("Attempting to upload file from: " + filePath);

        java.io.File testFile = new java.io.File(filePath);
        if (!testFile.exists()) {
            throw new SkipException("Test file not found at: " + filePath + ". Please create 'sample_cuti.docx' in src/test/resources/.");
        }

        inputFile.sendKeys(filePath);
        Thread.sleep(2000);

        WebElement importButton = checkAndGet(wait, "importButton", By.xpath("//button[@type='submit']"));
        Assert.assertNotNull(importButton, "Import button element not found on Import Cuti page.");
        importButton.click();
        Thread.sleep(5000);

    }

    @AfterClass
    public void tearDown() {
        DriverSingleton.quitDriver();
    }
}
