package com.selenium.functionalTest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ButtonClicking extends BrowserConfiguration {
	
	public static void pressingAll() {
		
		String continueXpath = "//input[@value='Continue']";
		String radioNoXpath = "//input[@name='newsletter' and @value='0']";
		
		// Selecting No radio for newsletter
		WebElement radioNo = driver.findElement(By.xpath(radioNoXpath));
		radioNo.click();
		
		// Agreeing to privacy policy
		WebElement agree = driver.findElement(By.name("agree"));
		agree.click();

		// Clicking continue to create account
		WebElement Continue = driver.findElement(By.xpath(continueXpath));
		Continue.click();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}
}
