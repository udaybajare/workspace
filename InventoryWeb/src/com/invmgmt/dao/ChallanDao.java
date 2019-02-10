package com.invmgmt.dao;

import org.aspectj.lang.annotation.AfterReturning;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.invmgmt.entity.ChallanDetails;

@Repository
public class ChallanDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public boolean saveChallan(ChallanDetails challanDetails) {
	boolean saved = false;
	try {
	    Session session = sessionFactory.getCurrentSession();

	    session.saveOrUpdate(challanDetails);

	    saved = true;
	} catch (Exception ex) {

	}

	return saved;
    }
}
