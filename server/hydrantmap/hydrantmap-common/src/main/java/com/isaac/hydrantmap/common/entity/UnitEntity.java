package com.isaac.hydrantmap.common.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Units")
public class UnitEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	boolean keyUnit;
	
	String description;
	double totalArea;
	String buildingHeight;
	int floorAboveGround;
	int floorBelowGround;
	String buildingStructure;
		
	String unitName;
	String address;
	String workNumber;
	String mobileNumber;
	String fireCharacters;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "personInChargeId")
	@JsonManagedReference("unit-person")
	PersonEntity personInCharge;

	String thumbPicturePath;
	String planDiagramPath;
	
	Double longitude;
	Double altitude;
	
	
	
	@Override
	public String toString() {
		return "UnitEntity [keyUnit=" + keyUnit + ", description=" + description + ", totalArea=" + totalArea
				+ ", buildingHeight=" + buildingHeight + ", floorAboveGround=" + floorAboveGround
				+ ", floorBelowGround=" + floorBelowGround + ", buildingStructure=" + buildingStructure + ", unitName="
				+ unitName + ", address=" + address + ", workNumber=" + workNumber + ", mobileNumber=" + mobileNumber
				+ ", fireCharacters=" + fireCharacters + ", personInCharge=" + personInCharge + ", planDiagramPath="
				+ planDiagramPath + ", firehouse=" + firehouse + "]";
	}



	public UnitEntity(boolean keyUnit, String description, double totalArea, String buildingHeight,
			int floorAboveGround, int floorBelowGround, String buildingStructure, String unitName, String address,
			String workNumber, String mobileNumber, String fireCharacters, PersonEntity personInCharge,
			String planDiagramPath, FirehouseEntity firehouse) {
		super();
		this.keyUnit = keyUnit;
		this.description = description;
		this.totalArea = totalArea;
		this.buildingHeight = buildingHeight;
		this.floorAboveGround = floorAboveGround;
		this.floorBelowGround = floorBelowGround;
		this.buildingStructure = buildingStructure;
		this.unitName = unitName;
		this.address = address;
		this.workNumber = workNumber;
		this.mobileNumber = mobileNumber;
		this.fireCharacters = fireCharacters;
		this.personInCharge = personInCharge;
		this.planDiagramPath = planDiagramPath;
		this.firehouse = firehouse;
	}



	public UnitEntity() {
		super();
	}



	public boolean isKeyUnit() {
		return keyUnit;
	}



	public void setKeyUnit(boolean keyUnit) {
		this.keyUnit = keyUnit;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public double getTotalArea() {
		return totalArea;
	}



	public void setTotalArea(double totalArea) {
		this.totalArea = totalArea;
	}



	public String getBuildingHeight() {
		return buildingHeight;
	}



	public void setBuildingHeight(String buildingHeight) {
		this.buildingHeight = buildingHeight;
	}



	public int getFloorAboveGround() {
		return floorAboveGround;
	}



	public void setFloorAboveGround(int floorAboveGround) {
		this.floorAboveGround = floorAboveGround;
	}



	public int getFloorBelowGround() {
		return floorBelowGround;
	}



	public void setFloorBelowGround(int floorBelowGround) {
		this.floorBelowGround = floorBelowGround;
	}



	public String getBuildingStructure() {
		return buildingStructure;
	}



	public void setBuildingStructure(String buildingStructure) {
		this.buildingStructure = buildingStructure;
	}



	public String getUnitName() {
		return unitName;
	}



	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getWorkNumber() {
		return workNumber;
	}



	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}



	public String getMobileNumber() {
		return mobileNumber;
	}



	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}



	public String getFireCharacters() {
		return fireCharacters;
	}



	public void setFireCharacters(String fireCharacters) {
		this.fireCharacters = fireCharacters;
	}



	public PersonEntity getPersonInCharge() {
		return personInCharge;
	}



	public void setPersonInCharge(PersonEntity personInCharge) {
		this.personInCharge = personInCharge;
	}



	public String getPlanDiagramPath() {
		return planDiagramPath;
	}



	public void setPlanDiagramPath(String planDiagramPath) {
		this.planDiagramPath = planDiagramPath;
	}



	public FirehouseEntity getFirehouse() {
		return firehouse;
	}



	public void setFirehouse(FirehouseEntity firehouse) {
		this.firehouse = firehouse;
	}



	@ManyToOne()
	@JoinColumn(name = "firehouseId")
	@JsonBackReference
	FirehouseEntity firehouse;
	

}
