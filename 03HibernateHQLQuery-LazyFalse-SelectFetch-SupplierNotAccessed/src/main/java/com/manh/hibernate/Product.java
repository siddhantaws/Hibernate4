package com.manh.hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity()
@Table()
public class Product
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private Supplier supplier;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private double price;
    
    public Product()
    {
        super();
    }
    
    public Product(String name, String description, double price)
    {
        super();
        this.name = name;
        this.description = description;
        this.price = price;
    }
    
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
 
    public Supplier getSupplier()
    {
        return supplier;
    }
    public void setSupplier(Supplier supplier)
    {
        this.supplier = supplier;
    }
    
    public double getPrice()
    {
        return price;
    }
    public void setPrice(double price)
    {
        this.price = price;
    }
}
