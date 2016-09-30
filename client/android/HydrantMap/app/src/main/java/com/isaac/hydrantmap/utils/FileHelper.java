package com.isaac.hydrantmap.utils;

import android.os.Environment;
import android.util.Xml;

import com.isaac.hydrantmap.core.interfaces.IHydrantDataSource;
import com.isaac.hydrantmap.model.Hydrant;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by yueguo01 on 3/12/2016.
 */
public class FileHelper
{
    public static String HydrantFolder = "com.isaac.hydrant";
    public static String HydrantXmlFile = "Hydrants.xml";

    public boolean isExternalStorageWritable()
    {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
        {
            return true;
        }

        return false;
    }

    public static void createHydrantFolder()
    {
        File folder = getHydrantFolder();
        if (!folder.exists())
            folder.mkdirs();
    }

    public static File getHydrantFolder()
    {
        File sdcard = Environment.getExternalStorageDirectory();
        File hydrantFolder = new File(sdcard, HydrantFolder);
        return hydrantFolder;
    }

    public static File getHydrantFile(String fileName)
    {
        File hydrantData = new File(getHydrantFolder(), fileName);
        return hydrantData;
    }


    public static File getHydrantFile()
    {
        File hydrantData = new File(getHydrantFolder(), HydrantXmlFile);
        return hydrantData;
    }

    public boolean saveData(IHydrantDataSource dataSource, String fileName)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(getHydrantFile(fileName));
            List<Hydrant> hydrants = dataSource.getHydrantData();
            StringWriter writer = new StringWriter();
            writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n") ;
            writer.write("<Hydrants>\r\n");
            for (Hydrant hydrant : hydrants)
            {
                writer.write(hydrant.toString()+ "\r\n");
            }
            writer.write("</Hydrants>");

            String dataWrite = writer.toString();
            fos.write(dataWrite.getBytes());
            fos.close();
            return true;
        } catch (FileNotFoundException e)
        {
            return false;
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public FileInputStream readData() throws Exception
    {
        if (!isExternalStorageWritable())
            throw new Exception("External Storage is not ready");
        File hydrantData = new File(getHydrantFolder(), HydrantXmlFile);
        if (!hydrantData.exists())
        {
            throw new Exception("File not existed");
        }
        FileInputStream fis = new FileInputStream(hydrantData);
        return fis;
    }
}
