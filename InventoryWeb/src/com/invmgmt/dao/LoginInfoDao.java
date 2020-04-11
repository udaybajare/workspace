package com.invmgmt.dao;

import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.invmgmt.entity.LoginInfo;

@Repository
public class LoginInfoDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public String gePasswordToValidate(LoginInfo loginInfo) {

		Session session = sessionFactory.getCurrentSession();

		String selectHql = " FROM LoginInfo logI where logI.userName='";

		Query query = session.createQuery(selectHql + loginInfo.getUserName() + "'");
		List results = query.getResultList();

		Iterator itr = results.iterator();

		loginInfo = (LoginInfo) itr.next();

		return loginInfo.getPassword();
	}

	@Transactional
	public boolean addLoginInfo(LoginInfo loginInfo) {

		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(loginInfo);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
