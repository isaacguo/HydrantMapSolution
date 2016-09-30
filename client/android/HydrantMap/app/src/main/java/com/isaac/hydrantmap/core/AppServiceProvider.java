package com.isaac.hydrantmap.core;

import com.isaac.hydrantmap.core.interfaces.IAppServiceProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yueguo01 on 11/22/2015.
 */
public class AppServiceProvider implements IAppServiceProvider
{
    Map<Class,Object> map=new HashMap<Class,Object>();

    @Override
    public <T> T getService(Class<T> clazz) {
        if(this.map.containsKey(clazz))
        {
            return (T)this.map.get(clazz);
        }
        else
            return null;

    }

    @Override
    public <T> void setService(Class<T> clazz, T instance) {

        this.map.put(clazz, instance);
    }
}
