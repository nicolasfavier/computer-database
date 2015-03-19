package com.nicolas.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nicolas.dao.interfaces.ComputerDao;
import com.nicolas.models.Computer;
import com.nicolas.models.Page;
import com.nicolas.models.Page.ComputerSortCriteria;

/**
 * 
 * implementation of ComputerDao to get,add,delete computers
 *
 */
@Repository
public class ComputerDaoImpl implements ComputerDao {
	static Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public ComputerDaoImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nicolas.dao.interfaces.ComputerDao#add(com.nicolas.models.Computer)
	 */
	@Override
	@Transactional
	public void add(Computer computer) {
		Session session = sessionFactory.getCurrentSession();
		session.save(computer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.ComputerDao#getByID(int)
	 */
	@Override
	@Transactional
	public Computer getByID(int index) {
		Computer c = null;

		Session session = sessionFactory.getCurrentSession();

		// Get All Employees
		Query query = session.createQuery("from Computer where id= :id");
		query.setLong("id", index);

		List<Computer> ComputerList = (List<Computer>) query.list();
		if (ComputerList.size() > 0) {
			c = ComputerList.get(0);
		}

		return c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.ComputerDao#getAll()
	 */
	@Override
	@Transactional
	public List<Computer> getAll() {
		List<Computer> lc = new ArrayList<Computer>();
		Session session = sessionFactory.getCurrentSession();

		// Get All Computers
		Query query = session.createQuery("from Computer");

		lc = (List<Computer>) query.list();

		return lc;
	}

	/*
	 * (non-Javadoc) Session session = sessionFactory.getCurrentSession();
	 * 
	 * 
	 * @see
	 * com.nicolas.dao.interfaces.ComputerDao#getPage(com.nicolas.models.Page,
	 * java.lang.String)
	 */
	@Override
	@Transactional
	public Page getPage(Page page) {
		Session session = sessionFactory.getCurrentSession();

		String orderBy = "";
		orderBy = generateOrderPart(page.getSortCriterion());

		String querry = "select cmp from Computer cmp left join cmp.company as company where cmp.name like :search OR company.name like :search order by "
				+ orderBy;

		Query query = session.createQuery(querry);
		query.setString("search", "%" + page.getSearch() + "%");
		query.setFirstResult(page.getIndex() * page.nbComputerPerPage);
		query.setMaxResults(page.nbComputerPerPage);

		page.setComputerList(query.list());

		return page;
	}

	private String generateOrderPart(ComputerSortCriteria sortCriterion) {
		// Thread synchronization isn't an issue in this scope
		// So using a StringBuilder is safe
		StringBuilder stringBuilder = new StringBuilder();
		switch (sortCriterion) {
		case ID:
			stringBuilder.append("cmp.id");
			break;
		case NAME:
			stringBuilder.append("cmp.name");
			break;
		case DATE_DISCONTINUED:
			stringBuilder.append("cmp.discontinued");
			break;
		case DATE_INTRODUCED:
			stringBuilder.append("cmp.introduced");
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
	@Override
	@Transactional
	public long getCount(String search) {
		Session session = sessionFactory.getCurrentSession();

		Query query = session
				.createQuery("select count(*) from Computer cmp left join cmp.company as company  where cmp.name like :search OR company.name like :search");

		query.setString("search", "%" + search + "%");

		return (long) query.uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nicolas.dao.interfaces.ComputerDao#update(com.nicolas.models.Computer
	 * )
	 */
	@Override
	@Transactional
	public void update(Computer computer) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(computer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.ComputerDao#delete(int)
	 */
	@Override
	@Transactional
	public void delete(int index) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete Computer where id= :id");
		query.setLong("id", index);
		query.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.ComputerDao#deleteIds(java.lang.String)
	 */
	@Override
	@Transactional
	public void deleteIds(String computerIds) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete Computer where id in :id");
		query.setString("id", computerIds);
		query.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.ComputerDao#deleteByCompanyId(int,
	 * java.sql.Connection)
	 */
	@Override
	@Transactional
	public void deleteByCompanyId(int companyId, Session session) {
		Query query = session
				.createQuery("delete Computer where company.id = :id");
		query.setLong("id", companyId);
		query.executeUpdate();
	}
}
