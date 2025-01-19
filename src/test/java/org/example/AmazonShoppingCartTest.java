package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AmazonShoppingCartTest {

    private Map<String, WebDriver> drivers = new HashMap<>();

    @BeforeEach
    public void setUp() {
        // Set system properties for each browser's driver
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        // Initialize WebDriver instances for each browser
        drivers.put("Chrome", new ChromeDriver());
        drivers.put("Firefox", new FirefoxDriver());
        drivers.put("Edge", new EdgeDriver());

        // Maximize all browser windows for consistency
        drivers.values().forEach(driver -> driver.manage().window().maximize());
    }

    @Test
    public void testAmazonShoppingCart() {
        for (Map.Entry<String, WebDriver> entry : drivers.entrySet()) {
            WebDriver driver = entry.getValue();
            String browser = entry.getKey();

            System.out.println("Running test on: " + browser);

            // Step 1: Open Amazon website
            driver.get("https://www.amazon.com");
            assertTrue(driver.getTitle().contains("Amazon"), "Title did not contain 'Amazon'");

            // Step 2: Search for an item (e.g., "headphones")
            WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
            assertNotNull(searchBox, "Search box not found");
            searchBox.sendKeys("headphones");
            searchBox.submit();

            // Step 3: Wait and select the first item in search results
            WebElement firstItem = driver.findElements(By.cssSelector("div.s-main-slot div[data-component-type='s-search-result']")).get(0);
            assertNotNull(firstItem, "First item not found in search results");
            firstItem.click();

            // Step 4: Add the item to the cart
            WebElement addToCartButton = driver.findElement(By.id("add-to-cart-button"));
            assertNotNull(addToCartButton, "Add to Cart button not found");
            addToCartButton.click();

            // Step 5: Verify that the cart count was updated
            WebElement cartCount = driver.findElement(By.id("nav-cart-count"));
            assertNotNull(cartCount, "Cart count not found");
            assertEquals("1", cartCount.getText(), "Cart count did not update");

            // Step 6: Open the cart page
            WebElement cartButton = driver.findElement(By.id("nav-cart"));
            cartButton.click();
            assertTrue(driver.getTitle().contains("Amazon.com Shopping Cart"), "Did not navigate to the shopping cart page");

            // Step 7: Verify item details in the cart
            WebElement cartItem = driver.findElement(By.cssSelector("div.sc-list-item-content"));
            assertNotNull(cartItem, "Item not found in cart");
            WebElement cartItemTitle = cartItem.findElement(By.cssSelector("span.a-truncate-cut"));
            assertTrue(cartItemTitle.getText().toLowerCase().contains("headphones"), "Item in cart does not match search query");

            // Step 8: Verify subtotal is displayed
            WebElement subtotal = driver.findElement(By.id("sc-subtotal-amount-activecart"));
            assertNotNull(subtotal, "Subtotal not found in cart");
            assertTrue(subtotal.getText().contains("$"), "Subtotal does not contain a dollar sign");

            System.out.println("Test completed successfully on: " + browser);
        }
    }

    @AfterEach
    public void tearDown() {
        // Quit each driver after tests complete
        drivers.values().forEach(WebDriver::quit);
    }
}
