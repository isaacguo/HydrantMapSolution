package com.isaac.hydrantmap.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "ResponsibleUnits")
public class FirehouseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	String name;
	
	public Set<UnitEntity> getUnits() {
		return units;
	}

	public void setUnits(Set<UnitEntity> units) {
		this.units = units;
	}

	@OneToMany(mappedBy = "firehouse", cascade = CascadeType.ALL, orphanRemoval = true)//, fetch = FetchType.LAZY)
	@JsonManagedReference("firehouse-unit")
	Set<UnitEntity> units=new HashSet<>();
	

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

	public Set<HydrantEntity> getHydrants() {
		return hydrants;
	}

	public void setHydrants(Set<HydrantEntity> hydrants) {
		this.hydrants = hydrants;
	}

	@OneToMany(mappedBy = "firehouse", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("hydrant-firehouse")
	private Set<HydrantEntity> hydrants = new HashSet<>();

	public FirehouseEntity() {
		super();
	}

	@Override
	public String toString() {
		return "FirehouseEntity [id=" + id + ", name=" + name + ", units=" + units + ", hydrants=" + hydrants + "]";
	}
}
