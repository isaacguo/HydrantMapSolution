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
import com.isaac.hydrantmap.datasource.UnitXmlDataSource;
import com.isaac.hydrantmap.model.UnitEntity;
import com.isaac.hydrantmap.utils.HttpUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yueguo01 on 9/27/2016.
 */

public class UnitsDatabaseManager extends AbstractHttpRequestAsyncManager
{
    private static final String ACTION_DOWNLOAD = "download";
    public static final String TAG = UnitsDatabaseManager.class.getCanonicalName();
    private static final String dataURL = "http://www.eziru.com:8080/public/hydrant/units.xml";
    private Context mContext;


    @Override
    public void initialize(AppServiceProvider serviceProvider)
    {
        super.initialize(serviceProvider);
        mContext = serviceProvider.getService(Context.class);
    }

    public void downloadUnitData()
    {
        HttpUtils.HttpRequestAsyncTask downloadAsyncTask = new HttpUtils.HttpRequestAsyncTask(dataURL, this, ACTION_DOWNLOAD, 0);
        downloadAsyncTask.execute();
    }

    @Override
    public void onResult(String actionType, int actionId, String result)
    {
        ContentResolver cr = this.mContext.getContentResolver();
        cr.delete(HydrantProviderMetaData.UnitTableMetaData.CONTENT_URI, null, null);

        InputStream is = new ByteArrayInputStream(result.getBytes());
        UnitXmlDataSource source = new UnitXmlDataSource();
        List<UnitEntity> units = source.GetUnitData(is);

        for (UnitEntity unit : units)
        {
            addUnit(unit.getDescription(), unit.getTotalArea(), unit.getBuildingHeight(), unit.getFloorAboveGround(), unit.getFloorBelowGround(), unit.getBuildingStructure(), unit.getUnitName(), unit.getAddress(), unit.getWorkNumber(), unit.getMobileNumber(), unit.getFireCharacters(), unit.getPersonInCharge(), unit.getThumbPath(), unit.getPlanDiagramPath(), unit.getFirehouse(), unit.getLongitude(), unit.getLatitude());
        }
    }

