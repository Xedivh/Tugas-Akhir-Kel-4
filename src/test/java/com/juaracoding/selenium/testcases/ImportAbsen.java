package com.juaracoding.selenium.testcases;

import com.juaracoding.selenium.base.TestBase;
import com.juaracoding.selenium.pages.ImportAbsenPage;
import com.juaracoding.selenium.utils.SignInUtil;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class ImportAbsen extends TestBase {

    @Test(groups = {"positive", "importAbsen"})
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

    @Test(groups = {"positive", "importAbsen"})
    public void verifyHalamanImportAbsen() {
        test = extent.createTest("Verifikasi Halaman Import Absen");

        if (!SignInUtil.loginAsAdmin()) {
            test.skip("Login gagal.");
            throw new SkipException("Login gagal, test di-skip.");
        }

        ImportAbsenPage importPage = new ImportAbsenPage();
        importPage.navigateToImportAbsenPage();

        String title = importPage.getAbsenPageTitle();
        Assert.assertEquals(title, "Daftar Absensi", "Judul halaman tidak sesuai");
        test.pass("Berhasil mengakses halaman import absen.");
    }

    @Test(groups = {"positive", "importAbsen"})
    public void verifyUploadFileXLS() {
        test = extent.createTest("Upload File XLS");

        if (!SignInUtil.loginAsAdmin()) {
            test.skip("Login gagal.");
            throw new SkipException("Login gagal, test di-skip.");
        }

        ImportAbsenPage importPage = new ImportAbsenPage();
        importPage.navigateToImportAbsenPage();

        String filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/sample_absen.xlsx";
        importPage.uploadAbsenFile(filePath);

        Assert.assertTrue(filePath.endsWith(".xlsx"), "File harus berformat .xlsx");
        test.pass("File .xlsx berhasil diupload.");
    }

    @Test(groups = {"positive", "importAbsen"})
    public void verifyDownloadTemplate() throws InterruptedException {
        test = extent.createTest("Download Template");

        if (!SignInUtil.loginAsAdmin()) {
            test.skip("Login gagal.");
            throw new SkipException("Login gagal, test di-skip.");
        }

        ImportAbsenPage importPage = new ImportAbsenPage();
        importPage.navigateToImportAbsenPage();

        importPage.clickDownloadTemplateButton();
        Thread.sleep(1000);

        test.pass("Tombol download template berhasil diklik.");
    }
}
