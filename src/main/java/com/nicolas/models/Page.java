package com.nicolas.models;

import java.util.ArrayList;
import java.util.List;

public class Page {
	public final static int NB_COMPUTERS = 10;
	private List<Computer> computerList;
	private int index;
	private int totalPages;

	public Page() {
		super();
		this.computerList = new ArrayList<Computer>();
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
}
