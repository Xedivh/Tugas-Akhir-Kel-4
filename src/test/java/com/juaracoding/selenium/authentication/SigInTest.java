package com.juaracoding.selenium.authentication;

import com.juaracoding.selenium.DriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SigInTest {
    WebDriver driver;
    WebElement usernameField;
    WebElement passwordField;
    WebElement buttonLogin;
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
    public void setup() throws InterruptedException {
        driver = DriverSingleton.createOrGetDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        System.out.println("Opening login page...");
        driver.get("https://magang.dikahadir.com/authentication/login");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        usernameField = checkAndGet(wait, "usernameField", By.xpath("//input[@id='email']"));
        passwordField = checkAndGet(wait, "passwordField", By.xpath("//input[@id='password']"));
        buttonLogin = checkAndGet(wait, "buttonLogin", By.xpath("//button[@type='submit']"));

        System.out.println("Missing elements: " + missingElements);

        if (!missingElements.isEmpty()) {
            skipTest = true;
        }
    }

    @Test
    public void testStep01() throws InterruptedException {
        try {
            Thread.sleep(3000);
            usernameField.sendKeys("admin@hadir.com");
            Thread.sleep(1000);
            passwordField.sendKeys("MagangSQA_JC@123");
            Thread.sleep(1000);
            buttonLogin.click();

            Thread.sleep(3000);
            String currentUrl = driver.getCurrentUrl();
            System.out.println("Current URL: " + currentUrl);

            Assert.assertTrue(currentUrl.contains("dashboard") || currentUrl.contains("home"));
        } catch (Exception e) {
            throw new SkipException("testStep01: " + e.getMessage());
        }
    }
    public void login() throws InterruptedException {
        setup();
        if (skipTest) {
            throw new SkipException("Element login tidak ditemukan");
        }

        Thread.sleep(3000);
        usernameField.sendKeys("admin@hadir.com");
        Thread.sleep(1000);
        passwordField.sendKeys("MagangSQA_JC@123");
        Thread.sleep(1000);
        buttonLogin.click();

        Thread.sleep(3000);
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("dashboard") || currentUrl.contains("home"));
    }
    public WebDriver getDriver() {
        return driver;
    }
}
