package com.invmgmt.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.invmgmt.entity.Mappings;

@Repository
public class MappingsDao {

	@Autowired
	SessionFactory sessionFactory;

	public List getAssociatedOptions(String superSet, String superSetVal, String subSet, String inventory) {
		List<String> associatedValues = new ArrayList<String>();
		Session session = null;
		String selectHql = "";

		if (inventory == null) {
			selectHql = "SELECT distinct mpng." + subSet + " FROM Mappings mpng";
		} else if (superSetVal.equalsIgnoreCase("null")) {
			selectHql = "SELECT distinct mpng." + subSet + " FROM Mappings mpng where mpng." + superSet + " is "
					+ superSetVal;
		} else {
			selectHql = "SELECT distinct mpng." + subSet + " FROM Mappings mpng where mpng.inventoryName='" + inventory
					+ "' and mpng." + superSet + " = '" + superSetVal + "'";
		}

		if (inventory != null && !(inventory.trim().equals("Pipe"))) {
			selectHql = "SELECT distinct mpng." + subSet + " FROM Mappings mpng where mpng.inventoryName='" + inventory
					+ "'";
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

	public ArrayList<Mappings> getAllMappinsData() {
		ArrayList<Mappings> mappingsList = new ArrayList<Mappings>();
		Session session = null;
		try {
			String selectHql = "From Mappings";
			session = sessionFactory.openSession();
			Query query = session.createQuery(selectHql);

			mappingsList = (ArrayList<Mappings>) query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mappingsList;
	}
}
