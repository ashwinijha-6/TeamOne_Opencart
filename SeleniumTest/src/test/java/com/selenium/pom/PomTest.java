package com.selenium.pom;

import com.selenium.functionalTest.BrowserConfiguration;
import com.selenium.functionalTest.PropertyReader;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;

public class PomTest extends BrowserConfiguration {
	
    // Declaring a reference object
	HomePage homePage;
	LoginPage loginPage;

	@Test
	public void searchAndLogin() throws IOException, InterruptedException {

		// To enter the text to be searched
		homePage.searchText(PropertyReader.getProperties("pomSearch"));
		// To click on search button
		homePage.clickSearchButton();

		// To click on my account
		homePage.clickMyAccount();
		
		// To click on login
		homePage.clickLoginEnter();

		// To enter the email
		String loginEmail = PropertyReader.getProperties("pomEmail");
		loginPage.usernameText(loginEmail);
		
		// To enter the password
		String loginPassword = PropertyReader.getProperties("pomPassword");
		loginPage.passwordText(loginPassword);
        
		// To click on login
		loginPage.clickLoginButton();

	}

	@BeforeMethod
	public void beforeMethod() throws IOException {
		
        //setting up browser as chrome
		BrowserConfiguration.setup("chrome");

        //passing base url from data file
		driver.get(PropertyReader.getProperties("customerHomeURL"));
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		driver.manage().window().maximize();

		// Instantiating the object
		homePage = new HomePage(driver);
		loginPage = new LoginPage(driver);
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

}