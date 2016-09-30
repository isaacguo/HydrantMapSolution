package com.isaac.hydrantmap.core;

/**
 * Created by yueguo01 on 12/4/2015.
 */
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.isaac.hydrantmap.application.HydrantMapApplication;
import com.isaac.hydrantmap.core.interfaces.IHydrantDataSource;
import com.isaac.hydrantmap.utils.JZLocationConverter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WGSToBaidu09Tests
{
    @Test
    public void WGSToBaidu09Tests_Test1() throws Exception
    {
        CoordinateConverter cc=new CoordinateConverter();
        LatLng ll= cc.from(CoordinateConverter.CoordType.GPS).coord(new LatLng(40.80638889,114.8913889)).convert();
        double lat=ll.latitude;
        double lan=ll.longitude;


    }

    @Test
    public void WGSToBaidu09Tests_Test2() throws Exception
    {
        JZLocationConverter.LatLng latLng=JZLocationConverter.wgs84ToBd09(new JZLocationConverter.LatLng(40.8075,114.871944444444));
        double lat= latLng.getLatitude();
        double lag=latLng.getLongitude();
        String v=lat+","+lag;



    }


}
