package com.isaac.hydrantmap.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.isaac.hydrantmap.R;
import com.isaac.hydrantmap.application.HydrantMapApplication;
import com.isaac.hydrantmap.core.interfaces.IHydrantDataSource;
import com.isaac.hydrantmap.core.interfaces.IUnitDataSource;
import com.isaac.hydrantmap.model.Hydrant;
import com.isaac.hydrantmap.model.UnitEntity;

import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.List;

public class UnitIntroActivity extends AppCompatActivity
{

    int index;
    TextView tvName;
    TextView tvAddress;
    TextView tvCharacters;
    TextView tvPerson;
    TextView tvMobileNumber;
    TextView tvDescription;
    TextView tvFirehouse;
    ImageView ivThumb;
    TextView tvBack;

    UnitEntity selectedUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_intro);

        tvName = (TextView) findViewById(R.id.unit_item_detail_name);
        tvAddress = (TextView) findViewById(R.id.unit_item_detail_address);
        tvCharacters = (TextView) findViewById(R.id.unit_item_detail_firecharacters);
        tvPerson = (TextView) findViewById(R.id.unit_item_detail_personincharge);
        tvMobileNumber = (TextView) findViewById(R.id.unit_item_detail_mobilenumber);
        tvDescription = (TextView) findViewById(R.id.unit_item_detail_description);
        tvFirehouse = (TextView) findViewById(R.id.unit_item_detail_firehouse);
        ivThumb = (ImageView) findViewById(R.id.unit_item_detail_thumb);
        tvBack=(TextView)findViewById(R.id.unit_back_TextView);
        tvBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        Intent intent = getIntent();
        index = intent.getIntExtra("selectedPosition", -1);
        setCenter(index);
    }

    private void setCenter(int i)
    {
        IUnitDataSource dataSource = HydrantMapApplication.serviceProvider.getService(IUnitDataSource.class);
        List<UnitEntity> units = dataSource.getUnitData();
        selectedUnit = units.get(i);
        LatLng llA;

        tvName.setText(selectedUnit.getUnitName());
        tvAddress.setText(selectedUnit.getAddress());
        tvCharacters.setText(selectedUnit.getFireCharacters());
        tvPerson.setText(selectedUnit.getPersonInCharge());
        tvMobileNumber.setText(selectedUnit.getMobileNumber());
        tvDescription.setText(selectedUnit.getDescription());
        tvFirehouse.setText(selectedUnit.getFirehouse());

        if (StringUtils.hasText(selectedUnit.getThumbPath()))
            new DownloadImageTask(ivThumb).execute(selectedUnit.getThumbPath());
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
    {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage)
        {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls)
        {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try
            {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e)
            {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result)
        {
            bmImage.setImageBitmap(result);
        }
    }
}
