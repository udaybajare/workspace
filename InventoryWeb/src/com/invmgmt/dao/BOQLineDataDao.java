package com.invmgmt.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.invmgmt.entity.BOQLineData;

@Repository
public class BOQLineDataDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public BOQLineData getLineData(String material)
	{
		ArrayList<BOQLineData> boqLineDataList = new ArrayList<BOQLineData>();
		
		try {
			Session session = sessionFactory.getCurrentSession();
					
			Query sqlQuery = session.createQuery("from BOQLineData as lineData where lineData.material=:material");
			sqlQuery.setParameter("material", material);
			
			List results = sqlQuery.getResultList();
			
			
			System.out.println("Getting data for : " + material);
			Iterator itr = results.iterator();
			
			while(itr.hasNext())
			{
				boqLineDataList.add((BOQLineData)itr.next());
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return boqLineDataList.get(0);
	}
}
