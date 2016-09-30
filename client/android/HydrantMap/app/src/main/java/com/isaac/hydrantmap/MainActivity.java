package com.isaac.hydrantmap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.isaac.hydrantmap.activities.HydrantDetailActivity;
import com.isaac.hydrantmap.activities.HydrantMapActivity;
import com.isaac.hydrantmap.activities.SettingActivity;
import com.isaac.hydrantmap.application.HydrantMapApplication;
import com.isaac.hydrantmap.core.adapters.HydrantAdapter;
import com.isaac.hydrantmap.core.interfaces.IHydrantDataSource;
import com.isaac.hydrantmap.core.interfaces.IUnitDataSource;
import com.isaac.hydrantmap.datasource.SQLliteDataSource;
import com.isaac.hydrantmap.datasource.UnitSQLliteDataSource;
import com.isaac.hydrantmap.model.Hydrant;
import com.isaac.hydrantmap.model.UnitEntity;
import com.isaac.hydrantmap.utils.FileHelper;


import java.util.List;

public class MainActivity extends AppCompatActivity
{
    ListView listHydrant;
    TextView tvGotoMap;
    TextView tvSetting;
    MainActivity instance = this;
    SQLliteDataSource hydrantDataSourceArray = null;
    UnitSQLliteDataSource unitDataSourceArray=null;
    HydrantAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FileHelper.createHydrantFolder();
        //IHydrantDataSource hydrantDataSourceArray=new DummyHydrantDataSource();
        //IHydrantDataSource hydrantDataSourceArray=new HydrantXmlDataSource();
        hydrantDataSourceArray = new SQLliteDataSource();
        hydrantDataSourceArray.initialize(HydrantMapApplication.serviceProvider, this);

        listHydrant = (ListView) findViewById(R.id.hydrantList);
        listHydrant.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id)
            {
                adapter.toggleIsEditable();
                adapter.notifyDataSetChanged();
                listHydrant.setAdapter(adapter);
                return true;
            }
        });
        HydrantMapApplication.serviceProvider.setService(IHydrantDataSource.class, hydrantDataSourceArray);
        List<Hydrant> dataSource = hydrantDataSourceArray.getHydrantData();

        updateView(this, dataSource);


        unitDataSourceArray =new UnitSQLliteDataSource();
        unitDataSourceArray.initialize(HydrantMapApplication.serviceProvider,this);
        HydrantMapApplication.serviceProvider.setService(IUnitDataSource.class, unitDataSourceArray);
        List<UnitEntity> unitsDataSource=unitDataSourceArray.getUnitData();


        tvGotoMap = (TextView) findViewById(R.id.navigation_right_TextView);
        tvGotoMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(instance, HydrantMapActivity.class);
                startActivity(intent);
            }
        });
        tvSetting = (TextView) findViewById(R.id.navigation_right2_TextView);
        tvSetting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(instance, SettingActivity.class);
                //startActivity(intent);
                startActivityForResult(intent,1);
            }
        });


    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        List<Hydrant> dataSource = hydrantDataSourceArray.getHydrantData();

        updateView(this, dataSource);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                //adapter.notifyDataSetChanged();

            }
            if (resultCode == Activity.RESULT_CANCELED)
            {
                //Write your code if there's no result
            }
        }
    }

    private void updateView(Context context, List<Hydrant> result)
    {
        if (result != null)
        {
            adapter = new HydrantAdapter(context, result);
            listHydrant.setAdapter(adapter);
        }
        listHydrant.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = null;
                if (adapter.isEditable())
                {
                    intent = new Intent(instance, HydrantDetailActivity.class);
                    intent.putExtra("selectedPosition", i);
                    startActivityForResult(intent, 1);

                } else
                {
                    intent = new Intent(instance, HydrantMapActivity.class);
                    intent.putExtra("selectedPosition", i);
                    startActivity(intent);
                }
            }
        });
        Toast.makeText(context, "共有记录" + result.size() + "条", Toast.LENGTH_LONG).show();
    }
}
