package com.selenium.functionalTest;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import static org.testng.Assert.assertEquals;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.AfterMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class InvalidLogin extends BrowserConfiguration {

	static boolean status = false;
	ExtentReports extent;
	ExtentTest logger;

	@Test
	public void invalidLogin() throws IOException, InterruptedException {

		String myAccount = "//span[@class='caret']";
		String logIn = "//a[text()='Login']";
		String logInButton = "//input[@value='Login']";
		String message = "//div[@class='alert alert-danger alert-dismissible']";

		driver.get(PropertyReader.getProperties("baseURL"));

		// Verify store page title
		assertEquals(driver.getTitle(), PropertyReader.getProperties("storePageTitle"));

		// click on myaccount
		driver.findElement(By.xpath(myAccount)).click();

		// click on login
		driver.findElement(By.xpath(logIn)).click();

		// scrolling down
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,250)", "");

		// enter invalid email
		driver.findElement(By.id("input-email")).sendKeys(PropertyReader.getProperties("invalidEmailAddress"));

		// enter invalid password
		driver.findElement(By.id("input-password")).sendKeys(PropertyReader.getProperties("invalidPassword"));

		// click no login
		driver.findElement(By.xpath(logInButton)).click();

		// verify account login page title
		assertEquals(driver.getTitle(), PropertyReader.getProperties("accountLoginPageTitle"));

		// comparing warning message
		assertEquals(driver.findElement(By.xpath(message)).getText(), PropertyReader.getProperties("warningMessage"));

		status = true;

	}

	@BeforeMethod
	public void beforeMethod() {

		extent = new ExtentReports(
				System.getProperty("user.dir") + "\\ExtentReports\\" + this.getClass().getSimpleName() + "Report.html");
		logger = extent.startTest(this.getClass().getSimpleName());

		BrowserConfiguration.setup("chrome");
		driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
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
