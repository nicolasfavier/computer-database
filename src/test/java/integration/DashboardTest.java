package integration;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DashboardTest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		// On instancie notre driver, et on configure notre temps d'attente
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8080/computer-database/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// On se connecte au site
		driver.get(baseUrl);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			// fail(verificationErrorString);
		}
	}

	@Test
	public void testSelenium() throws Exception {

		WebElement searchbox = driver.findElement(By.id("searchbox"));
		WebElement searchsubmit = driver.findElement(By.id("searchsubmit"));

		searchbox.clear();
		searchbox.sendKeys("bonjour tout le monde ca va");
		searchsubmit.click();

	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

}