package com.nicolas.cli;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class InputCliUtils {
	// TODO le singleton est il nécessaire ? multiple instances ?
	// public static Scanner sc = new Scanner(System.in);

	private static Scanner scannerInstance = null;

	public static synchronized Scanner getScannerInstance() {
		if (scannerInstance == null)
			scannerInstance = new Scanner(System.in);

		return scannerInstance;
	}

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

	public static LocalDate getDateFromUser(String description, boolean isNeeded) {
		System.out.println(description);
		LocalDate date = null;
		String strDate = "";
		boolean error = false;
		

		do {
			try {
				error = false;
				strDate = getScannerInstance().nextLine();
				if (strDate.isEmpty() && !isNeeded) {
					return null;
				}
				DateTimeFormatter formatter = DateTimeFormatter
						.ofPattern("dd/MM/yyyy");
				date = LocalDate.parse(strDate, formatter);
				System.out.printf("%s%n", date);
			} catch (DateTimeParseException exc) {
				System.out.printf("%s does not respect the format dd/MM/yyyy !%n",strDate);
				error = true;
			}
		} while (error);

		return date;
	}

	public static int getUserInput(int maxVal, String description,
			boolean isNeeded) {
		System.out.println(description);
		return getUserInput(maxVal, isNeeded);
	}

	public static int getUserInput(int maxVal, boolean isNeeded) {
		boolean error = false;
		int userVal = 0;

		do {
			error = false;

			try {
				String tmp = getScannerInstance().nextLine();

				if (tmp.trim().isEmpty() && !isNeeded) {
					return -1;
				}

				userVal = Integer.parseInt(tmp);
				if (maxVal != -1 && userVal > maxVal) {
					error = true;
					wrongEntrie(maxVal);
				}
			} catch (Exception e) {
				error = true;
				wrongEntrie(maxVal);
			}
		} while (error);

		// TODO see why close import error
		// sc.close();
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
