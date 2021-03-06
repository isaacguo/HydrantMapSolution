package com.isaac.hydrantmap.datasource;

import android.app.Activity;

import com.isaac.hydrantmap.core.AppServiceProvider;
import com.isaac.hydrantmap.core.interfaces.IHydrantDataSource;
import com.isaac.hydrantmap.manager.SqlliteDatabaseManager;
import com.isaac.hydrantmap.model.Hydrant;

import java.util.List;

/**
 * Created by yueguo01 on 3/15/2016.
 */
public class SQLliteDataSource implements IHydrantDataSource
{
    SqlliteDatabaseManager manager;

    Activity activity;
    public void initialize(AppServiceProvider provider, Activity activity)
    {
        manager = provider.getService(SqlliteDatabaseManager.class);
        this.activity=activity;
    }

    @Override
    public List<Hydrant> getHydrantData()
    {
        return manager.showHydrants(activity);

    }
}
