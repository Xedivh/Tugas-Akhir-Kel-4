package com.juaracoding.selenium.testcases;

import com.juaracoding.selenium.base.TestBase;
import com.juaracoding.selenium.pages.ImportAbsenPage;
import com.juaracoding.selenium.utils.SignInUtil;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class ImportAbsen extends TestBase {

    @Test(priority = 1)
    public void testVerifikasiHalamanImportAbsen() throws InterruptedException {
        test = extent.createTest("Verifikasi Halaman Import Absen");

        if (!SignInUtil.loginAsAdmin()) {
            test.skip("Login gagal: URL tidak ke dashboard");
            throw new SkipException("Login gagal");
        }

        ImportAbsenPage importPage = new ImportAbsenPage();
        importPage.navigateToImportAbsenPage();
        Thread.sleep(1000);

        String header = importPage.getImportAbsenHeaderText();
        Assert.assertTrue(header.toLowerCase().contains("import"),
                "Judul halaman tidak sesuai. Ditemukan: '" + header + "'");
        test.pass("Berhasil memverifikasi halaman Import Absen: " + header);
    }

    @Test (priority = 2)
    public void ImportExcelFile() throws InterruptedException {
        test = extent.createTest("Import File Excel Import Absen");

        if (!SignInUtil.loginAsAdmin()) {
            test.skip("Login gagal: URL tidak ke dashboard");
            throw new SkipException("Login gagal");
        }

        ImportAbsenPage importPage = new ImportAbsenPage();
        importPage.navigateToImportAbsenPage();
        Thread.sleep(1000);

        String filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/sample_Absen.xlsx";
        importPage.uploadAbsenFile(filePath);
        Thread.sleep(1000);

        Assert.assertTrue(importPage.isFileUploaded(), "File belum berhasil diupload.");
        test.pass("File Excel berhasil diupload.");

        importPage.clickImportButton();
        Thread.sleep(2000);

        Assert.assertTrue(importPage.isImportSuccess(), "Proses import gagal.");
        test.pass("File Excel berhasil diimport.");
    }

    @Test (priority = 3)
    public void testDownloadTemplate() throws InterruptedException {
        test = extent.createTest("Download Template");

        if (!SignInUtil.loginAsAdmin()) {
            test.skip("Login gagal: URL tidak ke dashboard");
            throw new SkipException("Login gagal");
        }

        ImportAbsenPage importPage = new ImportAbsenPage();
        importPage.navigateToImportAbsenPage();
        Thread.sleep(1000);

        Assert.assertTrue(importPage.isDownloadTemplateButtonVisible(), "Tombol download template tidak terlihat.");
        importPage.clickDownloadTemplateButton();
        Thread.sleep(2000);

        test.pass("Template berhasil di-download.");
    }
}
