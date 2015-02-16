package com.nicolas.models;

import java.util.ArrayList;
import java.util.List;

public class Page {
	public int nbComputerPerPage = 10;
	private int totalComputers =0;
	private List<Computer> computerList;
	private int index;
	private int totalPages;
	private int[] range;
	private final static int MAX_PAGE = 10;

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
	public int getNbComputerPerPage() {
		return nbComputerPerPage;
	}
	public void setNbComputerPerPage(int nbComputerPerPage) {
		this.nbComputerPerPage = nbComputerPerPage;
	}
	public int getTotalComputers() {
		return totalComputers;
	}
	public void setTotalComputers(int totalComputers) {
		this.totalComputers = totalComputers;
	}
	public int[] getRange() {
		int ofset = (int) MAX_PAGE/2;

		if(totalPages < MAX_PAGE){
			return new int[] {0,totalPages};
		}
		if(index < ofset  )
		{
			return new int[] {0,MAX_PAGE};
		}
		if(index > totalPages - ofset )
		{
			return new int[] {totalPages - MAX_PAGE,totalPages};
		}
		else
		{
			return new int[] {index -ofset,index +ofset};
		}
	}
	
	@Override
	public String toString() {
		String res = "*************** page " + index +" / "+  totalPages + " ***************\n";
				
				for (Computer c : computerList) {
					res += c.toString() + "\n";
				}
		return res;
	}	
	
}
