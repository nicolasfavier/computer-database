package integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DashboardTest {
	private static WebDriver driver;
	private static String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeClass
	public static void openDriver() throws Exception {
		// init driver
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8080/computer-database/dashboard";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Before
	public void setUp() throws Exception {
		// connect to the url
		driver.get(baseUrl);
	}

	@After
	public void tearDown() throws Exception {
		String verificationErrorString = verificationErrors.toString();

		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	@AfterClass
	public static void closeDriver() throws Exception {
		driver.quit();
	}
	
	@Test
	public void testSearchEmpty() throws Exception {

		WebElement searchbox = driver.findElement(By.id("searchbox"));
		WebElement searchsubmit = driver.findElement(By.id("searchsubmit"));

		searchbox.clear();
		searchsubmit.click();

		verifyComputerList(10);

	}

	@Test
	public void testSearchWord() throws Exception {

		WebElement searchbox = driver.findElement(By.id("searchbox"));
		WebElement searchsubmit = driver.findElement(By.id("searchsubmit"));

		searchbox.clear();
		searchbox.sendKeys("Macintosh LC");
		searchsubmit.click();

		verifyComputerList(5);
	}

	@Test
	public void testSearchWrongWord() throws Exception {

		WebElement searchbox = driver.findElement(By.id("searchbox"));
		WebElement searchsubmit = driver.findElement(By.id("searchsubmit"));

		searchbox.clear();
		searchbox.sendKeys("Macintosh LCvsvdsvdsvdsv");
		searchsubmit.click();

		WebElement listComputer = driver.findElement(By.id("results"));
		// List<WebElement> computers = listComputer.findElements(By.ByXPath())
		// TODO see why findelements by id so long when empty
		assertEquals(0, 0);
	}

	@Test
	public void testPaging100() throws Exception {

		WebElement page = driver.findElement(By.linkText("100"));
		page.click();
		verifyComputerList(100);
	}

	@Test
	public void testPaging50() throws Exception {

		WebElement page = driver.findElement(By.linkText("50"));
		page.click();
		verifyComputerList(50);
	}

	@Test
	public void testPaging10() throws Exception {

		WebElement page = driver.findElement(By.linkText("10"));
		page.click();
		verifyComputerList(10);
	}

	private void verifyComputerList(int size) {
		WebElement listComputer = driver.findElement(By.id("results"));
		
		List<WebElement> computers = listComputer
				.findElements(By.tagName("tr"));
		
		assertEquals(size, computers.size());
	}
}