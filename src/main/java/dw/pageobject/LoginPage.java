package dw.pageobject;

import org.openqa.selenium.By;

public class LoginPage extends AbstractPageObject {

    public static final String CSS_FIELD_USERNAME = "[data-testing-id='user-input']";
    public static final String CSS_FIELD_PASSWORD = "[data-testing-id='password-input']";
    public static final String CSS_BUTTON_LOGIN = "[data-testing-id='login-button']";
    public static final String TAG_ACTIVE_SESSION = "datawalk-session";

    public LoginPage typeUsername(String userLogin) {
        fillInputByChar(By.cssSelector(CSS_FIELD_USERNAME), userLogin);
        return this;
    }

    public LoginPage typePassword(String text) {
        fillInputByChar(By.cssSelector(CSS_FIELD_PASSWORD), text);
        return this;
    }

    public void clickLogin() {
        click(findElementByCssSelector(CSS_BUTTON_LOGIN));
    }

    public void logOn(String loginPage, String user, String password) {

        openUrl(loginPage);

        waitForElementDisplayed(By.cssSelector(CSS_BUTTON_LOGIN), 30);
        typeUsername(user);
        typePassword(password);
        clickLogin();
    }

    public void logOnInActiveSession(String password) {
        clickLogin();
        waitForElementDisplayed(By.cssSelector(CSS_FIELD_PASSWORD), 10);
        typePassword(password);
        clickLogin();
    }

    public boolean isActiveSession(){
        return isElementDisplayed(By.tagName(TAG_ACTIVE_SESSION), 5);
    }
}
