package com.selenium.functionalTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProductInShoppingCart extends BrowserConfiguration {

	boolean status = false;
	static ExtentReports extent;
	static ExtentTest logger;

	@Test
	public void productInCart() throws InterruptedException, IOException {

		// XPath used for locating
		String shoppingCart = "//i[@class='fa fa-shopping-cart']";
		String storeScreenshot = "c:\\tmp\\product.png";
		String productName = "//*[@id=\"content\"]/form/div/table/tbody/tr/td[2]/a";

		// Clicking on "Shopping Cart" on top navigation bar using Mouse
		// Hover
		Actions actions = new Actions(driver);
		WebElement moveOnShoppingCart = driver.findElement(By.xpath(shoppingCart));
		actions.moveToElement(moveOnShoppingCart).click().perform();

		// Shopping Cart page loads, asserting title to make sure we are on the Shopping
		// Cart page
		assertEquals(driver.getTitle(), PropertyReader.getProperties("shoppingCartPageTitle"));

		// Taking the screenshot of the product added to cart
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(storeScreenshot));

		// Logging out as a customer
		CustomerLogout.logout();

		// Logging in as a customer
		CustomerLogin.login();

		// Viewing cart again check the if data persists
		driver.findElement(By.xpath(shoppingCart)).click();

		// Asserting the product name
		assertTrue(driver.getPageSource().contains(driver.findElement(By.xpath(productName)).getText()));

		status = true;

	}

	@BeforeMethod
	public void beforeMethod() throws InterruptedException, IOException {
		extent = new ExtentReports(
				System.getProperty("user.dir") + "\\ExtentReports\\" + this.getClass().getSimpleName() + "Report.html");
		logger = extent.startTest(this.getClass().getSimpleName());

		// Setting up browser as chrome
		BrowserConfiguration.setup("chrome");

		// Using implicit wait
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		// Maximize the window
		driver.manage().window().maximize();

		// Loading AUT using the Home page Url
		driver.get(PropertyReader.getProperties("customerHomeURL"));

		// Logging in as a customer
		CustomerLogin.login();

		// Adding product to cart
		AddProductToCart.addProductToCart();

	}

	@AfterMethod
	public void afterMethod() {
		if (status) {
			// Logging the test as passed
			logger.log(LogStatus.PASS, "Test Pass");
		} else {
			// Logging the test as failed
			logger.log(LogStatus.FAIL, "Test Failed");
		}
		extent.endTest(logger);
		// deleting the contents of Extent report generated during previous execution.
		extent.flush();

		// quit the whole browser session
		driver.quit();
	}

}
