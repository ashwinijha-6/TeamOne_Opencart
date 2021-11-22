package com.selenium.ddt;

import org.testng.annotations.Test;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.selenium.functionalTest.BrowserConfiguration;
import com.selenium.functionalTest.PropertyReader;

import org.testng.annotations.BeforeMethod;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;

public class DDT extends BrowserConfiguration {
	static Logger log = Logger.getLogger(DDT.class.getName());
	
	String CSV_PATH = "files/ddt.csv";
	private CSVReader csvReader;
    String[] csvCell;
	ExtentReports extent;
	ExtentTest logger;
	static boolean status = false;
	String logInButton = "//input[@value='Login']";
	
	
	
  @Test
  public void feedingDataWithCSV() throws CsvValidationException, IOException, InterruptedException {
	  
	  PropertyConfigurator.configure("log4j2.properties");
	  
	  log.info("Test started");

	  csvReader = new CSVReader(new FileReader(CSV_PATH));
	  
	  
	  while ((csvCell = csvReader.readNext()) != null)
	  {
		  String CustomerEmail = csvCell[0];
          String CustomerPassword = csvCell[1];
          driver.findElement(By.id("input-email")).clear();
          driver.findElement(By.id("input-email")).sendKeys(CustomerEmail);
    	  log.info("Enterning user email.");

          driver.findElement(By.id("input-password")).clear();
          driver.findElement(By.id("input-password")).sendKeys(CustomerPassword);
    	  log.info("Entering user password.");

          
          driver.findElement(By.xpath(logInButton)).click();
    	  log.info("Clicking login button.");

	  }
	  
  }
  
  @BeforeMethod
  public void beforeMethod() throws IOException {
	  extent = new ExtentReports(System.getProperty("user.dir") + "\\ExtentReports\\" + this.getClass().getSimpleName() + "Report.html");
		logger = extent.startTest(this.getClass().getSimpleName());
		
		//setting up browser as chrome
		BrowserConfiguration.setup("chrome");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//passing base url from data file
		driver.get(PropertyReader.getProperties("loginUrl"));
		driver.manage().window().maximize();
  }

  @AfterMethod
  public void afterMethod() {
	  if (status) {
			logger.log(LogStatus.PASS, "Test Pass");
		} 
		else
			logger.log(LogStatus.FAIL, "Test Failed");

		extent.endTest(logger);
		extent.flush();
//		Thread.sleep(5000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//closing the driver
		driver.quit();
  }

}
