package com.selenium.functionalTest;

import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.testng.annotations.Test;

public class DeleteOrder extends BrowserConfiguration {

	static boolean status = false;
	ExtentReports extent;
	ExtentTest logger;

	@Test
	public void deleteOrder() throws InterruptedException {

		// Locators
		String sales = "//li[@id='menu-sale']";
		String orders = "//nav/ul/li[5]/ul/li[1]";
		String action = "//span[@class='caret']";
		String delete = "//i[@class='fa fa-trash-o']";

		// Click on sales
		driver.findElement(By.xpath(sales)).click();

		// Click on orders
		driver.findElement(By.xpath(orders)).click();

		// Click on action to reveal delete option
		driver.findElement(By.xpath(action)).click();

		// Click delete
		driver.findElement(By.xpath(delete)).click();

		Alert confirm_delete = driver.switchTo().alert();

		confirm_delete.accept();

		logger.log(LogStatus.INFO, "Order successfully deleted");

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
