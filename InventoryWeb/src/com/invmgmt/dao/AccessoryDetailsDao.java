package com.invmgmt.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.invmgmt.entity.AccessoryDetails;

@Repository
public class AccessoryDetailsDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public boolean saveAccessory(AccessoryDetails accessoryDetails) {

	Session session = sessionFactory.getCurrentSession();
	try {
	    session.save(accessoryDetails);
	} catch (Exception ex) {
	    return false;
	}
	return true;
    }

    @Transactional
    public ArrayList<AccessoryDetails> getAvailableInventory() {
	ArrayList<AccessoryDetails> inventoryList = new ArrayList<AccessoryDetails>();
	Session session = sessionFactory.getCurrentSession();
	String hql = "FROM AccessoryDetails accessD where accessD.assignedProject = null or accessD.assignedProject = ''";

	Query query = session.createQuery(hql);
	List results = query.getResultList();

	Iterator itr = results.iterator();

	while (itr.hasNext()) {

	    inventoryList.add((AccessoryDetails) itr.next());
	}
	return inventoryList;
    }

}
