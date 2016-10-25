package core;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import helper.Constants;
import helper.ExcelUtils;

public class Guru99 {
    static WebDriver driver;

    public static void Chromesetup() {
        System.setProperty("webdriver.chrome.driver", Constants.ChromeDriverLocation);
        ChromeOptions options = new ChromeOptions();
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        options.setBinary(Constants.ChromeBinaryLocation);
        capabilities.setCapability("chromeOptions", (Object)options);
        driver = new ChromeDriver((Capabilities)capabilities);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        String url = Constants.BaseURL;
        driver.get(url);
    }

    public static void main(String[] args) {
        Chromesetup();
        //ExcelUtils MyUtil = new ExcelUtils();
        try {
            ExcelUtils.setExcelFile(Constants.FileTestData, Constants.SheetName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        int i = 1;
        while (i <= ExcelUtils.TotalRows) {
            String ActualResult;
            String UserName = ExcelUtils.getCellData((int)i, (int)0);
            String PassWord = ExcelUtils.getCellData((int)i, (int)1);
            String ToTest = ExcelUtils.getCellData((int)i, (int)2);
            String ExpectedResult = ExcelUtils.getCellData((int)i, (int)3);
            driver.findElement(By.name((String)"uid")).sendKeys(new CharSequence[]{UserName});
            driver.findElement(By.name((String)"password")).sendKeys(new CharSequence[]{PassWord});
            driver.findElement(By.name((String)"btnLogin")).click();
            Alert confirmationAlert = null;
            if (ToTest.equals("Title")) {
                ActualResult = driver.getTitle();
            } else {
                confirmationAlert = driver.switchTo().alert();
                ActualResult = confirmationAlert.getText();
            }
            if (ExpectedResult.equals(ActualResult)) {
                System.out.println("The testcase number " + i + " is passed.");
            } else {
                System.out.println("The ActualResult is {" + ActualResult + "} . And ExpectedResult is {" + ExpectedResult + "}");
            }
            if (ToTest.equals("Title")) {
                driver.findElement(By.linkText((String)"Log out")).click();
                confirmationAlert = driver.switchTo().alert();
                confirmationAlert.accept();
            } else {
                confirmationAlert.accept();
            }
            ++i;
        }
        driver.close();
    }
}
