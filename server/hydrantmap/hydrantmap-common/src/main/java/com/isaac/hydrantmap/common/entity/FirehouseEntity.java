package com.isaac.hydrantmap.common.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Firehouses")
public class FirehouseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	String name;

	@ManyToOne
	@JsonManagedReference("firehouses-region")
	RegionEntity region;

	/*
	@OneToMany(mappedBy = "firehouse", cascade = CascadeType.ALL, orphanRemoval = true)//, fetch = FetchType.LAZY)
	@JsonManagedReference("firehouse-unit")
	Set<UnitEntity> units=new HashSet<>();
	
	
	@OneToMany(mappedBy = "firehouse", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("hydrant-firehouse")
	private Set<HydrantEntity> hydrants = new HashSet<>();
	*/
	
	public FirehouseEntity() {
		super();
	}
	


	public FirehouseEntity(Long id, String name, RegionEntity region) {
		super();
		this.id = id;
		this.name = name;
		this.region = region;
	}


	

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public RegionEntity getRegion() {
		return region;
	}



	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setRegion(RegionEntity region) {
		this.region = region;
	}



	@Override
	public String toString() {
		return "FirehouseEntity [id=" + id + ", name=" + name + ", region=" + region + "]";
	}
}
