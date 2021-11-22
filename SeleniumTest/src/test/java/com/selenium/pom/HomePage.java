package com.selenium.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

	static WebDriver driver;

	public HomePage(WebDriver driver) {
		HomePage.driver = driver;
	}
    
	// Xpath used
	By searchBar = By.xpath("//input[@name='search']");
	By searchButton = By.xpath("//i[@class='fa fa-search']");
	By myAccount = By.xpath("//span[@class='caret']");
	By loginEnter = By.xpath("//a[contains(text(),'Login')]");

	// Entering the search text
	public void searchText(String text) {
		driver.findElement(searchBar).sendKeys(text);
	}

	// Clicking on the search button
	public void clickSearchButton() {
		driver.findElement(searchButton).click();
	}

	// Clicking on My Account
	public void clickMyAccount() {
		driver.findElement(myAccount).click();
	}

	// Clicking on Login
	public void clickLoginEnter() {
		driver.findElement(loginEnter).click();
	}

}