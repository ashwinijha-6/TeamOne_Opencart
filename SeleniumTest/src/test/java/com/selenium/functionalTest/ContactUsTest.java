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

public class ContactUsTest extends BrowserConfiguration {

	static boolean status = false;
	ExtentReports extent;
	ExtentTest logger;

	@Test
	public void contact() throws IOException, InterruptedException {

		String contact = "//a[text()='Contact Us']";
		String submit = "//*[@id=\"content\"]/form/div/div/input";
		String messageXpath = "//*[@id=\"content\"]/p";

		driver.get(PropertyReader.getProperties("baseURL"));

		// Login to homepage
		// Login user= new Login();
		// user.login();
		CustomerLogin.login();

		// Scroll down to the end
		JavascriptExecutor js = (JavascriptExecutor) driver;
		;
		js.executeScript("window.scrollBy(0,1000)");

		// click on Contact us
		driver.findElement(By.xpath(contact)).click();

		// scroll
		js.executeScript("window.scrollBy(0,500)");

		WebElement enquiry = driver.findElement(By.id("input-enquiry"));

		enquiry.sendKeys(PropertyReader.getProperties("enquiryText"));

		// click on submit button
		driver.findElement(By.xpath(submit)).click();

		String message_displayed = driver.findElement(By.xpath(messageXpath)).getText();
		String expected_message = PropertyReader.getProperties("expectedMessage");

		if (message_displayed.equals(expected_message)) {
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
