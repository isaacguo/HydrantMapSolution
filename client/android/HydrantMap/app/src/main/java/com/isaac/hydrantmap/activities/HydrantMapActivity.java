package com.isaac.hydrantmap.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.isaac.hydrantmap.R;
import com.isaac.hydrantmap.application.HydrantMapApplication;
import com.isaac.hydrantmap.core.interfaces.IHydrantDataSource;
import com.isaac.hydrantmap.core.interfaces.IUnitDataSource;
import com.isaac.hydrantmap.model.Hydrant;
import com.isaac.hydrantmap.model.UnitEntity;
import com.isaac.hydrantmap.utils.JZLocationConverter;

import java.util.List;


/**
 * Created by yueguo01 on 11/21/2015.
 */
public class HydrantMapActivity extends AppCompatActivity
{
    private static final String TAG = "HydrantMapActivity";
    Context context = this;
    TextView tvGotoList;
    TextView tvLocateMyself;
    HydrantMapActivity instance = this;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Marker marker;
    BitmapDescriptor mCurrentMarker;

    MyLocationData locData;


    LocationClient mLocClient;
    public MyLocationListener myListener = new MyLocationListener();


    TextView tvHydrant_map_item_name;
    TextView tvHydrant_map_item_location_desc;
    TextView tvHydrant_map_item_state;
    TextView tvHydrant_map_item_unit;
    RelativeLayout rlHydrant_map_item_layout;

    Hydrant[] hydrants;
    UnitEntity[] units;
    Marker[] hydrantMarkers;
    Marker[] unitMarkers;


