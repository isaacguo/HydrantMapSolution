package com.isaac.hydrantmap.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.isaac.hydrantmap.R;
import com.isaac.hydrantmap.application.HydrantMapApplication;
import com.isaac.hydrantmap.core.AppServiceProvider;
import com.isaac.hydrantmap.core.interfaces.IHydrantDataSource;
import com.isaac.hydrantmap.core.interfaces.ISqlliteDatabaseManagerNewHydrantAddedListener;
import com.isaac.hydrantmap.manager.SqlliteDatabaseManager;
import com.isaac.hydrantmap.manager.UnitsDatabaseManager;
import com.isaac.hydrantmap.utils.FileHelper;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SettingActivity extends AppCompatActivity implements ISqlliteDatabaseManagerNewHydrantAddedListener
{
    Button btnImport;
    Button btnExport;
    Button btnTest;
    TextView tvStatus;
    private AppServiceProvider serviceProvider;


    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    switch (which)
                    {
                        case DialogInterface.BUTTON_POSITIVE:
                            SqlliteDatabaseManager m= serviceProvider.getService(SqlliteDatabaseManager.class);
                            m.addSqlliteDatabaseManagerNewHydrantAddedListener(SettingActivity.this);
                            m.downloadHydrantData();

                            UnitsDatabaseManager m2=serviceProvider.getService(UnitsDatabaseManager.class);
                            m2.downloadUnitData();
                            //Intent returnIntent = new Intent();
                            //returnIntent.putExtra("result",result);
                            //setResult(Activity.RESULT_OK,returnIntent);
                            //finish();
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
        setContentView(R.layout.activity_setting);

        this.serviceProvider = HydrantMapApplication.serviceProvider;

        btnImport = (Button) findViewById(R.id.importFromWeb);
        btnExport = (Button) findViewById(R.id.exportToLocal);
        tvStatus = (TextView) findViewById(R.id.databaseStatus);


        btnImport.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(btnImport.getContext());
                builder.setMessage("从网站下载原始数据会清空当前数据库中已有内容，确认要下载？")
                        .setPositiveButton("是的", dialogClickListener)
                        .setNegativeButton("算了", dialogClickListener).show();
            }
        });
        btnExport.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               IHydrantDataSource dataSource= serviceProvider.getService(IHydrantDataSource.class);
                FileHelper helper=new FileHelper();
                helper.saveData(dataSource,"Hydrants-"+
                        new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Calendar.getInstance().getTime())+".xml");
            }
        });
    }

    int newAddedCount=0;
    @Override
    public void onNewHydrantAdded()
    {
        tvStatus.setText(Integer.toString(++newAddedCount) + "消防栓信息添加至数据库");
    }
}
