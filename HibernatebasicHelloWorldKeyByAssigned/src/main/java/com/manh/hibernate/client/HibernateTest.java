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
	public static void main(String[] args)
	{
		   
        // Set up database tables
        HibernateUtil.droptable("drop table Person");
        HibernateUtil.setup("create table Person ( id VARCHAR(50), cname VARCHAR(20))");
        
        // Create SessionFactory and Session object
        SessionFactory sessions = new Configuration().configure().buildSessionFactory();
        Session session = sessions.openSession();
        
        // Perform life-cycle operations under a transaction
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            
            // Create a Person object and save it
            Person p1 = new Person();
            p1.setName("Sang Shin");
            session.save(p1);
            
            // Create another Person object and save it.
            Person p2 = new Person();
            p2.setName("Young Shin");
            session.save(p2);
            
            // Retrieve the person objects
            Person person = (Person)session.get(Person.class, p1.getId());
            System.out.println("First person, Id generated via uuid.hex = " + person.getId());
            person = (Person)session.get(Person.class, p2.getId());
            System.out.println("Second person, Id generated via uuid.hex = " + person.getId());
            
            tx.commit();
            tx = null;
            p1.setName("Siddhant");
            sessions.openSession();
            tx = session.beginTransaction();
            Person p3=(Person)session.merge(p1);
            tx.commit();
        } catch ( HibernateException e ) {
            if ( tx != null ) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        // Display tables
        HibernateUtil.checkData("select * from Person");

    }
}
