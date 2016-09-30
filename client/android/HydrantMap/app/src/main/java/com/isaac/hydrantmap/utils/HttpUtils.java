package com.isaac.hydrantmap.utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;

import com.isaac.hydrantmap.core.interfaces.IHttpRequestAsyncTaskCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yueguo01 on 7/30/2015.
 */
public class HttpUtils
{
    public static String TAG="HttpUtils";
    public static String qualifyURL(String url)
    {
        if(!url.startsWith("http"))
        {
            url="http://"+url;
        }
        return url;
    }

    public static String readStream(InputStream stream)
    {
        BufferedReader reader=new BufferedReader(new InputStreamReader(stream));
        StringBuilder out=new StringBuilder();
        String line;
        try {
            while((line=reader.readLine())!=null)
                out.append(line);
            reader.close();
        } catch (IOException e) {
            return "";
        }

        return out.toString();

    }

    public static String requestString(String urlString, String requestMethod, int connectTimeout, int readTimeout)
    {
        try
        {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(readTimeout);
            conn.setConnectTimeout(connectTimeout);
            conn.setRequestMethod(requestMethod);
            conn.setDoInput(true);
            conn.connect();
            InputStream stream = conn.getInputStream();
            String response = readStream(stream);
            //LogUtils.logInfo(TAG," inRequestString(), responseString:"+response);
            return response;
        } catch (Exception e) {
            String error=e.getMessage();
            Log.d(TAG,error);
        }
        return "";
    }

    public static String getTimeStamp() {
        Time now = new Time();
        now.setToNow();
        String sTime = now.format("%Y_%m_%d_%H_%M_%S");
        return sTime;
    }

    public static class HttpRequestAsyncTask extends AsyncTask<String, Void,String>
    {
        private static String TAG=HttpRequestAsyncTask.class.getSimpleName();
        IHttpRequestAsyncTaskCallback callback;
        ProgressDialog dialog;

        String urlString;
        String requestMethod="GET";
        int connectTimeout=15000;
        int readTimeout=10000;

        private String actionType;
        private int actionId;

        public void setUrlString(String urlString) {
            this.urlString = urlString;
        }
        public void setRequestMethod(String requestMethod) {
            this.requestMethod = requestMethod;
        }
        public void setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
        }
        public void setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
        }
        public int getReadTimeout() {
            return readTimeout;
        }
        public int getConnectTimeout() {
            return connectTimeout;
        }
        public String getRequestMethod() {
            return requestMethod;
        }
        public String getUrlString() {
            return urlString;
        }

        public HttpRequestAsyncTask(String urlString, IHttpRequestAsyncTaskCallback callback, String actionType, int actionId)
        {
            this.urlString=urlString;
            this.callback=callback;
            this.actionType=actionType;
            this.actionId=actionId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //LogUtils.logInfo(TAG, "onPreExecute()");
            //dialog = ProgressDialog.show(context, "Loading", "Please wait...", true);
        }

        @Override
        protected String doInBackground(String... params) {
            //LogUtils.logInfo(TAG, "doInBackground()");
           return requestString(urlString, requestMethod, connectTimeout, readTimeout);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(callback!=null)
                callback.onResult(actionType,actionId, result);
            //dialog.dismiss();
        }
    }
}
