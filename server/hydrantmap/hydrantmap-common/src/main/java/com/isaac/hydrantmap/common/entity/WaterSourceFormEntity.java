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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "WaterSourceForms")
public class WaterSourceFormEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	public WaterSourceFormEntity() {
		super();
	}

	@Override
	public String toString() {
		return "WaterSourceFormEntity [id=" + id + ", name=" + name + ", hydrants=" + hydrants + "]";
	}

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

	String name;

	@OneToMany(mappedBy = "waterSourceForm", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonBackReference("hydrant-waterSourceForm")
	private Set<HydrantEntity> hydrants = new HashSet<>();
}
