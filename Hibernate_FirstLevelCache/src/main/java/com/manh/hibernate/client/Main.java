package com.manh.hibernate.client;

import org.hibernate.*;
import org.hibernate.cfg.*;

import com.manh.hibernate.Product;
import com.manh.hibernate.util.HibernateUtil;

public class Main {

    public static void main(String[] args) {

        // Set up database tables and test data
        HibernateUtil.droptable("drop table Product");
        HibernateUtil.setup("create table Product ( id number, name VARCHAR2(30), description VARCHAR2(30), price number,supplierId number)");
        prepareTestData();

        // Create a Session object
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Product product = null;

        System.out.println("----> Perform a query 1st time");
        product = (Product) session.createQuery("from Product where name='ProductName1'").uniqueResult();

        System.out.println("----> Read it firs ttime after query");
        product = (Product) session.get(Product.class, product.getId());

        System.out.println("----> Read it second time after query");
        product = (Product) session.get(Product.class, product.getId());

        boolean b = session.contains(product);
        System.out.println("----> session.contains(product) = " + b);

        System.out.println("----> Evict it from session cache");
        session.evict(product);

        b = session.contains(product);
        System.out.println("----> session.contains(product) = " + b);

        System.out.println("----> Read it firt time after eviction");
        product = (Product) session.get(Product.class, product.getId());

        System.out.println("----> Read it again");
        product = (Product) session.get(Product.class, product.getId());

        tx.commit();

        tx = session.beginTransaction();
        System.out.println("----> Read it again in a new transaction");
        product = (Product) session.get(Product.class, product.getId());
        tx.commit();
        HibernateUtil.closeSession();
        // Display tables
        HibernateUtil.checkData("select * from Product");

    }

    // Below are utility methods
    private static void prepareTestData() {
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();

        Product product1 = new Product("ProductName1", "ProductDescription1", 2.0);
        session.save(product1);

        Product product12 = new Product("ProductName2", "ProductDescription2", 22.0);
        session.save(product12);

        Product product2 = new Product("ProductName3", "ProductDescription3", 30.0);
        session.save(product2);

        tx.commit();
        HibernateUtil.closeSession();
    }
}