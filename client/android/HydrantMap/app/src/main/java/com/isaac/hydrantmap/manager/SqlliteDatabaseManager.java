package com.isaac.hydrantmap.manager;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.isaac.hydrantmap.contentprovider.HydrantProviderMetaData;
import com.isaac.hydrantmap.core.AppServiceProvider;
import com.isaac.hydrantmap.core.interfaces.ISqlliteDatabaseManagerNewHydrantAddedListener;
import com.isaac.hydrantmap.datasource.HydrantXmlDataSource;
import com.isaac.hydrantmap.model.Hydrant;
import com.isaac.hydrantmap.utils.HttpUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLInput;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yueguo01 on 3/15/2016.
 */
public class SqlliteDatabaseManager extends AbstractHttpRequestAsyncManager

{
    private static final String ACTION_DOWNLOAD = "download";
    public static final String TAG = SqlliteDatabaseManager.class.getCanonicalName();
    private static final String dataURL = "http://www.eziru.com:8080/public/hydrant/hydrants.xml";
    private Context mContext;
    private ISqlliteDatabaseManagerNewHydrantAddedListener newHydrantAddedListener;

    public void addSqlliteDatabaseManagerNewHydrantAddedListener(ISqlliteDatabaseManagerNewHydrantAddedListener listener)
    {
        this.newHydrantAddedListener = listener;
    }

    @Override
    public void initialize(AppServiceProvider serviceProvider)
    {
        super.initialize(serviceProvider);
        mContext = serviceProvider.getService(Context.class);
    }

    public void downloadHydrantData()
    {
        HttpUtils.HttpRequestAsyncTask downloadAsyncTask = new HttpUtils.HttpRequestAsyncTask(dataURL, this, ACTION_DOWNLOAD, 0);
        downloadAsyncTask.execute();
    }

    @Override
    public void onResult(String actionType, int actionId, String result)
    {
        ContentResolver cr = this.mContext.getContentResolver();
        cr.delete(HydrantProviderMetaData.HydrantTableMetaData.CONTENT_URI, null, null);

        InputStream is = new ByteArrayInputStream(result.getBytes());
        HydrantXmlDataSource source = new HydrantXmlDataSource();
        List<Hydrant> hydrants = source.GetHydrantData(is);

        for (Hydrant hydrant : hydrants)
        {
            addHydrant(hydrant.getName(), hydrant.getUnit(), hydrant.getDistrict(), hydrant.getWorkingState(), hydrant.getWaterPressure(), hydrant.getLongitude(), hydrant.getLatitude(), hydrant.getLocationDesc());
        }
    }

    public void updateHydrant(String hydrantID, String unit, String district, String status, double pressure, double longitude, double latitude, String locDesc, int id)
    {
        Log.d(TAG, "Updating a hydrant");
        ContentValues cv = new ContentValues();
        cv.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_ID, hydrantID);
        cv.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_UNIT, unit);
        cv.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_DISTRICT, district);
        cv.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_STATUS, status);
        cv.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_PRESSURE, pressure);
        cv.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_LONGITUDE, longitude);
        cv.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_LATITUDE, latitude);
        cv.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_LOCDESC, locDesc);
        ContentResolver cr = this.mContext.getContentResolver();
        Uri uri = HydrantProviderMetaData.HydrantTableMetaData.CONTENT_URI;
        Log.d(TAG, "hydrant update uri:" + uri);
        int i= cr.update(uri,cv,"_id="+id,null);
        Log.d(TAG, "affected rows:"+ Integer.toString(i));
    }


    public void addHydrant(String hydrantID, String unit, String district, String status, double pressure, double longitude, double latitude, String locDesc)
    {
        Log.d(TAG, "Adding a hydrant");
        ContentValues cv = new ContentValues();
        cv.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_ID, hydrantID);
        cv.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_UNIT, unit);
        cv.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_DISTRICT, district);
        cv.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_STATUS, status);
        cv.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_PRESSURE, pressure);
        cv.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_LONGITUDE, longitude);
        cv.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_LATITUDE, latitude);
        cv.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_LOCDESC, locDesc);
        ContentResolver cr = this.mContext.getContentResolver();
        Uri uri = HydrantProviderMetaData.HydrantTableMetaData.CONTENT_URI;
        Log.d(TAG, "hydrant insert uri:" + uri);
        Uri insertedUri = cr.insert(uri, cv);
        Log.d(TAG, "inserted uri:" + insertedUri);
        if (newHydrantAddedListener != null)
            newHydrantAddedListener.onNewHydrantAdded();
    }

    public void removeHydrant(int id)
    {
        Uri uri = HydrantProviderMetaData.HydrantTableMetaData.CONTENT_URI;
        ContentResolver cr = mContext.getContentResolver();
        Uri delUri = Uri.withAppendedPath(uri, Integer.toString(id));
        cr.delete(delUri, null, null);
    }


    public List<Hydrant> showHydrants(Activity activity)
    {
        ArrayList<Hydrant> hydrants = new ArrayList<>();
        Uri uri = HydrantProviderMetaData.HydrantTableMetaData.CONTENT_URI;
        Cursor c = activity.managedQuery(uri, null, null, null, null);
        int hydrantIDColumn = c.getColumnIndex(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_ID);
        int hydrantUnitColumn = c.getColumnIndex(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_UNIT);
        int hydrantDistrictColumn = c.getColumnIndex(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_DISTRICT);
        int hydrantStatusColumn = c.getColumnIndex(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_STATUS);
        int hydrantPressureColumn = c.getColumnIndex(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_PRESSURE);
        int hydrantLongitudeColumn = c.getColumnIndex(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_LONGITUDE);
        int hydrantLatitudeColumn = c.getColumnIndex(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_LATITUDE);
        int hydrantLocDescColumn = c.getColumnIndex(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_LOCDESC);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
        {
            String hydrantID = c.getString(hydrantIDColumn);
            String hydrantUnit = c.getString(hydrantUnitColumn);
            String hydrantDistrict = c.getString(hydrantDistrictColumn);
            String hydrantStatus = c.getString(hydrantStatusColumn);
            double hydrantPressure = c.getDouble(hydrantPressureColumn);
            double hydrantLongitude = c.getDouble(hydrantLongitudeColumn);
            double hydrantLatitude = c.getDouble(hydrantLatitudeColumn);
            String hydrantLocDesc = c.getString(hydrantLocDescColumn);
            Hydrant hydrant = new Hydrant(0, hydrantID, "", hydrantUnit, hydrantDistrict, null, "", "", hydrantStatus, hydrantPressure, hydrantLongitude, hydrantLatitude, "", hydrantLocDesc);
            hydrants.add(hydrant);
        }

        //c.close();
        return hydrants;
    }

    public int getCount(Activity activity)
    {
        Uri uri = HydrantProviderMetaData.HydrantTableMetaData.CONTENT_URI;
        Cursor c = activity.managedQuery(uri, null, null, null, null);
        int numberOfRecords = c.getCount();
        c.close();
        return numberOfRecords;
    }
}
