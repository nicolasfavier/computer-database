package com.nicolas.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum Property {
	INSTANCE;

	private String dbName;
	private String dbUser;
	private String dbPassword;

	private Property() {
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

		dbName = prop.getProperty("dbName");
		dbUser = prop.getProperty("dbUser");
		dbPassword = prop.getProperty("dbPassword");

	}

	public String getDbName() {
		return dbName;
	}

	public String getDbUser() {
		return dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

}

