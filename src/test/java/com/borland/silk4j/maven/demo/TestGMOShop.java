/**
 * (C) Copyright 1992-2021 Micro Focus or one of its affiliates.
 * The only warranties for products and services of Micro Focus and its
 * affiliates and licensors ("Micro Focus") are as may be set forth in the
 * express warranty statements accompanying such products and services. Nothing
 * herein should be construed as constituting an additional warranty.
 * Micro Focus shall not be liable for technical or editorial errors or
 * omissions contained herein. The information contained herein is subject to
 * change without notice.
 */
package com.borland.silk4j.maven.demo;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.borland.silk.keyworddriven.annotations.Keyword;
import com.borland.silktest.jtf.BrowserBaseState;
import com.borland.silktest.jtf.Desktop;
import com.borland.silktest.jtf.common.types.FindOptions;
import com.borland.silktest.jtf.common.types.MouseButton;
import com.borland.silktest.jtf.common.types.Point;
import com.borland.silktest.jtf.xbrowser.BrowserApplication;
import com.borland.silktest.jtf.xbrowser.BrowserWindow;
import com.borland.silktest.jtf.xbrowser.DomButton;
import com.borland.silktest.jtf.xbrowser.DomElement;
import com.borland.silktest.jtf.xbrowser.DomLink;
import com.borland.silktest.jtf.xbrowser.DomListBox;
import com.borland.silktest.jtf.xbrowser.DomTextField;

/**
 * <p>
 * The test cases in this class show how different workflows inside the GMO sample application can be tested
 * with Silk Test. All scripts can be executed with Internet Explorer, Mozilla Firefox, Google Chrome, and Microsoft Edge.
 * </p>
 * <p>
 * When creating tests, you can identify the elements in the AUT by using either XPath expressions or object map identifiers.
 * When using object map ids, the mapping between an object map id and the corresponding XPath locator is defined in
 * an object map. This sample test uses object map ids, because using object map ids increases the reusability of test
 * scripts.
 * </p>
 * <p>
 * To increase the replay speed of the tests, you can disable screenshot capturing. To do so, navigate to
 * Silk4J -> Edit Options -> Truelog and set the Screenshot Mode to 'On Error'.
 * </p>
 */
public class TestGMOShop {

  private Desktop desktop = new Desktop();
  private static BrowserApplication browser;
  private BrowserBaseState baseState;

  @Keyword(value = "Start application", isBaseState = true, description = "Run browser + navigate to webshop")
  @Before
  public void baseState() {
    // Go to web page 'http://demo.borland.com/gmoajax/'
	// new url https://demo.borland.com/gmoajax/catalog.php
    baseState = new BrowserBaseState();
    desktop = new Desktop();
    baseState.execute(desktop);
    // find object map entry
    browser = desktop.find("gmo_store");
    browser.maximize();
    gmoWebPage().navigate("https://demo.borland.com/gmoajax/");
  }

  private BrowserWindow gmoWebPage() {
    return browser.find("BrowserWindow");
  }
  
  @Keyword(value = "Close Browser", description = "close browser window")
  @After
  public void resetOptions() {
	browser.close();
    desktop.resetAllOptions();
  }

