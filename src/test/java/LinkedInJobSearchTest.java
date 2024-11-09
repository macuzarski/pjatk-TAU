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

public class LinkedInJobSearchTest {

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
    public void testLinkedInJobSearch() {
        for (Map.Entry<String, WebDriver> entry : drivers.entrySet()) {
            WebDriver driver = entry.getValue();
            String browser = entry.getKey();

            System.out.println("Running test on: " + browser);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Step 1: Open LinkedIn Jobs page
            driver.get("https://www.linkedin.com/jobs/");
            assertTrue(driver.getTitle().contains("Jobs"), "Title did not contain 'Jobs'");

            // Step 2: Enter job title and location in the search fields
            WebElement jobSearchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Search jobs']")));
            assertNotNull(jobSearchBox, "Job search box not found");
            jobSearchBox.sendKeys("Software Engineer");

            WebElement locationBox = driver.findElement(By.cssSelector("input[placeholder='Search location']"));
            assertNotNull(locationBox, "Location search box not found");
            locationBox.clear();
            locationBox.sendKeys("San Francisco, CA");

            // Step 3: Submit the search
            WebElement searchButton = driver.findElement(By.cssSelector("button.jobs-search-box__submit-button"));
            assertNotNull(searchButton, "Search button not found");
            searchButton.click();

            // Step 4: Verify that job results are displayed
            WebElement jobResults = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.jobs-search__results-list")));
            assertNotNull(jobResults, "Job results not displayed");

            // Step 5: Verify presence of the first job item
            WebElement firstJob = jobResults.findElement(By.tagName("li"));
            assertNotNull(firstJob, "No jobs found in search results");

            // Step 6: Verify job title and company name are visible
            WebElement jobTitle = firstJob.findElement(By.cssSelector("h3.base-search-card__title"));
            WebElement companyName = firstJob.findElement(By.cssSelector("h4.base-search-card__subtitle"));
            assertNotNull(jobTitle, "Job title not found in first result");
            assertNotNull(companyName, "Company name not found in first result");

            // Step 7: Check location for the first job listing
            WebElement jobLocation = firstJob.findElement(By.cssSelector("span.job-search-card__location"));
            assertNotNull(jobLocation, "Job location not found in first result");
            assertTrue(jobLocation.getText().contains("San Francisco"), "Job location does not match search location");

            // Step 8: Verify "Save" button exists for the job listing
            WebElement saveButton = firstJob.findElement(By.tagName("button"));
            assertNotNull(saveButton, "Save button not found for the job listing");

            System.out.println("Test completed successfully on: " + browser);
        }
    }

    @AfterEach
    public void tearDown() {
        // Quit each driver after tests complete
        drivers.values().forEach(WebDriver::quit);
    }
}
