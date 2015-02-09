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

	public static String getStringFromUser(String description) {
		String res = "";
		System.out.println(description);
		String tmp = getScannerInstance().next();
		res = tmp;
		return res;
	}

	public static LocalDate getDateFromUser(String description) {
		System.out.println(description);
		String strDate = getScannerInstance().next();
		LocalDate date = null;

		try {
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("dd/MM/yyyy");
			date = LocalDate.parse(strDate, formatter);
			System.out.printf("%s%n", date);
		} catch (DateTimeParseException exc) {
			System.out.printf("%s is not parsable!%n", strDate);
		}

		return date;
	}

	public static int getUserInput(int maxVal, String description) {
		System.out.println(description);
		return getUserInput(maxVal);
	}

	public static int getUserInput(int maxVal) {
		boolean error = false;
		int userVal = 0;

		do {
			error = false;

			try {
				userVal = getScannerInstance().nextInt();
				if (maxVal != -1 && userVal > maxVal) {
					error = true;
					getScannerInstance().nextLine();
					wrongEntrie(maxVal);
				}
			} catch (InputMismatchException e) {
				error = true;
				getScannerInstance().nextLine();
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
			System.out.println("wrong input please choose a number beetween 0 and "
							+ (maxVal - 1));
		}
	}

}
