package com.isaac.hydrantmap.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import android.database.SQLException;

import java.util.HashMap;

/**
 * Created by yueguo01 on 3/13/2016.
 */
public class HydrantProvider extends ContentProvider
{

    private static final String TAG = "HydrantProvider";

    private static HashMap<String, String> sHydrantsProjectionMap;
    private static HashMap<String, String> sUnitsProjectionMap;

    static
    {
        sHydrantsProjectionMap = new HashMap<String, String>();
        sHydrantsProjectionMap.put(HydrantProviderMetaData.HydrantTableMetaData._ID, HydrantProviderMetaData.HydrantTableMetaData._ID);
        sHydrantsProjectionMap.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_ID, HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_ID);
        sHydrantsProjectionMap.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_UNIT, HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_UNIT);
        sHydrantsProjectionMap.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_DISTRICT, HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_DISTRICT);
        sHydrantsProjectionMap.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_STATUS, HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_STATUS);
        sHydrantsProjectionMap.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_PRESSURE, HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_PRESSURE);
        sHydrantsProjectionMap.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_LONGITUDE, HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_LONGITUDE);
        sHydrantsProjectionMap.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_LATITUDE, HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_LATITUDE);
        sHydrantsProjectionMap.put(HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_LOCDESC, HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_LOCDESC);

        sUnitsProjectionMap=new HashMap<String,String>();
        sUnitsProjectionMap.put(HydrantProviderMetaData.UnitTableMetaData._ID, HydrantProviderMetaData.UnitTableMetaData._ID);
        sUnitsProjectionMap.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_DESCRIPTION, HydrantProviderMetaData.UnitTableMetaData.UNIT_DESCRIPTION);
        sUnitsProjectionMap.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_NAME, HydrantProviderMetaData.UnitTableMetaData.UNIT_NAME);
        sUnitsProjectionMap.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_ADDRESS, HydrantProviderMetaData.UnitTableMetaData.UNIT_ADDRESS);
        sUnitsProjectionMap.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_CHARACTER, HydrantProviderMetaData.UnitTableMetaData.UNIT_CHARACTER);
        sUnitsProjectionMap.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_PERSON, HydrantProviderMetaData.UnitTableMetaData.UNIT_PERSON);
        sUnitsProjectionMap.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_MOBILE, HydrantProviderMetaData.UnitTableMetaData.UNIT_MOBILE);
        sUnitsProjectionMap.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_AREA, HydrantProviderMetaData.UnitTableMetaData.UNIT_AREA);
        sUnitsProjectionMap.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_HEIGHT, HydrantProviderMetaData.UnitTableMetaData.UNIT_HEIGHT);
        sUnitsProjectionMap.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_LONGITUDE, HydrantProviderMetaData.UnitTableMetaData.UNIT_LONGITUDE);
        sUnitsProjectionMap.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_LATITUDE, HydrantProviderMetaData.UnitTableMetaData.UNIT_LATITUDE);
        sUnitsProjectionMap.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_THUMB_PATH, HydrantProviderMetaData.UnitTableMetaData.UNIT_THUMB_PATH);
        sUnitsProjectionMap.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_PLAN_DIAGRAM_PATH, HydrantProviderMetaData.UnitTableMetaData.UNIT_PLAN_DIAGRAM_PATH);
        sUnitsProjectionMap.put(HydrantProviderMetaData.UnitTableMetaData.UNIT_FIREHOUSE, HydrantProviderMetaData.UnitTableMetaData.UNIT_FIREHOUSE);
    }

    private static final UriMatcher sUriMatcher;
    private static final int INCOMING_HYDRANT_COLLECTION_URI_INDICATOR = 1;
    private static final int INCOMING_SINGLE_HYDRANT_URI_INDICATOR = 2;
    private static final int INCOMING_UNIT_COLLECTION_URI_INDICATOR = 3;
    private static final int INCOMING_SINGLE_UNIT_URI_INDICATOR = 4;


    static
    {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(HydrantProviderMetaData.AUTHORITY, "hydrants", INCOMING_HYDRANT_COLLECTION_URI_INDICATOR);
        sUriMatcher.addURI(HydrantProviderMetaData.AUTHORITY, "hydrants/#", INCOMING_SINGLE_HYDRANT_URI_INDICATOR);
        sUriMatcher.addURI(HydrantProviderMetaData.AUTHORITY, "units", INCOMING_UNIT_COLLECTION_URI_INDICATOR);
        sUriMatcher.addURI(HydrantProviderMetaData.AUTHORITY, "units/#", INCOMING_SINGLE_UNIT_URI_INDICATOR);
    }


    private static class DatabaseHelper extends SQLiteOpenHelper
    {

        DatabaseHelper(Context context)
        {
            super(context,
                    HydrantProviderMetaData.DATABASE_NAME,
                    null,
                    HydrantProviderMetaData.DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db)
        {
            Log.d(TAG, "inner oncreate called");
            db.execSQL("CREATE TABLE " + HydrantProviderMetaData.HydrantTableMetaData.TABLE_NAME + " ("
                    + HydrantProviderMetaData.HydrantTableMetaData._ID + " INTEGER PRIMARY KEY,"
                    + HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_ID + " TEXT,"
                    + HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_UNIT + " TEXT,"
                    + HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_DISTRICT + " TEXT,"
                    + HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_STATUS + " TEXT,"
                    + HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_PRESSURE + " REAL,"
                    + HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_LONGITUDE + " REAL,"
                    + HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_LATITUDE + " REAL,"
                    + HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_LOCDESC + " TEXT"
                    + ");");


            db.execSQL("CREATE TABLE " + HydrantProviderMetaData.UnitTableMetaData.TABLE_NAME + " ("
                    + HydrantProviderMetaData.HydrantTableMetaData._ID + " INTEGER PRIMARY KEY,"
                    + HydrantProviderMetaData.UnitTableMetaData.UNIT_DESCRIPTION + " TEXT,"
                    + HydrantProviderMetaData.UnitTableMetaData.UNIT_NAME + " TEXT,"
                    + HydrantProviderMetaData.UnitTableMetaData.UNIT_ADDRESS + " TEXT,"
                    + HydrantProviderMetaData.UnitTableMetaData.UNIT_CHARACTER + " TEXT,"
                    + HydrantProviderMetaData.UnitTableMetaData.UNIT_PERSON + " TEXT,"
                    + HydrantProviderMetaData.UnitTableMetaData.UNIT_MOBILE + " TEXT,"
                    + HydrantProviderMetaData.UnitTableMetaData.UNIT_AREA + " REAL,"
                    + HydrantProviderMetaData.UnitTableMetaData.UNIT_HEIGHT + " TEXT,"
                    + HydrantProviderMetaData.UnitTableMetaData.UNIT_LONGITUDE + " REAL,"
                    + HydrantProviderMetaData.UnitTableMetaData.UNIT_LATITUDE + " REAL,"
                    + HydrantProviderMetaData.UnitTableMetaData.UNIT_THUMB_PATH + " TEXT,"
                    + HydrantProviderMetaData.UnitTableMetaData.UNIT_PLAN_DIAGRAM_PATH + " TEXT,"
                    + HydrantProviderMetaData.UnitTableMetaData.UNIT_FIREHOUSE + " TEXT"
                    + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + HydrantProviderMetaData.HydrantTableMetaData.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + HydrantProviderMetaData.UnitTableMetaData.TABLE_NAME);
            onCreate(db);
        }
    }

    private DatabaseHelper mOpenHelper;

    @Override
    public boolean onCreate()
    {
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String orderBy;
        switch (sUriMatcher.match(uri))
        {
            case INCOMING_HYDRANT_COLLECTION_URI_INDICATOR:
                qb.setTables(HydrantProviderMetaData.HydrantTableMetaData.TABLE_NAME);
                qb.setProjectionMap(sHydrantsProjectionMap);
                if (TextUtils.isEmpty(sortOrder))
                {
                    orderBy = HydrantProviderMetaData.HydrantTableMetaData.DEFAULT_SORT_ORDER;
                } else
                    orderBy = sortOrder;
                break;
            case INCOMING_SINGLE_HYDRANT_URI_INDICATOR:
                qb.setTables(HydrantProviderMetaData.HydrantTableMetaData.TABLE_NAME);
                qb.setProjectionMap(sHydrantsProjectionMap);
                qb.appendWhere(HydrantProviderMetaData.HydrantTableMetaData._ID + "=" + uri.getPathSegments().get(1));
                if (TextUtils.isEmpty(sortOrder))
                {
                    orderBy = HydrantProviderMetaData.HydrantTableMetaData.DEFAULT_SORT_ORDER;
                } else
                    orderBy = sortOrder;
                break;
            case INCOMING_UNIT_COLLECTION_URI_INDICATOR:
                qb.setTables(HydrantProviderMetaData.UnitTableMetaData.TABLE_NAME);
                qb.setProjectionMap(sUnitsProjectionMap);
                if (TextUtils.isEmpty(sortOrder))
                {
                    orderBy = HydrantProviderMetaData.UnitTableMetaData._ID;
                } else
                    orderBy = sortOrder;
                break;
            case INCOMING_SINGLE_UNIT_URI_INDICATOR:
                qb.setTables(HydrantProviderMetaData.UnitTableMetaData.TABLE_NAME);
                qb.setProjectionMap(sUnitsProjectionMap);
                qb.appendWhere(HydrantProviderMetaData.UnitTableMetaData._ID + "=" + uri.getPathSegments().get(1));
                if (TextUtils.isEmpty(sortOrder))
                {
                    orderBy = HydrantProviderMetaData.UnitTableMetaData._ID;
                } else
                    orderBy = sortOrder;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
        int i = c.getCount();
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri)
    {
        switch (sUriMatcher.match(uri))
        {
            case INCOMING_HYDRANT_COLLECTION_URI_INDICATOR:
                return HydrantProviderMetaData.HydrantTableMetaData.CONTENT_TYPE;
            case INCOMING_SINGLE_HYDRANT_URI_INDICATOR:
                return HydrantProviderMetaData.HydrantTableMetaData.CONTENT_ITEM_TYPE;
            case INCOMING_UNIT_COLLECTION_URI_INDICATOR:
                return HydrantProviderMetaData.UnitTableMetaData.CONTENT_TYPE;
            case INCOMING_SINGLE_UNIT_URI_INDICATOR:
                return HydrantProviderMetaData.UnitTableMetaData.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues)
    {
        ContentValues values;
        if (initialValues != null)
            values = new ContentValues(initialValues);
        else
            values = new ContentValues();
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        long rowId = 0;
        switch (sUriMatcher.match(uri))
        {
            case INCOMING_HYDRANT_COLLECTION_URI_INDICATOR:


                rowId = db.insert(HydrantProviderMetaData.HydrantTableMetaData.TABLE_NAME, HydrantProviderMetaData.HydrantTableMetaData.HYDRANT_ID, values);
                if (rowId > 0)
                {
                    Uri insertedHydrantUri = ContentUris.withAppendedId(HydrantProviderMetaData.HydrantTableMetaData.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(insertedHydrantUri, null);
                    return insertedHydrantUri;
                }
                throw new SQLException("Failed to insert row into " + uri);
            case INCOMING_UNIT_COLLECTION_URI_INDICATOR:

                rowId = db.insert(HydrantProviderMetaData.UnitTableMetaData.TABLE_NAME, null, values);
                if (rowId > 0)
                {
                    Uri insertedUnitUri = ContentUris.withAppendedId(HydrantProviderMetaData.UnitTableMetaData.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(insertedUnitUri, null);
                    return insertedUnitUri;
                }
                throw new SQLException("Failed to insert row into " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs)
    {


        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri))
        {
            case INCOMING_HYDRANT_COLLECTION_URI_INDICATOR:
                count = db.delete(HydrantProviderMetaData.HydrantTableMetaData.TABLE_NAME, where, whereArgs);
                break;
            case INCOMING_SINGLE_HYDRANT_URI_INDICATOR:
                String rowId = uri.getPathSegments().get(1);
                count = db.delete(HydrantProviderMetaData.HydrantTableMetaData.TABLE_NAME, HydrantProviderMetaData.HydrantTableMetaData._ID + "=" + rowId + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
                break;
            case INCOMING_UNIT_COLLECTION_URI_INDICATOR:
                count = db.delete(HydrantProviderMetaData.UnitTableMetaData.TABLE_NAME, where, whereArgs);
                break;
            case INCOMING_SINGLE_UNIT_URI_INDICATOR:
                String rowId2 = uri.getPathSegments().get(1);
                count = db.delete(HydrantProviderMetaData.UnitTableMetaData.TABLE_NAME, HydrantProviderMetaData.UnitTableMetaData._ID + "=" + rowId2 + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri))
        {
            case INCOMING_HYDRANT_COLLECTION_URI_INDICATOR:
                count = db.update(HydrantProviderMetaData.HydrantTableMetaData.TABLE_NAME, values, selection, selectionArgs);
                break;
            case INCOMING_SINGLE_HYDRANT_URI_INDICATOR:
                String rowId = uri.getPathSegments().get(1);
                count = db.update(HydrantProviderMetaData.HydrantTableMetaData.TABLE_NAME,
                        values, HydrantProviderMetaData.HydrantTableMetaData._ID + "=" + rowId + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case INCOMING_UNIT_COLLECTION_URI_INDICATOR:
                count = db.update(HydrantProviderMetaData.UnitTableMetaData.TABLE_NAME, values, selection, selectionArgs);
                break;
            case INCOMING_SINGLE_UNIT_URI_INDICATOR:
                String rowId2 = uri.getPathSegments().get(1);
                count = db.update(HydrantProviderMetaData.UnitTableMetaData.TABLE_NAME,
                        values, HydrantProviderMetaData.UnitTableMetaData._ID + "=" + rowId2 + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
