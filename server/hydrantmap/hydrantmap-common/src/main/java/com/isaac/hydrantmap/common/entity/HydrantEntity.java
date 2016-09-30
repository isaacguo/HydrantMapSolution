package com.isaac.hydrantmap.common.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.isaac.hydrantmap.common.HydrantForm;
import com.isaac.hydrantmap.common.HydrantStatus;
import com.isaac.hydrantmap.common.WayToAcquireWater;

@Entity
@Table(name = "Hydrants")
public class HydrantEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	/**
	 * 辖区中队
	 */
	@ManyToOne // (fetch=FetchType.LAZY)
	@JoinColumn(name = "firehouseId")
	FirehouseEntity firehouse;

	/**
	 * 水源编号
	 */
	Long hydrantUniqueId;

	/**
	 * 行政区
	 */
	@ManyToOne
	@JoinColumn(name = "regionId")
	RegionEntity region;

	/**
	 * 详细位置
	 */
	String address;

	/**
	 * 水源形式
	 */
	HydrantForm hydrantForm;

	boolean unlimitedWaterStorageCapacity;
	public boolean isUnlimitedWaterStorageCapacity() {
		return unlimitedWaterStorageCapacity;
	}

	public void setUnlimitedWaterStorageCapacity(boolean unlimitedWaterStorageCapacity) {
		this.unlimitedWaterStorageCapacity = unlimitedWaterStorageCapacity;
	}

	/**
	 * 储水量
	 */
	Long waterStorageCapacity;

	/**
	 * 取水方式
	 */
	WayToAcquireWater wayToAcquireWater; 
	
	
	/**
	 * 经度
	 */
	Double longitude;
	
	/**
	 * 纬度
	 */
	Double altitude;
	
	/**
	 * 状态
	 */
	HydrantStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FirehouseEntity getFirehouse() {
		return firehouse;
	}

	public void setFirehouse(FirehouseEntity firehouse) {
		this.firehouse = firehouse;
	}

	public Long getHydrantUniqueId() {
		return hydrantUniqueId;
	}

	public void setHydrantUniqueId(Long hydrantUniqueId) {
		this.hydrantUniqueId = hydrantUniqueId;
	}

	public RegionEntity getRegion() {
		return region;
	}

	public void setRegion(RegionEntity region) {
		this.region = region;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public HydrantForm getHydrantForm() {
		return hydrantForm;
	}

	public void setHydrantForm(HydrantForm hydrantForm) {
		this.hydrantForm = hydrantForm;
	}

	public Long getWaterStorageCapacity() {
		return waterStorageCapacity;
	}

	public void setWaterStorageCapacity(Long waterStorageCapacity) {
		this.waterStorageCapacity = waterStorageCapacity;
	}

	public WayToAcquireWater getWayToAcquireWater() {
		return wayToAcquireWater;
	}

	public void setWayToAcquireWater(WayToAcquireWater wayToAcquireWater) {
		this.wayToAcquireWater = wayToAcquireWater;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getAltitude() {
		return altitude;
	}

	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

	public HydrantStatus getStatus() {
		return status;
	}

	public void setStatus(HydrantStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "HydrantEntity [id=" + id + ", firehouse=" + firehouse + ", hydrantUniqueId=" + hydrantUniqueId
				+ ", region=" + region + ", address=" + address + ", hydrantForm=" + hydrantForm
				+ ", unlimitedWaterStorageCapacity=" + unlimitedWaterStorageCapacity + ", waterStorageCapacity="
				+ waterStorageCapacity + ", wayToAcquireWater=" + wayToAcquireWater + ", longitude=" + longitude
				+ ", altitude=" + altitude + ", status=" + status + "]";
	}

	public HydrantEntity() {
		super();
	}

	public HydrantEntity(Long id, FirehouseEntity firehouse, Long hydrantUniqueId, RegionEntity region, String address,
			HydrantForm hydrantForm, boolean unlimitedWaterStorageCapacity, Long waterStorageCapacity,
			WayToAcquireWater wayToAcquireWater, Double longitude, Double altitude, HydrantStatus status) {
		super();
		this.id = id;
		this.firehouse = firehouse;
		this.hydrantUniqueId = hydrantUniqueId;
		this.region = region;
		this.address = address;
		this.hydrantForm = hydrantForm;
		this.unlimitedWaterStorageCapacity = unlimitedWaterStorageCapacity;
		this.waterStorageCapacity = waterStorageCapacity;
		this.wayToAcquireWater = wayToAcquireWater;
		this.longitude = longitude;
		this.altitude = altitude;
		this.status = status;
	}

	
	
	
	/*
	String pipeDiameter;
	@ManyToOne(cascade = CascadeType.ALL) // (fetch=FetchType.LAZY)
	@JoinColumn(name = "pipeTypeId")
	@JsonBackReference("hydrant-pipeType")
	PipeTypeEntity pipeType;
	*/
	
	
	


	
}
