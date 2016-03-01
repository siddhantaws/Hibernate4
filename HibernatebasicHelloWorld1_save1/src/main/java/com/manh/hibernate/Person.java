package com.manh.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table
public class Person 
{
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="Customerseq")
	@SequenceGenerator(sequenceName="Customersequence",name="Customerseq",allocationSize=1)
    private Long id;
 
    @Column(name="cname") 
    private String name;
    
  
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public Person(String name) {
		super();
		this.name = name;
		
	}
	public Person(){}
}
