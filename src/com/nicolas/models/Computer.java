package com.nicolas.models;

import java.time.LocalDate;

public class Computer {
	private int id;
	private String name;
	private LocalDate introduced;
	private LocalDate disconected;
	private Company company;
	
	public Computer(){}

	public Computer(int id, String name, LocalDate introduced, LocalDate disconected,
			Company company) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.disconected = disconected;
		this.company = company;
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
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		String str = "Computer [id=" + id + ", name=" + name ;
		if(introduced != null)
			str += ", introduced= "+ introduced;
		if(disconected != null)
			str += ", disconected= "+ disconected;	
		if(company != null)
			str += ", "+ company.toString();
		str += "]";
		return str;
	}
}
