package com.nicolas.connection;

import java.sql.Connection;
import java.sql.SQLException;

public enum DbConnection {
	INSTANCE;
	
	private static final String DB_HOST = "localhost";
	private static final String DB_PORT = "3306";
	private static final String DB_NAME = "computer-database-db";
	private static final String DB_TIME_BEHAVIOR = "?zeroDateTimeBehavior=convertToNull";
	private static final String DB_USER = "admincdb";
	private static final String DB_PWD = "qwerty1234";
	
	private Connection connection = null;
	
	private DbConnection(){}
	
	public Connection getConnection(){
		if(connection == null){
			try {
				connection = java.sql.DriverManager.getConnection("jdbc:mysql://"
						+ DB_HOST + ":" + DB_PORT + "/" + DB_NAME
						+ DB_TIME_BEHAVIOR, DB_USER, DB_PWD);
			} catch (SQLException e) {
				System.out.print("with error");
				e.printStackTrace();
			}	
		}
		return connection;
	}
}
