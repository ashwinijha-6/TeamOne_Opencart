package com.selenium.functionalTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.remote.CapabilityType;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import org.testng.annotations.Test;
import org.openqa.selenium.edge.EdgeDriver;

//import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserConfiguration {

	public static WebDriver driver;

	public static void setup(String BrowserName) {

		if (BrowserName.equalsIgnoreCase("chrome")) {
			// WebDriverManager.chromedriver().setup();
			// ChromeOptions handlingSSL = new ChromeOptions();
			// Using the accept insecure cert method with true as parameter to accept the
			// untrusted certificate
			// handlingSSL.setAcceptInsecureCerts(true);
			System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
			driver = new ChromeDriver();

		} else if (BrowserName.equalsIgnoreCase("edge")) {
			// WebDriverManager.msedgedriver().setup();
			System.setProperty("webdriver.edge.driver", "resources/msedgedriver.exe");
			driver = new EdgeDriver();
		}

	}

}
