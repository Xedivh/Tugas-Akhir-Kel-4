package com.juaracoding.selenium.pages;

import com.juaracoding.selenium.DriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.juaracoding.selenium.pages.ImportCutiPage;

import java.io.File;
import java.time.Duration;

public class ImportCutiPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private final By inputFile = By.id("file_excel");
    private final By importButton = By.xpath("//button[@type='submit']");
    private final By downloadTemplateButton = By.xpath("//button[normalize-space()='Download Template']");
    private final By menuCuti = By.xpath("//a[normalize-space()='Cuti']");
    private final By judulHalamanCuti = By.xpath("//h1[normalize-space()='Daftar Cuti']");
    private final By tabelCuti = By.id("tableCuti");
    private final By tombolTambahCuti = By.xpath("//button[normalize-space()='Tambah Cuti']");

    private static final String SAMPLE_FILE_PATH = System.getProperty("user.dir") + "/src/test/resources/testdata/sample_cuti.xlsx";

    public ImportCutiPage() {
        this.driver = DriverSingleton.createOrGetDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToImportCutiPage() {
        driver.get("https://magang.dikahadir.com/imports/import-absen-cuti");
    }

    public void navigateToCutiPageViaMenu() {
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(menuCuti));
        menu.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(judulHalamanCuti));
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

    public void clickTambahCutiButton() {
        wait.until(ExpectedConditions.elementToBeClickable(tombolTambahCuti)).click();
    }

    public String getCutiPageTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(judulHalamanCuti)).getText();
    }

    public boolean isCutiTableDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(tabelCuti)).isDisplayed();
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

    public void importSampleCutiAndDownloadTemplate() {
        navigateToImportCutiPage();
        pause(1000);
        uploadCutiFile(SAMPLE_FILE_PATH);
        pause(1000);
        clickImportButton();
        pause(2000);
        clickDownloadTemplateButton();
        pause(2000);
    }

    public String getFileValidationMessage() {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement helperText = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("file_excel-helper-text")));
        return helperText.getText();
    } catch (Exception e) {
        return null;
    }

    }
    public String getJavaScriptAlertText() {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.alertIsPresent()).getText();
    } catch (Exception e) {
        System.out.println("Alert tidak ditemukan: " + e.getMessage());
        return null;
    }
}


}