    BitmapDescriptor iconRedMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_red_marker);
    //BitmapDescriptor iconYellowMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_yellow_marker);
    BitmapDescriptor iconBlueMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_blue_marker);
    BitmapDescriptor iconOrangeHouseMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_orange_house);

    Marker previousMarker = null;
    boolean isPreviousMarkerHydrant=false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hydrant_map_layout);

        tvHydrant_map_item_name = (TextView) findViewById(R.id.hydrant_map_item_name);
        tvHydrant_map_item_location_desc = (TextView) findViewById(R.id.hydrant_map_item_location_desc);
        tvHydrant_map_item_state = (TextView) findViewById(R.id.hydrant_map_item_state);
        tvHydrant_map_item_unit = (TextView) findViewById(R.id.hydrant_map_item_unit);
        rlHydrant_map_item_layout = (RelativeLayout) findViewById(R.id.hydrant_map_item_layout);
        rlHydrant_map_item_layout.setVisibility(View.INVISIBLE);

        tvGotoList = (TextView) findViewById(R.id.back_TextView);
        tvGotoList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });


        mMapView = (MapView) findViewById(R.id.bdMapView);
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
        mBaiduMap.setMapStatus(msu);


        Intent intent = getIntent();

        int i = intent.getIntExtra("selectedPosition", -1);

        initOverlay();
        setCenter(i);


        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(Marker marker)
            {
                int index = -1;
                for (int i = 0; i < hydrantMarkers.length; i++)
                {
                    if (hydrantMarkers[i] == marker)
                    {
                        index = i;
                        break;
                    }
                }

                if (index != -1)
                {
                    LatLng ll = marker.getPosition();
                    MapStatus st = new MapStatus.Builder().target(ll).zoom(18).build();
                    MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(st);
                    mBaiduMap.animateMapStatus(u);

                    if (previousMarker != null && isPreviousMarkerHydrant)
                    {
                        previousMarker.setIcon(iconRedMarker);
                    }

                    previousMarker = marker;
                    isPreviousMarkerHydrant=true;
                    marker.setIcon(iconBlueMarker);
                    FillPanel(hydrants[index]);
                    return true;
                }
                else
                {
                    for (int i = 0; i <unitMarkers.length; i++)
                    {
                        if (unitMarkers[i] == marker)
                        {
                            index = i;
                            break;
                        }
                    }
                    if(index!=-1)
                    {
                        LatLng ll = marker.getPosition();
                        MapStatus st = new MapStatus.Builder().target(ll).zoom(18).build();
                        MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(st);
                        mBaiduMap.animateMapStatus(u);

                        Intent intent=new Intent(instance, UnitIntroActivity.class);
                        intent.putExtra("selectedPosition", index);
                        startActivity(intent);

                        return true;
                    }
                }
                return true;
            }
        });


        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();

        mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.mylocation);
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker));


        tvLocateMyself = (TextView) findViewById(R.id.tvLocateMyself);
        tvLocateMyself.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (locData != null)
                {
                    mBaiduMap.setMyLocationData(locData);

                    LatLng ll = new LatLng(locData.latitude,
                            locData.longitude);
                    MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                    mBaiduMap.animateMapStatus(u);

                } else
                    Toast.makeText(getApplicationContext(), "定位中...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void FillPanel(Hydrant hydrant)
    {
        rlHydrant_map_item_layout.setVisibility(View.VISIBLE);
        tvHydrant_map_item_name.setText(hydrant.getName());
        tvHydrant_map_item_location_desc.setText(hydrant.getLocationDesc());
        tvHydrant_map_item_unit.setText(hydrant.getUnit());
        tvHydrant_map_item_state.setText(hydrant.getWorkingState());

        rlHydrant_map_item_layout.setVisibility(View.VISIBLE);


    }

    public void initOverlay()
    {
        IHydrantDataSource dataSource = HydrantMapApplication.serviceProvider.getService(IHydrantDataSource.class);
        List<Hydrant> hydrantList = dataSource.getHydrantData();
        hydrants = (Hydrant[]) hydrantList.toArray(new Hydrant[hydrantList.size()]);
        hydrantMarkers = new Marker[hydrants.length];

        for (int i = 0; i < hydrants.length; i++)
        {
            LatLng llA = new LatLng(hydrants[i].getLatitude(), hydrants[i].getLongitude());
            MarkerOptions mo = new MarkerOptions().position(llA).icon(iconRedMarker).zIndex(9).draggable(false);
            marker = (Marker) (mBaiduMap.addOverlay(mo));
            hydrantMarkers[i] = marker;
        }


        IUnitDataSource unitDataSource = HydrantMapApplication.serviceProvider.getService(IUnitDataSource.class);
        List<UnitEntity> unitLists = unitDataSource.getUnitData();
        units = (UnitEntity[]) unitLists.toArray(new UnitEntity[unitLists.size()]);
        unitMarkers = new Marker[units.length];
        for (int i = 0; i < units.length; i++)
        {
            LatLng llA = new LatLng(units[i].getLatitude(), units[i].getLongitude());
            MarkerOptions mo = new MarkerOptions().position(llA).icon(iconOrangeHouseMarker).zIndex(9).draggable(false);
            Log.i(TAG, "Adding unit marker, Latitude:" + llA.latitude + " Longitude: " + llA.longitude);
            marker = (Marker) (mBaiduMap.addOverlay(mo));
            unitMarkers[i] = marker;
        }


    }

    private void setCenter(int i)
    {
        IHydrantDataSource dataSource = HydrantMapApplication.serviceProvider.getService(IHydrantDataSource.class);
        List<Hydrant> hydrantList = dataSource.getHydrantData();
        hydrants = (Hydrant[]) hydrantList.toArray(new Hydrant[hydrantList.size()]);
        LatLng llA;
        if (i == -1)
            llA = new LatLng(40.82261569, 114.8768319);
        else
            llA = new LatLng(hydrants[i].getLatitude(), hydrants[i].getLongitude());

        MapStatus st = new MapStatus.Builder().target(llA).zoom(18).build();
        MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(st);
        mBaiduMap.animateMapStatus(u);
        if (i != -1)
        {
            FillPanel(hydrants[i]);
            hydrantMarkers[i].setIcon(iconBlueMarker);
            previousMarker = hydrantMarkers[i];
        }
    }

    @Override
    protected void onPause()
    {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy()
    {
        mMapView.onDestroy();
        super.onDestroy();
        iconRedMarker.recycle();
        mLocClient.stop();

    }

    public class MyLocationListener implements BDLocationListener
    {
        @Override
        public void onReceiveLocation(BDLocation location)
        {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
        }

        public void onReceivePoi(BDLocation poiLocation)
        {
        }
    }

}
