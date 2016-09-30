package com.isaac.hydrantmap;

import com.isaac.hydrantmap.core.DummyHydrantDataSource;
import com.isaac.hydrantmap.core.interfaces.IHydrantDataSource;
import com.isaac.hydrantmap.model.Hydrant;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by yueguo01 on 11/19/2015.
 */
public class HydrantDataSourceTests
{
    @Test
    public void HydrantDataSource_DummyDataSourceTest() throws Exception
    {
        IHydrantDataSource dataSource=new DummyHydrantDataSource();
        List<Hydrant> hydrants= dataSource.getHydrantData();
        assertEquals(807,hydrants.size());
    }
}
