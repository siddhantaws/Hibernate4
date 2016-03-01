package com.manh.hibernate.client;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.manh.hibernate.Person;

import com.manh.hibernate.util.HibernateUtil;

public class HibernateTest 
{
	
	 public static void main(String[] args) {

	        // Set up database tables
	        HibernateUtil.droptable("drop table person");
	        HibernateUtil.setup("create table person ( id number, cname VARCHAR(20))");

	        // Create SessionFactory and Session object
	        SessionFactory sessions = new Configuration().configure().buildSessionFactory();
	        Session session = sessions.openSession();

	        Person p1 = null;
	        Person p2 = null;
	        Person p1_merged = null;

	        Object id1 = null;
	        Object id2 = null;

	        // Perform life-cycle operations under a transaction
	        Transaction tx = null;
	        try {
	            tx = session.beginTransaction();

	            // Create a Person object and save it
	            p1 = new Person();  //transient state
	            p1.setName("Sang Shin");
	            // Persist the given transient instance, first assigning a 
	            // generated identifier.
	            session.save(p1);          // persistent state

	            id1 = session.getIdentifier(p1);
	            System.out.println("Session 1, id1 = " + id1);

	            // Create another Person object and save it.
	            p2 = new Person();  //transient state
	            p2.setName("Young Shin");
	            session.save(p2);          // persistent state

	            id2 = session.getIdentifier(p2);
	            System.out.println("Session 1, id2 = " + id2);

	            System.out.println("About to perform commit...");
	            tx.commit();
	            System.out.println("After commit...");
	            
	            // Start the 2nd transaction in the same session
	            tx = session.beginTransaction();
	            
	            // p1 is still in persistent state since it still
	            // has a session it is associated with.
	            p1.setName("Sang Shin2");   
	            // Since p1 is still a persistence object, there is
	            // no need to load it from the database table.
	            // So you will not see SQL.
	            System.out.println("About to perform merge...");
	            p1_merged = (Person) session.merge(p1);          
	            System.out.println("After the merge....");

	            id1 = session.getIdentifier(p1_merged);
	            System.out.println("Session 2, id1 = " + id1);

	            // p2 is still in persistent state since it still
	            // has a session it is associated with.
	            p2.setName("Young Shin2"); 
	            session.save(p2);          
	            id2 = session.getIdentifier(p2);
	            System.out.println("Session 2, id2 = " + id2);
	            
	            System.out.println("About to perform commit...");
	            tx.commit();
	            System.out.println("After commit...");

	        } catch (HibernateException e) {
	            if (tx != null) {
	                tx.rollback();
	            }
	            e.printStackTrace();
	        } finally {
	            session.close();
	        }

	        // p1 and p2 are now in detached state
	        // You will experience  Session is closed! exception
	        // id1 = session.getIdentifier(p1);

	        // Display tables
	        HibernateUtil.checkData("select * from person");

	    }
}
