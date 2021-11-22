package com.selenium.functionalTest;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
 

public class AdminLogin extends BrowserConfiguration{
	
	static boolean status = false;
	ExtentReports extent;
	ExtentTest logger;
	
  @Test
  public static void login() throws IOException, InterruptedException {
	  
	  String loginButton = "//button[@type='submit']";
	  
	  // Load AUT
	  driver.get(PropertyReader.getProperties("adminBaseURL"));
	  
	  // Enter Admin username
	  driver.findElement(By.id("input-username")).sendKeys(PropertyReader.getProperties("adminUser"));
	  
	  // Enter Admin password
	  driver.findElement(By.id("input-password")).sendKeys(PropertyReader.getProperties("adminPassword"));
	  
	  // Click Login
	  driver.findElement(By.xpath(loginButton)).click();
	  
	  // Verify page title by comparing with the one fetched by the driver
	  assertEquals(driver.getTitle(), PropertyReader.getProperties("adminTitle"));
	  status = true;
  }
  
  @Parameters("BrowserName")
  
  @BeforeMethod
  public void beforeMethod() {
	  

	  extent = new ExtentReports(System.getProperty("user.dir") + "\\ExtentReports\\" + this.getClass().getSimpleName() + "Report.html");
	  logger = extent.startTest(this.getClass().getSimpleName());
	  
	  BrowserConfiguration.setup("chrome");
	  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  driver.manage().window().maximize();
  }

  @AfterMethod
  public void afterMethod() {
	  if(status) {
		  // logging the test as passed
		  logger.log(LogStatus.PASS,"Test Pass");
	  }
	  else
		  // logging the test as failed
		  logger.log(LogStatus.FAIL,"Test Failed");
	  
	  extent.endTest(logger);
	  
	  // deleting the contents of Extent report generated during previous execution.
	  extent.flush();
	  
	  driver.quit();
  }

}
