package com.selenium.functionalTest;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.testng.Assert.assertEquals;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;

public class PrintInvoice extends BrowserConfiguration {

	static boolean status = false;
	ExtentReports extent;
	ExtentTest logger;

	@Test
	public void printInvoice() throws InterruptedException, IOException {

		// XPATH to all the buttons and checkbox
		String sales = "//li[@id='menu-sale']";
		String orders = "//nav/ul/li[5]/ul/li[1]";
		String chkBox = "selected[]";
		String print = "//i[@class='fa fa-print']";

		driver.findElement(By.xpath(sales)).click();

		driver.findElement(By.xpath(orders)).click();

		// checking check box of a particular order
		WebElement checkBox = driver.findElement(By.name(chkBox));
		if (!(checkBox.isSelected()))
			checkBox.click();

		driver.findElement(By.xpath(print)).click();

		// get window handlers as list
		List<String> browserTabs = new ArrayList<String>(driver.getWindowHandles());
		// switch to new tab
		driver.switchTo().window(browserTabs.get(1));

		// Verify page title by comparing with the one fetched by the driver
		assertEquals(driver.getTitle(), PropertyReader.getProperties("invoiceTitle"));
		logger.log(LogStatus.INFO, "Opened invoice in a new tab.");

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
