package com.nicolas.cli;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.nicolas.dao.instance.ComputerDao;
import com.nicolas.models.Company;
import com.nicolas.models.Computer;
import com.nicolas.models.Page;

public enum ComputerCli {
	INSTANCE;
	
	private final String MENU_COMPUTER_CREATION_HEADER = "############ Computer creation ############";
	private final String MENU_COMPUTER_UPDATE_HEADER = "############ Computer Update ############";
	private final String MENU_COMPUTER_DELETE_HEADER = "############ Computer Delete ############";
	private final String MENU_COMPUTER_CREATION_NAME = "enter a name :";
	private final String MENU_COMPUTER_CREATION_INTRODUCED = "enter the introduced date :";
	private final String MENU_COMPUTER_CREATION_DISCONTINUED = "enter the discontinued date :";
	private final String MENU_COMPUTER_UPDATE_INDEX = "enter the computer index:";
	private final String MENU_COMPUTER_DETAILS_INDEX = "enter the computer index for details :";
	private final String INVALID_INDEX = "This Index is not Valide";

	private ComputerCli() {
	}

	public void showComputers() {
		List<Computer> computers = new ArrayList<Computer>();
		computers = ComputerDao.INSTANCE.getAll();
		showComputers(computers);
	}
	
	private void showComputers(List<Computer> computers){
		for (Computer c : computers) {
			System.out.println(c.toString());
		}
	}

	public void showComputersByPage() {
		int index = 0;
		boolean exit = false;
		Page p;
		
		do{
			p = ComputerDao.INSTANCE.get(index);
			showComputers(p.getComputerList());
			String input = InputCliUtils.getStringFromUser("enter for next page q for quit",false);
			if(input.equals("q"))
				exit = true;
			if(p.getComputerList().size() < Page.NB_COMPUTERS)
				exit = true;
			index++;
		}while(!exit);
	}
	
	public void createComputer() {
		System.out.println(MENU_COMPUTER_CREATION_HEADER);
		
		String name = InputCliUtils.getStringFromUser( MENU_COMPUTER_CREATION_NAME, true);
		LocalDate introducedDate = InputCliUtils.getDateFromUser( MENU_COMPUTER_CREATION_INTRODUCED, false);
		LocalDate discontinuedDate = InputCliUtils.getDateFromUser( MENU_COMPUTER_CREATION_DISCONTINUED, false);
		int companyId = CompanyCli.INSTANCE.selectValidCompanyIndex();
		
		Company tmpCompany = null;
		if(companyId != -1)
			tmpCompany = new Company(companyId,"");
		
		Computer tmpComputer = new Computer(0, name, introducedDate, discontinuedDate, tmpCompany);

		if (ComputerDao.INSTANCE.add(tmpComputer))
			System.out.println("create with success");
		else
			System.out.println("error");

	}

	public void updateComputer() {
		System.out.println(MENU_COMPUTER_UPDATE_HEADER);
		Computer tmpComputer = selectValidComputerIndex();

		String name = InputCliUtils.getStringFromUser(MENU_COMPUTER_CREATION_NAME, false);
		if(!name.isEmpty())
			tmpComputer.setName(name);
		
		LocalDate introducedDate = InputCliUtils.getDateFromUser(MENU_COMPUTER_CREATION_INTRODUCED, false);
		if(introducedDate != null)
			tmpComputer.setIntroduced(introducedDate);
		
		LocalDate discontinuedDate = InputCliUtils.getDateFromUser(MENU_COMPUTER_CREATION_DISCONTINUED, false);
		if(discontinuedDate != null)
			tmpComputer.setDisconected(discontinuedDate);

		int companyId = CompanyCli.INSTANCE.selectValidCompanyIndex();	
		Company tmpCompany = null;
		if(companyId != -1)
		{
			tmpCompany = new Company(companyId,"");
			tmpComputer.setCompany(tmpCompany);
		}
		
		if (ComputerDao.INSTANCE.update(tmpComputer))
			System.out.println("update with success");
		else
			System.out.println("error");
	}

	public void getComputerDetails() {
		int index = InputCliUtils.getUserInput(-1, MENU_COMPUTER_DETAILS_INDEX,
				false);
		Computer detail = ComputerDao.INSTANCE.getByID(index);
		if (detail == null) {
			System.out.println(INVALID_INDEX);
		} else {
			System.out.println(detail.toString());
		}
	}
	
	public void deleteComputer(){
		System.out.println(MENU_COMPUTER_DELETE_HEADER);
		int index = selectValidComputerIndex().getId();
		if (ComputerDao.INSTANCE.delete(index))
			System.out.println("deleted with success");
		else
			System.out.println("error");

	}

	private Computer selectValidComputerIndex() {
		Computer tmpComputer = null;
		boolean error = false;
		do {
			if(error)
				System.out.println("The index does not exist");
			error = false;
			int index = InputCliUtils.getUserInput(-1, MENU_COMPUTER_UPDATE_INDEX, false);
			tmpComputer = ComputerDao.INSTANCE.getByID(index);
			if (tmpComputer == null)
					error = true;
		} while(error);
		return tmpComputer;
	}

}
