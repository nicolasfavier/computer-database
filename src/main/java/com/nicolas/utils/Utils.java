package com.nicolas.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Utils {
	private Utils(){}
	
	private static final String INT_REGEX = "^[0-9]*$";
	private static final String DATE_REGEX = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)\\d\\d$";

	
	public static Timestamp getTimestamp(LocalDate ld){
		if (ld == null)
			return null;
		Instant instantIntroduced = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		return Timestamp.from(instantIntroduced);
	}
	
	public static LocalDate getLocalDate(Timestamp ts){
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
	 * validate a int using regex
	 */
	public static boolean checkInt(String inputString){
		Pattern p = Pattern.compile(INT_REGEX); 
		Matcher m = p.matcher(inputString);   
		return m.matches(); 
	}
	
	/**arg0
	 * 
	 * @param inputString
	 * @return boolean
	 * 
	 * validate a date using regex
	 */
	public static boolean checkDate(String inputString){
		Pattern p = Pattern.compile(DATE_REGEX); 
		Matcher m = p.matcher(inputString);   
		return m.find(); 
	}
}
