package dw;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class UITestBase extends TestBase {

    public static WebDriver driver;

    @BeforeClass
    public void start() {

        //System.setProperty("webdriver.chrome.driver", "");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));//TODO: to decrease
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));//TODO: to decrease
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(20));//TODO: to decrease
    }

    @AfterClass(alwaysRun = true)
    public void stop() {
        driver.quit();
    }
}
