package com.selenium.functionalTest;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;

public class SearchFunctionNegative extends BrowserConfiguration {

	static boolean status = false;
	ExtentReports extent;
	ExtentTest logger;

	@Test
	public void negativeSearch() throws IOException, InterruptedException {

		String searchButton = "//button[@class=\"btn btn-default btn-lg\"]";
		String messageXpath = "//*[@id=\"content\"]/p[2]";
		String expected_message = "There is no product that matches the search criteria.";

		// open browser
		driver.get(PropertyReader.getProperties("baseURL"));

		// Login to home page
		CustomerLogin.login();

		// Go to search box
		WebElement search_box = driver.findElement(By.name("search"));

		// search for invalid product
		search_box.sendKeys(PropertyReader.getProperties("invalidSampleProduct"));
		driver.findElement(By.xpath(searchButton)).click();

		// scroll down and get the message displayed
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,300)");

		WebElement message = driver.findElement(By.xpath(messageXpath));
		//
		String displayed_message = message.getText();

		// check if the displayed message matches with the expcted message

		if (displayed_message.equals(expected_message)) {

			status = true;
		} else {
			status = false;
		}
	}

	@BeforeMethod
	public void beforeMethod() {
		extent = new ExtentReports(
				System.getProperty("user.dir") + "\\ExtentReports\\" + this.getClass().getSimpleName() + "Report.html");
		logger = extent.startTest(this.getClass().getSimpleName());

		BrowserConfiguration.setup("chrome");
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@AfterMethod
	public void afterMethod() {
		if (status) {
			logger.log(LogStatus.PASS, "Test Pass");
		} else
			logger.log(LogStatus.FAIL, "Test Failed");

		extent.endTest(logger);
		extent.flush();

		driver.quit();
	}

}
