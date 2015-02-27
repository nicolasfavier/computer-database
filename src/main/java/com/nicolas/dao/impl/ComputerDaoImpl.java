package com.nicolas.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nicolas.dao.interfaces.ComputerDao;
import com.nicolas.dao.mapper.ComputerRowMapperSpring;
import com.nicolas.dto.ComputerDtoMapper;
import com.nicolas.models.Computer;
import com.nicolas.models.Page;
import com.nicolas.utils.Utils;

/**
 * 
 * implementation of ComputerDao to get,add,delete computers
 *
 */
@Repository
public class ComputerDaoImpl implements ComputerDao {
	static Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);

	public final static String DB_TABLE = "computer";
	public final static String DB_TABLE_COMPANY = "company";
	public final static String DB_COLUMN_ID = "id";
	public final static String DB_COLUMN_NAME = "name";
	public final static String DB_COLUMN_INTRODUCED = "introduced";
	public final static String DB_COLUMN_DISCONTINUED = "discontinued";
	public final static String DB_COMPUTER_COLUMN_COMPANY_ID = "company_id";
	public final static String DB_COMPUTER_COLUMN_COMPANY_NAME = "companyName";
	public final static String DB_COLUMN_COMPANY_ID = "companyId";
	public final static String DB_COLUMN_COUNT = "count";

	private final static String ADD_COMPUTER_SQL = "INSERT INTO `" + DB_TABLE + "` " + "("
			+ DB_COLUMN_NAME + "," + DB_COLUMN_INTRODUCED + "," + DB_COLUMN_DISCONTINUED + ","
			+ DB_COMPUTER_COLUMN_COMPANY_ID + ") VALUES" + "(?,?,?,?);";

	private final static String SELECT_ALL_COMPUTERS_SQL = "SELECT " + ComputerDaoImpl.DB_TABLE
			+ ".*, " + CompanyDaoImpl.DB_COMPANY_TABLE + "." + CompanyDaoImpl.DB_COLUMN_NAME
			+ " AS " + DB_COMPUTER_COLUMN_COMPANY_NAME + " FROM " + ComputerDaoImpl.DB_TABLE
			+ " LEFT JOIN " + CompanyDaoImpl.DB_COMPANY_TABLE + " ON " + ComputerDaoImpl.DB_TABLE
			+ "." + ComputerDaoImpl.DB_COMPUTER_COLUMN_COMPANY_ID + "="
			+ CompanyDaoImpl.DB_COMPANY_TABLE + "." + CompanyDaoImpl.DB_COLUMN_ID;

	private final static String FIND_COMPUTER_BY_ID_SQL = SELECT_ALL_COMPUTERS_SQL + " WHERE "
			+ DB_TABLE + "." + DB_COLUMN_ID + " =?";

	private final static String GET_PAGES_SQL = SELECT_ALL_COMPUTERS_SQL + " WHERE " + DB_TABLE
			+ "." + DB_COLUMN_NAME + " LIKE ? OR " + DB_TABLE_COMPANY + "." + DB_COLUMN_NAME
			+ " LIKE ? ORDER BY " + DB_COLUMN_ID + " LIMIT ?,? ";

	private final static String UPDATE_COMPUTER_SQL = "UPDATE `" + DB_TABLE + "` SET `"
			+ DB_COLUMN_NAME + "`=?,`" + DB_COLUMN_INTRODUCED + "`=?,`" + DB_COLUMN_DISCONTINUED
			+ "`=?,`" + DB_COMPUTER_COLUMN_COMPANY_ID + "`=? WHERE " + DB_COLUMN_ID + " = ?";

	private final static String DELETE_COMPUTER_SQL = "DELETE FROM " + DB_TABLE + " WHERE "
			+ DB_COLUMN_ID + " =?";

	private final static String DELETE_COMPUTERS_SQL = "DELETE FROM " + DB_TABLE + " WHERE "
			+ DB_COLUMN_ID + " IN ?";

	private final static String DELETE_COMPUTERS_BY_COMPANY_ID_SQL = "DELETE FROM " + DB_TABLE
			+ " WHERE " + DB_COMPUTER_COLUMN_COMPANY_ID + " = ?";

	private final static String GET_COUNT_SQL = "SELECT COUNT(*) as " + DB_COLUMN_COUNT + " FROM "
			+ ComputerDaoImpl.DB_TABLE + " LEFT JOIN " + CompanyDaoImpl.DB_COMPANY_TABLE + " ON "
			+ ComputerDaoImpl.DB_TABLE + "." + ComputerDaoImpl.DB_COMPUTER_COLUMN_COMPANY_ID + "="
			+ CompanyDaoImpl.DB_COMPANY_TABLE + "." + CompanyDaoImpl.DB_COLUMN_ID + " WHERE "
			+ DB_TABLE + "." + DB_COLUMN_NAME + " LIKE  ? OR " + DB_TABLE_COMPANY + "."
			+ DB_COLUMN_NAME + " LIKE ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	ComputerRowMapperSpring computerRowMapperSpring;
	

	public ComputerDaoImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nicolas.dao.interfaces.ComputerDao#add(com.nicolas.models.Computer)
	 */
	@Override
	public void add(Computer computer) {
		Integer companyId = null;
		if (computer.getCompany() != null && computer.getCompany().getId() != 0)
			companyId = computer.getCompany().getId();

		this.jdbcTemplate.update(ADD_COMPUTER_SQL,
				new Object[] { computer.getName(), Utils.getTimestamp(computer.getIntroduced()),
						Utils.getTimestamp(computer.getDiscontinued()), companyId });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.ComputerDao#getByID(int)
	 */
	@Override
	public Computer getByID(int index) {
		return this.jdbcTemplate.queryForObject(FIND_COMPUTER_BY_ID_SQL, new Object[] { index },
				computerRowMapperSpring);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.ComputerDao#getAll()
	 */
	@Override
	public List<Computer> getAll() {
		return this.jdbcTemplate.query(SELECT_ALL_COMPUTERS_SQL, computerRowMapperSpring);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nicolas.dao.interfaces.ComputerDao#getPage(com.nicolas.models.Page,
	 * java.lang.String)
	 */
	@Override
	public Page getPage(Page page, String name) {
		String WrapName = "%" + name + "%";
		int nbComputer = page.getIndex() * page.getNbComputerPerPage();
		int nbComputerPerPage = page.getNbComputerPerPage();

		Object[] params = new Object[] { WrapName, WrapName, nbComputer, nbComputerPerPage };

		List<Computer> computerList = this.jdbcTemplate.query(GET_PAGES_SQL, params,
				computerRowMapperSpring);
		page.setComputerList(ComputerDtoMapper.ComputerToDto(computerList));

		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.ComputerDao#getCount(java.lang.String)
	 */
	public int getCount(String name) {
		String wrapName = "%" + name + "%";
		return this.jdbcTemplate.queryForObject(GET_COUNT_SQL, new Object[] { wrapName, wrapName },
				Integer.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nicolas.dao.interfaces.ComputerDao#update(com.nicolas.models.Computer
	 * )
	 */
	@Override
	public void update(Computer computer) {

		Integer companyId = null;
		if (computer.getCompany() != null && computer.getCompany().getId() != 0)
			companyId = computer.getCompany().getId();

		Object[] params = new Object[] { computer.getName(),
				Utils.getTimestamp(computer.getIntroduced()),
				Utils.getTimestamp(computer.getDiscontinued()), companyId, computer.getId() };

		this.jdbcTemplate.update(UPDATE_COMPUTER_SQL, params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.ComputerDao#delete(int)
	 */
	@Override
	public void delete(int index) {
		this.jdbcTemplate.update(DELETE_COMPUTER_SQL, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.ComputerDao#deleteIds(java.lang.String)
	 */
	@Override
	public void deleteIds(String computerIds) {
		String ids = "(" + computerIds + ")";
		this.jdbcTemplate.update(DELETE_COMPUTERS_SQL, ids);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.ComputerDao#deleteByCompanyId(int,
	 * java.sql.Connection)
	 */
	@Override
	public void deleteByCompanyId(int companyId) {
		this.jdbcTemplate.update(DELETE_COMPUTERS_BY_COMPANY_ID_SQL, companyId);
	}
}
