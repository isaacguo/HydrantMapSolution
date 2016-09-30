package com.isaac.hydrantmap.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Hydrants")
public class HydrantEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	Long waterSourceNumber;

	@ManyToOne(cascade = CascadeType.ALL) // (fetch=FetchType.LAZY)
	@JoinColumn(name = "districtId")
	@JsonBackReference("hydrant-district")
	DistrictEntity district;
	String address;
	@ManyToOne(cascade = CascadeType.ALL) // (fetch=FetchType.LAZY)
	@JoinColumn(name = "firehouseId")
	@JsonBackReference("hydrant-firehouse")
	FirehouseEntity firehouse;
	@ManyToOne(cascade = CascadeType.ALL) // (fetch=FetchType.LAZY)
	@JoinColumn(name = "waterSourceFormId")
	@JsonBackReference("hydrant-waterSourceForm")
	WaterSourceFormEntity waterSourceForm;
	String pipeDiameter;
	@ManyToOne(cascade = CascadeType.ALL) // (fetch=FetchType.LAZY)
	@JoinColumn(name = "pipeTypeId")
	@JsonBackReference("hydrant-pipeType")
	PipeTypeEntity pipeType;
	Double longitude;
	Double altitude;
	String status;

	public HydrantEntity() {
		super();
	}

	public String getAddress() {
		return address;
	}

	public Double getAltitude() {
		return altitude;
	}

	public DistrictEntity getDistrict() {
		return district;
	}

	public Long getId() {
		return id;
	}

	public Double getLongitude() {
		return longitude;
	}



	public String getPipeDiameter() {
		return pipeDiameter;
	}

	public PipeTypeEntity getPipeType() {
		return pipeType;
	}

	public FirehouseEntity getResponsibleUnit() {
		return firehouse;
	}

	public String getStatus() {
		return status;
	}

	public WaterSourceFormEntity getWaterSourceForm() {
		return waterSourceForm;
	}

	public Long getWaterSourceNumber() {
		return waterSourceNumber;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

	public void setDistrict(DistrictEntity district) {
		this.district = district;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	

	public void setPipeDiameter(String pipeDiameter) {
		this.pipeDiameter = pipeDiameter;
	}

	public void setPipeType(PipeTypeEntity pipeType) {
		this.pipeType = pipeType;
	}

	public void setResponsibleUnit(FirehouseEntity responsibleUnit) {
		this.firehouse = responsibleUnit;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public HydrantEntity(Long waterSourceNumber, DistrictEntity district, String address,
			FirehouseEntity firehouse, WaterSourceFormEntity waterSourceForm, String pipeDiameter,
			PipeTypeEntity pipeType, Double longitude, Double altitude, String status) {
		super();
		this.waterSourceNumber = waterSourceNumber;
		this.district = district;
		this.address = address;
		this.firehouse = firehouse;
		this.waterSourceForm = waterSourceForm;
		this.pipeDiameter = pipeDiameter;
		this.pipeType = pipeType;
		this.longitude = longitude;
		this.altitude = altitude;
		this.status = status;
	}

	public void setWaterSourceForm(WaterSourceFormEntity waterSourceForm) {
		this.waterSourceForm = waterSourceForm;
	}

	public void setWaterSourceNumber(Long waterSourceNumber) {
		this.waterSourceNumber = waterSourceNumber;
	}

	@Override
	public String toString() {
		return "HydrantEntity [id=" + id + ", waterSourceNumber=" + waterSourceNumber + ", district=" + district
				+ ", address=" + address + ", firehouse=" + firehouse + ", waterSourceForm="
				+ waterSourceForm + ", pipeDiameter=" + pipeDiameter + ", pipeType=" + pipeType + ", longitude="
				+ longitude + ", altitude=" + altitude + ", status=" + status + "]";
	}
}
