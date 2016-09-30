package com.isaac.hydrantmap.datasource;

import android.util.Xml;

import com.isaac.hydrantmap.core.interfaces.IHydrantDataSource;
import com.isaac.hydrantmap.core.interfaces.IUnitDataSource;
import com.isaac.hydrantmap.model.Hydrant;
import com.isaac.hydrantmap.model.UnitEntity;
import com.isaac.hydrantmap.utils.FileHelper;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yueguo01 on 3/12/2016.
 */
public class UnitXmlDataSource implements IUnitDataSource
{
    private static final String ns = null;

    public List<UnitEntity> GetUnitData(InputStream is)
    {
        try
        {
            return parse(is);
        }
        catch (Exception e)
        {
            return new ArrayList<UnitEntity>();
        }
    }

    @Override
    public List<UnitEntity> getUnitData()
    {
        FileHelper fh = new FileHelper();
        try
        {
            FileInputStream fis = fh.readData();
            return parse(fis);
        } catch (Exception e)
        {
            return new ArrayList<UnitEntity>();
        }
    }

    private List<UnitEntity> parse(InputStream in) throws XmlPullParserException, IOException
    {
        try
        {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readUnits(parser);
        } finally
        {
            in.close();
        }
    }

    private List<UnitEntity> readUnits(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        List units = new ArrayList();
        parser.require(XmlPullParser.START_TAG, ns, "Units");
        while (parser.next() != XmlPullParser.END_TAG)
        {
            if (parser.getEventType() != XmlPullParser.START_TAG)
                continue;

            String name = parser.getName();
            if (name.equals("Unit"))
                units.add(readUnit(parser));
            else
                skip(parser);
        }
        return units;
    }

    private UnitEntity readUnit(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        parser.require(XmlPullParser.START_TAG, ns, "Unit");
        String unitName = null;
        String address= null;
        String fireCharacters= null;
        String personInCharge= null;
        String mobileNumber= null;
        String thumbPicturePath= null;
        String planDiagramPath= null;
        String description= null;
        String totalArea=null;
        String buildingHeight=null;
        String longitude=null;
        String latitude=null;
        String firehouse=null;


        unitName = parser.getAttributeValue(ns, "UnitName");
        address = parser.getAttributeValue(ns, "Address");
        fireCharacters = parser.getAttributeValue(ns, "FireCharacters");
        personInCharge = parser.getAttributeValue(ns, "PersonInCharge");
        mobileNumber = parser.getAttributeValue(ns, "MobileNumber");
        thumbPicturePath = parser.getAttributeValue(ns, "ThumbPicturePath");
        planDiagramPath = parser.getAttributeValue(ns, "PlanDiagramPath");
        description = parser.getAttributeValue(ns, "Description");
        totalArea = parser.getAttributeValue(ns, "TotalArea");
        buildingHeight = parser.getAttributeValue(ns, "BuildingHeight");
        longitude = parser.getAttributeValue(ns, "Longitude");
        latitude = parser.getAttributeValue(ns, "Latitude");
        firehouse=parser.getAttributeValue(ns, "Firehouse");
        parser.nextTag();
        return new UnitEntity(true,description,Double.parseDouble(totalArea),buildingHeight,0,0,"",unitName,address,"",mobileNumber,fireCharacters,personInCharge,thumbPicturePath,planDiagramPath,firehouse, Double.parseDouble(longitude),Double.parseDouble(latitude));
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
