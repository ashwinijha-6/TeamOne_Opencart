package com.selenium.functionalTest;

import java.io.IOException;
import org.openqa.selenium.By;

public class LoginAdmin extends BrowserConfiguration {
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

	}

	public static void logout() {
		String logout = "//i[@class='fa fa-sign-out']";

		// click logout
		driver.findElement(By.xpath(logout)).click();
	}
}
