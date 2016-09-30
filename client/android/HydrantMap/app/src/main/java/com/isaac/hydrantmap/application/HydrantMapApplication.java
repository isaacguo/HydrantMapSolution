package com.isaac.hydrantmap.application;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.isaac.hydrantmap.core.AppServiceProvider;
import com.isaac.hydrantmap.manager.SqlliteDatabaseManager;
import com.isaac.hydrantmap.manager.UnitsDatabaseManager;

/**
 * Created by yueguo01 on 11/21/2015.
 */
public class HydrantMapApplication extends Application
{
    public static AppServiceProvider serviceProvider=new AppServiceProvider();

    @Override
    public void onCreate()
    {
        super.onCreate();
        SDKInitializer.initialize(this);

        serviceProvider.setService(android.content.res.Resources.class, getResources());
        serviceProvider.setService(Context.class, this.getApplicationContext());


        SqlliteDatabaseManager sqm=new SqlliteDatabaseManager();
        sqm.initialize(serviceProvider);
        serviceProvider.setService(SqlliteDatabaseManager.class,sqm);

        UnitsDatabaseManager udm=new UnitsDatabaseManager();
        udm.initialize(serviceProvider);
        serviceProvider.setService(UnitsDatabaseManager.class,udm);



    }
}
