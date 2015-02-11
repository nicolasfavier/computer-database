package com.nicolas.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum Property {
	INSTANCE;

	private Property() {
	}

	public String getDataBaseName() {

		String dataBaseName = "";
		Properties prop = new Properties();
		String propFileName = "db.properties";

		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream("config/" + propFileName);

		if (inputStream != null) {
			try {
				prop.load(inputStream);
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.printf("property file '" + propFileName
					+ "' not found in the classpath");
		}

		dataBaseName = prop.getProperty("dbname");

		return dataBaseName;
	}
}
