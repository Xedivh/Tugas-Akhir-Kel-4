package com.juaracoding.selenium.authentication;

import com.juaracoding.selenium.DriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ImportCuti {

    WebDriver driver;
    List<String> missingElements = new ArrayList<>();

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
    }

    @Test(dependsOnMethods = {"com.juaracoding.selenium.authentication.ImportAbsen.testImportAbsenFunctionality"})
    public void testImportCutiFunctionality() throws InterruptedException {

        System.out.println("Navigating to Import Cuti page...");
        driver.get("https://magang.dikahadir.com/imports/import-absen-cuti");
        Thread.sleep(2000);

        // Refresh halaman sebelum memulai pengujian
        /*System.out.println("Refreshing the page...");
        driver.navigate().refresh();*/

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Identifikasi elemen input file
        WebElement inputFile = checkAndGet(wait, "inputFile", By.xpath("//input[@id='file_excel']"));
        Assert.assertNotNull(inputFile, "File input element not found on Import Cuti page.");

        // 2. Siapkan path file untuk diunggah
        String projectDir = System.getProperty("user.dir");
        String filePath = projectDir + "/src/test/resources/sample_cuti.xlsx";
        System.out.println("Attempting to upload file from: " + filePath);

        // 3. Cek apakah file ada sebelum mengunggah
        java.io.File testFile = new java.io.File(filePath);
        Assert.assertTrue(testFile.exists(), "Test file not found at: " + filePath);

        // 4. Unggah file
        inputFile.sendKeys(filePath);
        Thread.sleep(2000);

        // 5. Klik tombol submit/import
        WebElement importButton = checkAndGet(wait, "importButton", By.xpath("//button[@type='submit']"));
        Assert.assertNotNull(importButton, "Import button not found");
        importButton.click();
        Thread.sleep(5000);

        // 6. Download template
        System.out.println("Downloading template...");
        WebElement downloadButton = checkAndGet(wait, "downloadButton", By.xpath("//button[normalize-space()='Download Template']"));
        Assert.assertNotNull(downloadButton, "Download button not found");
        downloadButton.click();
        Thread.sleep(5000);
    }

    @AfterClass
    public void tearDown() {
        DriverSingleton.quitDriver();
    }
}
