package com.isaac.hydrantmap.contentprovider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by yueguo01 on 3/13/2016.
 */
public class HydrantProviderMetaData
{
    public static final String AUTHORITY = "com.isaac.hydrant.contentprovider.HydrantProvider";
    public static final String DATABASE_NAME = "hydrants.db";
    public static final int DATABASE_VERSION = 1;
    public static final String HYDRANTS_TABLE_NAME = "hydrants";
    public static final String UNITS_TABLE_NAME="units";

    private HydrantProviderMetaData(){}

    public static final class HydrantTableMetaData implements BaseColumns
    {
        private HydrantTableMetaData(){}

        public static final  String TABLE_NAME="hydrants";

        public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/hydrants");
        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd.isaac.hydrant";
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd.isaac.hydrant";

        public static final String DEFAULT_SORT_ORDER="hydrantid DESC";

        public static final String HYDRANT_ID="hydrantid";
        public static final String HYDRANT_UNIT="unit";
        public static final String HYDRANT_DISTRICT="district";
        public static final String HYDRANT_STATUS="status";
        public static final String HYDRANT_PRESSURE="pressure";
        public static final String HYDRANT_LONGITUDE="longitude";
        public static final String HYDRANT_LATITUDE="latitude";
        public static final String HYDRANT_LOCDESC="locdesc";
        public static final String HYDRANT_DATE="date";
    }

    public static final class UnitTableMetaData implements BaseColumns
    {
        private UnitTableMetaData(){}

        public static final  String TABLE_NAME="units";

        public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/units");
        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd.isaac.unit";
        public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd.isaac.unit";

        public static final String DEFAULT_SORT_ORDER="unitid DESC";

        public static final String UNIT_ID="unitid";
        public static final String UNIT_NAME="name";
        public static final String UNIT_ADDRESS="address";
        public static final String UNIT_CHARACTER="character";
        public static final String UNIT_PERSON="person";
        public static final String UNIT_MOBILE="mobile";
        public static final String UNIT_DESCRIPTION="description";
        public static final String UNIT_AREA="area";
        public static final String UNIT_HEIGHT="height";
        public static final String UNIT_LONGITUDE="longitude";
        public static final String UNIT_LATITUDE="latitude";
        public static final String UNIT_THUMB_PATH="thumb_path";
        public static final String UNIT_PLAN_DIAGRAM_PATH="plan_path";
        public static final String UNIT_FIREHOUSE="firehouse";
    }


}
