package com.manh.hibernate.client;


import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.manh.hibernate.Person;
import com.manh.hibernate.util.HibernateUtil;

public class Main {

    public static void main(String[] args) {
        
        Person p1 = null;
        Person p2 = null;
        Person p1_merged = null;

        Object id1 = null;
        Object id2 = null;

        // Set up database tables
        HibernateUtil.droptable("drop table person");
        HibernateUtil.setup("create table person ( id number, name VARCHAR2(20))");

        // Create SessionFactory and Session object
        SessionFactory sessions = new Configuration().configure().buildSessionFactory();
        Session session = sessions.openSession();
        System.out.println("----> Session 1 is created");

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

            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }        

        // Create a new session object
        session = sessions.openSession();
        System.out.println("----> Session 2 is created");

        // Perform life-cycle operations under a transaction
        try {
            tx = session.beginTransaction();

            // Create another Person object and save it.
            p2 = new Person();  //transient state
            p2.setName("Young Shin");
            session.save(p2);          // persistent state

            id2 = session.getIdentifier(p2);
            System.out.println("Session 1, id2 = " + id2);

            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }        
        
        // Display tables
        HibernateUtil.checkData("select * from person");

    }
}
