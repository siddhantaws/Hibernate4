package com.manh.hibernate.client;


import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.manh.hibernate.Product;
import com.manh.hibernate.util.HibernateUtil;

public class Main {

    public static void main(String[] args) {

        // Set up database tables
        HibernateUtil.droptable("drop table Product");
        HibernateUtil.setup("create table Product ( id number, version int, name VARCHAR(20))");

        // Create SessionFactory and Session object
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        // Perform life-cycle operations under a transaction
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            // Create a Product object and commit it
            Product Product = new Product();
            Product.setName("Sang Shin");
            session.save(Product);

            System.out.println("Before commit: Name = " + Product.getName() + ", Version = " + Product.getVersion());  // version = 0
            tx.commit();
            session.refresh(Product);
            System.out.println("After commit: Name = " + Product.getName() + ", Version = " + Product.getVersion());     // version = 0

            // 2nd commit
            tx = session.beginTransaction();
            Product.setName("Yo man!");
            System.out.println("Before commit: Name = " + Product.getName() + ", Version = " + Product.getVersion());  // version = 0
            tx.commit();
            session.refresh(Product);
            System.out.println("After commit: Name = " + Product.getName() + ", Version = " + Product.getVersion());     // version = 1

            // 3rd commit
            tx = session.beginTransaction();
            Product.setName("Crazy Horse");
            System.out.println("Before commit: Name = " + Product.getName() + ", Version = " + Product.getVersion());  // version = 1
            tx.commit();
            session.refresh(Product);
            System.out.println("After commit: Name = " + Product.getName() + ", Version = " + Product.getVersion());    // version = 2

            // 4th commit
            tx = session.beginTransaction();
            Product.setName("Beautie");
            System.out.println("Before commit: Name = " + Product.getName() + ", Version = " + Product.getVersion());  // version = 2
            tx.commit();
            session.refresh(Product);
            System.out.println("After commit: Name = " + Product.getName() + ", Version = " + Product.getVersion());  // version = 3
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
        HibernateUtil.checkData("select * from Product");

    }
}
