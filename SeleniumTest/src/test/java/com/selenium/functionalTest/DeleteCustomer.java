package com.selenium.functionalTest;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class DeleteCustomer extends BrowserConfiguration {

	static boolean status = false;
	ExtentReports extent;
	ExtentTest logger;

	@Test
	public void deleteCustomer() throws InterruptedException {

		// locators for the buttons and elements
		String customers = "//li[@id='menu-customer']";
		String customerList = "//nav/ul/li[6]/ul/li[1]/a";
		String chkBox = "selected[]";
		String delete = "//button[@class = 'btn btn-danger']";
		// String success = "//div[@class='alert alert-success alert-dismissible']";

		// click customers in nav bar
		driver.findElement(By.xpath(customers)).click();

		// click customers in customer list
		driver.findElement(By.xpath(customerList)).click();

		// checking check box of a particular customer
		WebElement checkBox = driver.findElement(By.name(chkBox));
		if (!(checkBox.isSelected()))
			checkBox.click();

		// click delete
		driver.findElement(By.xpath(delete)).click();

		// accepting the alert "Are you sure?"
		Alert confirm_delete = driver.switchTo().alert();
		

		confirm_delete.accept();

		logger.log(LogStatus.INFO, "Customer successfully deleted.");

		LoginAdmin.logout();

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
