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
import org.testng.SkipException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ImportAbsen {

    WebDriver driver;
    List<String> missingElements = new ArrayList<>();
    boolean skipTest = false;

    // Helper method to check and get WebElement
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

        // Pastikan Anda sudah login sebelum menjalankan pengujian ini
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.contains("dashboard") && !currentUrl.contains("home")) {
            skipTest = true; // Jika tidak berada di halaman dashboard, lewati pengujian
            System.out.println("User  is not logged in. Skipping Import Absen Test.");
        }
    }

    @Test
    public void testImportAbsenFunctionality() throws InterruptedException {
        if (skipTest) {
            throw new SkipException("Skipping Import Absen Test due to not being logged in.");
        }

        System.out.println("Navigating to Import Absen page...");
        driver.get("https://magang.dikahadir.com/imports/import-absen-hadir");
        Thread.sleep(2000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Identifikasi elemen input file
        WebElement inputFile = checkAndGet(wait, "inputFile", By.xpath("//input[@id='selfie']"));
        Assert.assertNotNull(inputFile, "File input element not found on Import Absen page.");

        // 2. Siapkan path file untuk diunggah
        String projectDir = System.getProperty("user.dir");
        String filePath = projectDir + "/src/test/resources/sample_absen.xlsx"; // Sesuaikan nama file jika berbeda
        System.out.println("Attempting to upload file from: " + filePath);

        // Cek apakah file ada sebelum mencoba mengunggah
        java.io.File testFile = new java.io.File(filePath);
        if (!testFile.exists()) {
            throw new SkipException("Test file not found at: " + filePath + ". Please create 'sample_absen.xlsx' in src/test/resources/.");
        }

        // 3. Unggah file
        inputFile.sendKeys(filePath);
        Thread.sleep(2000); // Tunggu sebentar setelah mengunggah file

        // 4. Identifikasi dan klik tombol submit/import
        WebElement importButton = checkAndGet(wait, "importButton", By.xpath("//button[@type='submit']"));
        Assert.assertNotNull(importButton, "Import button element not found on Import Absen page.");
        importButton.click();
        Thread.sleep(5000); // Tunggu proses impor selesai dan halaman diperbarui

        // Tambahkan langkah untuk mengunduh template
        System.out.println("Attempting to download template...");
        WebElement downloadButton = checkAndGet(wait, "downloadTemplateButton", By.xpath("//button[normalize-space()='Download Template']"));
        Assert.assertNotNull(downloadButton, "Download Template button not found on Import Absen page.");
        downloadButton.click();
        Thread.sleep(5000); // Tunggu hingga proses unduh selesai (sesuaikan waktu jika perlu)
        System.out.println("Template download initiated.");
    }

}
