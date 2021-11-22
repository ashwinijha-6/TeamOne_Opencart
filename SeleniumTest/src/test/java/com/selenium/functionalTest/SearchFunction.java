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

public class SearchFunction extends BrowserConfiguration {

	static boolean status = false;
	ExtentReports extent;
	ExtentTest logger;

	@Test
	public void Search() throws IOException, InterruptedException {

		String searchButton = "//button[@class=\"btn btn-default btn-lg\"]";
		String searchedProduct = "iPhone";
		String titleXpath = "//*[@id=\"content\"]/div[3]/div/div/div[2]/div[1]/h4/a";

		// open browser
		driver.get(PropertyReader.getProperties("baseURL"));

		// Login to home page
		CustomerLogin.login();

		// Go to search box
		WebElement search_box = driver.findElement(By.name("search"));

		// search for valid product
		search_box.sendKeys(PropertyReader.getProperties("sampleProduct"));
		driver.findElement(By.xpath(searchButton)).click();

		// Scroll down
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");

		// get title of the product displayed
		WebElement title = driver.findElement(By.xpath(titleXpath));
		String displayed_product = title.getText();

		// Check if the displayed product matches the searched product
		if (displayed_product.equals(searchedProduct)) {

			status = true;
		} else {
			status = false;
		}

		//
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
