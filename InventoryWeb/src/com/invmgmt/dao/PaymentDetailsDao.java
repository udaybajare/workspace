package com.invmgmt.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.invmgmt.entity.BOQDetails;
import com.invmgmt.entity.PaymentDetails;

@Repository
public class PaymentDetailsDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public boolean savePaymentDetails(PaymentDetails paymentDetails) {

	Session session = sessionFactory.getCurrentSession();
	try {
	    session.save(paymentDetails);
	} catch (Exception ex) {
	    return false;
	}
	return true;
    }

    @Transactional
    public ArrayList<PaymentDetails> getPayentDetails(String projectId) {

	ArrayList<PaymentDetails> paymentDetailsList = new ArrayList<PaymentDetails>();

	Session session = sessionFactory.getCurrentSession();
	String selectHql = " FROM PaymentDetails payD where payD.projectId='";

	Query query = session.createQuery(selectHql + projectId + "'");
	List results = query.getResultList();

	Iterator itr = results.iterator();

	while (itr.hasNext()) {
	    paymentDetailsList.add((PaymentDetails) itr.next());
	}

	return paymentDetailsList;
    }

}
