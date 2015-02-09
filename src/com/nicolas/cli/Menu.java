package com.nicolas.cli;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
	private final String MENU_HEADER = ""
			+ "********************************************************\n"
			+ "*                        MENU                          *\n"
			+ "********************************************************\n";

	public enum MenuEntries {
		SHOW_COMPUTER("List computers"),SHOW_PBYP_COMPUTER("List computers page by page"), SHOW_COMPANIES("List companies"), SHOW_COMPUTER_DETAILS(
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

	private CompanyCli companyCli;
	private ComputerCli computerCli;

	public Menu() {
		companyCli = new CompanyCli();
		computerCli = new ComputerCli();
	}

	public void run() {
		while (true) {
			display();
			int choix = InputCliUtils.getUserInput(MenuEntries.values().length, true);
			select(choix);
		}
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
			System.out.println("All computers :");
			computerCli.showComputers();
			System.out.println("");
			break;
			
		case SHOW_PBYP_COMPUTER:
			System.out.println("Computers :");
			computerCli.showComputersByPage();
			System.out.println("");
			break;	

		case SHOW_COMPANIES:
			System.out.println("All companies :");
			companyCli.showCompanies();
			System.out.println("");
			break;

		case SHOW_COMPUTER_DETAILS:
			computerCli.getComputerDetails();
			break;

		case CREATE_COMPUTER:
			computerCli.createComputer();
			break;

		case UPDATE_COMPUTER:
			computerCli.updateComputer();
			break;

		case DELETE_COMPUTER:
			computerCli.deleteComputer();
			break;

		case QUIT:
			quit();
			break;

		default:
			break;
		}

	}

	private void quit() {
		System.out.println("See you next time!");
		System.exit(0);
	}
}
