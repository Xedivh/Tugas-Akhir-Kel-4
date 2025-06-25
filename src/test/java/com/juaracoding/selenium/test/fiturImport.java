package com.juaracoding.selenium.test;
import com.juaracoding.selenium.authentication.SigInTest;
import com.juaracoding.selenium.DriverSingleton;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class fiturImport extends SigInTest {
    SigInTest SignInTest;
    WebDriver driver;
    WebElement importPage;
    WebElement uploadFile;
    WebElement buttonSubmit;
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

    @Test
    public void testFiturImport() throws InterruptedException {
        SigInTest auth = new SigInTest();
        auth.login();

        WebDriver driver = auth.getDriver();
        driver.findElement(By.id("menuImport")).click();
        driver.findElement(By.id("uploadFile")).sendKeys("C:/Users/qonita/Downloads/data.csv");
        driver.findElement(By.id("submitBtn")).click();
    }
}
