package com.juaracoding.selenium.testcases;

import com.juaracoding.selenium.base.TestBase;
import com.juaracoding.selenium.pages.ImportCutiPage;
import com.juaracoding.selenium.utils.SignInUtil;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class ImportAbsenNeg extends TestBase {
    @Test
    public void testUploadFileDenganEkstensiTidakValid() throws InterruptedException {
    test = extent.createTest("Negative Test - Upload File Bukan Excel");

    if (!SignInUtil.loginAsAdmin()) {
        test.skip("Login gagal: URL tidak mengarah ke dashboard/home");
        throw new SkipException("Login gagal, test di-skip.");
    }

    ImportCutiPage importPage = new ImportCutiPage();
    importPage.navigateToImportCutiPage();
    Thread.sleep(1000);

    String filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/sample_absen.docx";
    importPage.uploadCutiFile(filePath);
    Thread.sleep(1000);
    importPage.clickImportButton();
    Thread.sleep(2000);


    String helperText = importPage.getFileValidationMessage();
    System.out.println("Helper Text = " + helperText);

    Assert.assertNotNull(helperText, "Helper text tidak muncul.");
    Assert.assertTrue(helperText.contains("Excel"), "Pesan validasi tidak sesuai: " + helperText);

    test.pass("Sistem menolak file .docx dan menampilkan pesan helper text.");
    }
    
    @Test
    public void testImportTanpaUploadFile() throws InterruptedException {
    test = extent.createTest("Negative Test - Klik Import TANPA upload file");

    if (!SignInUtil.loginAsAdmin()) {
        test.skip("Login gagal: URL tidak mengarah ke dashboard/home");
        throw new SkipException("Login gagal, test di-skip.");
    }

    ImportCutiPage importPage = new ImportCutiPage();
    importPage.navigateToImportCutiPage();
    Thread.sleep(1000);

    importPage.clickImportButton();
    Thread.sleep(1500);

    String alertText = importPage.getJavaScriptAlertText();
    System.out.println("Alert Text: " + alertText);
    }


}

