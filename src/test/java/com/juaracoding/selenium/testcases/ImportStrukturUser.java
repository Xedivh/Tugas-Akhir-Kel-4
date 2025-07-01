package com.juaracoding.selenium.testcases;

import com.juaracoding.selenium.base.TestBase;
import com.juaracoding.selenium.pages.ImportStrukturUserPage;
import com.juaracoding.selenium.utils.SignInUtil;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class ImportStrukturUser extends TestBase {

    @Test(priority = 1)
    public void testVerifikasiHalamanImportStrukturUser() throws InterruptedException {
        test = extent.createTest("Verifikasi Halaman Import Struktur User");

        if (!SignInUtil.loginAsAdmin()) {
            test.skip("Login gagal: URL tidak ke dashboard");
            throw new SkipException("Login gagal");
        }

        ImportStrukturUserPage importPage = new ImportStrukturUserPage();
        importPage.navigateToImportStrukturUserPage();
        Thread.sleep(1000);

        String header = importPage.getImportStrukturUserHeaderText();
        Assert.assertTrue(header.toLowerCase().contains("import"),
                "Judul halaman tidak sesuai. Ditemukan: '" + header + "'");
        test.pass("Berhasil memverifikasi halaman Import Struktur User: " + header);
    }

    @Test (priority = 2)
    public void testImportExcelFile() throws InterruptedException {
        test = extent.createTest("Import File Excel Struktur User");

        if (!SignInUtil.loginAsAdmin()) {
            test.skip("Login gagal: URL tidak ke dashboard");
            throw new SkipException("Login gagal");
        }

        ImportStrukturUserPage importPage = new ImportStrukturUserPage();
        importPage.navigateToImportStrukturUserPage();
        Thread.sleep(1000);

        String filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/sample_struktur_user.xlsx";
        importPage.uploadStrukturUserFile(filePath);
        Thread.sleep(1000);

        Assert.assertTrue(importPage.isFileUploaded(), "File belum berhasil diupload.");
        test.pass("File Excel Struktur User berhasil diupload.");

        importPage.clickImportButton();
        Thread.sleep(2000);

        Assert.assertTrue(importPage.isImportSuccess(), "Proses import gagal.");
        test.pass("File Excel Struktur User berhasil diimport.");
    }

    @Test (priority = 3)
    public void testDownloadTemplate() throws InterruptedException {
        test = extent.createTest("Download Template Import Struktur User");

        if (!SignInUtil.loginAsAdmin()) {
            test.skip("Login gagal: URL tidak ke dashboard");
            throw new SkipException("Login gagal");
        }

        ImportStrukturUserPage importPage = new ImportStrukturUserPage();
        importPage.navigateToImportStrukturUserPage();
        Thread.sleep(1000);

        Assert.assertTrue(importPage.isDownloadTemplateButtonVisible(), "Tombol download template tidak terlihat.");
        importPage.clickDownloadTemplateButton();
        Thread.sleep(2000);

        test.pass("Template Struktur User berhasil di-download.");
    }
}
