package com.juaracoding.selenium.testcases;

import com.juaracoding.selenium.base.TestBase;
import com.juaracoding.selenium.pages.ImportCutiPage;
import com.juaracoding.selenium.utils.SignInUtil;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class ImportCuti extends TestBase {

    @Test
    public void testImportCuti() throws InterruptedException {
        test = extent.createTest("Import Cuti Test");

        if (!SignInUtil.loginAsAdmin()) {
            test.skip("Login gagal: URL tidak mengarah ke dashboard/home");
            throw new SkipException("Login gagal, test di-skip.");
        }

        ImportCutiPage importPage = new ImportCutiPage();
        importPage.navigateToImportCutiPage();
        Thread.sleep(1000);

        String filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/sample_cuti.xlsx";
        importPage.uploadCutiFile(filePath);
        Thread.sleep(1000);

        importPage.clickImportButton();
        Thread.sleep(2000);

        importPage.clickDownloadTemplateButton();
        Thread.sleep(2000);

        test.pass("Import cuti dan download template berhasil dijalankan.");
    }
}