package com.isaac.hydrantmap.common.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Persons")
public class PersonEntity {
	
	String name;
	public PersonEntity() {
		super();
	}
	public PersonEntity(String name, String phoneNumber, String workNumber, UnitEntity unit) {
		super();
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.workNumber = workNumber;
		this.unit = unit;
	}
	@Override
	public String toString() {
		return "PersonEntity [name=" + name + ", phoneNumber=" + phoneNumber + ", workNumber=" + workNumber + ", unit="
				+ unit + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getWorkNumber() {
		return workNumber;
	}
	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}
	public UnitEntity getUnit() {
		return unit;
	}
	public void setUnit(UnitEntity unit) {
		this.unit = unit;
	}
	String phoneNumber;
	String workNumber;
	@OneToOne(mappedBy="personInCharge")
	@JsonBackReference("unit-person")
	UnitEntity unit;
	
	
	

}
