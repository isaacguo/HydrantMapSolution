package com.isaac.hydrantmap.core.interfaces;

/**
 * Created by yueguo01 on 7/30/2015.
 */
public interface IHttpRequestAsyncTaskCallback
{
    void onResult(String actionType, int actionId, String result);
}
