package com.selenium.pom;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

	static WebDriver driver;

	public LoginPage(WebDriver driver) {
		LoginPage.driver = driver;
	}

	// XPath used
	By user = By.xpath("//input[@name='email']");
	By pass = By.xpath("//input[@name='password']");
	By login = By.xpath("//input[@value='Login']");

	// Entering the email address during login
	public void usernameText(String username) throws IOException {

		driver.findElement(user).click();
		driver.findElement(user).clear();
		driver.findElement(user).sendKeys(username);

	}

    // Entering the password during login
	public void passwordText(String pomPassword) throws IOException {

		driver.findElement(user).click();
		driver.findElement(pass).clear();
		driver.findElement(pass).sendKeys(pomPassword);

	}

    // Clicking on the login button
	public void clickLoginButton() {
		driver.findElement(login).click();
	}
}