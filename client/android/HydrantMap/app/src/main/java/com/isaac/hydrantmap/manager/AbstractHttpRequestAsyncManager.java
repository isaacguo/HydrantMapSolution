package com.isaac.hydrantmap.manager;


import android.text.TextUtils;

import com.isaac.hydrantmap.core.AppServiceProvider;
import com.isaac.hydrantmap.core.interfaces.IHttpRequestAsyncTaskCallback;
import com.isaac.hydrantmap.core.interfaces.ILogToView;

import java.util.HashMap;

/**
 * Created by yueguo01 on 7/30/2015.
 */
public abstract class AbstractHttpRequestAsyncManager implements IHttpRequestAsyncTaskCallback
{

    protected static final String SUCCESS="success";
    protected AppServiceProvider serviceProvider;
    protected android.content.res.Resources resources;

    protected String host;
    protected String name;
    protected String password;
    protected String uid;
    protected String auth;

    ILogToView logToView;
    HashMap<String,String> args=new HashMap<String,String>();

    public void setLogToView(ILogToView view)
    {
        this.logToView = view;
    }

    public ILogToView getLogToView()
    {
        return logToView;
    }

    public void setArgs(HashMap<String, String> args)
    {
        this.args = args;
    }

    public void initialize(AppServiceProvider serviceProvider)
    {
        this.serviceProvider = serviceProvider;
        resources = serviceProvider.getService(android.content.res.Resources.class);
    }

    protected void logToView(String log)
    {
        if (logToView!=null)
        {
            logToView.logLine(log);
        }
    }


    protected boolean validateKeyFields(String... args)
    {
        for(String arg : args)
        {
            if(TextUtils.isEmpty(arg))
            {
                return false;
            }
        }
        return true;
    }
}
