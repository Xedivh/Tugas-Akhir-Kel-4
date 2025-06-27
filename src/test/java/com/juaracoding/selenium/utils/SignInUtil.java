package com.juaracoding.selenium.utils;

import com.juaracoding.selenium.pages.SignInPage;
import org.openqa.selenium.TimeoutException;

public class SignInUtil {

    public static boolean loginValid(String email, String password) {
        SignInPage signInPage = new SignInPage();
        signInPage.login(email, password);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String currentUrl = signInPage.getCurrentUrl();

        try {
            String error = signInPage.getErrorMessage();
            if (!error.isEmpty()) {
            }
        } catch (TimeoutException ignored) {

        }

        return currentUrl.contains("dashboard") || currentUrl.contains("home");
    }

    public static boolean loginAsAdmin() {
        return loginValid("admin@hadir.com", "MagangSQA_JC@123");
    }
}
