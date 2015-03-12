package com.nicolas.models;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


@Entity
@Table(name = "computer")
public class Computer implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "name")
	private String name;
	@Type(type = "com.nicolas.dao.mapper.CustomLocalDateTimeUserType")
	private LocalDate introduced;
	@Type(type = "com.nicolas.dao.mapper.CustomLocalDateTimeUserType")
	private LocalDate discontinued;
	@ManyToOne()
    @JoinColumn(name = "company_id")
	private Company company;

	public Computer() {
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

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + id;
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * Builder class for a Computer object
	 *
	 */
	public static class Builder {
		private Computer computer;

		public Builder() {

			computer = new Computer();
		}

		/**
		 * Sets the id attribute of the underlying Computer object.
		 *
		 * @param id
		 *            The id that should be set
		 * @return A reference to the current instance of Builder
		 */
		public Builder id(int id) {
			computer.setId(id);
			return this;
		}

		/**
		 * Sets the name attribute of the underlying Computer object.
		 *
		 * @param name
		 *            The name that should be set
		 * @return A reference to the current instance of Builder
		 */
		public Builder name(String name) {
			computer.setName(name);
			return this;
		}

		/**
		 * Sets the introduced attribute of the underlying Computer object.
		 *
		 * @param introduced
		 *            The introduction date that should be set
		 * @return A reference to the current instance of Builder
		 */
		public Builder introduced(LocalDate introduced) {
			computer.setIntroduced(introduced);
			return this;
		}

		/**
		 * Sets the discontinued attribute of the underlying Computer object.
		 *
		 * @param discontinued
		 *            The date on which the computer was discontinued
		 * @return A reference to the current instance of Builder
		 */
		public Builder discontinued(LocalDate discontinued) {
			computer.setDiscontinued(discontinued);
			return this;
		}

		/**
		 * Sets the company attribute of the underlying Computer object.
		 *
		 * @param company
		 *            The company that should be set
		 * @return A reference to the current instance of Builder
		 */
		public Builder company(Company company) {
			computer.setCompany(company);
			return this;
		}

		/**
		 * Sets the company attribute of the underlying Computer object.
		 *
		 * @param company
		 *            The company that should be set
		 * @return A reference to the current instance of Builder
		 */
		public Builder company(int companyId) {
			computer.setCompany(new Company(companyId));
			return this;
		}

		/**
		 * Creates an instance of Computer. Each attribute of Computer have a
		 * matching method in the Builder class
		 *
		 * @return An instance of computer
		 */
		public Computer build() {
			return computer;
		}
	}
}
