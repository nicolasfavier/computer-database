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

/**
 * 
 * Create and handle connection
 *
 */
public class ConnectionManager {
	public static final String DB_USER = Property.INSTANCE.getDbUser();
	public static final String DB_PWD = Property.INSTANCE.getDbPassword();
	public static final String DB_NAME = Property.INSTANCE.getDbName();
	
	private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
	
	private static final String DB_HOST = "localhost";
	private static final String DB_PORT = "3306";
	private static final String DB_ARGUMENT = "?zeroDateTimeBehavior=convertToNull";
	private static final String DB_PATH = DB_NAME + DB_ARGUMENT;
	
	private static int maxConnectionsPerPartition = Property.INSTANCE.getMaxConnectionsPerPartition();
	private static int minConnectionsPerPartition = Property.INSTANCE.getMinConnectionsPerPartition();
	private static int partitionCount = Property.INSTANCE.getPartitionCount();
	
	private static BoneCP connectionPool = null;

	private static ThreadLocal<Connection> connection = new ThreadLocal<Connection>();

	/**
	 * connection pool configuration
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

	public static Connection getConnection() {
		return connection.get();
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
	public static void openConnection(boolean isAutoCommit) {
		try {
			if (connection.get() == null) {
				connection.set(connectionPool.getConnection());
			}
			connection.get().setAutoCommit(isAutoCommit);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new PersistenceException(e);
		}
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
	public static void closeConnection(boolean isAutoCommit) {
		try {
			if (connection.get() != null) {
				if (!isAutoCommit)
					connection.get().commit();

				connection.get().close();
				connection.set(null);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new PersistenceException(e);
		}
	}

	/**
	 * Use to rollback a transaction if an exception is throw
	 * 
	 * @param connection
	 */
	public static void rollback() {
		try {
			if (connection.get() != null) {
				connection.get().rollback();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new PersistenceException(e);
		}
	}
}
