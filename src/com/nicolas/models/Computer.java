package com.nicolas.models;

import java.time.LocalDate;
import java.util.Date;

public class Computer {
	private int id;
	private String name;
	private LocalDate introduced;
	private LocalDate disconected;
	private int company_id;
	
	public Computer(){}

	public Computer(int id, String name, LocalDate introduced, LocalDate disconected,
			int company_id) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.disconected = disconected;
		this.company_id = company_id;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getIntroduced() {
		return introduced;
	}
	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}
	public LocalDate getDisconected() {
		return disconected;
	}
	public void setDisconected(LocalDate disconected) {
		this.disconected = disconected;
	}
	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced="
				+ introduced + ", disconected=" + disconected + ", company_id="
				+ company_id + "]";
	}
}
