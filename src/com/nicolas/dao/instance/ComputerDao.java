package com.nicolas.dao.instance;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import com.nicolas.models.Computer;

public class ComputerDao {
	private Connection connection;
	private String dB_HOST;
	private String dB_PORT;
	private String dB_NAME;
	private String dB_USER;
	private String dB_PWD;
	private String dB_TIME_BEHAVIOR;

	private final static String DB_COMPUTER_TABLE = "computer";
	private final static String DB_COMPUTER_COLUMN_ID = "id";
	private final static String DB_COMPUTER_COLUMN_NAME = "name";
	private final static String DB_COMPUTER_COLUMN_INTRODUCED = "introduced";
	private final static String DB_COMPUTER_COLUMN_DISCONTINUED = "discontinued";
	private final static String DB_COMPUTER_COLUMN_COMPANY_ID = "company_id";

	public ComputerDao(String DB_HOST, String DB_PORT, String DB_NAME,
			String DB_TIME_BEHAVIOR, String DB_USER, String DB_PWD) {
		dB_HOST = DB_HOST;
		dB_PORT = DB_PORT;
		dB_NAME = DB_NAME;
		dB_USER = DB_USER;
		dB_PWD = DB_PWD;
		dB_TIME_BEHAVIOR = DB_TIME_BEHAVIOR;		
	}

	private void openConnection(){
		// create connection
				try {
					connection = java.sql.DriverManager.getConnection("jdbc:mysql://"
							+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME
							+ dB_TIME_BEHAVIOR, dB_USER, dB_PWD);
				} catch (SQLException e) {
					System.out.print("with error");

					e.printStackTrace();
				}
	}
	
	public void addComputer(Computer computer) {
		java.sql.Statement query;
		java.sql.PreparedStatement preparedStatement = null;

		// create connection
		try {
			openConnection();
			query = connection.createStatement();

			LocalDate ld = computer.getIntroduced();
			Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault())
					.toInstant();
			Timestamp ts = Timestamp.from(instant);

			String addComputerSQL = "INSERT INTO `" + dB_NAME + "`.`"
					+ DB_COMPUTER_TABLE + "` " + "(" + DB_COMPUTER_COLUMN_NAME
					+ "," + DB_COMPUTER_COLUMN_INTRODUCED + ","
					+ DB_COMPUTER_COLUMN_DISCONTINUED + ","
					+ DB_COMPUTER_COLUMN_COMPANY_ID + ") VALUES" + "(?,?,?,?);";

			preparedStatement = connection.prepareStatement(addComputerSQL);
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setTimestamp(2, ts);
			preparedStatement.setTimestamp(3, ts);
			preparedStatement.setInt(4, computer.getCompany_id());

			int rs = preparedStatement.executeUpdate();

			query.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Computer> getAllComputers() {
		ArrayList<Computer> computerList = new ArrayList<Computer>();
		java.sql.Statement query;
		
		try {
			openConnection();
			query = connection.createStatement();
			java.sql.ResultSet rs = query.executeQuery("SELECT * FROM "	+ DB_COMPUTER_TABLE + ";");

			LocalDate introducedTime = null;
			LocalDate discontinuedTime = null;

			while (rs.next()) {

				Timestamp tsIntroduced = rs
						.getTimestamp(DB_COMPUTER_COLUMN_INTRODUCED);
				if (tsIntroduced != null)
					introducedTime = tsIntroduced.toLocalDateTime()
							.toLocalDate();

				Timestamp tsDiscontinued = rs
						.getTimestamp(DB_COMPUTER_COLUMN_DISCONTINUED);
				if (tsDiscontinued != null)
					discontinuedTime = tsDiscontinued.toLocalDateTime()
							.toLocalDate();

				Computer computer = new Computer(
						rs.getInt(DB_COMPUTER_COLUMN_ID),
						rs.getString(DB_COMPUTER_COLUMN_NAME), introducedTime,
						discontinuedTime,
						rs.getInt(DB_COMPUTER_COLUMN_COMPANY_ID));

				computerList.add(computer);
			}

			rs.close();
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computerList;
	}

	public void updateComputer(Computer computer) {
		java.sql.PreparedStatement preparedStatement = null;

		try {
			openConnection();
			String updateComputerSQL = "UPDATE `" + dB_NAME + "`.`" + DB_COMPUTER_TABLE
					+ "` SET `" 
					+ DB_COMPUTER_COLUMN_NAME+ "`=?,`"
					+ DB_COMPUTER_COLUMN_INTRODUCED + "`=?,`"
					+ DB_COMPUTER_COLUMN_DISCONTINUED + "`=?,`"
					+ DB_COMPUTER_COLUMN_COMPANY_ID +"`=? WHERE "+ DB_COMPUTER_COLUMN_ID+" = ?";
			
			LocalDate ldIntroduced = computer.getIntroduced();
			Instant instantIntroduced = ldIntroduced.atStartOfDay().atZone(ZoneId.systemDefault())
					.toInstant();
			Timestamp tsIntroduced = Timestamp.from(instantIntroduced);
			
			LocalDate ldDiscontinued = computer.getIntroduced();
			Instant instantDiscontinued = ldDiscontinued.atStartOfDay().atZone(ZoneId.systemDefault())
					.toInstant();
			Timestamp tsDiscontinued= Timestamp.from(instantDiscontinued);
			
			preparedStatement = connection.prepareStatement(updateComputerSQL);
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setTimestamp(2, tsIntroduced);
			preparedStatement.setTimestamp(3, tsDiscontinued);
			preparedStatement.setInt(4, computer.getCompany_id());
			preparedStatement.setInt(5, computer.getId());

			int rs = preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteUser(Computer computer) {
		// Cr�ation de la requ�te
		java.sql.Statement query;
		try {
			openConnection();
			query = connection.createStatement();

			// Executer puis parcourir les r�sultats
			String sql = "DELETE FROM`" + dB_NAME + "`.`" + DB_COMPUTER_TABLE
					+ "` WHERE `computers`.`id` = '" + computer.getId() + "'";

			query.executeUpdate(sql);
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
