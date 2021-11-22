package com.selenium.functionalTest;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;


public class AddProductToCart extends BrowserConfiguration {

	public static void addProductToCart() throws InterruptedException, IOException {

		// XPath used for locating
		String addToCart = "//button[@id='button-cart']";
		String addedToCart = "//div[@class='alert alert-success alert-dismissible']";
		String closeMessage = "//button[@class='close']";
		String phonesPda = "//a[text()='Phones & PDAs']";
		String htc = "//h4/a[text()='HTC Touch HD']";


		// JavascriptExecutor to perform scroll
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Locating the "Phones & PDAs" on the Home page
		driver.findElement(By.xpath(phonesPda)).click();

		js.executeScript("window.scrollBy(0,500)");

		// Locate the product you want to add to cart
		driver.findElement(By.xpath(htc)).click();

		// Click on "ADD TO CART BUTTON"
		js.executeScript("window.scrollBy(0,250)");
				
		driver.findElement(By.xpath(addToCart)).click();
		
		// Product added to cart
		driver.findElement(By.xpath(addedToCart));
		
		// Dismiss the success message popped
		driver.findElement(By.xpath(closeMessage)).click();
		
	}
}
