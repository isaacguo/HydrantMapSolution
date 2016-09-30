package com.isaac.hydrantmap.core;

import com.isaac.hydrantmap.application.HydrantMapApplication;
import com.isaac.hydrantmap.core.interfaces.IHydrantDataSource;
import com.isaac.hydrantmap.model.Hydrant;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by yueguo01 on 11/22/2015.
 */
public class AppServiceProviderTests
{
    @Test
    public void AppServiceProviderTests_SimpleSetGetTest() throws Exception
    {
        IHydrantDataSource dataSource=new DummyHydrantDataSource();
        HydrantMapApplication.serviceProvider.setService(IHydrantDataSource.class, dataSource);
        IHydrantDataSource dataSource1= HydrantMapApplication.serviceProvider.getService(IHydrantDataSource.class);
        assertNotNull(dataSource1);
        assertEquals(dataSource,dataSource1);
    }
    @Test
    public void AppServiceProviderTests_SetSameTypeOfObjectTwice() throws Exception
    {
        IHydrantDataSource dataSource=new DummyHydrantDataSource();
        HydrantMapApplication.serviceProvider.setService(IHydrantDataSource.class, dataSource);
        IHydrantDataSource dataSource1=new DummyHydrantDataSource();
        HydrantMapApplication.serviceProvider.setService(IHydrantDataSource.class, dataSource1);
        IHydrantDataSource dataSource2= HydrantMapApplication.serviceProvider.getService(IHydrantDataSource.class);
        assertNotNull(dataSource2);
        assertNotEquals(dataSource2,dataSource);
        assertEquals(dataSource2, dataSource1);
    }
}