  @Keyword(value = "Buy and Checkout", description = "navigate through catalog and add item to shopping cart")
  @Test
  public void testBuyWithCheckout() {
    gmoWebPage().<DomLink> find("storeBtn").click();

    // Check if cart is empty. If not, clear the cart.
    DomElement cartQuantity1 = gmoWebPage().<DomElement> find("Cart Quantity");
    if (!cartQuantity1.getText().equals("0")) {
      gmoWebPage().<DomElement> find("showCart").click();
      gmoWebPage().<DomElement> find("cart trash image").domClick();
      gmoWebPage().<DomElement> find("showCart").click(); // Hide cart again.
    }

    // Add some items to the cart.
    gmoWebPage().<DomButton> find("addButton").domClick();
    DomElement cartQuantity2 = gmoWebPage().<DomElement> find("Cart Quantity");
    assertThat(cartQuantity2.getText(), equalTo("1"));

    gmoWebPage().<DomButton> find("addButton2").domClick();
    DomElement cartQuantity3 = gmoWebPage().<DomElement> find("Cart Quantity");
    assertThat(cartQuantity3.getText(), equalTo("2"));

    gmoWebPage().<DomTextField> find("qtyInput").click();
    gmoWebPage().<DomTextField> find("qtyInput").setText("13");
    gmoWebPage().<DomButton> find("addButton3").domClick();
    DomElement cartQuantity4 = gmoWebPage().<DomElement> find("Cart Quantity");
    assertThat(cartQuantity4.getText(), equalTo("3"));

    gmoWebPage().<DomElement> find("showCart").click();
    DomElement cartTotal = gmoWebPage().<DomElement> find("Cart Total");
    assertThat(cartTotal.getText(), equalTo("Total : £395.85"));
    gmoWebPage().<DomElement> find("cart confirm button").click();

    // Verifications of the content of the cart modal dialog.
    DomElement item1quantity = gmoWebPage().<DomElement> find("Modal Cart.Row1.Quantity");
    assertThat(item1quantity.getText(), equalTo("1"));

    DomElement item1price = gmoWebPage().<DomElement> find("Modal Cart.Row1.Price");
    assertThat(item1price.getText(), equalTo("£114.99"));

    DomElement item2quantity = gmoWebPage().<DomElement> find("Modal Cart.Row2.Quantity");
    assertThat(item2quantity.getText(), equalTo("1"));

    DomElement item2price = gmoWebPage().<DomElement> find("Modal Cart.Row2.Price");
    assertThat(item2price.getText(), equalTo("£98.99"));

    DomElement item3quantity = gmoWebPage().<DomElement> find("Modal Cart.Row3.Quantity");
    assertThat(item3quantity.getText(), equalTo("13"));

    DomElement item3price = gmoWebPage().<DomElement> find("Modal Cart.Row3.Price");
    assertThat(item3price.getText(), equalTo("£181.87"));

    DomElement modalCartTotal = gmoWebPage().<DomElement> find("Modal Cart.Total");
    assertThat(modalCartTotal.getText(), equalTo("Total : £395.85"));

    gmoWebPage().<DomLink> find("modalBtn Confirm").domClick();

    gmoWebPage().<DomTextField> find("full_name Input").click();
    gmoWebPage().<DomTextField> find("full_name Input").typeKeys("John Smith");

    gmoWebPage().<DomTextField> find("email").setText("john.smith@gmail.com");

    gmoWebPage().<DomTextField> find("phone").setText("4312345678");

    gmoWebPage().<DomTextField> find("address").click();
    gmoWebPage().<DomTextField> find("address").typeKeys("Borland Street 42");

    gmoWebPage().<DomTextField> find("address2").setText("9/2");

    gmoWebPage().<DomTextField> find("city").click();
    gmoWebPage().<DomTextField> find("city").typeKeys("Linz");

    gmoWebPage().<DomTextField> find("zip").click();
    gmoWebPage().<DomTextField> find("zip").typeKeys("4020");

    gmoWebPage().<DomListBox> find("countries").select("Country...");
    gmoWebPage().<DomListBox> find("countries").select("Austria");

    gmoWebPage().<DomTextField> find("pay_name").click();
    gmoWebPage().<DomTextField> find("pay_name").typeKeys("JOHN SMITH");

    gmoWebPage().<DomTextField> find("card_num Input").setText("1234566789001234");

    gmoWebPage().<DomTextField> find("exp_date").setText("022065");

    gmoWebPage().<DomTextField> find("cvv").setText("321");

    gmoWebPage().<DomElement> find("submitFormIMG").click();

    gmoWebPage().<DomButton> find("confirmBtn").click();

    // If a progress indicator is present, wait for it to disappear. Then we know that we can continue.
    DomElement progressIndicator = gmoWebPage().<DomElement> find("//p[@id='loadTxt']", new FindOptions(false, 2000));
    if (progressIndicator != null) {
      progressIndicator.waitForDisappearance(30000);
    }

    DomElement headerTotal = gmoWebPage().<DomElement> find("headerTotal");
    assertThat(headerTotal.getText(), equalTo("£395.85"));

    DomElement full_name = gmoWebPage().<DomElement> find("full_name");
    assertThat(full_name.getText(), equalTo("John Smith"));

    DomElement card_num = gmoWebPage().<DomElement> find("card_num");
    // wrong compare, see card number setting above
    //assertThat(card_num.getText(), equalTo("1234-5667-8900-1234"));
    assertThat(card_num.getText(), equalTo("1234566789001234"));
    
    gmoWebPage().<DomButton> find("FinishOrder").click(MouseButton.LEFT, new Point(131, 21));
  }

  @Keyword(value = "Navigate from About back to home page via menu", description = "navigate page back via menu")
  @Test
  public void testAboutPageBackViaMenu() {
    gmoWebPage().<DomLink> find("aboutBtn").domClick();
    DomElement sctGMOTitle = gmoWebPage().<DomElement> find("sctGMO title");
    assertThat(sctGMOTitle.getText(), equalTo("Green Mountain Outpost Demo"));
    gmoWebPage().<DomElement> find("showMenu").domClick();
    gmoWebPage().<DomLink> find("menuHome").domClick();
    gmoWebPage().<DomLink> find("storeBtn"); // Find "Store" link to check if we are back on the main page.
  }

  /**
   * This test uses XPath locators instead of object map ids.
   */
  @Keyword(value = "Clear Cart", description = "clear shopping cart")
  @Test
  public void testClearCart() {
    BrowserWindow window = desktop.find("//BrowserWindow");

    window.<DomElement> find("//a[@id='storeBtn']").click();

    // Add items to the cart.
    window.<DomElement> find("//li[@id='item'][1]/button[@id='addButton']").click(MouseButton.LEFT, new Point(65, 69));
    window.<DomElement> find("//li[@id='item'][2]/button[@id='addButton']").click(MouseButton.LEFT, new Point(65, 69));

    // Verify that the cart is not empty.
    DomElement cartQuantityNotEmpty = window.find("//A[@title='Cart Qty']");
    assertThat(cartQuantityNotEmpty.getText(), not(equalTo("0")));

    window.<DomElement> find("//img[@id='showCart']").click();

    // Empty the cart.
    window.<DomElement> find("//img[@id='cart trash image']").domClick();

    // Verify that the cart is empty.
    DomElement cartQuantityEmpty = window.find("//A[@title='Cart Qty']");
    assertThat(cartQuantityEmpty.getText(), equalTo("0"));
  }

}
