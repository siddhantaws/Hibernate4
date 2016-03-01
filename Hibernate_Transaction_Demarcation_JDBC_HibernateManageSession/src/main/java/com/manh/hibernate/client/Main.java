package com.manh.hibernate.client;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.manh.hibernate.Product;
import com.manh.hibernate.util.HibernateUtil;

public class Main {
    
    public static void main(String[] args) {
        
        // Set up database tables
        HibernateUtil.droptable("drop table Product");
        HibernateUtil.setup("create table Product ( id int, name VARCHAR(20))");
        
        // Create SessionFactory and Session object
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        //Session session = sessionFactory.openSession();
        Session session = sessionFactory.getCurrentSession();
        
        // Perform life-cycle operations under a transaction
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            
            // Create a Product object and save it
            Product p1 = new Product();
            p1.setName("Sang Shin");
            session.save(p1);
            
            // Create another Product object and save it.
            Product p2 = new Product();
            p2.setName("Young Shin");
            session.save(p2);
            
            // Retrieve the Product objects
            Product Product = (Product)session. get(Product.class, p1.getId());
            System.out.println("First Product retrieved = " + Product.getName());
            Product = (com.manh.hibernate.Product)session.get(Product.class, p2.getId());
            System.out.println("Second Product retrieved = " + Product.getName());
            
            // Hibernate will close the session after commit is done
            tx.commit();
            tx = null;
        } catch ( HibernateException e ) {
            if ( tx != null ) tx.rollback();
            e.printStackTrace();
        } 
        
        // No need to close session since transaction commit or rollback automatically
        // closes the session.  Indeed, if you comment out the code below, you will
        // experience " Session was already closed" exception.
        //finally {
         //   session.close();
       // }
        
        // Display tables
        HibernateUtil.checkData("select * from Product");
              
    }
       
}