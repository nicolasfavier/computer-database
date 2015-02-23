package com.nicolas.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * get all properties present in the db.properties file
 *
 */
public enum Property {
	INSTANCE;

	private String dbName;
	private String dbUser;
	private String dbPassword;
	private int maxConnectionsPerPartition;
	private int minConnectionsPerPartition;
	private int partitionCount;

	private Property() {
		Properties prop = new Properties();
		String propFileName = "db.properties";

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
				"config/" + propFileName);

		if (inputStream != null) {
			try {
				prop.load(inputStream);
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.printf("property file '" + propFileName + "' not found in the classpath");
		}

		dbName = prop.getProperty("dbName");
		dbUser = prop.getProperty("dbUser");
		dbPassword = prop.getProperty("dbPassword");
		maxConnectionsPerPartition = Integer.parseInt(prop
				.getProperty("bonecp.maxConnectionsPerPartition"));
		minConnectionsPerPartition = Integer.parseInt(prop
				.getProperty("bonecp.minConnectionsPerPartition"));
		partitionCount = Integer.parseInt(prop.getProperty("bonecp.partitionCount"));

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

	public int getMaxConnectionsPerPartition() {
		return maxConnectionsPerPartition;
	}

	public int getMinConnectionsPerPartition() {
		return minConnectionsPerPartition;
	}

	public int getPartitionCount() {
		return partitionCount;
	}

}
