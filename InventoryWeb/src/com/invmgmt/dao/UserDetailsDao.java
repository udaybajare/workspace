package com.invmgmt.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.invmgmt.entity.UserDetails;

@Repository
public class UserDetailsDao {
	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public String getEmailAddress(String userName) {
		String emailAddress = null;
		String queryString = "select emailAddress from UserDetails where userName=:userName";

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(queryString);
		query.setParameter("userName", userName == null ? "" : userName);

		emailAddress = (String) query.getSingleResult();

		return emailAddress;
	}

	@Transactional
	public boolean saveUser(UserDetails userDetails) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(userDetails);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return false;
	}
}