    private void addUnit(String description, double totalArea, String buildingHeight, int floorAboveGround, int floorBelowGround,
                         String buildingStructure, String unitName, String address, String workNumber, String mobileNumber, String
                                 fireCharacters, String personInCharge, String thumbPath, String planDiagramPath, String firehouse, double longitude, double latitude)
    {
        Log.d(TAG, "Adding a Unit");
        ContentValues cv = new ContentValues();
        cv.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_DESCRIPTION, description);
        cv.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_AREA, totalArea);
        cv.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_HEIGHT, buildingHeight);
        cv.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_NAME, unitName);
        cv.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_ADDRESS, address);
        cv.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_MOBILE, mobileNumber);
        cv.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_CHARACTER, fireCharacters);
        cv.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_PERSON, personInCharge);
        cv.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_THUMB_PATH, thumbPath);
        cv.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_PLAN_DIAGRAM_PATH, planDiagramPath);
        cv.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_FIREHOUSE, firehouse);
        cv.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_LONGITUDE, longitude);
        cv.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_LATITUDE, latitude);


        ContentResolver cr = this.mContext.getContentResolver();
        Uri uri = HydrantProviderMetaData.UnitTableMetaData.CONTENT_URI;
        Log.d(TAG, "unit insert uri:" + uri);
        Uri insertedUri = cr.insert(uri, cv);
        Log.d(TAG, "inserted uri:" + insertedUri);
    }

    public void updateUnit(String hydrantID, String unit, String district, String status, double pressure, double longitude, double latitude, String locDesc, int id)
    {
        Log.d(TAG, "Updating a Unit");

        //Log.d(TAG, "affected rows:" + Integer.toString(i));
    }

    public void removeUnit(int id)
    {
        Uri uri = HydrantProviderMetaData.UnitTableMetaData.CONTENT_URI;
        ContentResolver cr = mContext.getContentResolver();
        Uri delUri = Uri.withAppendedPath(uri, Integer.toString(id));
        cr.delete(delUri, null, null);
    }


    public List<UnitEntity> showUnits(Activity activity)
    {
        ArrayList<UnitEntity> units = new ArrayList<>();
        Uri uri = HydrantProviderMetaData.UnitTableMetaData.CONTENT_URI;
        Cursor c = activity.managedQuery(uri, null, null, null, null);
        int unitDescriptionColumn = c.getColumnIndex(HydrantProviderMetaData.UnitTableMetaData.UNIT_DESCRIPTION);
        int unitAreaColumn = c.getColumnIndex(HydrantProviderMetaData.UnitTableMetaData.UNIT_AREA);
        int unitHeightColumn = c.getColumnIndex(HydrantProviderMetaData.UnitTableMetaData.UNIT_HEIGHT);
        int unitNameColumn = c.getColumnIndex(HydrantProviderMetaData.UnitTableMetaData.UNIT_NAME);
        int unitAddressColumn = c.getColumnIndex(HydrantProviderMetaData.UnitTableMetaData.UNIT_ADDRESS);
        int unitMobileColumn = c.getColumnIndex(HydrantProviderMetaData.UnitTableMetaData.UNIT_MOBILE);
        int unitFireCharacterColumn = c.getColumnIndex(HydrantProviderMetaData.UnitTableMetaData.UNIT_CHARACTER);
        int unitPersonColumn = c.getColumnIndex(HydrantProviderMetaData.UnitTableMetaData.UNIT_PERSON);
        int unitThumbPathColumn = c.getColumnIndex(HydrantProviderMetaData.UnitTableMetaData.UNIT_THUMB_PATH);
        int unitPlanDiagramPathColumn = c.getColumnIndex(HydrantProviderMetaData.UnitTableMetaData.UNIT_PLAN_DIAGRAM_PATH);
        int unitFirehouseColumn = c.getColumnIndex(HydrantProviderMetaData.UnitTableMetaData.UNIT_FIREHOUSE);
        int unitLongitudeColumn = c.getColumnIndex(HydrantProviderMetaData.UnitTableMetaData.UNIT_LONGITUDE);
        int unitLatitudeColumn = c.getColumnIndex(HydrantProviderMetaData.UnitTableMetaData.UNIT_LATITUDE);

        String unitName = null;
        String address = null;
        String fireCharacters = null;
        String personInCharge = null;
        String mobileNumber = null;
        String thumbPicturePath = null;
        String planDiagramPath = null;
        String description = null;
        double totalArea = 0;
        String buildingHeight = null;
        double longitude = 0;
        double latitude = 0;
        String firehouse = null;


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
        {
            unitName = c.getString(unitNameColumn);
            address = c.getString(unitAddressColumn);
            fireCharacters = c.getString(unitFireCharacterColumn);
            personInCharge = c.getString(unitPersonColumn);
            mobileNumber = c.getString(unitMobileColumn);
            thumbPicturePath = c.getString(unitThumbPathColumn);
            planDiagramPath = c.getString(unitPlanDiagramPathColumn);
            description = c.getString(unitDescriptionColumn);
            totalArea = c.getDouble(unitAreaColumn);
            buildingHeight = c.getString(unitHeightColumn);
            longitude = c.getDouble(unitLongitudeColumn);
            latitude = c.getDouble(unitLatitudeColumn);
            firehouse = c.getString(unitFirehouseColumn);

            UnitEntity unit = new UnitEntity(true, description, totalArea, buildingHeight, 0, 0, "", unitName, address, "", mobileNumber, fireCharacters, personInCharge, thumbPicturePath, planDiagramPath, firehouse, longitude, latitude);
            units.add(unit);
        }

        //c.close();
        return units;
    }

    public int getCount(Activity activity)
    {
        Uri uri = HydrantProviderMetaData.UnitTableMetaData.CONTENT_URI;
        Cursor c = activity.managedQuery(uri, null, null, null, null);
        int numberOfRecords = c.getCount();
        c.close();
        return numberOfRecords;
    }
}
