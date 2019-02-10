package com.invmgmt.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

	}
	return taxInvoiceaved;
    }
}
