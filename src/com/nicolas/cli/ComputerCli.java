package com.nicolas.cli;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.nicolas.dao.fabric.DaoFabric;
import com.nicolas.dao.instance.ComputerDao;
import com.nicolas.models.Computer;

public class ComputerCli {
	private ComputerDao computerDao;
	private final String MENU_COMPUTER_CREATION_HEADER = "############ Computer creation ############";
	private final String MENU_COMPUTER_UPDATE_HEADER = "############ Computer Update ############";
	private final String MENU_COMPUTER_CREATION_NAME = "enter a name :";
	private final String MENU_COMPUTER_CREATION_INTRODUCED = "enter the introduced date :";
	private final String MENU_COMPUTER_CREATION_DISCONTINUED = "enter the discontinued date :";
	private final String MENU_COMPUTER_UPDATE_INDEX = "enter the computer index to update :";

	public ComputerCli(){
		computerDao = DaoFabric.getInstance().createComputerDao();
	}
	
	public void showComputers(){
		List<Computer> computers = new ArrayList<Computer>();
		computers = computerDao.getAllComputers();
		for(Computer c : computers){
			System.out.println(c.toString());
		}
	}
	
	public void createComputer(){
		System.out.println(MENU_COMPUTER_CREATION_HEADER);
		String name = InputCliUtils.getStringFromUser(MENU_COMPUTER_CREATION_NAME);
		LocalDate introducedDate = InputCliUtils.getDateFromUser(MENU_COMPUTER_CREATION_INTRODUCED);
		LocalDate discontinuedDate = InputCliUtils.getDateFromUser(MENU_COMPUTER_CREATION_DISCONTINUED);
		
		Computer tmpComputer = new Computer (0,name,introducedDate,discontinuedDate,1);
		computerDao.addComputer(tmpComputer);

	}
	
	public void updateComputer(){
		System.out.println(MENU_COMPUTER_UPDATE_HEADER);
		int index = InputCliUtils.getUserInput(-1, MENU_COMPUTER_UPDATE_INDEX);
		String name = InputCliUtils.getStringFromUser(MENU_COMPUTER_CREATION_NAME);
		LocalDate introducedDate = InputCliUtils.getDateFromUser(MENU_COMPUTER_CREATION_INTRODUCED);
		LocalDate discontinuedDate = InputCliUtils.getDateFromUser(MENU_COMPUTER_CREATION_DISCONTINUED);
		
		Computer tmpComputer = new Computer (index ,name,introducedDate,discontinuedDate,1);
		computerDao.updateComputer(tmpComputer);

	}
	
}
