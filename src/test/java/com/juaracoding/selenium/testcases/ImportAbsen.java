package com.juaracoding.selenium.testcases;

import com.juaracoding.selenium.base.TestBase;
import com.juaracoding.selenium.pages.ImportAbsenPage;
import com.juaracoding.selenium.utils.SignInUtil;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class ImportAbsen extends TestBase {

    @Test
    public void testImportAbsen() throws InterruptedException {
        test = extent.createTest("Import Absen Test");
        if (!SignInUtil.loginAsAdmin()) {
            test.skip("Login gagal: URL tidak mengarah ke dashboard/home");
            throw new SkipException("Login gagal, test di-skip.");
        }
        ImportAbsenPage importPage = new ImportAbsenPage();
        importPage.navigateToImportAbsenPage();
        Thread.sleep(1000);

        String filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/sample_absen.xlsx";
        importPage.uploadAbsenFile(filePath);
        Thread.sleep(1000);

        importPage.clickImportButton();
        Thread.sleep(2000);

        importPage.clickDownloadTemplateButton();
        Thread.sleep(2000);

        test.pass("Import absen dan download template berhasil dijalankan.");
    }
}
