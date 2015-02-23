package com.nicolas.cli;

/**
 * 
 * Display a menu of  various actions on computers and companies
 *
 */
public class Menu {

	private static final String MENU_HEADER = ""
			+ "********************************************************\n"
			+ "*                        MENU                          *\n"
			+ "********************************************************\n";

	public enum MenuEntries {
		SHOW_COMPUTER("List computers"), SHOW_PBYP_COMPUTER(
				"List computers page by page"), SHOW_COMPANIES("List companies"), SHOW_COMPUTER_DETAILS(
				"Show computer details"), CREATE_COMPUTER("Create a computer"), UPDATE_COMPUTER(
				"Update a computer"), DELETE_COMPUTER("Delete a computer"), DELETE_COMPANY("Delete a company"), QUIT(
				"Quit");

		private String description = "";

		MenuEntries(String description) {
			this.description = description;
		}

		public String toString() {
			return description;
		}
	}

	public static void run() {
		while (true) {
			display();
			int choix = InputCliUtils.getUserInput(MenuEntries.values().length,
					true);
			select(choix);
		}
	}

	private static void display() {
		int i = 0;
		System.out.println(MENU_HEADER);
		for (MenuEntries entrie : MenuEntries.values()) {
			System.out.println(i + ") " + entrie);
			i++;
		}
	}

	private static void select(int choice) {
		MenuEntries mEntrie = MenuEntries.values()[choice];

		switch (mEntrie) {
		case SHOW_COMPUTER:
			System.out.println("All computers :");
			ComputerCli.showComputers();
			System.out.println("");
			break;

		case SHOW_PBYP_COMPUTER:
			System.out.println("Computers :");
			ComputerCli.showComputersByPage();
			System.out.println("");
			break;

		case SHOW_COMPANIES:
			System.out.println("All companies :");
			CompanyCli.showCompanies();
			System.out.println("");
			break;

		case SHOW_COMPUTER_DETAILS:
			ComputerCli.getComputerDetails();
			break;

		case CREATE_COMPUTER:
			ComputerCli.createComputer();
			break;

		case UPDATE_COMPUTER:
			ComputerCli.updateComputer();
			break;

		case DELETE_COMPUTER:
			ComputerCli.deleteComputer();
			break;

		case DELETE_COMPANY:
			CompanyCli.deleteCompany();
			break;
			
		case QUIT:
			quit();
			break;

		default:
			break;
		}

	}

	private static void quit() {
		System.out.println("See you next time!");
		System.exit(0);
	}
}
