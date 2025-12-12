package dw;

import dw.framework.listeners.ScreenshotListener;
import dw.framework.webdriver.WebdriverManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import static dw.framework.webdriver.WebdriverMethods.createChromedriver;
import static dw.framework.webdriver.WebdriverMethods.setUpWebdriverSettings;

@Listeners({ScreenshotListener.class})
public class UITestBase extends TestBase {

    @BeforeClass
    public void start() {
        WebdriverManager.setWebDriver(createChromedriver());
        setUpWebdriverSettings();
    }

    @AfterClass(alwaysRun = true)
    public void stop() {
        if (WebdriverManager.getDriver() != null) {
            try {
                WebdriverManager.getDriver().quit();
            } finally {
                WebdriverManager.removeDriver();
            }
        }
    }
}
