package com.nicolas.dao.impl;


public enum DaoManagerImpl {
		INSTANCE;
		
		private static CompanyDaoImpl companyDaoImpl;
		private static ComputerDaoImpl computerDaoImpl;
		
		static{
			companyDaoImpl = new CompanyDaoImpl();
			computerDaoImpl = new ComputerDaoImpl();	
		}
		
		private DaoManagerImpl(){}
		
		public CompanyDaoImpl getCompanyDaoImpl(){
			return companyDaoImpl;
		}
		
		public ComputerDaoImpl getComputerDaoImpl(){
			return computerDaoImpl;
		}
}
