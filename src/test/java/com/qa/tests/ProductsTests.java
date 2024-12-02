package com.qa.tests;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsDetailsPage;
import com.qa.pages.ProductsPage;
import com.qa.pages.SettingsPage;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.*;


import java.io.InputStream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ProductsTests extends BaseTest {
    LoginPage loginPage;
    ProductsPage productsPage;
    ProductsDetailsPage productDetailsPage;
    SettingsPage settingsPage;
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
    public void afterClass() {

    }

    @BeforeMethod
    public void beforeMethod() {
        loginPage = new LoginPage();
        productsPage = new ProductsPage();
        settingsPage = new SettingsPage();
        loginPage.login(data.getJSONObject("loginScreen").getString("validUsername"), data.getJSONObject("loginScreen").getString("validPassword"));

    }

    @AfterMethod
    public void afterMethod() {
        if (platformName.equals("Android")) {
            productsPage.pressSettingsBtn();
        } else {
            tap(driver, 30, 62);
        }

        settingsPage.pressLogoutBtn();
    }

    @Test
    public void validateProduct() {


        String tittle = productsPage.getTitle();
        String expectedTittle = data.getJSONObject("productsScreen").getString("title");
        assertTrue(productsPage.productsTittle.isDisplayed());
        assertEquals(tittle, expectedTittle);


        String expectedSlbTittle = data.getJSONObject("productsScreen").getString("SLBTitle");
        String slbTittle = productsPage.getProductTitle(productsPage.SLBTittle);
        assertTrue(productsPage.SLBTittle.isDisplayed());
        assertEquals(slbTittle, expectedSlbTittle);

        String expectedSlbPrice = data.getJSONObject("productsScreen").getString("SLBPrice");
        String slbPrice = productsPage.getProductPrice(productsPage.SLBPrice);
        assertTrue(productsPage.SLBPrice.isDisplayed());
        assertEquals(slbPrice, expectedSlbPrice);


    }

    @Test
    public void validateProductOnProductsDetailedPage() {
        productDetailsPage = new ProductsDetailsPage();


        productsPage.pressProductTitle(productsPage.SLBTittle);

        String expectedSlbTittle = data.getJSONObject("productsDetailsScreen").getString("SLBTitle");
        String slbTittle = productDetailsPage.getTitle();
        assertTrue(productDetailsPage.title.isDisplayed());
        assertEquals(slbTittle, expectedSlbTittle);

        String expectedDescription = data.getJSONObject("productsDetailsScreen").getString("SLBDescription");
        String slbDescription = productDetailsPage.getDescription();
        assertTrue(productDetailsPage.description.isDisplayed());
        assertEquals(slbDescription, expectedDescription);

    }


}
