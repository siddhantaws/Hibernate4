package com.manh.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLock;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

@Entity()
@Table()

public class Person {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
    @Column
    private String name;
    @Version
    @Column
    //@OptimisticLock(excluded=true)
    private int version;
    public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Person() {
        super();
    }
    
    public Person(String name) {
        super();
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
