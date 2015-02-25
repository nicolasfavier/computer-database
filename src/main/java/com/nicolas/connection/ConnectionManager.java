package com.nicolas.connection;

import java.sql.Connection;
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

	private static int maxConnectionsPerPartition = Property.INSTANCE
			.getMaxConnectionsPerPartition();
	private static int minConnectionsPerPartition = Property.INSTANCE
			.getMinConnectionsPerPartition();
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
	public static Connection getConnection() {
		try {
			if (connection.get() == null) {
				connection.set(connectionPool.getConnection());
				logger.info("new connection created " + connection.get().hashCode());
			}
			else{
				logger.info("connection was already created "+ connection.get().hashCode());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new PersistenceException(e);
		}
		return connection.get();
	}

	/**
	 * init a connection with autocommit true
	 */
	public static void initTransactionConnection() {
		try {
			connection.set(connectionPool.getConnection());
			connection.get().setAutoCommit(false);
			logger.info("new transaction connection created "+ connection.get().hashCode());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new PersistenceException(e);
		}
	}

	/**
	 * commit the transaction then close it
	 */
	public static void closeTransactionConnection() {
		try {
			if (connection.get() != null) {
				connection.get().commit();
				connection.get().close();
				connection.set(null);
				logger.info("connection transaction closed ");
			}
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
	public static void closeConnection() {
		try {
			if (connection.get() != null) {
				if (connection.get().getAutoCommit()) {
					connection.get().close();
					connection.set(null);
					logger.info("connection closed");
				}
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
				logger.info("rollback");
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new PersistenceException(e);
		}
	}
}
