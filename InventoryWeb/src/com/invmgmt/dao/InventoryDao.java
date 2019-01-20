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

import com.invmgmt.entity.Inventory;
import com.invmgmt.entity.InventorySpec;

@Repository
public class InventoryDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public boolean saveInventory(Inventory inventory) {
		Session session = sessionFactory.getCurrentSession();

		System.out.println("Saving in db : " + inventory);
		try {
			session.saveOrUpdate(inventory);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

	@Transactional
	public ArrayList<Inventory> getAvailableInventory()
	{
		ArrayList<Inventory> inventoryList = new ArrayList<Inventory>();
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Inventory";

		Query query = session.createQuery(hql);
		List results = query.getResultList();

		Iterator itr = results.iterator();

		while (itr.hasNext()) {

			inventoryList.add((Inventory) itr.next());
		}
		return inventoryList;
	}
	
	
	@Transactional
	public int getAvailableQuantity(Inventory inventory) {
		int availableQuantity = 0;
		InventorySpec inventorySpec = inventory.getInventorySpec();

		Session session = null;
		String selectHql = "SELECT invD.quantity FROM Inventory invD where "
				+ "invD.inventorySpec.inventory = '" + inventorySpec.getInventoryName() + "' and  "
				+ "invD.inventorySpec.material = '" + inventorySpec.getMaterial() + "' and  "
				+ "invD.inventorySpec.type = '" + inventorySpec.getType() + "' and  "
				+ "invD.inventorySpec.manifMethod = '" + inventorySpec.getManifMethod() + "' and  "
				+ "invD.inventorySpec.gradeOrClass = '" + inventorySpec.getGradeOrClass() + "' and  "
				+ "invD.inventorySpec.size = '" + inventorySpec.getSize() + "' and  " 
				+ "invD.inventorySpec.ends = '"	+ inventorySpec.getEnds() + "'";
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(selectHql);

			availableQuantity = (int) query.uniqueResult();

			System.out.println("Available Quentity is : " + availableQuantity);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return availableQuantity;
	}

	@Transactional
	public int getPurchaseRate(Inventory inventory) {
		int purchaseRate = 0;
		InventorySpec inventorySpec = inventory.getInventorySpec();

		Session session = null;
		String selectHql = "SELECT invD.purchaseRate FROM Inventory invD where "
				+ "invD.inventorySpec.inventory = '" + inventorySpec.getInventoryName() + "' and  "
				+ "invD.inventorySpec.material = '" + inventorySpec.getMaterial() + "' and  "
				+ "invD.inventorySpec.type = '" + inventorySpec.getType() + "' and  "
				+ "invD.inventorySpec.manifMethod = '" + inventorySpec.getManifMethod() + "' and  "
				+ "invD.inventorySpec.gradeOrClass = '" + inventorySpec.getGradeOrClass() + "' and  "
				+ "invD.inventorySpec.size = '" + inventorySpec.getSize() + "' and  " 
				+ "invD.inventorySpec.ends = '"	+ inventorySpec.getEnds() + "'";
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(selectHql);

			purchaseRate = Integer.valueOf((String)query.uniqueResult());

			System.out.println("PurchaseRate is : " + purchaseRate);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return purchaseRate;
	}
}
