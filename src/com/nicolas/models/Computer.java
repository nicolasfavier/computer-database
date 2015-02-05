package com.nicolas.models;

import java.sql.Date;

public class Computer {
	private int id;
	private String name;
	private Date introduced;
	private Date disconected;
	private int company_id;
	
	public Computer(){}

	public Computer(int id, String name, Date introduced, Date disconected,
			int company_id) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.disconected = disconected;
		this.company_id = company_id;
	}

	public int getI() {
		return id;
	}
	public void setI(int i) {
		this.id = i;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getIntroduced() {
		return introduced;
	}
	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}
	public Date getDisconected() {
		return disconected;
	}
	public void setDisconected(Date disconected) {
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
