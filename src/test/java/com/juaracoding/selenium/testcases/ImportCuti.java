package com.juaracoding.selenium.testcases;

import com.juaracoding.selenium.base.TestBase;
import com.juaracoding.selenium.pages.ImportCutiPage;
import com.juaracoding.selenium.utils.SignInUtil;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class ImportCuti extends TestBase {

    @Test(priority = 1)
    public void testVerifikasiHalamanImportCuti() throws InterruptedException {
        test = extent.createTest("Verifikasi Halaman Import Cuti");

        if (!SignInUtil.loginAsAdmin()) {
            test.skip("Login gagal: URL tidak ke dashboard");
            throw new SkipException("Login gagal");
        }

        ImportCutiPage importPage = new ImportCutiPage();
        importPage.navigateToImportCutiPage();
        Thread.sleep(1000);

        String header = importPage.getImportCutiHeaderText();
        Assert.assertFalse(header.toLowerCase().contains("import"),
                "Judul halaman tidak sesuai. Ditemukan: '" + header + "'");
        test.pass("Berhasil memverifikasi halaman Import Cuti: " + header);
    }

    @Test(priority = 2)
    public void testImportExcelFile() throws InterruptedException {
        test = extent.createTest("Import File Excel Cuti");

        if (!SignInUtil.loginAsAdmin()) {
            test.skip("Login gagal: URL tidak ke dashboard");
            throw new SkipException("Login gagal");
        }

        ImportCutiPage importPage = new ImportCutiPage();
        importPage.navigateToImportCutiPage();
        Thread.sleep(1000);

        String filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/sample_Cuti.xlsx";
        importPage.uploadCutiFile(filePath);
        Thread.sleep(1000);

        Assert.assertTrue(importPage.isFileUploaded(), "File belum berhasil diupload.");
        test.pass("File Excel Cuti berhasil diupload.");

        importPage.clickImportButton();
        Thread.sleep(2000);

        Assert.assertTrue(importPage.isImportSuccess(), "Proses import gagal.");
        test.pass("File Excel Cuti berhasil diimport.");
    }

    @Test(priority = 3)
    public void testDownloadTemplate() throws InterruptedException {
        test = extent.createTest("Download Template Cuti");

        if (!SignInUtil.loginAsAdmin()) {
            test.skip("Login gagal: URL tidak ke dashboard");
            throw new SkipException("Login gagal");
        }

        ImportCutiPage importPage = new ImportCutiPage();
        importPage.navigateToImportCutiPage();
        Thread.sleep(1000);

        Assert.assertTrue(importPage.isDownloadTemplateButtonVisible(), "Tombol download template tidak terlihat.");
        importPage.clickDownloadTemplateButton();
        Thread.sleep(2000);

        test.pass("Template Cuti berhasil di-download.");
    }
}
