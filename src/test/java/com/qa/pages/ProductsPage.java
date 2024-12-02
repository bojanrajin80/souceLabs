package com.qa.pages;

import com.qa.BaseTest;
import com.qa.MenuPage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ProductsPage extends MenuPage {

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"PRODUCTS\"]")
    @iOSXCUITFindBy(iOSNsPredicate = "name == 'PRODUCTS' AND label == 'PRODUCTS' AND value == 'PRODUCTS'")
    public WebElement productsTittle;

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"test-Item title\" and @text=\"Sauce Labs Backpack\"]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"test-Item title\" and @label=\"Sauce Labs Backpack\"]")
    public WebElement SLBTittle;

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"test-Price\" and @text=\"$29.99\"]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"test-Price\" and @label=\"$29.99\"]")
    public WebElement SLBPrice;

    @AndroidFindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"test-ADD TO CART\"])[1]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeOther[@name=\"test-ADD TO CART\"])[1]")
    public WebElement SLBAddToCartBtn;

    public String getTitle() {
        return productsTittle.getText();
    }

    public String getProductTitle(WebElement e) {
        return e.getText();
    }

    public String getProductPrice(WebElement e) {
        return e.getText();
    }

    public void pressAddToCart(WebElement e) {
        click(e);
    }

    public void pressProductTitle(WebElement e) {
        click(e);
    }
}
