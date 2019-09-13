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
    public int saveInventory(Inventory inventory) {
	Session session = sessionFactory.getCurrentSession();

	try {
	    session.saveOrUpdate(inventory);
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return inventory.getInventoryRowId();
    }

    @Transactional
    public ArrayList<Inventory> getAvailableInventory() {
	ArrayList<Inventory> inventoryList = new ArrayList<Inventory>();
	Session session = sessionFactory.getCurrentSession();
	String hql = "FROM Inventory invD where invD.assignedProject = null or invD.assignedProject = ''";

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
	String selectHql = "SELECT invD.quantity FROM Inventory invD where " + "invD.inventorySpec.inventoryName = '"
		+ inventorySpec.getInventoryName() + "' and  " + "invD.inventorySpec.material = '"
		+ inventorySpec.getMaterial() + "' and  " + "invD.inventorySpec.type = '" + inventorySpec.getType()
		+ "' and  " + "invD.inventorySpec.manifMethod = '" + inventorySpec.getManifMethod() + "' and  "
		+ "invD.inventorySpec.gradeOrClass = '" + inventorySpec.getGradeOrClass() + "' and  "
		+ "invD.inventorySpec.size = '" + inventorySpec.getSize() + "' and  " + "invD.inventorySpec.ends = '"
		+ inventorySpec.getEnds() + "' and  (" + "invD.assignedProject = null or invD.assignedProject = '')";
	try {
	    session = sessionFactory.openSession();
	    Query query = session.createQuery(selectHql);

	    Object result = query.uniqueResult();

	    availableQuantity = (int) (result != null ? result : 0);

	    System.out.println("Available Quentity is : " + availableQuantity);
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return availableQuantity;
    }

    @Transactional
    public int getAssignedQuantity(Inventory inventory) {
	int availableQuantity = 0;
	InventorySpec inventorySpec = inventory.getInventorySpec();

	Session session = null;
	String selectHql = "SELECT invD.quantity FROM Inventory invD where " + "invD.inventorySpec.inventoryName = '"
		+ inventorySpec.getInventoryName() + "' and  " + "invD.inventorySpec.material = '"
		+ inventorySpec.getMaterial() + "' and  " + "invD.inventorySpec.type = '" + inventorySpec.getType()
		+ "' and  " + "invD.inventorySpec.manifMethod = '" + inventorySpec.getManifMethod() + "' and  "
		+ "invD.inventorySpec.gradeOrClass = '" + inventorySpec.getGradeOrClass() + "' and  "
		+ "invD.inventorySpec.size = '" + inventorySpec.getSize() + "' and  " + "invD.inventorySpec.ends = '"
		+ inventorySpec.getEnds() + "' and  " + "invD.assignedProject = '" + inventory.getAssignedProject()
		+ "' and invD.status='assigned'";
	try {
	    session = sessionFactory.openSession();
	    Query query = session.createQuery(selectHql);

	    Object result = query.uniqueResult();

	    availableQuantity = (int) (result != null ? result : 0);

	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return availableQuantity;
    }

    public int isEntityPresent(Inventory inventory) {
	int associatedRowId = 0;
	InventorySpec inventorySpec = inventory.getInventorySpec();

	Session session = null;
	String selectHql = "SELECT invD.inventoryRowId FROM Inventory invD where "
		+ "invD.inventorySpec.inventoryName = '" + inventorySpec.getInventoryName() + "' and  "
		+ "invD.inventorySpec.material = '" + inventorySpec.getMaterial() + "' and  "
		+ "invD.inventorySpec.type = '" + inventorySpec.getType() + "' and  "
		+ "invD.inventorySpec.manifMethod = '" + inventorySpec.getManifMethod() + "' and  "
		+ "invD.inventorySpec.gradeOrClass = '" + inventorySpec.getGradeOrClass() + "' and  "
		+ "invD.inventorySpec.size = '" + inventorySpec.getSize() + "' and  " + "invD.assignedProject = '"
		+ inventory.getAssignedProject() + "' and  " + "invD.inventorySpec.ends = '" + inventorySpec.getEnds()
		+ "'";
	try {
	    session = sessionFactory.openSession();
	    Query query = session.createQuery(selectHql);

	    associatedRowId = query.uniqueResult() != null ? (int) query.uniqueResult() : 0;

	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return associatedRowId;

    }

    @Transactional
    public double getPurchaseRate(Inventory inventory) {
	double purchaseRate = 0;
	InventorySpec inventorySpec = inventory.getInventorySpec();

	Session session = null;
	String selectHql = "SELECT invD.purchaseRate FROM Inventory invD where "
		+ "invD.inventorySpec.inventoryName = '" + inventorySpec.getInventoryName() + "' and  "
		+ "invD.inventorySpec.material = '" + inventorySpec.getMaterial() + "' and  "
		+ "invD.inventorySpec.type = '" + inventorySpec.getType() + "' and  "
		+ "invD.inventorySpec.manifMethod = '" + inventorySpec.getManifMethod() + "' and  "
		+ "invD.inventorySpec.gradeOrClass = '" + inventorySpec.getGradeOrClass() + "' and  "
		+ "invD.inventorySpec.size = '" + inventorySpec.getSize() + "' and  " + "invD.inventorySpec.ends = '"
		+ inventorySpec.getEnds() + "'  and  " + "invD.assignedProject = '" + inventory.getAssignedProject()
		+ "'";
	try {
	    session = sessionFactory.openSession();
	    Query query = session.createQuery(selectHql);

	    purchaseRate = Double.parseDouble((String) query.uniqueResult());

	    System.out.println("PurchaseRate is : " + purchaseRate);
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return purchaseRate;
    }

    @Transactional
    public ArrayList<Inventory> getAssignedInventory(String projectName) {

	ArrayList<Inventory> assignedInventory = new ArrayList<Inventory>();

	Session session = null;
	String selectHql = "FROM Inventory invD where " + "invD.assignedProject = '" + projectName + "' and "
		+ "invD.status = 'assigned'";
	try {
	    session = sessionFactory.openSession();
	    Query query = session.createQuery(selectHql);

	    assignedInventory = (ArrayList<Inventory>) query.getResultList();

	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return assignedInventory;
    }

    @Transactional
    public int getLatestInventoryEntryNo() {
	Session session = sessionFactory.getCurrentSession();

	String selectHql = "Select max(invD.inventoryRowId) from Inventory invD";

	Query query = session.createQuery(selectHql);
	int inventoryEntryNo = query.uniqueResult() == null ? 0 : (int) query.uniqueResult();
	return inventoryEntryNo;
    }
}
