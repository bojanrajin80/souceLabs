package com.qa;

import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;


import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;


public class BaseTest {
    protected static AppiumDriver driver;
    protected Properties props;
    InputStream inputStream;
    public String platformName;

    @BeforeClass
    public void before() throws Exception {
        props = new Properties();
        String propFileName = "config.properties";
        inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        props.load(inputStream);

        URI appUri = URI.create(props.getProperty("appiumURL"));
        URL url = appUri.toURL();



        platformName = props.getProperty("platformName");

        String appUrl;

        switch (platformName) {
            case "Android":
                appUrl = getClass().getResource(props.getProperty("androidAppLocation")).getFile();
                UiAutomator2Options androidOptions = new UiAutomator2Options();

                androidOptions.setNewCommandTimeout(Duration.ofSeconds(300));

                androidOptions.setUdid(props.getProperty("androidUdId")).
                        setAutomationName(props.getProperty("androidAutomationName")).
                        setAvdLaunchTimeout(Duration.ofSeconds(180)).
                        setAvdReadyTimeout(Duration.ofSeconds(60)).
                        setApp(appUrl).
                        setAppPackage(props.getProperty("androidAppPackage")).
                        setAppActivity(props.getProperty("androidAppActivity"));
                driver = new AndroidDriver(url, androidOptions);

                break;

            case "iOS":
                appUrl = getClass().getResource(props.getProperty("iOSAppLocation")).getFile();
                XCUITestOptions iOSOptions = new XCUITestOptions();
                iOSOptions.setBundleId(props.getProperty("iOSBundleId")).
                        setAutomationName(props.getProperty("iOSAutomationName")).
                        setDeviceName(props.getProperty("iOSDeviceName")).
                        setPlatformVersion(props.getProperty("iOSPlatformVersion")).
                        setApp(appUrl);

                driver = new IOSDriver(url, iOSOptions);
                break;

            default:
                throw new Exception("Invalid platform: - " + platformName);

        }

    }


    public void waitForVisibility(WebElement e) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestUtils.WAIT));
        wait.until(ExpectedConditions.visibilityOf(e));
    }


    public void click(WebElement e) {
        waitForVisibility(e);
        e.click();
    }


    public void sendKeys(WebElement e, String txt) {
        waitForVisibility(e);
        e.sendKeys(txt);
    }

    public void clear(WebElement e) {
        waitForVisibility(e);
        e.clear();
    }

    public String getAttribute(WebElement e, String attribute) {
        waitForVisibility(e);
        return e.getAttribute(attribute);
    }

    public void closeApp() throws Exception {

        if (platformName == null) {
            throw new Exception("Platform name is not initialized. Check the config file.");
        }

        switch (platformName) {
            case "Android":
                ((InteractsWithApps) driver).terminateApp(props.getProperty("androidAppPackage"));
                break;
            case "iOS":
                ((InteractsWithApps) driver).terminateApp(props.getProperty("iOSBundleId"));
                break;
            default:
                throw new Exception("Invalid platform: " + platformName);
        }
    }

    public void launchApp() throws Exception {
        if (platformName == null) {
            throw new Exception("Platform name is not initialized. Check the config file.");
        }

        switch (platformName) {
            case "Android":
                ((InteractsWithApps) driver).activateApp(props.getProperty("androidAppPackage"));
                break;
            case "iOS":
                ((InteractsWithApps) driver).activateApp(props.getProperty("iOSBundleId"));
                break;
            default:
                throw new Exception("Invalid platform: - " + platformName);
        }
    }

    public static void tap(AppiumDriver driver, int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence sequence = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger, Duration.ofMillis(150)))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(sequence));

    }

    @AfterTest
    public void after() throws Exception {
        driver.quit();

    }
}
