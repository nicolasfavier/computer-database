package com.nicolas.service.Interfaces;

import java.util.List;

import com.nicolas.models.Computer;
import com.nicolas.models.Page;

public interface ComputerService {
		/**
		* Permit to add a computer in the database.
		* @param ComputerTest
		* @return true if it is successful or false if it's not.
		*/
		public void add(Computer computer);

		/**
		* Get a list of Computer from the database.
		* @return a List<Computer>
		*/
		public List<Computer> getAll();

		/**
		* Get a page of Computer from the database.
		* @param index represent the actual page.
		* @return a List<Computer> that is the page[index].
		*/
		public Page getPage(Page page, String name); 

		/**
		* Delete a computer in the database.
		* @param computerId, the id of the computer to delete.
		* @return true if the delete is successful or false if it isn't.
		*/
		public void delete(int computerId);

		/**
		* Delete a computer in the database.
		* @param computerId, the id of the computer to delete.
		* @return true if the delete is successful or false if it isn't.
		*/
		public void deleteIds(String computerIds);
		
		/**
		* Update a computer in the database.
		* @param computer that is the computer to update.
		* @return true if the update is successful of false if it isn't.
		*/
		public void update(Computer computer);

		/**
		* Get a computer by id from the database
		* @param computerId, the id of the computer to find
		* @return the computer find in the database or null if not found.
		*/
		public Computer getByID(int computerId);
		
	}