package com.manh.hibernate.client;


import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.manh.hibernate.Person;
import com.manh.hibernate.util.HibernateUtil;

public class Main {

    public static void main(String[] args) {


        // Create SessionFactory and Session object
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        // Perform life-cycle operations under a transaction
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            // Read the first record
            Person person = (Person) session.load(Person.class, 1);
            person.setName("new name");

            // Commit the change
            System.out.println("Before commit: Name = " + person.getName() + ", Version = " + person.getVersion());
            tx.commit();
            session.refresh(person);
            System.out.println("After commit: Name = " + person.getName() + ", Version = " + person.getVersion());

            tx = null;
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
