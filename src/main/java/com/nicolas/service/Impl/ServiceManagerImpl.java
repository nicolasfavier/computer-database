package com.nicolas.service.Impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 
 * Create and return Services Implementations
 *
 */

@Component
public class ServiceManagerImpl {
	
	private ServiceManagerImpl(){}
	
	@Bean
	public CompanyServiceImpl companyServiceImpl(){
		return new CompanyServiceImpl();
	}
	
	@Bean
	public ComputerServiceImpl computerServiceImpl(){
		return new ComputerServiceImpl();
	}
}
