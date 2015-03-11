package com.nicolas.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nicolas.dao.interfaces.UserDao;
import com.nicolas.models.User;

@Repository
public class UserDaoImpl implements UserDao{

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public User getUser(String username) {
		User u = null;
		Session session = sessionFactory.getCurrentSession();

		// Get All Employees
		Query query = session.createQuery("from User where username= :username");
		query.setString("username", username);
		@SuppressWarnings("unchecked")
		List<User> user = (List<User>) query.list();
		if (user.size() > 0) {
			u = user.get(0);
		}

		return u;
	}
     
  
}
