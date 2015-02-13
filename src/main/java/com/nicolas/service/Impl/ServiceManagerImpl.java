package com.nicolas.service.Impl;


public enum ServiceManagerImpl {
	INSTANCE;
	
	private static CompanyServiceImpl companyServiceImpl;
	private static ComputerServiceImpl computerServiceImpl;
	
	static{
		companyServiceImpl = new CompanyServiceImpl();
		computerServiceImpl = new ComputerServiceImpl();	
	}
	
	private ServiceManagerImpl(){}
	
	public CompanyServiceImpl getCompanyServiceImpl(){
		return companyServiceImpl;
	}
	
	public ComputerServiceImpl getComputerServiceImpl(){
		return computerServiceImpl;
	}
}
