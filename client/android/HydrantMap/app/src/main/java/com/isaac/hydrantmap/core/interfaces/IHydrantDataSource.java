package com.isaac.hydrantmap.core.interfaces;

import com.isaac.hydrantmap.model.Hydrant;

import java.util.List;

/**
 * Created by yueguo01 on 11/18/2015.
 * define an interface to get hydrant data source from
 */
public interface IHydrantDataSource
{
    List<Hydrant> getHydrantData();
}
