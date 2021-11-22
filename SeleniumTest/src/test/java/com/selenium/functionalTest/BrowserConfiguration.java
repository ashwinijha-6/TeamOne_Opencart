

package com.selenium.functionalTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;



public class BrowserConfiguration {
	
	public static WebDriver driver;
	
	public static void setup(String BrowserName) {
		if (BrowserName.equalsIgnoreCase("chrome")) {

			System.setProperty("webdriver.chrome.driver", "./resources/chromedriver.exe");
			driver = new ChromeDriver();
			
		} else if (BrowserName.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", "./resources/msedgedriver.exe");
			// WebDriverManager.msedgedriver().setup();
			driver = new EdgeDriver();
		}
	}

	

}
