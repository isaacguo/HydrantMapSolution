package com.isaac.hydrantmap.model;

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
@Table(name = "PipeTypes")
public class PipeTypeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	String name;
	
	@OneToMany(mappedBy = "pipeType", cascade = CascadeType.ALL)
	@JsonBackReference("hydrant-pipeType")
	private Set<HydrantEntity> hydrants= new HashSet<>();

	public PipeTypeEntity() {
		super();
	}

	@Override
	public String toString() {
		return "PipeTypeEntity [id=" + id + ", name=" + name + ", hydrants=" + hydrants + "]";
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
}
