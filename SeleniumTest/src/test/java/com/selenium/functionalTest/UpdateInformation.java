package com.selenium.functionalTest;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class UpdateInformation extends BrowserConfiguration {

	static boolean status = false;
	ExtentReports extent;
	ExtentTest logger;

	@Test
	public void updateInformation() throws IOException, InterruptedException {

		String updateInfo = "//a[text()='Edit your account information']";
		String continueButton = "//input[@class='btn btn-primary']";

		// logging in
		CustomerLogin.login();

		// click on edit your account information
		driver.findElement(By.xpath(updateInfo)).click();

		// scroll down
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,300)");

		// enter updated first name
		WebElement firstname = driver.findElement(By.id("input-firstname"));
		firstname.clear();
		firstname.sendKeys(PropertyReader.getProperties("updatedFirstName"));

		// enter updated last name
		WebElement lastname = driver.findElement(By.id("input-lastname"));
		lastname.clear();
		lastname.sendKeys(PropertyReader.getProperties("updatedLastName"));

		// enter updated email
		WebElement email = driver.findElement(By.id("input-email"));
		email.clear();
		email.sendKeys(PropertyReader.getProperties("updatedEmail"));

		// enter updated telephone number
		WebElement telephone = driver.findElement(By.id("input-telephone"));
		telephone.clear();
		telephone.sendKeys(PropertyReader.getProperties("telephone"));

		// click on continue
		driver.findElement(By.xpath(continueButton)).click();

		// verify success message
		assertTrue(driver.getPageSource().contains(PropertyReader.getProperties("successMessage")));

		status = true;

	}

	@BeforeMethod
	public void beforeMethod() throws IOException {

		extent = new ExtentReports(
				System.getProperty("user.dir") + "\\ExtentReports\\" + this.getClass().getSimpleName() + "Report.html");
		logger = extent.startTest(this.getClass().getSimpleName());

		BrowserConfiguration.setup("chrome");
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(PropertyReader.getProperties("baseURL"));
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
