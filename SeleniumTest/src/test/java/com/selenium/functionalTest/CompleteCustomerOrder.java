package com.selenium.functionalTest;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeMethod;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.openqa.selenium.support.ui.Select;

public class CompleteCustomerOrder extends BrowserConfiguration {

	static boolean status = false;
	ExtentReports extent;
	ExtentTest logger;

	@Test
	public void orderComplete() throws InterruptedException, IOException {

		// XPATH to all the buttons
		String sales = "//li[@id='menu-sale']";
		String orders = "//nav/ul/li[5]/ul/li[1]";
		String action = "//span[@class='caret']";
		String edit = "//i[@class='fa fa-pencil']";
//		String chooseCustomer = "//input[@id='input-customer']";
		String customerContinue = "//button[@id='button-customer']";
		String cartContinue = "//button[@id='button-cart']";
		String paymentAddContinue = "//button[@id='button-payment-address']";
		String shipAddContinue = "//button[@id='button-shipping-address']";
		String orderStatus = "//select[@id='input-order-status']";
		String save = "//button[@id='button-save']";

		// Verify page title by comparing with the one fetched by the driver
		assertEquals(driver.getTitle(), PropertyReader.getProperties("adminTitle"));

		driver.findElement(By.xpath(sales)).click();

		driver.findElement(By.xpath(orders)).click();

		driver.findElement(By.xpath(action)).click();

		driver.findElement(By.xpath(edit)).click();

//		WebElement customer = driver.findElement(By.xpath(chooseCustomer));
//		customer.clear();
//		customer.sendKeys("user test");

		driver.findElement(By.xpath(customerContinue)).click();

		driver.findElement(By.xpath(cartContinue)).click();

		driver.findElement(By.xpath(paymentAddContinue)).click();

		driver.findElement(By.xpath(shipAddContinue)).click();

		driver.findElement(By.xpath(orderStatus)).click();

		WebElement selectStatus = driver.findElement(By.name("order_status_id"));
		Select select = new Select(selectStatus);
		select.selectByIndex(12);

		// logging the status of "Order Status" changed to Shipped
		logger.log(LogStatus.PASS, "Order status changed to Shipped");

		driver.findElement(By.xpath(save)).click();

		status = true;
	}

	@BeforeMethod
	public void beforeMethod() throws IOException, InterruptedException {

		extent = new ExtentReports(
				System.getProperty("user.dir") + "\\ExtentReports\\" + this.getClass().getSimpleName() + "Report.html");
		logger = extent.startTest(this.getClass().getSimpleName());

		BrowserConfiguration.setup("chrome");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		// using object of LoginAdmin class to call login method
		LoginAdmin.login();
	}

	@AfterMethod
	public void afterMethod() {
		if (status) {
			// logging the test as passed
			logger.log(LogStatus.PASS, "Test Pass");
		} else
			// logging the test as failed
			logger.log(LogStatus.FAIL, "Test Failed");

		extent.endTest(logger);

		// deleting the contents of Extent report generated during previous execution.
		extent.flush();

		driver.quit();
	}
}
