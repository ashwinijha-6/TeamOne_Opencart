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
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;

public class UserInterface extends BrowserConfiguration {
	String continueXpath = "//input[@value='Continue']";
	String radioYesXpath = "//input[@name='newsletter' and @value='1']";
	String radioNoXpath = "//input[@name='newsletter' and @value='0']";
	String expectedTitle = "Account Login";
	ExtentReports extent;
	ExtentTest logger;
	static boolean status = false;

	@Test
	public void CheckingAllUIFeatures() throws InterruptedException, IOException {

		// To check Login Link
		WebElement loginLink = driver.findElement(By.linkText("login page"));
		loginLink.click();

		if (driver.getTitle().equals(expectedTitle)) {
			logger.log(LogStatus.PASS, "Login Page Link Check Passed");
		} else {
			logger.log(LogStatus.FAIL, "Login Page Link Check Failed");
		}

		// Navigate back to registration page
		driver.navigate().back();

		// entering firstname in text field is
		WebElement firstName = driver.findElement(By.id("input-firstname"));
		firstName.sendKeys(PropertyReader.getProperties("firstName"));
		logger.log(LogStatus.INFO, "First Name text field is writtable");

		// Using javascriptexecuter to scroll down till continue button is visible
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement Continue = driver.findElement(By.xpath(continueXpath));
		js.executeScript("arguments[0].scrollIntoView();", Continue);

		// Checking Radio buttons
		WebElement radioYes = driver.findElement(By.xpath(radioYesXpath));
		radioYes.click();
		logger.log(LogStatus.INFO, "Radio button Yes is Clickable");

		WebElement radioNo = driver.findElement(By.xpath(radioNoXpath));
		radioNo.click();
		logger.log(LogStatus.INFO, "Radio button No is Clickable");

		// Checking privacy policy link
		WebElement privacyPolicyLink = driver.findElement(By.linkText("Privacy Policy"));
		privacyPolicyLink.click();
		logger.log(LogStatus.INFO, "Privacy Policy link is Clickable and opens new window");

		// Moving mouse pointer under policy window and clicking to close it using
		// Actions class
		Actions action = new Actions(driver);
		action.moveByOffset(0, 15).click().build().perform();
		logger.log(LogStatus.INFO, "Moving mouse away from the policy window and clicking to close it");

		// Checking privacy policy checkbox
		WebElement agree = driver.findElement(By.name("agree"));
		agree.click();
		logger.log(LogStatus.INFO, "Privacy Policy checkbox Yes is Clickable");

		agree.click();
		logger.log(LogStatus.INFO, "Privacy Policy checkbox No is Clickable");

		// Checking continue button to complete registration
		Continue.click();
		logger.log(LogStatus.INFO, "Continue button Yes is Clickable");
		status = true;
	}

	@BeforeMethod
	public void beforeMethod() throws IOException {
		extent = new ExtentReports(
				System.getProperty("user.dir") + "\\ExtentReports\\" + this.getClass().getSimpleName() + "Report.html");
		logger = extent.startTest(this.getClass().getSimpleName());

		// setting up browser as chrome
		BrowserConfiguration.setup("chrome");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// passing base url from data file
		driver.get(PropertyReader.getProperties("registrationBaseURL"));
		driver.manage().window().maximize();
	}

	@AfterMethod
	public void afterMethod() throws InterruptedException {
		if (status) {
			logger.log(LogStatus.PASS, "Test Pass , All UI Features are working perfect");
		} else
			logger.log(LogStatus.FAIL, "Test Failed");

		extent.endTest(logger);
		extent.flush();

		// closing the driver
		driver.quit();
	}

}
