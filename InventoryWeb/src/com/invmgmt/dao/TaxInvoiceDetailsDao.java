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

import com.invmgmt.entity.ProjectDetails;
import com.invmgmt.entity.TaxInvoiceDetails;

@Repository
public class TaxInvoiceDetailsDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public boolean saveTaxIvoice(TaxInvoiceDetails taxInvoiceDetails) {
	boolean taxInvoiceaved = false;

	try 
	{
	    Session session = sessionFactory.getCurrentSession();
	    session.save(taxInvoiceDetails);
	    taxInvoiceaved = true;
	} catch (Exception ex) 
	{
	    ex.printStackTrace();
	}
	return taxInvoiceaved;
    }
    
    @Transactional
    public ArrayList<TaxInvoiceDetails> getTaxIvoiceData(String tag, String value) {
	ArrayList<TaxInvoiceDetails> taxInvoiceDetailsList = new ArrayList<TaxInvoiceDetails>();
	try {
	    Session session = sessionFactory.getCurrentSession();
	    String sql = "from TaxInvoiceDetails td where td." + tag + "='" + value+"'";

	    Query query = session.createQuery(sql);

	    List results = query.getResultList();

	    Iterator itr = results.iterator();

	    while (itr.hasNext()) {
		taxInvoiceDetailsList.add((TaxInvoiceDetails) itr.next());
	    }
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return taxInvoiceDetailsList;
    }
}
