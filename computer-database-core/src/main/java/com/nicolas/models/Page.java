package com.nicolas.models;

import java.util.ArrayList;
import java.util.List;

public class Page {
	public int nbComputerPerPage = 10;
	private long totalComputers = 0;
	private List<Computer> computerList;
	private int index = 0;
	private int totalPages;
	private ComputerSortCriteria sortCriterion = ComputerSortCriteria.ID;
	private String search = "";
	
	public enum ComputerSortCriteria {
		ID, NAME, DATE_DISCONTINUED, DATE_INTRODUCED, COMPANY_NAME;
	}
	
	
	public Page() {
		super();
		this.computerList = new ArrayList<Computer>();
	}

	
	public String getSearch() {
		return search;
	}


	public void setSearch(String search) {
		this.search = search;
	}


	public ComputerSortCriteria getSortCriterion() {
		return sortCriterion;
	}

	public void setSortCriterion(ComputerSortCriteria sortCriterion) {
		this.sortCriterion = sortCriterion;
	}

	public List<Computer> getComputerList() {
		return computerList;
	}

	public void setComputerList(List<Computer> computerList) {
		this.computerList = computerList;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getNbComputerPerPage() {
		return nbComputerPerPage;
	}

	public void setNbComputerPerPage(int nbComputerPerPage) {
		this.nbComputerPerPage = nbComputerPerPage;
	}

	public long getTotalComputers() {
		return totalComputers;
	}

	public void setTotalComputers(long totalComputers) {
		this.totalComputers = totalComputers;
	}


	@Override
	public String toString() {
		String res = "*************** page " + index + " / " + totalPages
				+ " ***************\n";

		for (Computer c : computerList) {
			res += c.toString() + "\n";
		}
		return res;
	}

}
