package com.nicolas.connection;

import java.sql.Connection;
import java.sql.SQLException;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nicolas.dao.impl.ComputerDaoImpl;
import com.nicolas.utils.Property;

public enum DbConnection {
	INSTANCE;

	static Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);

	public static final String DB_HOST = "localhost";
	public static final String DB_PORT = "3306";
	public static final String DB_NAME = Property.INSTANCE.getDbName();
	public static final String DB_TIME_BEHAVIOR = "?zeroDateTimeBehavior=convertToNull";
	public static final String DB_USER = Property.INSTANCE.getDbUser();
	public static final String DB_PWD = Property.INSTANCE.getDbPassword();

	private DbConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * open and return a new connection
	 * 
	 * @return Connection
	 */
	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://" + DB_HOST + ":"
					+ DB_PORT + "/" + DB_NAME + DB_TIME_BEHAVIOR, DB_USER, DB_PWD);
		} catch (SQLException e) {
			LOGGER.error(e.toString());
			throw new RuntimeErrorException(new Error());
		}
		return connection;
	}

	public void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			LOGGER.error(e.toString());
			throw new RuntimeErrorException(new Error());
		}
	}
}
