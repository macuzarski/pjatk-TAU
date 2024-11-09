import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class WikipediaContentNavigationTest {

    private Map<String, WebDriver> drivers = new HashMap<>();

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        // Initialize drivers for different browsers
        drivers.put("Chrome", new ChromeDriver());
        drivers.put("Firefox", new FirefoxDriver());
        drivers.put("Edge", new EdgeDriver());

        // Maximize all browser windows for consistency
        drivers.values().forEach(driver -> driver.manage().window().maximize());
    }

    @Test
    public void testWikipediaContentNavigation() {
        for (Map.Entry<String, WebDriver> entry : drivers.entrySet()) {
            WebDriver driver = entry.getValue();
            String browser = entry.getKey();

            System.out.println("Running test on: " + browser);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 1: Open Wikipedia homepage
            driver.get("https://www.wikipedia.org/");
            assertTrue(driver.getTitle().contains("Wikipedia"), "Title did not contain 'Wikipedia'");

            // Step 2: Search for a term (e.g., "Python programming")
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchInput")));
            assertNotNull(searchBox, "Search box not found on Wikipedia homepage");
            searchBox.sendKeys("Python programming");
            searchBox.submit();

            // Step 3: Click on the "Python (programming language)" article link
            WebElement articleLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Python (programming language)")));
            assertNotNull(articleLink, "Python (programming language) link not found in search results");
            articleLink.click();

            // Step 4: Verify that the first heading is "Python (programming language)"
            WebElement firstHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstHeading")));
            assertEquals("Python (programming language)", firstHeading.getText(), "Heading did not match expected text");

            // Step 5: Verify the "History" section exists
            WebElement historySection = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("History")));
            assertNotNull(historySection, "History section not found in the article");

            // Step 6: Verify the "External links" section exists
            WebElement externalLinksSection = driver.findElement(By.id("External_links"));
            assertNotNull(externalLinksSection, "External links section not found in the article");

            // Step 7: Verify that there are external links listed
            WebElement externalLinksList = externalLinksSection.findElement(By.xpath("following-sibling::ul"));
            assertTrue(externalLinksList.findElements(By.tagName("li")).size() > 0, "No external links found in the 'External links' section");

            // Step 8: Verify "References" section exists
            WebElement referencesSection = driver.findElement(By.id("References"));
            assertNotNull(referencesSection, "References section not found in the article");

            System.out.println("Test completed successfully on: " + browser);
        }
    }

    @AfterEach
    public void tearDown() {
        // Quit each driver after tests complete
        drivers.values().forEach(WebDriver::quit);
    }
}
