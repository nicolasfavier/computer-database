package com.nicolas.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nicolas.dao.interfaces.ComputerDao;
import com.nicolas.dao.mapper.ComputerRowMapperSpring;
import com.nicolas.dto.ComputerDtoMapper;
import com.nicolas.models.Computer;
import com.nicolas.models.Page;
import com.nicolas.models.Page.ComputerSortCriteria;
import com.nicolas.runtimeException.PersistenceException;
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
	private ComputerDtoMapper computerDtoMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ComputerRowMapperSpring computerRowMapperSpring;

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

		try {
			this.jdbcTemplate.update(
					ADD_COMPUTER_SQL,
					new Object[] { computer.getName(),
							Utils.getTimestamp(computer.getIntroduced()),
							Utils.getTimestamp(computer.getDiscontinued()), companyId });
		} catch (DataAccessException e) {
			LOGGER.error("[sql error] " + e);
			throw new PersistenceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.ComputerDao#getByID(int)
	 */
	@Override
	public Computer getByID(int index) {
		Computer c = null;
		try {
			c = this.jdbcTemplate.queryForObject(FIND_COMPUTER_BY_ID_SQL, new Object[] { index },
					computerRowMapperSpring);
		} catch (DataAccessException e) {
			LOGGER.error("[sql error] " + e);
			throw new PersistenceException(e);
		}
		return c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.ComputerDao#getAll()
	 */
	@Override
	public List<Computer> getAll() {
		List<Computer> lc = new ArrayList<Computer>();
		try {
			lc = this.jdbcTemplate.query(SELECT_ALL_COMPUTERS_SQL, computerRowMapperSpring);
		} catch (DataAccessException e) {
			LOGGER.error("[sql error] " + e);
			throw new PersistenceException(e);
		}
		return lc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nicolas.dao.interfaces.ComputerDao#getPage(com.nicolas.models.Page,
	 * java.lang.String)
	 */
	@Override
	public Page getPage(Page page) {
		String WrapName = "%" + page.getSearch() + "%";
		String orderBy = "";
		int nbComputer = page.getIndex() * page.getNbComputerPerPage();
		int nbComputerPerPage = page.getNbComputerPerPage();

		orderBy = generateOrderPart(page.getSortCriterion());

		Object[] params = new Object[] { WrapName, WrapName, nbComputer, nbComputerPerPage };

		String querry = "SELECT computer.*, company.name AS companyName FROM computer LEFT JOIN company ON computer.company_id=company.id WHERE computer.name LIKE ?  OR  company.name LIKE ? ORDER BY "
				+ orderBy + " LIMIT ?,?";

		try {
			List<Computer> computerList = this.jdbcTemplate.query(querry, params,
					computerRowMapperSpring);

			page.setComputerList(computerDtoMapper.ComputerToDto(computerList));
		} catch (DataAccessException e) {
			LOGGER.error("[sql error] " + e);
			throw new PersistenceException(e);
		}

		return page;
	}

	private String generateOrderPart(ComputerSortCriteria sortCriterion) {
		// Thread synchronization isn't an issue in this scope
		// So using a StringBuilder is safe
		StringBuilder stringBuilder = new StringBuilder();
		switch (sortCriterion) {
		case ID:
			stringBuilder.append("computer.id");
			break;
		case NAME:
			stringBuilder.append("computer.name");
			break;
		case DATE_DISCONTINUED:
			stringBuilder.append("computer.discontinued");
			break;
		case DATE_INTRODUCED:
			stringBuilder.append("computer.introduced");
			break;
		case COMPANY_NAME:
			stringBuilder.append("company.name");
			break;
		default:
			stringBuilder.append(".id");
		}
		stringBuilder.append(" asc");

		return stringBuilder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.ComputerDao#getCount(java.lang.String)
	 */
	public int getCount(String name) {
		int c = 0;
		String wrapName = "%" + name + "%";

		try {
			c = this.jdbcTemplate.queryForObject(GET_COUNT_SQL,
					new Object[] { wrapName, wrapName }, Integer.class);
		} catch (DataAccessException e) {
			LOGGER.error("[sql error] " + e);
			throw new PersistenceException(e);
		}
		return c;
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

		try {
			this.jdbcTemplate.update(UPDATE_COMPUTER_SQL, params);
		} catch (DataAccessException e) {
			LOGGER.error("[sql error] " + e);
			throw new PersistenceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.ComputerDao#delete(int)
	 */
	@Override
	public void delete(int index) {
		try {
			this.jdbcTemplate.update(DELETE_COMPUTER_SQL, index);
		} catch (DataAccessException e) {
			LOGGER.error("[sql error] " + e);
			throw new PersistenceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.ComputerDao#deleteIds(java.lang.String)
	 */
	@Override
	public void deleteIds(String computerIds) {
		String ids = "(" + computerIds + ")";
		try {
			this.jdbcTemplate.update(DELETE_COMPUTERS_SQL, ids);
		} catch (DataAccessException e) {
			LOGGER.error("[sql error] " + e);
			throw new PersistenceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.ComputerDao#deleteByCompanyId(int,
	 * java.sql.Connection)
	 */
	@Override
	public void deleteByCompanyId(int companyId) {
		try {
			this.jdbcTemplate.update(DELETE_COMPUTERS_BY_COMPANY_ID_SQL, companyId);
		} catch (DataAccessException e) {
			LOGGER.error("[sql error] " + e);
			throw new PersistenceException(e);
		}
	}
}
