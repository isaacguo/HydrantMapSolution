package com.isaac.hydrantmap.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.isaac.hydrantmap.R;
import com.isaac.hydrantmap.application.HydrantMapApplication;
import com.isaac.hydrantmap.core.interfaces.IHydrantDataSource;
import com.isaac.hydrantmap.manager.SqlliteDatabaseManager;
import com.isaac.hydrantmap.model.Hydrant;

import java.util.List;

public class HydrantDetailActivity extends AppCompatActivity
{
    TextView tvCancel;
    TextView tvDone;
    TextView tvLocateMyself;

    TextView tvHydrantState;
    EditText etHydrantName;
    TextView tvHydrantPressure;
    EditText etHydrantLocDesc;
    EditText etHydrantUnit;
    EditText etHydrantDistrict;

    double waterPressure;
    TextView tvSetHydrantLocationAsCurrentLocation;
    TextView tvSetHydrantLocationAsCenterOfMap;

    int index;


    MyLocationData locData;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    LocationClient mLocClient;
    MyLocationListener myListener = new MyLocationListener();
    BitmapDescriptor iconRedMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_red_marker);
    Marker marker;
    Hydrant selectedHydrant;


    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case DialogInterface.BUTTON_POSITIVE:
                    tvHydrantState.setText("可用");
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    tvHydrantState.setText("不可用");
                    break;
            }
        }
    };
    DialogInterface.OnClickListener setMapCenterDialogClickListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case DialogInterface.BUTTON_POSITIVE:
                    LatLng latLng = mBaiduMap.getMapStatus().bound.getCenter();
                    marker.setPosition(latLng);
                    selectedHydrant.setLongitude(latLng.longitude);
                    selectedHydrant.setLatitude(latLng.latitude);
                    Toast.makeText(getApplicationContext(), "消火栓位置已设置为当前地图中心位置", Toast.LENGTH_SHORT).show();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };
    DialogInterface.OnClickListener setCurrentLocationDialogClickListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case DialogInterface.BUTTON_POSITIVE:

                    if (locData != null)
                    {
                        LatLng ll = new LatLng(locData.latitude, locData.longitude);
                        selectedHydrant.setLongitude(ll.longitude);
                        selectedHydrant.setLatitude(ll.latitude);
                        marker.setPosition(ll);
                        Toast.makeText(getApplicationContext(), "消火栓位置已设置为当前所在位置", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), "定位中...", Toast.LENGTH_SHORT).show();

                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hydrant_detail);
        tvCancel = (TextView) findViewById(R.id.hydrant_detail_navigation_left_TextView);
        tvCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent returnIntent=new Intent();
                setResult(Activity.RESULT_CANCELED,returnIntent);
                finish();
            }
        });
        tvDone = (TextView) findViewById(R.id.hydrant_detail_navigation_right2_TextView);
        tvDone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                selectedHydrant.setName(etHydrantName.getText().toString());
                selectedHydrant.setWorkingState(tvHydrantState.getText().toString());
                selectedHydrant.setWaterPressure(Double.parseDouble(tvHydrantPressure.getText().toString()));
                selectedHydrant.setUnit(etHydrantUnit.getText().toString());
                selectedHydrant.setDistrict(etHydrantDistrict.getText().toString());
                selectedHydrant.setLocationDesc(etHydrantLocDesc.getText().toString());

                SqlliteDatabaseManager manager = HydrantMapApplication.serviceProvider.getService(SqlliteDatabaseManager.class);
                manager.updateHydrant(selectedHydrant.getName(), selectedHydrant.getUnit(), selectedHydrant.getDistrict(), selectedHydrant.getWorkingState(), selectedHydrant.getWaterPressure(), selectedHydrant.getLongitude(), selectedHydrant.getLatitude(), selectedHydrant.getLocationDesc(), index);

                Intent returnIntent=new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        etHydrantName = (EditText) findViewById(R.id.hydrant_item_detail_name);
        tvHydrantPressure = (TextView) findViewById(R.id.hydrant_item_detail_water_pressure);
        tvHydrantPressure.setOnClickListener(new View.OnClickListener()
                                             {
                                                 @Override
                                                 public void onClick(View v)
                                                 {
                                                     final AlertDialog.Builder dialog = new AlertDialog.Builder(tvHydrantPressure.getContext());

                                                     LayoutInflater inflater = (LayoutInflater) tvHydrantPressure.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                                                     View layout = inflater.inflate(R.layout.dialog_set_water_pressure, null);

                                                     Button btnMinus = (Button) layout.findViewById(R.id.btnMinus);
                                                     Button btnAdd = (Button) layout.findViewById(R.id.btnAdd);
                                                     Button btnOK = (Button) layout.findViewById(R.id.btnMyOK);
                                                     Button btnCancel = (Button) layout.findViewById(R.id.btnMyCancel);
                                                     dialog.setView(layout);


                                                     final AlertDialog alertDialog = dialog.create();
                                                     //Window window = alertDialog.getWindow();
                                                     //window.setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                                     //window.setGravity(Gravity.CENTER);

                                                     final TextView tvValue = (TextView) layout.findViewById(R.id.water_pressure_value);
                                                     tvValue.setText(Double.toString(waterPressure));


                                                     btnOK.setOnClickListener(new View.OnClickListener()
                                                     {
                                                         @Override
                                                         public void onClick(View v)
                                                         {
                                                             tvHydrantPressure.setText(Double.toString(waterPressure));
                                                             alertDialog.dismiss();
                                                         }
                                                     });
                                                     btnCancel.setOnClickListener(new View.OnClickListener()
                                                     {
                                                         @Override
                                                         public void onClick(View v)
                                                         {
                                                             waterPressure = Double.parseDouble(tvHydrantPressure.getText().toString());
                                                             alertDialog.dismiss();
                                                         }
                                                     });

                                                     btnMinus.setOnClickListener(new View.OnClickListener()
                                                     {
                                                         @Override
                                                         public void onClick(View v)
                                                         {
                                                             if (waterPressure >= 0.1)
                                                                 waterPressure -= 0.1;
                                                             if (waterPressure < 0)
                                                                 waterPressure = 0;
                                                             waterPressure = Math.round(waterPressure * 100.0) / 100.0;
                                                             tvValue.setText(Double.toString(waterPressure));
                                                         }
                                                     });
                                                     btnAdd.setOnClickListener(new View.OnClickListener()
                                                     {
                                                         @Override
                                                         public void onClick(View v)
                                                         {
                                                             waterPressure = Math.round((waterPressure + 0.1) * 100.0) / 100.0;
                                                             tvValue.setText(Double.toString(waterPressure));
                                                         }
                                                     });
                                                     alertDialog.setCanceledOnTouchOutside(false);
                                                     alertDialog.show();
                                                 }
                                             }

        );


        etHydrantUnit = (EditText)

                findViewById(R.id.hydrant_item_detail_unit);

        etHydrantDistrict = (EditText)

                findViewById(R.id.hydrant_item_detail_district);

        etHydrantLocDesc = (EditText)

                findViewById(R.id.hydrant_item_detail_location_desc);

        tvHydrantState = (TextView)

                findViewById(R.id.hydrant_item_detail_tvState);

        tvHydrantState.setOnClickListener(new View.OnClickListener()

                                          {
                                              @Override
                                              public void onClick(View v)
                                              {
                                                  AlertDialog.Builder builder = new AlertDialog.Builder(tvHydrantState.getContext());
                                                  builder.setMessage("请选择消火栓当前状态")
                                                          .setPositiveButton("可用", dialogClickListener)
                                                          .setNegativeButton("不可用", dialogClickListener).show();
                                              }
                                          }

        );


        tvSetHydrantLocationAsCurrentLocation = (TextView) findViewById(R.id.tvSetCurrentLocation);
        tvSetHydrantLocationAsCurrentLocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(tvSetHydrantLocationAsCenterOfMap.getContext());
                builder.setMessage("确定将当前所在位置设置为消防栓的位置？")
                        .setPositiveButton("是的", setCurrentLocationDialogClickListener)
                        .setNegativeButton("算了", setCurrentLocationDialogClickListener).show();
            }
        });

        tvSetHydrantLocationAsCenterOfMap = (TextView) findViewById(R.id.tvSetMapCenter);
        tvSetHydrantLocationAsCenterOfMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(tvSetHydrantLocationAsCenterOfMap.getContext());
                builder.setMessage("确定将当前地图中心的位置设置为消防栓的位置？")
                        .setPositiveButton("是的", setMapCenterDialogClickListener)
                        .setNegativeButton("算了", setMapCenterDialogClickListener).show();
            }
        });


        mMapView = (MapView)

                findViewById(R.id.hydrant_detail_bdMapView);

        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
        mBaiduMap.setMapStatus(msu);


        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new

                LocationClient(this);

        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener()

                                           {
                                               @Override
                                               public boolean onMarkerClick(Marker marker)
                                               {
                                                   LatLng ll = marker.getPosition();
                                                   MapStatus st = new MapStatus.Builder().target(ll).zoom(18).build();
                                                   MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(st);
                                                   mBaiduMap.animateMapStatus(u);
                                                   return true;
                                               }
                                           }

        );


        tvLocateMyself = (TextView)

                findViewById(R.id.hydrant_detail_tvLocateMyself);

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
                                          }

        );

        Intent intent = getIntent();
        index = intent.getIntExtra("selectedPosition", -1);

        setCenter(index);
    }

    private void setCenter(int i)
    {
        IHydrantDataSource dataSource = HydrantMapApplication.serviceProvider.getService(IHydrantDataSource.class);
        List<Hydrant> hydrantList = dataSource.getHydrantData();
        selectedHydrant = hydrantList.get(i);
        LatLng llA;
        if (i == -1)
            llA = new LatLng(40.816383, 114.896904);
        else
            llA = new LatLng(selectedHydrant.getLatitude(), selectedHydrant.getLongitude());

        MapStatus st = new MapStatus.Builder().target(llA).zoom(18).build();
        MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(st);
        mBaiduMap.animateMapStatus(u);

        MarkerOptions mo = new MarkerOptions().position(llA).icon(iconRedMarker).zIndex(9).draggable(false);
        marker = (Marker) (mBaiduMap.addOverlay(mo));

        etHydrantName.setText(selectedHydrant.getName());
        tvHydrantState.setText(selectedHydrant.getWorkingState());
        tvHydrantPressure.setText(Double.toString(selectedHydrant.getWaterPressure()));
        etHydrantUnit.setText(selectedHydrant.getUnit());
        etHydrantDistrict.setText(selectedHydrant.getDistrict());
        etHydrantLocDesc.setText(selectedHydrant.getLocationDesc());
        waterPressure = selectedHydrant.getWaterPressure();
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
