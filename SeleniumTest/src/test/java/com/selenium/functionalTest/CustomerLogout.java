package com.selenium.functionalTest;

import org.openqa.selenium.By;

public class CustomerLogout extends BrowserConfiguration {

	public static void logout() throws InterruptedException {

		// XPath used for locating
		String myAccount = "//span[@class='caret']";
		String logout = "//a[text()='Logout']";

		// Clicking on My Account
		driver.findElement(By.xpath(myAccount)).click();

		// Clicking on Logout
		driver.findElement(By.xpath(logout)).click();
	}

}
