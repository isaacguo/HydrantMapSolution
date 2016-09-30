package com.isaac.hydrantmap.core.interfaces;

/**
 * Created by yueguo01 on 11/22/2015.
 */
public interface IAppServiceProvider
{
    <T> T getService(Class<T> clazz);

    <T> void setService(Class<T> clazz, T instance);
}
