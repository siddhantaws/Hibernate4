package com.manh.hibernate.client;


import org.hibernate.*;
import org.hibernate.cfg.*;

public class Main {

    public static void main(String[] args) {

        // Perform the read operation
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();

        // Begin transaction and do Rollback
        System.out.println("Begin transaction...");
        Transaction tx = session.beginTransaction();

        // Display the concrete implementation class of the Transaction interface
        System.out.println("---> Concrete implementation class of Transaction interface: "
                + tx.getClass());
    }
}