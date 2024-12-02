package com.qa.pages;

import com.qa.BaseTest;
import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class LoginPage extends BaseTest {
    public LoginPage() {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(accessibility = "test-Username")
    @iOSXCUITFindBy(accessibility = "test-Username")
    public WebElement username;

    @AndroidFindBy(accessibility = "test-Password")
    @iOSXCUITFindBy(accessibility = "test-Password")
    public WebElement password;

    @AndroidFindBy(accessibility = "test-LOGIN")
    @iOSXCUITFindBy(accessibility = "test-LOGIN")
    public WebElement login;


    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Username and password do not match any user in this service.\"]")
    @iOSXCUITFindBy(accessibility = "test-Error message")
    public WebElement errorMessage;

    public void enterUserName(String usernameTxt) {
        clear(username);
        sendKeys(username, usernameTxt);
    }

    public void enterPassword(String passwordTxt) {
        sendKeys(password, passwordTxt);
    }

    public void pressLoginButton() {
        click(login);
    }

    public String getErrorTxt() {
        return errorMessage.getText();
    }

    public void login(String username, String password) {
        enterUserName(username);
        enterPassword(password);
        pressLoginButton();
    }




}
