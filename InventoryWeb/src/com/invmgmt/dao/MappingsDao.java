package com.invmgmt.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MappingsDao {

	@Autowired
	SessionFactory sessionFactory;
	
	public List getAssociatedOptions(String superSet, String superSetVal, String subSet) {
		List<String> associatedValues = new ArrayList<String>();
		Session session = null;
		String selectHql = "";
		if(superSetVal.equalsIgnoreCase("null"))
		{
			selectHql = "SELECT distinct mpng." + subSet + " FROM Mappings mpng where mpng." + superSet + " is "
					+ superSetVal;
		}
		else
		{
			selectHql = "SELECT distinct mpng." + subSet + " FROM Mappings mpng where mpng." + superSet + " = '"
					+ superSetVal +"'";	
		}
		
		
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(selectHql);

			associatedValues = query.getResultList();
		} finally {
			session.close();
		}
		return associatedValues;
	}
}
