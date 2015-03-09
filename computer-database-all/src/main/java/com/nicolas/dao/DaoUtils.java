package com.nicolas.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * close results set and preparedStatement
 *
 */
public final class DaoUtils {
	private DaoUtils() {
	}

	public static void closeResultSet(ResultSet rs) {
		try {
			if(rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closePreparedStatement(PreparedStatement preparedStatement) {
		try {
			if(preparedStatement != null)
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
