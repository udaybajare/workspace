package com.invmgmt.dao;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.invmgmt.entity.Valves;

@Repository
public class ValvesDao {
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public ArrayList<Valves> getValveDetails()
	{
		Session session = sessionFactory.getCurrentSession();
		
		String sqlStr = "From Valves";
		
		Query query = session.createQuery(sqlStr);
		
		ArrayList<Valves> valveDetailsList = (ArrayList<Valves>) query.getResultList();
		
		return valveDetailsList;
	}

}
