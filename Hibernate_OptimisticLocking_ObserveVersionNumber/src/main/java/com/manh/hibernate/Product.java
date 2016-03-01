package com.manh.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity()
@Table()
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
    @Column
    private String name;
    @Version
    private int version;
    public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Product() {
        super();
    }
    
    public Product(String name) {
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
