package com.selenium.functionalTest;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

	public static String getProperties(String s) throws IOException {
		FileReader read = new FileReader("files/data/");
		Properties p = new Properties();
		p.load(read);
		return (p.getProperty(s));

	}
}