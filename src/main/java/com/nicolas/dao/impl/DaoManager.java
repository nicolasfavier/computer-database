package com.nicolas.dao.impl;

/**
 * 
 * Create and return DAO Implementations
 *
 */
public enum DaoManager {
		INSTANCE;
		
		private static CompanyDaoImpl companyDaoImpl;
		private static ComputerDaoImpl computerDaoImpl;
		
		static{
			companyDaoImpl = new CompanyDaoImpl();
			computerDaoImpl = new ComputerDaoImpl();	
		}
		
		private DaoManager(){}
		
		public CompanyDaoImpl getCompanyDaoImpl(){
			return companyDaoImpl;
		}
		
		public ComputerDaoImpl getComputerDaoImpl(){
			return computerDaoImpl;
		}
}
