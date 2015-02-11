package com.nicolas.cli;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.nicolas.Service.Impl.ComputerServiceImpl;
import com.nicolas.models.Company;
import com.nicolas.models.Computer;
import com.nicolas.models.Page;

/**
 * 
 * @author nicolas
 *
 * handle user input and actions on computer
 */
public class ComputerCli {

	private static final String MENU_COMPUTER_CREATION_HEADER = "############ Computer creation ############";
	private static final String MENU_COMPUTER_UPDATE_HEADER = "############ Computer Update ############";
	private static final String MENU_COMPUTER_DELETE_HEADER = "############ Computer Delete ############";
	private static final String MENU_COMPUTER_CREATION_NAME = "enter a name :";
	private static final String MENU_COMPUTER_CREATION_INTRODUCED = "enter the introduced date :";
	private static final String MENU_COMPUTER_CREATION_DISCONTINUED = "enter the discontinued date :";
	private static final String MENU_COMPUTER_UPDATE_INDEX = "enter the computer index:";
	private static final String MENU_COMPUTER_DETAILS_INDEX = "enter the computer index for details :";
	private static final String INVALID_INDEX = "This Index is not Valide";

	private ComputerCli() {
	}
	
	/**
	 * show all computers return by the database
	 */
	public static void showComputers() {
		List<Computer> computers = new ArrayList<Computer>();
		computers = ComputerServiceImpl.INSTANCE.getAll();
		showComputers(computers);
	}

	private static void showComputers(List<Computer> computers) {
		for (Computer c : computers) {
			System.out.println(c.toString());
		}
	}

	/**
	 * show computers by blocks
	 */
	public static void showComputersByPage() {
		int index = 0;
		boolean exit = false;
		Page p;

		do {
			p = ComputerServiceImpl.INSTANCE.get(index);
			showComputers(p.getComputerList());
			String input = InputCliUtils.getStringFromUser(
					"enter for next page q for quit", false);
			if (input.equals("q"))
				exit = true;
			if (p.getComputerList().size() < Page.NB_COMPUTERS)
				exit = true;
			index++;
		} while (!exit);
	}

	public static void createComputer() {
		System.out.println(MENU_COMPUTER_CREATION_HEADER);

		String name = InputCliUtils.getStringFromUser(
				MENU_COMPUTER_CREATION_NAME, true);
		LocalDate introducedDate = InputCliUtils.getDateFromUser(
				MENU_COMPUTER_CREATION_INTRODUCED, false);
		LocalDate discontinuedDate = InputCliUtils.getDateFromUser(
				MENU_COMPUTER_CREATION_DISCONTINUED, false);
		int companyId = CompanyCli.selectValidCompanyIndex();

		Company tmpCompany = null;
		if (companyId != -1)
			tmpCompany = new Company(companyId, "");

		Computer tmpComputer = new Computer(0, name, introducedDate,
				discontinuedDate, tmpCompany);

		if (ComputerServiceImpl.INSTANCE.add(tmpComputer))
			System.out.println("create with success");
		else
			System.out.println("error");

	}

	public static void updateComputer() {
		System.out.println(MENU_COMPUTER_UPDATE_HEADER);
		Computer tmpComputer = selectValidComputerIndex();

		String name = InputCliUtils.getStringFromUser(
				MENU_COMPUTER_CREATION_NAME, false);
		if (!name.isEmpty())
			tmpComputer.setName(name);

		LocalDate introducedDate = InputCliUtils.getDateFromUser(
				MENU_COMPUTER_CREATION_INTRODUCED, false);
		if (introducedDate != null)
			tmpComputer.setIntroduced(introducedDate);

		LocalDate discontinuedDate = InputCliUtils.getDateFromUser(
				MENU_COMPUTER_CREATION_DISCONTINUED, false);
		if (discontinuedDate != null)
			tmpComputer.setDiscontinued(discontinuedDate);

		int companyId = CompanyCli.selectValidCompanyIndex();
		Company tmpCompany = null;
		if (companyId != -1) {
			tmpCompany = new Company(companyId, "");
			tmpComputer.setCompany(tmpCompany);
		}

		if (ComputerServiceImpl.INSTANCE.update(tmpComputer))
			System.out.println("update with success");
		else
			System.out.println("error");
	}

	public static void getComputerDetails() {
		int index = InputCliUtils.getUserInput(-1, MENU_COMPUTER_DETAILS_INDEX,
				false);
		Computer detail = ComputerServiceImpl.INSTANCE.getByID(index);
		if (detail == null) {
			System.out.println(INVALID_INDEX);
		} else {
			System.out.println(detail.toString());
		}
	}

	public static void deleteComputer() {
		System.out.println(MENU_COMPUTER_DELETE_HEADER);
		int index = selectValidComputerIndex().getId();
		if (ComputerServiceImpl.INSTANCE.delete(index))
			System.out.println("deleted with success");
		else
			System.out.println("error");

	}

	private static Computer selectValidComputerIndex() {
		Computer tmpComputer = null;
		boolean error = false;
		do {
			if (error)
				System.out.println("The index does not exist");
			error = false;
			int index = InputCliUtils.getUserInput(-1,
					MENU_COMPUTER_UPDATE_INDEX, false);
			tmpComputer = ComputerServiceImpl.INSTANCE.getByID(index);
			if (tmpComputer == null)
				error = true;
		} while (error);
		return tmpComputer;
	}

}
