package com.isaac.hydrantmap.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.isaac.hydrantmap.R;
import com.isaac.hydrantmap.model.HydrantEntity;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class TestActivity extends AppCompatActivity
{
    private static final String TAG = "TestActivity";
    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnTest=(Button)findViewById(R.id.btnTest);

        btnTest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new HttpRequestTask().execute();

            }
        });
    }


    private class HttpRequestTask extends AsyncTask<Void, Void,HydrantEntity[]>
    {
        @Override
        protected HydrantEntity[] doInBackground(Void... params) {
            try {
                final String url = "http://192.168.31.233:8080/hydrants";

                RestTemplate restTemplate = new RestTemplate();
                //restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                //HydrantEntity = restTemplate.getForObject(url, Greeting.class);
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<HydrantEntity[]> responseEntity = restTemplate.getForEntity(url,HydrantEntity[].class);

                return responseEntity.getBody();
            } catch (Exception e) {
                Log.e("TestActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(HydrantEntity[] hydrants) {
            Log.i(TAG,String.valueOf( hydrants.length));
            Log.i(TAG,hydrants[0].toString());
            Log.i(TAG,hydrants[1].toString());

        }
    }

}
