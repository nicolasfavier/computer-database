package com.nicolas.models;

import java.util.ArrayList;
import java.util.List;

import com.nicolas.dto.ComputerDto;

public class Page {
	public int nbComputerPerPage = 10;
	private long totalComputers = 0;
	private List<ComputerDto> computerList;
	private int index = 0;
	private int totalPages;
	private int[] range;
	private final static int MAX_PAGE = 10;
	private ComputerSortCriteria sortCriterion = ComputerSortCriteria.ID;
	private String search = "";
	
	public enum ComputerSortCriteria {
		ID, NAME, DATE_DISCONTINUED, DATE_INTRODUCED, COMPANY_NAME;
	}
	
	
	public Page() {
		super();
		this.computerList = new ArrayList<ComputerDto>();
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

	public List<ComputerDto> getComputerList() {
		return computerList;
	}

	public void setComputerList(List<ComputerDto> computerList) {
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

	public int[] getRange() {
		int ofset = (int) MAX_PAGE / 2;

		if (totalPages < MAX_PAGE) {
			return new int[] { 0, totalPages };
		}
		if (index < ofset) {
			return new int[] { 0, MAX_PAGE };
		}
		if (index > totalPages - ofset) {
			return new int[] { totalPages - MAX_PAGE, totalPages };
		} else {
			return new int[] { index - ofset, index + ofset };
		}
	}

	@Override
	public String toString() {
		String res = "*************** page " + index + " / " + totalPages
				+ " ***************\n";

		for (ComputerDto c : computerList) {
			res += c.toString() + "\n";
		}
		return res;
	}

}
