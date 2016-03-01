package com.manh.hibernate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table
public class Person implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
	@Column()
    private String name;
   
    public Person() {
    }
   
    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }
   
    public int getId() {
        return id;
    }
   
    public void setId(int id) {
        this.id = id;
    }
   
    public String getName() {
        return name;
    }
   
    public void setName(String name) {
        this.name = name;
    }
   
}
