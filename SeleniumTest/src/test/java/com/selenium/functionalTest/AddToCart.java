package com.selenium.functionalTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddToCart extends BrowserConfiguration {

	boolean status = false;
	static ExtentReports extent;
	static ExtentTest logger;

	@Test
	public void addToCart() throws InterruptedException, IOException {

		// XPath used for locating
		String addToCart = "//button[@id='button-cart']";
		String addedToCart = "//div[@class='alert alert-success alert-dismissible']";
		String closeMessage = "//button[@class='close']";
		String phonesPda = "//a[text()='Phones & PDAs']";
		String htc = "//h4/a[text()='HTC Touch HD']";


		// JavascriptExecutor to perform scroll
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Clicking on the "Phones & PDAs" on the menu
		driver.findElement(By.xpath(phonesPda)).click();
		
		js.executeScript("window.scrollBy(0,500)");
		
		// Locate the product you want to add to cart & click on it
		driver.findElement(By.xpath(htc)).click();

		assertEquals(driver.getTitle(), PropertyReader.getProperties("actualHtc"));

		// Click on "ADD TO CART BUTTON"
		js.executeScript("window.scrollBy(0,250)");
		
		driver.findElement(By.xpath(addToCart)).click();

		// Product added to cart
		driver.findElement(By.xpath(addedToCart));
		assertTrue(driver.getPageSource().contains(PropertyReader.getProperties("actualSuccessMessage")));

		// Dismiss the success message popped
		driver.findElement(By.xpath(closeMessage)).click();
		
        // Logging out as a customer
		CustomerLogout.logout();

		status = true;
	}

	@BeforeMethod
	public void beforeMethod() throws IOException, InterruptedException {
		extent = new ExtentReports(
				System.getProperty("user.dir") + "\\ExtentReports\\" + this.getClass().getSimpleName() + "Report.html");
		logger = extent.startTest(this.getClass().getSimpleName());

		// Setting up browser as chrome
		BrowserConfiguration.setup("chrome");

		// Using implicit wait
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Maximize the window
		driver.manage().window().maximize();

		// Loading AUT using the Home page Url
		driver.get(PropertyReader.getProperties("customerHomeURL"));

		// Logging in as a customer
		CustomerLogin.login();

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