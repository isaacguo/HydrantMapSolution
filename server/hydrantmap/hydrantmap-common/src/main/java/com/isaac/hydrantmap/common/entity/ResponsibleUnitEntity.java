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
@Table(name = "ResponsibleUnits")
public class ResponsibleUnitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	String name;

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

	@OneToMany(mappedBy = "responsibleUnit", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonBackReference("hydrant-responsibleUnit")
	private Set<HydrantEntity> hydrants = new HashSet<>();

	public ResponsibleUnitEntity() {
		super();
	}

	@Override
	public String toString() {
		return "ResponsibleUnitEntity [id=" + id + ", name=" + name + ", hydrants=" + hydrants + "]";
	}
}
