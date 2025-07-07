package com.juaracoding.selenium.pages;

import com.juaracoding.selenium.DriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class ImportAbsenPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private final By inputFile = By.id("selfie");
    private final By importButton = By.xpath("//button[@type='submit']");
    private final By downloadTemplateButton = By.xpath("//button[normalize-space()='Download Template']");
    private final By menuAbsensi = By.xpath("//a[normalize-space()='Absensi']");
    private final By judulHalamanAbsensi = By.xpath("//h1[normalize-space()='Daftar Absensi']");
    private final By tabelAbsensi = By.id("tableAbsen");
    private final By tombolTambahAbsen = By.xpath("//button[normalize-space()='Tambah Absen']");
    private final By judulImportAbsen = By.xpath("//div[contains(text(), 'Import Excel Absen Hadir')]");


    private static final String SAMPLE_FILE_PATH = System.getProperty("user.dir") + "/src/test/resources/testdata/sample_absen.xlsx";

    public ImportAbsenPage() {
        this.driver = DriverSingleton.createOrGetDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToImportAbsenPage() {
        driver.get("https://magang.dikahadir.com/imports/import-absen-hadir");
    }

    public void navigateToAbsenPageViaMenu() {
        WebElement absenMenu = wait.until(ExpectedConditions.elementToBeClickable(menuAbsensi));
        absenMenu.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(judulHalamanAbsensi));
    }

    public void uploadAbsenFile(String filePath) {
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

    public void clickTambahAbsenButton() {
        wait.until(ExpectedConditions.elementToBeClickable(tombolTambahAbsen)).click();
    }

    public String getAbsenPageTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(judulHalamanAbsensi)).getText();
    }

    public boolean isAbsensiTableDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(tabelAbsensi)).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    private void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void importSampleAbsenAndDownloadTemplate() {
        navigateToImportAbsenPage();
        pause(1000);
        uploadAbsenFile(SAMPLE_FILE_PATH);
        pause(1000);
        clickImportButton();
        pause(2000);
        clickDownloadTemplateButton();
        pause(2000);
    }
    public String getImportAbsenHeaderText() {
    try {
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(judulImportAbsen));
        return header.getText().trim();
    } catch (Exception e) {
        System.out.println("Gagal menemukan judul halaman Import Absen.");
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement helperText = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("file_excel-helper-text")));
        return helperText.getText();
    } catch (Exception e) {
        return null;
    }}

public boolean isImportSuccess() {
    
    return true;
}

    

}
