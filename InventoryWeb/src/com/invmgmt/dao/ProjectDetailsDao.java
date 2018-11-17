package com.invmgmt.dao;

import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.invmgmt.entity.Project;
import com.invmgmt.entity.ProjectDetails;

@Repository
public class ProjectDetailsDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public void updateProjet(ProjectDetails projectDetails) {

		Session session = sessionFactory.getCurrentSession();

		try {
			session.saveOrUpdate(projectDetails);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Transactional
	public Project searchProject(Project project) {

		Session session = sessionFactory.getCurrentSession();

		try {

			CriteriaQuery query = session.getCriteriaBuilder().createQuery(Project.class);
			// query.add(Restrictions.like("column", "a", MatchMode.ANYWHERE));

			project = session.find(Project.class, project.getProjectName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return project;
	}

	@Transactional
	public ProjectDetails getProjectDetails(Project project) {

		Session session = sessionFactory.getCurrentSession();

		ProjectDetails projectDetails = null;

		try {
			projectDetails = session.find(ProjectDetails.class, project.getProjectId());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return projectDetails;
	}

	@Transactional
	public ProjectDetails getProjectDetails(int projectId) {
		ProjectDetails progDetails = new ProjectDetails();

		Session session = sessionFactory.getCurrentSession();

		String hql = "FROM ProjectDetails P WHERE P.projectId = ";

		Query query = session.createQuery(hql + projectId);
		List results = query.getResultList();

		Iterator itr = results.iterator();

		while (itr.hasNext()) {
			progDetails = (ProjectDetails) itr.next();
		}

		return progDetails;
	}
}
