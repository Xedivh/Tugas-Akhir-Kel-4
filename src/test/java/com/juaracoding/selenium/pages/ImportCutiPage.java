package com.juaracoding.selenium.pages;

import com.juaracoding.selenium.DriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class ImportCutiPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private final By inputFile = By.id("file_excel");
    private final By importButton = By.xpath("//button[@type='submit']");
    private final By downloadTemplateButton = By.xpath("//button[normalize-space()='Download Template']");
    private final By judulImportCuti = By.xpath("//div[contains(text(), 'Import Excel Absen Cuti')]");

    // private static final String SAMPLE_FILE_PATH = System.getProperty("user.dir") + "/src/test/resources/testdata/sample_cuti.xlsx";

    public ImportCutiPage() {
        this.driver = DriverSingleton.createOrGetDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToImportCutiPage() {
        driver.get("https://magang.dikahadir.com/imports/import-absen-cuti");
    }

    public void uploadCutiFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("Test file not found at: " + filePath);
        }
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(inputFile));
        fileInput.sendKeys(filePath);
    }

    public void clickImportButton() {
        wait.until(ExpectedConditions.elementToBeClickable(importButton)).click();
    }

    public void clickDownloadTemplateButton() {
        wait.until(ExpectedConditions.elementToBeClickable(downloadTemplateButton)).click();
    }

    public String getImportCutiHeaderText() {
        try {
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(judulImportCuti));
            return header.getText().trim();
        } catch (Exception e) {
            System.out.println("\u274C Gagal menemukan judul halaman Import Cuti.");
            return "";
        }
    }

    public boolean isFileUploaded() {
        try {
            WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(inputFile));
            String value = fileInput.getAttribute("value");
            return value != null && !value.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDownloadTemplateButtonVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(downloadTemplateButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isImportSuccess() {
        return true; // Perlu implementasi validasi sukses import (misal: toast atau alert muncul)
    }

    public String getFileValidationMessage() {
        try {
            WebElement helperText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("file_excel-helper-text")));
            return helperText.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public String getJavaScriptAlertText() {
        try {
            return wait.until(ExpectedConditions.alertIsPresent()).getText();
        } catch (Exception e) {
            System.out.println("Alert tidak ditemukan: " + e.getMessage());
            return null;
        }
    }
} 
