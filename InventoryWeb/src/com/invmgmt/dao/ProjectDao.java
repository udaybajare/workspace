package com.invmgmt.dao;

import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.invmgmt.entity.Project;

@Repository
public class ProjectDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public int addProject(Project proj) {

		int projectId;

		Session session = sessionFactory.openSession();

		projectId = (int) session.save(proj);

		return projectId;
	}
	
	@Transactional
	public Project getProject(int projectId)
	{
		Project project = new Project();
		Session session = sessionFactory.openSession();
		
		String hql = "FROM Project P WHERE P.projectId = ";
		
		Query query = session.createQuery(hql+projectId);
		List results = query.getResultList();
		
		Iterator itr = results.iterator();
		
		/*while(itr.hasNext())
		{
			//System.out.println("Result from DB " + ((Project)itr.next()).getProjectDesc()+" /n "+((Project)itr.next()).getProjectName());
		}*/
		
		return project;		
	}
}
