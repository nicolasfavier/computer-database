package com.nicolas.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.nicolas.runtimeException.PersistenceException;
import com.nicolas.utils.Property;

public class ConnectionManager {
	public static final String DB_USER = Property.INSTANCE.getDbUser();
	public static final String DB_PWD = Property.INSTANCE.getDbPassword();
	public static final String DB_NAME = Property.INSTANCE.getDbName();

	private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
	private static final String DB_HOST = "localhost";
	private static final String DB_PORT = "3306";
	private static final String DB_ARGUMENT = "?zeroDateTimeBehavior=convertToNull";
	private static final String DB_PATH = DB_NAME + DB_ARGUMENT;

	private static int maxConnectionsPerPartition = Property.INSTANCE
			.getMaxConnectionsPerPartition();
	private static int minConnectionsPerPartition = Property.INSTANCE
			.getMinConnectionsPerPartition();
	private static int partitionCount = Property.INSTANCE.getPartitionCount();
	
	private static BoneCP connectionPool = null;


	/**
	 *  connection pool configuration
	 */
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			BoneCPConfig config = new BoneCPConfig();
			
			config.setJdbcUrl("jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_PATH);
			config.setUsername(DB_USER);
			config.setPassword(DB_PWD);
			
			// 2*10 = 20 connection will be available
			config.setMinConnectionsPerPartition(minConnectionsPerPartition);
			config.setMaxConnectionsPerPartition(maxConnectionsPerPartition);
			config.setPartitionCount(partitionCount);
			
			connectionPool = new BoneCP(config);
			
			logger.info("Total connections = " + connectionPool.getTotalCreatedConnections());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new PersistenceException(e);
		}
	}

	/**
	 * This method must be called only once when the application stops. Don't
	 * need to call it every time when you get a connection from the Connection
	 * Pool
	 */
	public static void shutdownConnPool() {
		try {
			logger.info("contextDestroyed");
			if (connectionPool != null) {
				connectionPool.shutdown();
				logger.info(".Connection Pooling shut downed!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new PersistenceException(e);
		}
	}

	/**
	 * Get a thread-safe connection from the BoneCP connection pool.
	 * Synchronization of the method will be done inside BoneCP source
	 * 
	 * @return connection
	 */
	public static Connection getConnection(boolean isAutoCommit) {
		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
			connection.setAutoCommit(isAutoCommit);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new PersistenceException(e);
		}
		return connection;
	}

	/**
	 * Close the rseultset in params if not null
	 *
	 * @param ResultSet
	 */
	public static void closeResultSet(ResultSet rSet) {
		try {
			if (rSet != null)
				rSet.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new PersistenceException(e);
		}
	}

	/**
	 * release the connection - The connection is not closed it is released and
	 * it will stay in pool.
	 *
	 * @param Connection
	 */
	public static void closeConnection(Connection connection, boolean isAutoCommit) {
		try {
			if (connection != null) {
				if (!isAutoCommit)
					connection.commit();
				connection.close();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new PersistenceException(e);
		}
	}

	/**
	 * Use to rollback a transactio if an exception is throw
	 * 
	 * @param connection
	 */
	public static void rollback(Connection connection) {
		try {
			if (connection != null)
				connection.rollback();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new PersistenceException(e);
		}
	}
}
