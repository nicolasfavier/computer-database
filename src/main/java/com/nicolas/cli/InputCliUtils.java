package com.nicolas.cli;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author nicolas
 *
 * this class offer methods to get inputs from user and validate their values
 */
public class InputCliUtils {
	private static Scanner scannerInstance = null;
	
	private static final String DATE_REGEX = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)\\d\\d$";
	private static final String INT_REGEX = "^[0-9]*$";

	private static synchronized Scanner getScannerInstance() {
		if (scannerInstance == null)
			scannerInstance = new Scanner(System.in);

		return scannerInstance;
	}

	/**
	 * 
	 * @param description 
	 * @param isNeeded
	 * @return String
	 * 
	 * Ask the user to enter a string, the message shown is pass in description
	 * if isNeeded is false, enter will skip the input and the function will return null
	 */
	public static String getStringFromUser(String description, boolean isNeeded) {
		String res = null;
		String tmp = null;
		boolean valid = true;

		do {
			System.out.println(description);
			if (valid == false) {
				System.out.println("The value can't be null");
			}
			valid = true;

			tmp = getScannerInstance().nextLine();

			if (tmp.trim().isEmpty() && isNeeded)
				valid = false;
			else
				res = tmp;
		} while (!valid);

		return res;
	}

	/**
	 * 
	 * @param description 
	 * @param isNeeded
	 * @return LocalDate
	 * 
	 * Ask the user to enter a date, the message shown is pass in description
	 * if isNeeded is false, enter will skip the input and the function will return null
	 */
	public static LocalDate getDateFromUser(String description, boolean isNeeded) {
		System.out.println(description);
		LocalDate date = null;
		String strDate = "";
		boolean wrongInput = false;
		

		do {
			try {
				wrongInput = false;
				strDate = getScannerInstance().nextLine();
				if (strDate.isEmpty() && !isNeeded) {
					return null;
				}
				
				if(!checkDate(strDate)){
					System.out.printf("%s does not respect the format dd/MM/yyyy !%n",strDate);
					wrongInput = true;
				}
				else{
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					date = LocalDate.parse(strDate, formatter);
				}
				
			} catch (DateTimeParseException exc) {
				System.out.printf(exc.toString());
			}
		} while (wrongInput);

		return date;
	}
	
	/**
	 * 
	 * @param inputString
	 * @return boolean
	 * 
	 * validate a date using regex
	 */
	private static boolean checkDate(String inputString){
		Pattern p = Pattern.compile(DATE_REGEX); 
		Matcher m = p.matcher(inputString);   
		return m.find(); 
	}
	
	/**
	 * 
	 * @param inputString
	 * @return boolean
	 * 
	 * validate a int using regex
	 */
	private static boolean checkInt(String inputString){
		Pattern p = Pattern.compile(INT_REGEX); 
		Matcher m = p.matcher(inputString);   
		return m.matches(); 
	}
	
	/**
	 * 
	 * @param maxVal
	 * @param description
	 * @param isNeeded
	 * @return int
	 * 
	 * Ask the user to enter a int , the message shown is pass in description
	 * if isNeeded is false, enter will skip the input and the function will return null
	 * 
	 */
	public static int getUserInput(int maxVal, String description,
			boolean isNeeded) {
		System.out.println(description);
		return getUserInput(maxVal, isNeeded);
	}

	public static int getUserInput(int maxVal, boolean isNeeded) {
		boolean wrongInput = false;
		int userVal = 0;

		do {
			wrongInput = false;

			try {
				String tmp = getScannerInstance().nextLine();

				if (tmp.trim().isEmpty() && !isNeeded) {
					return -1;
				}
				if(!checkInt(tmp)){
					wrongInput = true;
					wrongEntrie(maxVal);
				}
				else{
					userVal = Integer.parseInt(tmp);
					if (maxVal != -1 && userVal > maxVal) {
						wrongInput = true;
						wrongEntrie(maxVal);
					}
				}
			} catch (Exception e) {
				System.out.printf(e.toString());
			}
		} while (wrongInput);

		return userVal;
	}

	private static void wrongEntrie(int maxVal) {
		if (maxVal == -1) {
			System.out.println("wrong input please choose a number");
		} else {
			System.out
					.println("wrong input please choose a number beetween 0 and "
							+ (maxVal - 1));
		}
	}

}
