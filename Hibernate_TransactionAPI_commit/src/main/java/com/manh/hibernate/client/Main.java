package com.manh.hibernate.client;

import java.util.*;
import java.sql.*;

import org.hibernate.*;
import org.hibernate.cfg.*;
import org.hibernate.criterion.*;

import com.manh.hibernate.Product;
import com.manh.hibernate.util.HibernateUtil;

public class Main {
    
    public static void main(String[] args) {
        
        // Set up database tables and test data
        HibernateUtil.droptable("drop table Product");
        HibernateUtil.setup("create table Product ( id number, name VARCHAR2(30), description VARCHAR2(30), price number,supplierId number)");
        prepareTestData();
        
        // Perform the read operation
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        
        // Perform a read operation just to verify the product name in the database
        Product product = null;
        product = (Product)session.createQuery("from Product where name='ProductNameOld1'").uniqueResult();
        System.out.println("Name of product retrieved from database = " + product.getName());
        
        // Begin transaction and do a commit
        System.out.println("Begin transaction...");
        Transaction tx = session.beginTransaction();
        System.out.println("Changing name of product to ProductNameNew by product.setName() method");
        product.setName("ProductNameNew");
        System.out.println("\nAbout to do Commit...");
        System.out.println("Name of product before Commit = " + product.getName());
        tx.commit();
        System.out.println("After Commit...");
        System.out.println("Name of product after Commit = " + product.getName());
        
        // Begin transaction and do Refresh 
        tx = session.beginTransaction();
        System.out.println("\nChanging name of product to ProductNameSangShin by product.setName() method");
        product.setName("ProductNameSangShin");
        System.out.println("Name of product before Refresh = " + product.getName());
        
        // The object will be refreshed with the data from the database.
        // Note that product name ProductNameSangShin in the product object will be
        // replaced by the ProductNameNew from the database.
        System.out.println("\nAbout to Refresh...");
        session.refresh(product);
        System.out.println("Name of product after Refresh = " + product.getName());
        tx.commit();
        System.out.println("Name of product after Commit = " + product.getName());
        
        // Display tables
        HibernateUtil.checkData("select * from Product");
        
    }
    
    // Below are utility methods
    
    private static void prepareTestData(){
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        
        Product product1 = new Product("ProductNameOld1","Description for Product 1", 2.0);
        session.save(product1);
        
        Product product12 = new Product("ProductNameOld2","Description for Product 2", 22.0);
        session.save(product12);
        
        Product product2 = new Product("ProductNameOld3", "Description for Product 3", 30.0);
        session.save(product2);
        
        tx.commit();
        HibernateUtil.closeSession();
    }
    
}