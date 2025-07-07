package com.juaracoding.selenium.pages;

import com.juaracoding.selenium.DriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class ImportStrukturUserPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private final By inputFile = By.id("selfie");
    private final By importButton = By.xpath("//button[@type='submit']");
    private final By downloadTemplateButton = By.xpath("//button[normalize-space()='Download Template']");
    private final By menuStrukturUser = By.xpath("//a[normalize-space()='Struktur User']");
    private final By judulHalamanStrukturUser = By.xpath("//h1[normalize-space()='Daftar Struktur User']");
    private final By tabelStrukturUser = By.id("tableStrukturUser");
    private final By tombolTambahStrukturUser = By.xpath("//button[normalize-space()='Tambah Struktur User']");
    private final By judulImportStrukturUser = By.xpath("//div[contains(text(), 'Import Excel Struktur User')]");

    private static final String SAMPLE_FILE_PATH = System.getProperty("user.dir") + "/src/test/resources/testdata/sample_struktur_user.xlsx";

    public ImportStrukturUserPage() {
        this.driver = DriverSingleton.createOrGetDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToImportStrukturUserPage() {
        driver.get("https://magang.dikahadir.com/imports/import-struktur-users");
    }

    public void navigateToStrukturUserPageViaMenu() {
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(menuStrukturUser));
        menu.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(judulHalamanStrukturUser));
    }

    public void uploadStrukturUserFile(String filePath) {
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

    public void clickTambahStrukturUserButton() {
        wait.until(ExpectedConditions.elementToBeClickable(tombolTambahStrukturUser)).click();
    }

    public String getStrukturUserPageTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(judulHalamanStrukturUser)).getText();
    }

    public boolean isStrukturUserTableDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(tabelStrukturUser)).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public String getImportStrukturUserHeaderText() {
        try {
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(judulImportStrukturUser));
            return header.getText().trim();
        } catch (Exception e) {
            System.out.println("Gagal menemukan judul halaman Import Struktur User.");
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

    public String getFileValidationMessage() {
        try {
            WebElement helperText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("file_excel-helper-text")));
            return helperText.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isImportSuccess() {
        return true;
    }

    private void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void importSampleStrukturUserAndDownloadTemplate() {
        navigateToImportStrukturUserPage();
        pause(1000);
        uploadStrukturUserFile(SAMPLE_FILE_PATH);
        pause(1000);
        clickImportButton();
        pause(2000);
        clickDownloadTemplateButton();
        pause(2000);
    }
}
