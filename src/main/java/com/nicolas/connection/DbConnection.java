package com.nicolas.connection;

import java.sql.Connection;
import java.sql.SQLException;

import com.nicolas.utils.Property;

public enum DbConnection {
	INSTANCE;

	public static final String DB_HOST = "localhost";
	public static final String DB_PORT = "3306";
	public static final String DB_NAME = Property.INSTANCE.getDataBaseName();
	public static final String DB_TIME_BEHAVIOR = "?zeroDateTimeBehavior=convertToNull";
	public static final String DB_USER = "admincdb";
	public static final String DB_PWD = "qwerty1234";

	private DbConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * open and return a new connection 
	 * @return Connection
	 */
	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"
					+ DB_HOST + ":" + DB_PORT + "/" + DB_NAME
					+ DB_TIME_BEHAVIOR, DB_USER, DB_PWD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
