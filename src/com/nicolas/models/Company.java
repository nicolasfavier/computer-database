package com.nicolas.models;

public class Company {
	private int i;
	private String name;

	public Company(){}
	
	public Company(int i, String name) {
		super();
		this.i = i;
		this.name = name;
	}
	
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Company [i=" + i + ", name=" + name + "]";
	}	
}
