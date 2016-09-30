package com.isaac.hydrantmap.datasource;

import android.app.Activity;

import com.isaac.hydrantmap.core.AppServiceProvider;
import com.isaac.hydrantmap.core.interfaces.IHydrantDataSource;
import com.isaac.hydrantmap.core.interfaces.IUnitDataSource;
import com.isaac.hydrantmap.manager.SqlliteDatabaseManager;
import com.isaac.hydrantmap.manager.UnitsDatabaseManager;
import com.isaac.hydrantmap.model.Hydrant;
import com.isaac.hydrantmap.model.UnitEntity;

import java.util.List;

/**
 * Created by yueguo01 on 3/15/2016.
 */
public class UnitSQLliteDataSource implements IUnitDataSource
{
    UnitsDatabaseManager manager;

    Activity activity;
    public void initialize(AppServiceProvider provider, Activity activity)
    {
        manager = provider.getService(UnitsDatabaseManager.class);
        this.activity=activity;
    }

    @Override
    public List<UnitEntity> getUnitData()
    {
        return manager.showUnits(activity);
    }
}
