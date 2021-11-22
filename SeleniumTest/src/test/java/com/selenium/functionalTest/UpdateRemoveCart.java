package com.selenium.functionalTest;

import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.testng.annotations.BeforeMethod;

import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;

public class UpdateRemoveCart extends BrowserConfiguration {

	boolean status = false;
	static ExtentReports extent;
	static ExtentTest logger;

	@Test
	public void updateRemoveCart() throws Exception {

		// XPath used for locating
		String quantityForm = "//input[@class='form-control']";
		String shoppingCart = "//span[text()='Shopping Cart']";
		String update = "//i[@class='fa fa-refresh']";
		String emptyCartMsg = "//div[@id='content']/p";
		String removeProduct = "//i[@class='fa fa-times-circle']";

		// Clicking on "Shopping Cart" on top navigation bar
		driver.findElement(By.xpath(shoppingCart)).click();

		// Locating the form to enter new quantity
		driver.findElement(By.xpath(quantityForm)).click();

		// Clearing the quantity form
		driver.findElement(By.xpath(quantityForm)).clear();

		// Sending 0 to the quantity form in the Shopping cart page
		driver.findElement(By.xpath(quantityForm)).sendKeys("");
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_0);

		// Clicking on update button
		driver.findElement(By.xpath(update)).click();

		// Assert the empty cart message
		assertTrue(driver.getPageSource().contains(driver.findElement(By.xpath(emptyCartMsg)).getText()));

		// As cart is empty add a product to the cart
		AddProductToCart.addProductToCart();

		// Clicking on "Shopping Cart" on top navigation bar of Home page
		driver.findElement(By.xpath(shoppingCart)).click();

		// Clicking, Clearing the quantity form & entering 6 as the quantity
		driver.findElement(By.xpath(quantityForm)).click();

		driver.findElement(By.xpath(quantityForm)).clear();

		driver.findElement(By.xpath(quantityForm)).sendKeys("");
		robot.keyPress(KeyEvent.VK_6);

		// Clicking on update button
		driver.findElement(By.xpath(update)).click();

		// Assert Update Success Message
		assertTrue(driver.getPageSource().contains(PropertyReader.getProperties("updateMessage")));

		// Remove product from Shopping Cart
		driver.findElement(By.xpath(removeProduct)).click();

		// Assert empty cart message
		assertTrue(driver.getPageSource().contains(driver.findElement(By.xpath(emptyCartMsg)).getText()));

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
