package com.isaac.hydrantmap.common.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Regions")
public class RegionEntity {
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	Long id;
	
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
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	String name;
	
	public RegionEntity() {
		super();
	}
	@Override
	public String toString() {
		return "RegionEntity [id=" + id + ", name=" + name + ", parentId=" + parentId + ", grade=" + grade + "]";
	}
	Long parentId;
	Integer grade;
	
	/*
	@OneToMany(mappedBy = "district", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonBackReference("hydrant-district")
	private Set<HydrantEntity> hydrants= new HashSet<>();
	

	public Set<HydrantEntity> getHydrants() {
		return hydrants;
	}

	public void setHydrants(Set<HydrantEntity> hydrants) {
		this.hydrants = hydrants;
	}
	*/

	

}
