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

public class ConfirmPasswordWarning extends BrowserConfiguration {

	String confirmPasswordWarning = "Password confirmation does not match password!";
	ExtentReports extent;
	ExtentTest logger;
	static boolean status = false;

	@Test
	public void checkConfirmPassword() throws IOException, InterruptedException {

		// entering firstname
		WebElement firstName = driver.findElement(By.id("input-firstname"));
		firstName.sendKeys(PropertyReader.getProperties("firstName"));
		logger.log(LogStatus.INFO, "Entering firstname");

		// entering lastname
		WebElement lastName = driver.findElement(By.id("input-lastname"));
		lastName.sendKeys(PropertyReader.getProperties("lastName"));
		logger.log(LogStatus.INFO, "Entering lastname");

		// entering email
		WebElement email = driver.findElement(By.id("input-email"));
		email.sendKeys(PropertyReader.getProperties("email12"));
		logger.log(LogStatus.INFO, "Entering emailID");

		// Using JavaScript Executer to scroll down
		JavascriptExecutor js = (JavascriptExecutor) driver;
		;
		js.executeScript("window.scrollBy(1000,0)");
		logger.log(LogStatus.INFO, "scrolling down");

		// entering phone
		WebElement phone = driver.findElement(By.id("input-telephone"));
		phone.sendKeys(PropertyReader.getProperties("samplePhone"));
		logger.log(LogStatus.INFO, "Entering phone number");

		// entering password
		WebElement password = driver.findElement(By.id("input-password"));
		password.sendKeys(PropertyReader.getProperties("password"));
		logger.log(LogStatus.INFO, "Entering password");

		// entering confirm password
		WebElement confirmPassword = driver.findElement(By.id("input-confirm"));
		confirmPassword.sendKeys(PropertyReader.getProperties("sampleConfirmpassword"));
		logger.log(LogStatus.INFO, "Entering confirm password");

		// Calling pressingAll function to click on the remaining buttons
		ButtonClicking.pressingAll();

		logger.log(LogStatus.INFO, "Selecting no for newsletter");

		logger.log(LogStatus.INFO, "Agreeing privacy policy");

		logger.log(LogStatus.INFO, "Clicking continue to create account");

		// Scrolling down and finding the warning
		js.executeScript("window.scrollBy(0,1000)");
		status = driver.getPageSource().contains(confirmPasswordWarning);

	}

	@BeforeMethod
	public void beforeMethod() throws IOException {
		extent = new ExtentReports(
				System.getProperty("user.dir") + "\\ExtentReports\\" + this.getClass().getSimpleName() + "Report.html");
		logger = extent.startTest(this.getClass().getSimpleName());

		// setting up browser as chrome
		BrowserConfiguration.setup("chrome");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// passing base url from data file
		driver.get(PropertyReader.getProperties("registrationBaseURL"));
		driver.manage().window().maximize();
	}

	@AfterMethod
	public void afterMethod() {
		if (status) {
			logger.log(LogStatus.WARNING, "Password confirmation does not match password!");
			logger.log(LogStatus.PASS, "Test Pass");
		} else
			logger.log(LogStatus.FAIL, "Test Failed");

		extent.endTest(logger);
		extent.flush();
//		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// closing the driver
		driver.quit();
	}

}
