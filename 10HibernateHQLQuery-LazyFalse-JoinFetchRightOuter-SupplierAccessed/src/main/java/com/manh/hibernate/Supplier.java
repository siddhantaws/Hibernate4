package com.manh.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table
public class Supplier
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @Column
	private String name;
    @OneToMany(mappedBy="supplier",cascade=CascadeType.ALL,targetEntity=Product.class,fetch=FetchType.LAZY)
    private List products = new ArrayList();
    
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
    public List getProducts()
    {
        return products;
    }
    public void setProducts(List products)
    {
        this.products = products;
    }
}
