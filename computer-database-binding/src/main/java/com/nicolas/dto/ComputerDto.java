package com.nicolas.dto;

import org.hibernate.validator.constraints.NotBlank;

import com.nicolas.models.Company;
import com.nicolas.validator.Date;

/**
 * light object for the view Date are store with string (smaller that object
 * date)
 *
 */

public class ComputerDto {
	private int id;

	@NotBlank(message = "{computer_dto.name_not_blank}")
	private String name;

	@Date(message = "{computer_dto.date_introduced_format}")
	private String introduced;

	@Date(message = "{computer_dto.date_discontinued_format}")
	private String discontinued;

	private Company company;

	public ComputerDto() {
	}

	public ComputerDto(int id, String name, String introduced,
			String discontinued, Company company) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	public ComputerDto(int id, String name, String introduced,
			String discontinued, int companyId) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = new Company(companyId);
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

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		String str = "Computer [id=" + id + ", name=" + name;
		if (introduced != null)
			str += ", introduced= " + introduced;
		if (discontinued != null)
			str += ", discontinued= " + discontinued;
		if (company != null)
			str += ", " + company.toString();
		str += "]";
		return str;
	}

}
