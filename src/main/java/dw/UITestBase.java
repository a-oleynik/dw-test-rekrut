package dw;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UITestBase extends TestBase {

    public static WebDriver driver;

    @BeforeClass
    public void start() {

        ChromeOptions options = new ChromeOptions();
        // Disables the new Chrome “Search Engine Choice” screen
        options.addArguments("--disable-search-engine-choice-screen");
        // Disable banner "Chrome is being controlled by automated test software"
        options.setExperimentalOption("excludeSwitches",
                Arrays.asList("enable-automation", "enable-logging"));
        options.setExperimentalOption("useAutomationExtension", false);
        // Disable password saving dialogs
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(20));
    }

    @AfterClass(alwaysRun = true)
    public void stop() {
        driver.quit();
    }
}
