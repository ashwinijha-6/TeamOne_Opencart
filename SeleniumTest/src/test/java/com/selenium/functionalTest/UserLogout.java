package com.selenium.functionalTest;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.assertEquals;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class UserLogout extends BrowserConfiguration {

	static boolean status = false;
	ExtentReports extent;
	ExtentTest logger;

	@Test
	public void logout() throws IOException, InterruptedException {

		String myAccount = "//span[@class='caret']";
		String logOut = "//a[text()='Logout']";
		CustomerLogin.login();
		// verify account page title
		assertEquals(driver.getTitle(), PropertyReader.getProperties("myAccountPageTitle"));
		// click on myaccount
		driver.findElement(By.xpath(myAccount)).click();
		// click on logout option
		driver.findElement(By.xpath(logOut)).click();
		// verify logout page title
		assertEquals(driver.getTitle(), PropertyReader.getProperties("logoutPageTitle"));

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
