package com.nicolas.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Utils {
	private Utils() {
	}

	private static final String INT_REGEX = "^[0-9]*$";
	private static final String DATE_REGEX = "^(19|20)\\d\\d-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";

	public static Timestamp getTimestamp(LocalDate ld) {
		if (ld == null)
			return null;
		Instant instantIntroduced = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		return Timestamp.from(instantIntroduced);
	}

	public static LocalDate getLocalDate(Timestamp ts) {
		LocalDate ld = null;
		if (ts != null)
			ld = ts.toLocalDateTime().toLocalDate();
		return ld;
	}

	/**
	 * 
	 * @param inputString
	 * @return boolean
	 * 
	 *         validate a int using regex
	 */
	public static boolean checkInt(String inputString) {
		Pattern p = Pattern.compile(INT_REGEX);
		Matcher m = p.matcher(inputString);
		return m.matches();
	}

	/**
	 * 
	 * @param inputString
	 * @return int
	 * 
	 */
	public static int getIntFromString(String inputString) {
		int res = 0;
		if (inputString == null)
			return res;
		Pattern p = Pattern.compile(INT_REGEX);
		Matcher m = p.matcher(inputString);
		if (m.matches())
			res = Integer.parseInt(inputString);
		return res;
	}

	/**
	 * arg0
	 * 
	 * @param inputString
	 * @return date or null if wrong format
	 * 
	 *         validate a date using regex
	 */
	public static LocalDate getDateFromString(String inputString) {
		LocalDate date = null;

		if(inputString == null || inputString.isEmpty())
			return date;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		date = LocalDate.parse(inputString, formatter);

		return date;
	}

	/**
	 * 
	 * @param inputString
	 * @return true if date or blank false else
	 * 
	 *         validate a date using regex
	 */
	public static boolean isDate(String inputString) {
		if (inputString == null || inputString.isEmpty())
			return true;

		Pattern p = Pattern.compile(DATE_REGEX);
		Matcher m = p.matcher(inputString);

		if (m.find()) {
			return true;
		}
		return false;
	}
}
