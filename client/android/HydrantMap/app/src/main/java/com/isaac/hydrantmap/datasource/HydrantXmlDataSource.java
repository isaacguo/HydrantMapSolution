package com.isaac.hydrantmap.datasource;

import android.animation.FloatArrayEvaluator;
import android.util.Xml;

import com.isaac.hydrantmap.core.interfaces.IHydrantDataSource;
import com.isaac.hydrantmap.model.Hydrant;
import com.isaac.hydrantmap.utils.FileHelper;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yueguo01 on 3/12/2016.
 */
public class HydrantXmlDataSource implements IHydrantDataSource
{
    private static final String ns = null;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public List<Hydrant> GetHydrantData(InputStream is)
    {
        try
        {
            return parse(is);
        }
        catch (Exception e)
        {
            return new ArrayList<Hydrant>();
        }
    }

    @Override
    public List<Hydrant> getHydrantData()
    {
        FileHelper fh = new FileHelper();
        try
        {
            FileInputStream fis = fh.readData();
            return parse(fis);
        } catch (Exception e)
        {
            return new ArrayList<Hydrant>();
        }
    }

    private List<Hydrant> parse(InputStream in) throws XmlPullParserException, IOException
    {
        try
        {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readHydrants(parser);
        } finally
        {
            in.close();
        }
    }

    private List<Hydrant> readHydrants(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        List hydrants = new ArrayList();
        parser.require(XmlPullParser.START_TAG, ns, "Hydrants");
        while (parser.next() != XmlPullParser.END_TAG)
        {
            if (parser.getEventType() != XmlPullParser.START_TAG)
                continue;

            String name = parser.getName();
            if (name.equals("Hydrant"))
                hydrants.add(readHydrant(parser));
            else
                skip(parser);
        }
        return hydrants;
    }

    private Hydrant readHydrant(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        parser.require(XmlPullParser.START_TAG, ns, "Hydrant");
        String HydrantID = null;
        String Unit = null;
        String District = null;
        String Status = null;
        String Pressure = null;
        String Longitude = null;
        String Latitude = null;
        String LocDesc = null;

        HydrantID = parser.getAttributeValue(ns, "HydrantID");
        Unit = parser.getAttributeValue(ns, "Unit");
        District = parser.getAttributeValue(ns, "District");
        Status = parser.getAttributeValue(ns, "Status");
        Pressure = parser.getAttributeValue(ns, "Pressure");
        Longitude = parser.getAttributeValue(ns, "Longitude");
        Latitude = parser.getAttributeValue(ns, "Latitude");
        LocDesc = parser.getAttributeValue(ns, "LocDesc");
        parser.nextTag();
        try
        {
            return new Hydrant(0, HydrantID, "", Unit, District, simpleDateFormat.parse("1980-01-01"), "", "", Status, Double.parseDouble(Pressure), Double.parseDouble(Longitude), Double.parseDouble(Latitude), "bd0911", LocDesc);
        } catch (ParseException e)
        {
            return new Hydrant();
        }
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        if (parser.getEventType() != XmlPullParser.START_TAG)
        {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0)
        {
            switch (parser.next())
            {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
