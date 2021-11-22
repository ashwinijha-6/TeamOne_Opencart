package com.selenium.functionalTest;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.testng.annotations.BeforeMethod;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;

public class CheckoutProduct extends BrowserConfiguration {

	boolean status = false;
	static ExtentReports extent;
	static ExtentTest logger;

	@Test
	public void checkoutProduct() throws InterruptedException, IOException {

// XPath used for locating
		String shoppingCart = "//span[text()='Shopping Cart']";
		String checkoutBtn = "//div[@class='pull-right']";
		String newAddRdoBtn = "//input[@value='new']";
		String firstName = "//input[@id='input-payment-firstname']";
		String lastName = "//input[@id='input-payment-lastname']";
		String company = "//input[@id='input-payment-company']";
		String address1 = "//input[@id='input-payment-address-1']";
		String address2 = "//input[@id='input-payment-address-2']";
		String city = "//input[@id='input-payment-city']";
		String postCode = "//input[@id='input-payment-postcode']";
		String country = "//select[@id='input-payment-country']";
		String india = "//option[@value='99']";
		String regionState = "//select[@id='input-payment-zone']";
		String maharashtra = "//option[@value='1493']";
		String billContinueBtn = "//input[@id='button-payment-address']";
		String commentOrder = "//textarea[@name='comment']";
		String termsChkBox = "//input[@name='agree']";
		String payContinueBtn = "//input[@id='button-payment-method']";

		String contactUs = "//a[text()='contact us']";
		String enquiryForm = "//textarea[@id='input-enquiry']";
		String submitEnquiry = "//input[@value='Submit']";
		String enquiryMsg = "//div[@id='content']/p";

		String delButton = "//input[@id='button-shipping-address']";
		String shipButton = "//input[@id='button-shipping-method']";
		String cnfButton = "//input[@id='button-confirm']";
		String delDetails = "//div[2]/div/div/div/div[3]/div[2]/div/form/div[5]/div/input";

// JavascriptExecutor to perform scroll
		JavascriptExecutor js = (JavascriptExecutor) driver;

// Clicking on "Shopping Cart" on top navigation bar
		driver.findElement(By.xpath(shoppingCart)).click();

// Scrolling down & clicking on "checkout button"
		js.executeScript("window.scrollBy(0,500)");

		driver.findElement(By.xpath(checkoutBtn)).click();

// Assert checkout page title
		assertEquals(driver.getTitle(), PropertyReader.getProperties("checkoutPageTitle"));

// If want to enter new address
		try {
			WebElement rdoBtnNewAdd = driver.findElement(By.xpath(newAddRdoBtn));
			rdoBtnNewAdd.click();
		} catch (NoSuchElementException e) {
			logger.log(LogStatus.INFO, "No new address radio button found, filling in new address.");
		}

// Step 2: Billing Details

// Entering the first name
		driver.findElement(By.xpath(firstName)).sendKeys(PropertyReader.getProperties("checkoutFirstName"));

		js.executeScript("window.scrollBy(0,250)");

// Entering the last name
		driver.findElement(By.xpath(lastName)).sendKeys(PropertyReader.getProperties("checkoutLastName"));

// Entering the company
		driver.findElement(By.xpath(company)).sendKeys(PropertyReader.getProperties("checkoutCompany"));

// Entering address line 1
		driver.findElement(By.xpath(address1)).sendKeys(PropertyReader.getProperties("checkoutAddress1"));

// Entering address line 2
		driver.findElement(By.xpath(address2)).sendKeys(PropertyReader.getProperties("checkoutAddress2"));

// Entering the city
		driver.findElement(By.xpath(city)).sendKeys(PropertyReader.getProperties("checkoutCity"));

		js.executeScript("window.scrollBy(0,100)", "");

// Entering the post code
		driver.findElement(By.xpath(postCode)).sendKeys(PropertyReader.getProperties("checkoutPostCode"));

// Clicking on the country
		driver.findElement(By.xpath(country)).click();

// Selecting India as the country
		driver.findElement(By.xpath(india)).click();

// Clicking on the region/state
		driver.findElement(By.xpath(regionState)).click();

// Selecting Maharashtra as the country
		driver.findElement(By.xpath(maharashtra)).click();

// Clicking on continue button in billing details
		driver.findElement(By.xpath(billContinueBtn)).click();

// Step 3: Delivery Details

		boolean delFlag = false;

		try {
			driver.findElement(By.xpath(delDetails));
			delFlag = true;
		} catch (NoSuchElementException e) {
			delFlag = false;
		}

		if (delFlag) {

// Clicking on continue button in delivery details
			driver.findElement(By.xpath(delButton)).click();

// Step 4: Shipping Method

// Clicking on continue button in shipping method
			driver.findElement(By.xpath(shipButton)).click();

// Step 5: Payment Method

// Adding comments about the order
			driver.findElement(By.xpath(commentOrder)).sendKeys(PropertyReader.getProperties("checkoutCommentOrder"));

// Checking the terms & conditions check box to agree with them
			WebElement conditionsChkBox = driver.findElement(By.xpath(termsChkBox));

			conditionsChkBox.click();

// Clicking on continue button in payment method
			driver.findElement(By.xpath(payContinueBtn)).click();

// Step 6: Confirm Order
// Clicking on continue button to confirm order

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(cnfButton)));
			driver.findElement(By.xpath(cnfButton)).click();

// Logging info "order successfully placed"
			logger.log(LogStatus.INFO, "Order successfully placed.");

		}

		else {
// Clicking on contact us
			driver.findElement(By.xpath(contactUs)).click();

// Entering enquiry message
			driver.findElement(By.xpath(enquiryForm)).sendKeys(PropertyReader.getProperties("contactEnquiryMsg"));

// Clicking on submit enquiry button
			driver.findElement(By.xpath(submitEnquiry)).click();

// Logging info "order not placed but contacted the owner about the issue"
			logger.log(LogStatus.INFO, "Order not placed but contacted the owner about the issue.");

// Assert enquiry message has been successfully sent to owner
			assertTrue(driver.getPageSource().contains(driver.findElement(By.xpath(enquiryMsg)).getText()));
		}

		status = true;

	}

	@BeforeMethod
	public void beforeMethod() throws InterruptedException, IOException {
		extent = new ExtentReports(
				System.getProperty("user.dir") + "\\ExtentReports\\" + this.getClass().getSimpleName() + "Report.html");
		logger = extent.startTest(this.getClass().getSimpleName());

// Setting up browser as chrome
		BrowserConfiguration.setup("chrome");

// Using implicit wait
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

// Maximize the window
		driver.manage().window().maximize();

// Loading AUT using the Home page URL
		driver.get(PropertyReader.getProperties("customerHomeURL"));

// Logging in as a customer
		CustomerLogin.login();

// Add product to cart
		AddProductToCart.addProductToCart();

	}

	@AfterMethod
	public void afterMethod() {
		if (status) {
// Logging the test as passed
			logger.log(LogStatus.PASS, "Test Pass");
		} else {
// Logging the test as failed
			logger.log(LogStatus.FAIL, "Test Failed");
		}
		extent.endTest(logger);
// deleting the contents of Extent report generated during previous execution.
		extent.flush();

// quit the whole browser session
		driver.quit();
	}

}