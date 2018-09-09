package com.invmgmt.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.invmgmt.entity.ProjectDetails;

@Repository
public class ProjectDetailsDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public void updateProjet(ProjectDetails projectDetails) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		tx = session.beginTransaction();

		try {
			session.save(projectDetails);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		tx.commit();
	}
}
