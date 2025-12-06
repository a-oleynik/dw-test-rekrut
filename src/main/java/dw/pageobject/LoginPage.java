package dw.pageobject;

import org.openqa.selenium.By;

public class LoginPage extends AbstractPageObject {

    public static final String CSS_FIELD_USERNAME = "[data-testing-id='user-input']";
    public static final String CSS_FIELD_PASSWORD = "[data-testing-id='password-input']";
    public static final String CSS_BUTTON_LOGIN = "[data-testing-id='login-button']";//"//div[@location='login']//button[@type='submit']/span";
    public static final String TAG_ACTIVE_SESSION = "datawalk-session";

    public LoginPage typeUsername(String userLogin) {
        fillInputByChar(By.cssSelector(CSS_FIELD_USERNAME), userLogin);
        return this;
    }

    public LoginPage typePassword(String text) {
        fillInputByChar(By.cssSelector(CSS_FIELD_PASSWORD), text);
        return this;
    }

    public void clickToLogin() {
        click(findElementByCssSelector(CSS_BUTTON_LOGIN));
    }

    public void logOn(String loginPage, String user, String pwd) {

        openUrl(loginPage);

        waitForElementDisplayed(By.cssSelector(CSS_BUTTON_LOGIN), 30);
        typeUsername(user);
        typePassword(pwd);
        clickToLogin();
    }

    public void logOnInActiveSession(String user, String pwd) {
        clickToLogin();
        waitForElementDisplayed(By.cssSelector(CSS_FIELD_PASSWORD), 10);
        typePassword(pwd);
        clickToLogin();
    }

    public boolean isActiveSession(){
        return isElementDisplayed(By.tagName(TAG_ACTIVE_SESSION), 5);
    }
}
