package com.nicolas.cli;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
	private final String MENU_HEADER = ""
			+ "********************************************************\n"
			+ "*                        MENU                          *\n"
			+ "********************************************************\n";

	public enum MenuEntries {
		SHOW_COMPUTER("List computers"), SHOW_COMPANIES("List companies"), SHOW_COMPUTER_DETAILS(
				"Show computer details"), CREATE_COMPUTER("Create a computer"), UPDATE_COMPUTER(
				"Update a computer"), DELETE_COMPUTER("Delete a computer"), QUIT(
				"Quit");

		private String description = "";

		MenuEntries(String description) {
			this.description = description;
		}

		public String toString() {
			return description;
		}
	}

	public void run() {
		while (true) {
			display();
			getUserInput();
		}
	}

	private void getUserInput() {
		boolean error;
		int choice;

		do {
			Scanner sc = new Scanner(System.in);
			error = false;
			try {
				choice = sc.nextInt();
				if (choice < MenuEntries.values().length) {
					select(choice);
				} else {
					error = true;
					wrongEntrie();
				}
			} catch (InputMismatchException e) {
				error = true;
				wrongEntrie();
			}
		} while (error);

	}

	private void wrongEntrie(){
		int maxValue = MenuEntries.values().length -1;
		System.out
				.println("wrong input please choose a number beetween 0 and "
						+ maxValue );
	}
	private void display() {
		int i = 0;
		System.out.println(MENU_HEADER);
		for (MenuEntries entrie : MenuEntries.values()) {
			System.out.println(i + ") " + entrie);
			i++;
		}
	}

	private void select(int choice) {

		MenuEntries mEntrie = MenuEntries.values()[choice];

		switch (mEntrie) {
		case SHOW_COMPUTER:
			System.out.println("action show computers");
			break;

		case SHOW_COMPANIES:
			System.out.println("action show companies");
			break;

		case SHOW_COMPUTER_DETAILS:
			System.out.println("action  SHOW_COMPUTER_DETAILS");
			break;

		case CREATE_COMPUTER:
			System.out.println("action CREATE_COMPUTER");
			break;

		case UPDATE_COMPUTER:
			System.out.println("action UPDATE_COMPUTER");
			break;

		case DELETE_COMPUTER:
			System.out.println("action DELETE_COMPUTER");
			break;

		case QUIT:
			System.exit(0);
			break;

		default:
			break;

		}

	}

	private void quit() {
		System.out.println("See you next time!");
	}
}
