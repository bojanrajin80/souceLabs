package com.qa.tests;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.*;


import java.io.InputStream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginTests extends BaseTest {
    LoginPage loginPage;
    ProductsPage productsPage;

    InputStream dataIs;
    JSONObject data;

    @BeforeClass
    public void beforeClass() throws Exception {
        try {
            String dataFileName = "data/string.json";
            dataIs = getClass().getClassLoader().getResourceAsStream(dataFileName);
            JSONTokener tokener = new JSONTokener(dataIs);

            data = new JSONObject(tokener);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (dataIs != null) {
                dataIs.close();
            }
        }

        closeApp();
        launchApp();
    }


    @AfterClass
    public void afterClass() throws Exception {

    }

    @BeforeMethod
    public void beforeMethod() {
        loginPage = new LoginPage();

    }

    @AfterMethod
    public void afterMethod() {

    }

    @Test
    public void invalidUserName() {
        loginPage.enterUserName(data.getJSONObject("loginScreen").getString("invalidUsername"));
        loginPage.enterPassword(data.getJSONObject("loginScreen").getString("validPassword"));
        loginPage.pressLoginButton();
        assertTrue(loginPage.errorMessage.isDisplayed());
        String expectedErrorMessage = data.getJSONObject("loginScreen").getString("loginErrorMessage");
        assertEquals(expectedErrorMessage, loginPage.getErrorTxt());
    }

    @Test
    public void invalidPassword() {
        loginPage.enterUserName(data.getJSONObject("loginScreen").getString("validUsername"));
        loginPage.enterPassword(data.getJSONObject("loginScreen").getString("invalidPassword"));
        loginPage.pressLoginButton();
        assertTrue(loginPage.errorMessage.isDisplayed());
        String expectedErrorMessage = data.getJSONObject("loginScreen").getString("loginErrorMessage");
        assertEquals(expectedErrorMessage, loginPage.getErrorTxt());
    }

    @Test
    public void succesfullLogin() {
        productsPage = new ProductsPage();
        loginPage.enterUserName(data.getJSONObject("loginScreen").getString("validUsername"));
        loginPage.enterPassword(data.getJSONObject("loginScreen").getString("validPassword"));
        loginPage.pressLoginButton();
        String tittle = productsPage.getTitle();
        String expectedTittle = data.getJSONObject("productsScreen").getString("title");
        assertEquals(tittle, expectedTittle);
        assertTrue(productsPage.productsTittle.isDisplayed());
    }
}
