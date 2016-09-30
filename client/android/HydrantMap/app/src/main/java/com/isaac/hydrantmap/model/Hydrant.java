package com.isaac.hydrantmap.model;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by yueguo01 on 11/18/2015.
 * Define the model of the hydrant
*/
public class Hydrant
{
    public Hydrant()
    {}


    public Hydrant(int id, String name, String alias, String unit, String district, Date lastMaintenanceDate, String comments, String hydrantType, String workingState, double waterPressure, double longitude, double latitude, String locationType, String locationDesc)
    {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.unit = unit;
        this.district = district;
        this.lastMaintenanceDate = lastMaintenanceDate;
        this.comments = comments;
        this.hydrantType = hydrantType;
        this.workingState = workingState;
        this.longitude = longitude;
        this.latitude = latitude;
        this.locationType = locationType;
        this.locationDesc= locationDesc;
        this.waterPressure=waterPressure;
    }

    int id;
    String name;
    String alias;
    String unit;
    String district;
    Date lastMaintenanceDate;
    String comments;
    String hydrantType;
    String workingState;
    double latitude;
    double longitude;
    String locationType;
    double waterPressure;


    public String getLocationDesc()
    {
        return locationDesc;
    }

    public void setLocationDesc(String locationDesc)
    {
        this.locationDesc = locationDesc;
    }

    String locationDesc;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAlias()
    {
        return alias;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getDistrict()
    {
        return district;
    }

    public void setDistrict(String district)
    {
        this.district = district;
    }

    public Date getLastMaintenanceDate()
    {

        return lastMaintenanceDate;
    }

    public void setLastMaintenanceDate(Date createdDate)
    {
        this.lastMaintenanceDate = createdDate;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public String getHydrantType()
    {
        return hydrantType;
    }

    public void setHydrantType(String hydrantType)
    {
        this.hydrantType = hydrantType;
    }

    public String getWorkingState()
    {
        return workingState;
    }

    public void setWorkingState(String workingState)
    {
        this.workingState = workingState;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public String getLocationType()
    {
        return locationType;
    }

    public void setLocationType(String locationType)
    {
        this.locationType = locationType;
    }

    public double getWaterPressure(){

        return waterPressure;
    }
    public void setWaterPressure(double waterPressure){this.waterPressure=waterPressure;}

    @Override
    public String toString()
    {
        return String.format("<Hydrant HydrantID=\"%1$s\" Unit=\"%2$s\" District=\"%3$s\" Status=\"%4$s\" Pressure=\"%5$s\" Longitude=\"%6$s\" Latitude=\"%7$s\" LocDesc=\"%8$s\" Date=\"\"/>",
                             getName(), getUnit(),getDistrict(),getWorkingState(),getWaterPressure(),getLongitude(),getLatitude(),getLocationDesc(),"");
        //HydrantID="001" Unit="测试中队" District="海淀区" Status="可用" Pressure="0.3" Longitude="124.8863147" Latitude="42.80871696" LocDesc="北京市" Date=""/>
    }
}
