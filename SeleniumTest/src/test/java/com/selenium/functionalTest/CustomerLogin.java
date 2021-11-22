package com.selenium.functionalTest;

import java.io.IOException;
import org.openqa.selenium.By;

public class CustomerLogin extends BrowserConfiguration {

	public static void login() throws InterruptedException, IOException {

		// XPath used for locating
		String myAccount = "//span[@class='caret']";
		String login = "//a[text()='Login']";
		String logInButton = "//input[@value='Login']";

		// Clicking on My Account
		driver.findElement(By.xpath(myAccount)).click();

		// Clicking on Login
		driver.findElement(By.xpath(login)).click();

		// Entering the email id required for login
		driver.findElement(By.id("input-email")).sendKeys(PropertyReader.getProperties("sampleEmail"));
		// Entering password required for login
		driver.findElement(By.id("input-password")).sendKeys(PropertyReader.getProperties("samplePassword"));

		// Clicking on login button to login
		driver.findElement(By.xpath(logInButton)).click();
	}

}
