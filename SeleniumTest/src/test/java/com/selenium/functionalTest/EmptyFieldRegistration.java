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

public class EmptyFieldRegistration extends BrowserConfiguration {

	String warning1 = "(//div[@class='text-danger'])[1]";
	String warning2 = "(//div[@class='text-danger'])[2]";
	String warning3 = "(//div[@class='text-danger'])[3]";
	String warning4 = "(//div[@class='text-danger'])[4]";
	String warning5 = "(//div[@class='text-danger'])[5]";
	String expectedTitle = "Your Account Has Been Created!";
	ExtentReports extent;
	ExtentTest logger;
	static boolean status = false;
	static boolean fn = false;
	static boolean ln = false;
	static boolean te = false;
	static boolean ema = false;
	static boolean pass = false;

	@Test
	public void EmptyField() throws IOException, InterruptedException {

		// entering blank in firstname
		WebElement firstName = driver.findElement(By.id("input-firstname"));
		firstName.sendKeys("");
		logger.log(LogStatus.INFO, "Entering firstname as empty");

		// entering blank in lastname
		WebElement lastName = driver.findElement(By.id("input-lastname"));
		lastName.sendKeys("");
		logger.log(LogStatus.INFO, "Entering lastname as empty");

		// entering blank in email
		WebElement email = driver.findElement(By.id("input-email"));
		email.sendKeys("");
		logger.log(LogStatus.INFO, "Entering emailID as empty");

		// Using JavaScript Executer to scroll down 1000 pixels
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(1000,0)");
		logger.log(LogStatus.INFO, "scrolling down");

		// entering blank in phone
		WebElement phone = driver.findElement(By.id("input-telephone"));
		phone.sendKeys("");
		logger.log(LogStatus.INFO, "Entering phone number as empty");

		// entering blank in password
		WebElement password = driver.findElement(By.id("input-password"));
		password.sendKeys("");
		logger.log(LogStatus.INFO, "Entering password as empty");

		// entering blank in confirmpassword
		WebElement confirmPassword = driver.findElement(By.id("input-confirm"));
		confirmPassword.sendKeys("");
		logger.log(LogStatus.INFO, "Entering confirm password as empty");

		// Calling pressingAll function to click on the remaining button
		ButtonClicking.pressingAll();

		logger.log(LogStatus.INFO, "Selecting no for newsletter");

		logger.log(LogStatus.INFO, "Agreeing privacy policy");

		logger.log(LogStatus.INFO, "Clicking continue to create account");

		// Checking if all warning are displayed and logging them
		fn = driver.findElement(By.xpath(warning1)).isDisplayed();
		logger.log(LogStatus.WARNING, driver.findElement(By.xpath(warning1)).getText() + " is displayed");
		ln = driver.findElement(By.xpath(warning2)).isDisplayed();
		logger.log(LogStatus.WARNING, driver.findElement(By.xpath(warning2)).getText() + " is displayed");
		js.executeScript("window.scrollBy(1000,0)");
		te = driver.findElement(By.xpath(warning3)).isDisplayed();
		logger.log(LogStatus.WARNING, driver.findElement(By.xpath(warning3)).getText() + " is displayed");
		ema = driver.findElement(By.xpath(warning4)).isDisplayed();
		logger.log(LogStatus.WARNING, driver.findElement(By.xpath(warning4)).getText() + " is displayed");
		pass = driver.findElement(By.xpath(warning5)).isDisplayed();
		logger.log(LogStatus.WARNING, driver.findElement(By.xpath(warning5)).getText() + " is displayed");

		// if all warning s are displayed status = true
		if ((((fn && ln) && te) && ema) && pass)
			status = true;

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
