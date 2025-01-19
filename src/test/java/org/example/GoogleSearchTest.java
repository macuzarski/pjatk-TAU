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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GoogleSearchTest {

    private Map<String, WebDriver> drivers = new HashMap<>();

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        // Initialize drivers for different browsers
        drivers.put("Chrome", new ChromeDriver());
        drivers.put("Firefox", new FirefoxDriver());
        drivers.put("Edge", new EdgeDriver());
    }

    @Test
    public void testGoogleSearch() {
        for (Map.Entry<String, WebDriver> entry : drivers.entrySet()) {
            WebDriver driver = entry.getValue();
            String browser = entry.getKey();
            driver.get("https://www.google.com");

            // Assertion 1: Verify the title contains "Google"
            assertTrue(driver.getTitle().contains("Google"), "Title did not contain 'Google'");

            // Assertion 2: Check if search box is present
            WebElement searchBox = driver.findElement(By.name("q"));
            assertNotNull(searchBox, "Search box not found");

            // Type in search query
            searchBox.sendKeys("Selenium with Java");
            searchBox.submit();

            // Assertion 3: Verify the results page title
            assertTrue(driver.getTitle().contains("Selenium with Java"));

            // Assertion 4: Check if results are displayed
            WebElement results = driver.findElement(By.id("search"));
            assertNotNull(results, "Results not displayed");

            // Assertion 5: Verify there is at least one result
            assertTrue(driver.findElements(By.cssSelector("h3")).size() > 0, "No results found");

            // Assertion 6: Check if one of the results contains the word "Selenium"
            assertTrue(driver.findElements(By.cssSelector("h3")).stream().anyMatch(e -> e.getText().contains("Selenium")),
                    "No result contains the word 'Selenium'");

            // Assertion 7: Check if URL contains the search query
            assertTrue(driver.getCurrentUrl().contains("Selenium+with+Java"), "URL did not contain search query");

            // Assertion 8: Check if 'Images' link is available on the results page
            WebElement imagesLink = driver.findElement(By.linkText("Images"));
            assertNotNull(imagesLink, "Images link not found on results page");

            driver.quit();
        }
    }

    @AfterEach
    public void tearDown() {
        // Quit all drivers
        drivers.values().forEach(WebDriver::quit);
    }
}
