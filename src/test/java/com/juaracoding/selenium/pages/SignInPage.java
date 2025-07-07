package com.juaracoding.selenium.pages;

import com.juaracoding.selenium.DriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignInPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private final By usernameField = By.id("email");
    private final By passwordField = By.id("password");
    private final By buttonLogin = By.xpath("//button[@type='submit']");
    private final By errorMessage = By.className("invalid-feedback");

    public SignInPage() {
        this.driver = DriverSingleton.createOrGetDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void inputUsername(String username) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        field.clear();
        field.sendKeys(username);
    }

    public void inputPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        field.clear();
        field.sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonLogin)).click();
    }

    public void login(String username, String password) {
        inputUsername(username);
        inputPassword(password);
        clickLogin();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }
}
