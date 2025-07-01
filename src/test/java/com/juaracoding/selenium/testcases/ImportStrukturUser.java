package com.juaracoding.selenium.testcases;

import com.juaracoding.selenium.base.TestBase;
import com.juaracoding.selenium.pages.ImportStrukturUserPage;
import com.juaracoding.selenium.utils.SignInUtil;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class ImportStrukturUser extends TestBase {

    @Test
    public void testImportStrukturUser() throws InterruptedException {
        test = extent.createTest("Import Struktur User Test");

        if (!SignInUtil.loginAsAdmin()) {
            test.skip("Login gagal: URL tidak mengarah ke dashboard/home");
            throw new SkipException("Login gagal, test di-skip.");
        }

        ImportStrukturUserPage importPage = new ImportStrukturUserPage();
        importPage.navigateToImportStrukturUserPage();
        Thread.sleep(1000);

        String filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/sample_struktur_user.xlsx";
        importPage.uploadStrukturUserFile(filePath);
        Thread.sleep(1000);

        importPage.clickImportButton();
        Thread.sleep(2000);

        importPage.clickDownloadTemplateButton();
        Thread.sleep(2000);

        test.pass("Import struktur user dan download template berhasil dijalankan.");
    }
}