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

@Repository
public class BOQDetailsDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public boolean saveBOQ(BOQDetails boqDetails) {

		Session session = sessionFactory.getCurrentSession();
		try {
			session.save(boqDetails);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	@Transactional
	public ArrayList<BOQDetails> getBOQFromName(String boqName, String projectId) {

		ArrayList<BOQDetails> boqDetailsList = new ArrayList<BOQDetails>();

		Session session = sessionFactory.getCurrentSession();
		String selectHql = " FROM BOQDetails boqD where boqD.boqName='";

		Query query = session.createQuery(selectHql + boqName + "' and projectId='"+projectId+"'");
		List results = query.getResultList();

		Iterator itr = results.iterator();

		while (itr.hasNext()) {
			boqDetailsList.add((BOQDetails) itr.next());
		}

		return boqDetailsList;
	}

	@Transactional
	public ArrayList<String> getAssociatedBOQNames(String projectId) {

		ArrayList<String> boqDetailsNameList = new ArrayList<String>();

		Session session = sessionFactory.getCurrentSession();
		String selectHql = "Select distinct boqD.boqName FROM BOQDetails boqD where boqD.projectId='";

		Query query = session.createQuery(selectHql + projectId + "'");
		List results = query.getResultList();

		Iterator itr = results.iterator();

		while (itr.hasNext()) {
			boqDetailsNameList.add((String) itr.next());
		}

		return boqDetailsNameList;
	}
	
	@Transactional
	public ArrayList<String> getMatchingBOQNames(String boqName, String projectId) {

		ArrayList<String> boqNames = new ArrayList<String>();

		Session session = sessionFactory.getCurrentSession();
		String selectHql = "SELECT boqD.boqName FROM BOQDetails boqD where boqD.boqName like '%" ;

		Query query = session.createQuery(selectHql + boqName + "%' and boqD.projectId='"+projectId+"'");
		List results = query.getResultList();

		Iterator itr = results.iterator();

		while (itr.hasNext()) {
			boqNames.add((String) itr.next());
		}

		return boqNames;
	}	
}
