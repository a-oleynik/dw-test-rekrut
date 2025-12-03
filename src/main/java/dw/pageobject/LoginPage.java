package dw.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends AbstractPageObject {

    public static final String ID_FIELD_USERNAME = "username-input";
    public static final String ID_FIELD_PASSWORD = "password-input";
    public static final String XPATH_BUTTON_LOGIN = "//div[@location='login']//button[@type='submit']/span";

    public LoginPage typeUsername(String userLogin) {
        fillInputByChar(By.id(ID_FIELD_USERNAME), userLogin);
        return this;
    }

    public LoginPage typePassword(String text) {
        fillInputByChar(By.id(ID_FIELD_PASSWORD), text);
        return this;
    }

    public void clickToLogin() {
        click(findElementByXpath(XPATH_BUTTON_LOGIN));
    }

    public void logOn(String loginPage, String user, String pwd) throws InterruptedException {

        openUrl(loginPage);

        waitForElementDisplayed(XPATH_BUTTON_LOGIN, 30);

        Thread.sleep(1000);
        typeUsername(user);
        typePassword(pwd);
        clickToLogin();
    }
}
