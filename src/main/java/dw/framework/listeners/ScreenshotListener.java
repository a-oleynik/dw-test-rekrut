package dw.framework.listeners;

import dw.framework.webdriver.WebdriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import static dw.framework.config.Constants.SCREENSHOTS_FOLDER;
import static dw.framework.utils.DateTimeUtils.getDateTimeForAllureConsoleLog;
import static dw.framework.utils.DateTimeUtils.getDateTimeForScreenshotName;
import static dw.framework.utils.Utils.createDirectoryIfNotExist;
import static dw.framework.utils.Utils.saveBytesAsFile;
import static java.lang.String.format;

public class ScreenshotListener implements IInvokedMethodListener {
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        boolean isTestSkipped = (testResult.getStatus() == 3);
        if (!testResult.isSuccess() && (!isTestSkipped)) {
            try {
                WebDriver driver = WebdriverManager.getDriver();
                if (null != driver) {
                    String testMethod = method.getTestMethod().getMethodName();
                    byte[] screenshotBytes = makeScreenshotOnFailure(testMethod, driver);
                    saveScreenshot(method, screenshotBytes);
                }
            } catch (Exception exception) {
                System.out.println(format("%s exception thrown. Exception message:\n%s", exception.getClass().getSimpleName(),
                        exception.getMessage()));
            }
        }
    }

    private byte[] makeScreenshotOnFailure(String testName, WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    private void saveScreenshot(IInvokedMethod method, byte[] screenshotBytes) {
        String testClass = method.getTestResult()
                .getTestClass()
                .getRealClass()
                .getSimpleName();
        String testMethod = method.getTestMethod().getMethodName();
        String screenshotName = format("%s-%s-%s.png", getDateTimeForScreenshotName(), testClass,
                testMethod);
        String screenshotPath = SCREENSHOTS_FOLDER + screenshotName;
        createDirectoryIfNotExist(SCREENSHOTS_FOLDER);
        saveBytesAsFile(screenshotPath, screenshotBytes);
        System.out.println(getDateTimeForAllureConsoleLog() + ": screenshot saved in " + screenshotPath);
    }
}
