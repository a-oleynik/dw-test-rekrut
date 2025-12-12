package dw.pageobject;

import dw.UITestBase;
import dw.framework.webdriver.WebdriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static dw.framework.utils.Utils.sleep;
import static java.lang.String.format;
import static org.awaitility.Awaitility.*;

public class AbstractPageObject extends UITestBase {

    public static String SPINNER = "//div[@class='spinner-overlay' or @class='loading-indicator' or @data-ng-if='saveGraph.loading$ | async:this'][not(@disabled)]";


    public void click(String xpath) {
        findElementByXpath(xpath, 1).click();
    }

    public void actionClick(WebElement element) {
        Actions act = new Actions(WebdriverManager.getDriver());
        act.click(element).build().perform();
    }

    public void actionFillInput(WebElement element, String text) {
        Actions act = new Actions(WebdriverManager.getDriver());
        act.sendKeys(element, text).build().perform();
    }

    public void click(WebElement el) {
        el.click();
    }

    public WebElement findElementBy(By by) {
        return WebdriverManager.getDriver().findElement(by);
    }

    public List<WebElement> findElementsBy(By by) {
        return WebdriverManager.getDriver().findElements(by);
    }

    public WebElement findElementBy(By by, int waitSec) {
        WebElement element = findElementBy(by);
        return element;
    }

    public List<WebElement> findElementsBy(By by, int waitSec) {
        List<WebElement> elements = findElementsBy(by);
        return elements;
    }

    public WebElement findElementByXpath(String xpath) {
        return findElementBy(By.xpath(xpath));
    }

    // Method to scroll left until element present and visible
    public WebElement scrollLeftUntilElementPresent(By locator, String scrollBarXpath) {
        WebDriver driver = WebdriverManager.getDriver();
        AtomicReference<WebElement> result = new AtomicReference<>();
        try {
            await()
                    .pollInterval(Duration.ofMillis(300))
                    .atMost(Duration.ofSeconds(30))
                    .ignoreExceptions()
                    .until(() -> {
                        var found = driver.findElements(locator);
                        if (!found.isEmpty()) {
                            result.set(found.get(0));
                            return true;
                        }

                        scrollLeftElementByXpath(driver, scrollBarXpath);
                        return false;
                    });

        } catch (org.awaitility.core.ConditionTimeoutException e) {
            throw new NoSuchElementException(
                    "Element " + locator + " not found within timeout while scrolling", e);
        }

        WebElement element = result.get();
        if (element == null) {
            throw new NoSuchElementException(
                    "Element " + locator + " not found");
        }
        return element;
    }

    // Another variant to scroll left until element present and visible
    public WebElement scrollLeftUntilElementPresentV2(By locator, String scrollBarXpath, int maxScrolls) {
        WebDriver driver = WebdriverManager.getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(WebdriverManager.getDriver(), Duration.ofSeconds(5));

        for (int i = 0; i < maxScrolls; i++) {

            // 1. Try to find the element in current DOM
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty()) {
                WebElement element = elements.get(0);

                // 2. Scroll it nicely into view and wait for visibility
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
                wait.until(ExpectedConditions.visibilityOf(element));

                return element;
            }

            // 3. Element not found yet → scroll further left
            scrollLeftElementByXpath(driver, scrollBarXpath);
            sleep(300); // small pause to let content load (or use a smarter wait)
        }

        throw new NoSuchElementException(
                "Element " + locator + " not found after scrolling " + maxScrolls + " times");
    }

    public void scrollLeftElementByXpath(WebDriver driver, String XPath) {
        String script = format("var el = document.evaluate(\"%s\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;\n" +
                "el.scrollLeft += 200;", XPath);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script);
    }

    public WebElement findElementByXpath(String xpath, int waitSec) {
        return findElementBy(By.xpath(xpath), waitSec);
    }

    public WebElement findElementByCssSelector(String cssSelector) {
        return findElementBy(By.cssSelector(cssSelector));
    }

    public WebElement findElementByXpath(WebElement el, String xpath) {
        return el.findElement(By.xpath(xpath));
    }

    public WebElement findElementByXpath(WebElement el, String xpath, int waitSec) {
        WebElement element = null;
        try {
            element = el.findElement(By.xpath(xpath));
        } catch (WebDriverException e) {

        }
        return element;
    }

    public List<WebElement> findElementsByXpath(String xpath) {
        return findElementsBy(By.xpath(xpath));
    }

    public boolean isElementDisplayed(By locator, int maxWaitSec) {
        WebDriverWait wait = new WebDriverWait(WebdriverManager.getDriver(), Duration.ofSeconds(maxWaitSec));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isElementDisplayed(String xpath, int maxWaitSec) {
        WebDriverWait wait = new WebDriverWait(WebdriverManager.getDriver(), Duration.ofSeconds(maxWaitSec));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public void clickElement(By by) {
        findElementBy(by).click();
    }

    public void fillInputByChar(By by, String text) {
        clickElement(by);
        findElementBy(by, 10).clear();

        for (int i = 0; i < text.length(); i++) {
            findElementBy(by).sendKeys(String.valueOf(text.toCharArray()[i]));
        }
    }

    public void fillInputByChar(String xpath, String text) {
        fillInputByChar(By.xpath(xpath), text);
    }

    public void fillInputByChar(WebElement element, String text) {
        click(element);
        element.clear();

        for (int i = 0; i < text.length(); i++) {
            element.sendKeys(String.valueOf(text.toCharArray()[i]));
        }
    }


    public void fillInput(String xpath, String text) {
        WebElement element = findElementByXpath(xpath);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));     // clear input
        Actions actions = new Actions(WebdriverManager.getDriver());
        actions.click(element)
                .sendKeys(element, text)
                .build().perform();
    }


    public void openUrl(String url) {
        WebdriverManager.getDriver().get(url);
    }


    public void selectByValue(String selectXpath, String value) {
        WebElement selectElement = findElementByXpath(selectXpath);
        Select select = new Select(selectElement);
        select.selectByValue(value);
    }


    public String getSelectedValue(String selectXpath) {
        WebElement selectElement = findElementByXpath(selectXpath);
        Select select = new Select(selectElement);
        return select.getFirstSelectedOption().getText();
    }


    public void selectByVisibleText(String selectXpath, String value) {
        WebElement selectElement = findElementByXpath(selectXpath);
        Select select = new Select(selectElement);
        select.selectByVisibleText(value);
    }


    public void selectByVisibleText(WebElement selectElement, String value) {
        Select select = new Select(selectElement);
        select.selectByVisibleText(value);
    }

    public boolean waitForElementDisplayed(By by, int maxSeconds) {
        try {
            Wait<WebDriver> wait = new FluentWait<>(WebdriverManager.getDriver())
                    .withTimeout(Duration.ofSeconds(maxSeconds))
                    .pollingEvery(Duration.ofMillis(200))
                    .ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (TimeoutException e) {
            return false;  // element never appeared (acceptable)
        }
    }

    public boolean waitForElementNotDisplayed(By by, int maxSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(WebdriverManager.getDriver(), Duration.ofSeconds(maxSeconds));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void waitForSpinnerFinish(int waitTime) {
        if (waitForElementDisplayed(By.xpath(SPINNER), 2)) {
            waitForElementNotDisplayed(By.xpath(SPINNER), waitTime);
        }
    }
}
