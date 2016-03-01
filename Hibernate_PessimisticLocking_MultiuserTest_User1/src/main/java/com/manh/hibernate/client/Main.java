package com.manh.hibernate.client;

import javax.swing.JOptionPane;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.manh.hibernate.Person;
import com.manh.hibernate.util.HibernateUtil;

public class Main {

    public static void main(String[] args) {

        // Set up database tables
        HibernateUtil.droptable("drop table person");
        HibernateUtil.setup("create table person ( id int, version int, name VARCHAR(20))");

        // Create SessionFactory and Session object
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        // Perform life-cycle operations under a transaction
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            // Create a Person object and commit it
            Person person = new Person();
            person.setName("Sang Shin");
            session.save(person);

            System.out.println("Before commit: Name = " + person.getName() + ", Version = " + person.getVersion());
            tx.commit();
            session.refresh(person);
            System.out.println("After commit: Name = " + person.getName() + ", Version = " + person.getVersion());

            // 2nd commit
            tx = session.beginTransaction();
            person.setName("Yo man!");
            System.out.println("Before commit: Name = " + person.getName() + ", Version = " + person.getVersion());
            tx.commit();
            session.refresh(person);
            System.out.println("After commit: Name = " + person.getName() + ", Version = " + person.getVersion());

            // 3rd commit
            tx = session.beginTransaction();
            person.setName("Crazy Horse");
            System.out.println("Before commit: Name = " + person.getName() + ", Version = " + person.getVersion());
            tx.commit();
            session.refresh(person);
            System.out.println("After commit: Name = " + person.getName() + ", Version = " + person.getVersion());

            // 4th commit
            tx = session.beginTransaction();
            person.setName("Beautie");
            System.out.println("Before commit: Name = " + person.getName() + ", Version = " + person.getVersion());
            tx.commit();
            
            // Pessimistic locking
            session.refresh(person, LockMode.UPGRADE);
            System.out.println("After commit: Name = " + person.getName() + ", Version = " + person.getVersion());

            // Here you are going to display the dialog box asking
            // a user to enter data.  This is to allow another user
            // to change the database table.
            tx = session.beginTransaction();
            String newName = "";
            newName = JOptionPane.showInputDialog("Please enter your name - you will experience an exception after another user changes the row");
            person.setName(newName);
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
