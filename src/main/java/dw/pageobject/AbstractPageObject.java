package dw.pageobject;

import dw.UITestBase;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class AbstractPageObject extends UITestBase {

    public static String SPINNER = "//div[@class='spinner-overlay' or @class='loading-indicator' or @data-ng-if='saveGraph.loading$ | async:this'][not(@disabled)]";


    public void click(String xpath) {
        findElementByXpath(xpath, 1).click();
    }

    public void actionClick(WebElement element) {
        Actions act = new Actions(driver);
        act.click(element).build().perform();
    }

    public void actionFillInput(WebElement element, String text) {
        Actions act = new Actions(driver);
        act.sendKeys(element, text).build().perform();
    }

    public void click(WebElement el) {
        el.click();
    }

    public WebElement findElementBy(By by) {
        return driver.findElement(by);
    }

    public List<WebElement> findElementsBy(By by) {
        return driver.findElements(by);
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxWaitSec));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isElementDisplayed(String xpath, int maxWaitSec) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxWaitSec));
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
        Actions actions = new Actions(driver);
        actions.click(element)
                .sendKeys(element, text)
                .build().perform();
    }


    public void openUrl(String url) {
        driver.get(url);
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
            Wait<WebDriver> wait = new FluentWait<>(driver)
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
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxSeconds));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            return true;
        }
        catch (TimeoutException e){
            return false;
        }
    }

    public void waitForSpinnerFinish(int waitTime) {
        if (waitForElementDisplayed(By.xpath(SPINNER), 2)) {
            waitForElementNotDisplayed(By.xpath(SPINNER), waitTime);
        }
    }
}
