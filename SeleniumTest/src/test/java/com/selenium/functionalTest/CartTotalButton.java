package com.selenium.functionalTest;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.testng.annotations.BeforeMethod;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;

public class CartTotalButton extends BrowserConfiguration {

	boolean status = false;
	static ExtentReports extent;
	static ExtentTest logger;

	@Test
	public void cartTotal() throws InterruptedException, IOException {

		// XPath used for locating
		String shoppingCart = "//span[text()='Shopping Cart']";
		String cartTotalBtn = "//span[@id='cart-total']";
		String productName = "//*[@id=\"cart\"]/ul/li[1]/table/tbody/tr/td[2]/a";
		String nameOfProduct, cartEmptyMsg;
		String quantityOfProduct = "//*[@id=\"cart\"]/ul/li[1]/table/tbody/tr/td[3]";
		String amount = "//*[@id=\"cart\"]/ul/li[1]/table/tbody/tr/td[4]";
		String removeBtn = "//button[@title='Remove']";
		String emptyCartMsg = "//li/p";

		// Clicking on "Shopping Cart" on top navigation bar
		driver.findElement(By.xpath(shoppingCart)).click();

		// Clicking on "Cart-Total" Button
		driver.findElement(By.xpath(cartTotalBtn)).click();
		
		// Assert the product name
		nameOfProduct = driver.findElement(By.xpath(productName)).getText();
		assertTrue(driver.getPageSource().contains(nameOfProduct));
		
        // Logging the name, quantity & amount as INFO
		logger.log(LogStatus.INFO, nameOfProduct);
		logger.log(LogStatus.INFO, driver.findElement(By.xpath(quantityOfProduct)).getText());
		logger.log(LogStatus.INFO, driver.findElement(By.xpath(amount)).getText());

		// Clicking on Remove Button
		driver.findElement(By.xpath(removeBtn)).click();
		
		// Assert empty cart message
		cartEmptyMsg = driver.findElement(By.xpath(emptyCartMsg)).getText();
		assertTrue(driver.getPageSource().contains(cartEmptyMsg));

		// Logging the empty cart message as INFO
		logger.log(LogStatus.INFO, cartEmptyMsg);

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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Maximize the window
		driver.manage().window().maximize();

		// Loading AUT using the Home page Url
		driver.get(PropertyReader.getProperties("customerHomeURL"));

		// Logging in as a customer
		CustomerLogin.login();

		// Add product to cart
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
